package lesson7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {

    Server server = null;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    private String nickname;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    // цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth")) {
                            String[] token = str.split("\\s");
                            String newNick = server.getAuthService().getNicknameByLoginAndPassword(token[1], token[2]);

                            if (newNick != null) {
                                nickname = newNick;
                                sendMsg("/authok " + nickname);
                                server.subscribe(this);
                                System.out.println("Клиент " + nickname + " подключился");
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }

                    // цикл работы

                    while (true) {
                        String str = in.readUTF();
                        System.out.println("the message is: " + str);
                        String[] array = str.split("->");
                        if (array[1].equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        }
                        String[] s;
                        if (!array[0].equals("")) {
                            s = array[0].split(" ");
                            server.broadCastMsg(this, s, array[1]);
                        } else {
                            server.broadCastMsgToAll(this, array[1]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }
}

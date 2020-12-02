package lesson8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {
    Server server = null;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    private String nickname;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            socket.setSoTimeout(5000);
            new Thread(()-> {
                    try {
                        // цикл аутентификации
                        while (true){
                            String str = in.readUTF();
                            if (str.startsWith("/auth")){
                                String[] token = str.split("\\s");
                                String newNick = server.getAuthService().getNicknameByLoginAndPassword(token[1], token[2]);
                                login = token[1];
                                if (newNick != null){
                                    if(!server.isLoginAuthenticated(token[1])) {
                                        nickname = newNick;
                                        sendMsg("/authok " + nickname);
                                        socket.setSoTimeout(0);
                                        server.subscribe(this);
                                        System.out.println("Клиент " + nickname + " подключился");
                                        break;
                                    }else{
                                        sendMsg("С данной учетной записью уже зашли");
                                    }
                                }else {
                                    sendMsg("Неверный логин / пароль");
                                }
                            }

                            if (str.startsWith("/reg")){
                                String[] token = str.split("\\s");
                                if(token.length < 4){
                                    continue;
                                }
                                boolean isRegistration = server.getAuthService()
                                        .registration(token[1], token[2], token[3]);
                                if(isRegistration){
                                    sendMsg("/regok");
                                } else {
                                    sendMsg("/regno");
                                }
                            }
                        }

                        // цикл работы
                        while (true) {
                            String str = in.readUTF();

                            if(str.startsWith("/")) {

                                if (str.equals("/end")) {
                                    out.writeUTF("/end");
                                    break;
                                }

                                if (str.startsWith("/w")) {
                                    String[] token = str.split("\\s+", 3);
                                    if (token.length <3){
                                        continue;
                                    }
                                    server.privateCastMsg(this, token[1], token[2]);
                                }

                            }else {
                                server.broadCastMsg(this, str);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }finally {
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

    public String getNickname(){
        return nickname;
    }

    public String getLogin() {
        return login;
    }
}

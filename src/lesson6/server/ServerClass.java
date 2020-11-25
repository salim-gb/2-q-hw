package lesson6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerClass {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                new Thread(new MyServer(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyServer implements Runnable {
    Socket socket;

    public MyServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            new Thread(() -> {
                try {
                    while (true) {
                        out.writeUTF(scanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                String str = in.readUTF();
                if (str.equalsIgnoreCase("exit")) {
                    System.out.println("Клиент отключился!");
                    break;
                }
                System.out.println("Клиент: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

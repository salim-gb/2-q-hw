package lesson6.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientClass {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8189)) {
            Scanner scanner = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
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
                System.out.println("Сервер: " + str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

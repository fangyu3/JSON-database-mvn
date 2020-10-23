package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int PORT = 23456;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)
        ) {
            System.out.println("Server started!");
            try (
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                String msg = input.readUTF(); // reading a message
                System.out.println("Received: Give me a record # " + msg);
                output.writeUTF(msg);
                System.out.println("Sent: A record # " + msg + " was sent!");
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

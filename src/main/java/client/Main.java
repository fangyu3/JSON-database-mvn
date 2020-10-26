package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int PORT = 23456;
    private static final String SERVER_ADDRESS = "127.0.0.1";

    public static void main(String[] args) {

        try (Socket socket = new Socket(SERVER_ADDRESS,PORT);) {

            System.out.println("Client started!");

            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());)
            {
                ClientInput input = new ClientInput();
                JCommander.newBuilder()
                        .addObject(input)
                        .build()
                        .parse(args);

                String output = new Gson().toJson(input);
                outputStream.writeUTF(output);
                System.out.println("Sent: " + output);

                String response = inputStream.readUTF();
                System.out.println("Received: " +response);
            }
        }
        catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

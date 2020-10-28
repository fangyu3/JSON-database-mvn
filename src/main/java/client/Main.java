package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import utility.FileIOUtil;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int PORT = 23456;
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final String clientInputFilePath = "./src/main/java/client/data/";

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

                String output = input.getFileName() == null
                                ? new Gson().toJson(input)
                                : readFile(input.getFileName());

                outputStream.writeUTF(output);
                System.out.println("Sent: " + output);

                String response = inputStream.readUTF();
                System.out.println("Received: " + response);

            }
        }
        catch (UnknownHostException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String readFile(String fileName) throws FileNotFoundException,IOException {

        File inputFile = new File(clientInputFilePath+fileName);
        if (!inputFile.exists()) {
            inputFile.getParentFile().mkdirs();
            inputFile.createNewFile();
        }

        try(Scanner scanner = new Scanner(inputFile)) {
            if (scanner.hasNext())
                return scanner.nextLine();
        }

        return "";
    }
}

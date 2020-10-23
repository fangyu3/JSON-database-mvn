package server;

import com.beust.jcommander.JCommander;
import server.exceptions.InvalidCommandException;
import server.exceptions.InvalidIndexException;
import server.exceptions.ReadEmptyCellException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int PORT = 23456;
    private static boolean exit = false;
    private static final int dbSize = 1000;
    private static final String[] db = new String[dbSize];

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Server started!");
            initializeDB();

            // Listens for client sockets
            while (!exit) {

                List<String> inputs = new ArrayList<>();

                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream()))
                {
                    try {
                        int inputLengh = input.readInt();
                        for (int i = 0; i < inputLengh; i++)
                            inputs.add(input.readUTF());

                        ClientArgs clientArgs = new ClientArgs();
                        JCommander.newBuilder()
                                .addObject(clientArgs)
                                .build()
                                .parse(inputs.toArray(new String[0]));

                        if (!ClientArgsValidator.validateCommand(clientArgs.getTask()))
                            throw new InvalidCommandException("Error");

                        if (!ClientArgsValidator.validateIdx(clientArgs.getIndex()))
                            throw new InvalidIndexException("Error");

                        output.writeUTF(handleRequest(clientArgs.getTask(), clientArgs.getIndex(), clientArgs.getEntry()));
                    }
                    catch (ReadEmptyCellException e) {
                        System.out.println("Error: trying to read empty cell!");
                        output.writeUTF("ERROR");
                    }
                    catch (InvalidCommandException e) {
                        System.out.println("Error: invalid command!");
                        output.writeUTF("ERROR");
                    }
                    catch (InvalidIndexException e) {
                        System.out.println("Error: invalid index!");
                        output.writeUTF("ERROR");
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initializeDB() {
        for (int i=0; i<db.length; i++)
            db[i] = "";
    }

    public static int getDbSize() {
        return dbSize;
    }

    private static String handleRequest(String command, int idx, String newEntry) {

        String response = "";

        if(command.equals("exit")) {
            exit = true;
            response = "OK";
        }
        else if (command.equals("set")) {
            db[idx] = newEntry;
            response = "OK";
        }
        else if(command.equals("delete")) {
            db[idx] = "";
            response = "OK";
        }
        else if(command.equals("get")) {
            if(db[idx].equals(""))
                throw new ReadEmptyCellException("Error");
            else
                response = db[idx];
        }

        return response;
    }
}

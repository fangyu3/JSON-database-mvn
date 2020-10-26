package server;

import client.ClientInput;
import com.google.gson.Gson;
import server.exception.EmptyCellException;
import server.exception.InvalidCommandException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final int PORT = 23456;
    private static boolean exit = false;
    private static final int dbSize = 1000;
    private static final Map<String,String> db = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Server started!");

            // Listens for client sockets
            while (!exit) {
                try (Socket socket = server.accept();
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()))
                {
                    String clientInputStr = inputStream.readUTF();
                    ClientInput clientInput = new Gson().fromJson(clientInputStr, ClientInput.class);
                    ServerResponse serverResponse = handleRequest(clientInput);
                    outputStream.writeUTF(new Gson().toJson(serverResponse));
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getDbSize() {
        return dbSize;
    }

    private static ServerResponse handleRequest(ClientInput clientInput) {

        ServerResponse serverResponse = new ServerResponse();
        ClientInputValidator inputValidator = new ClientInputValidator();

        try {
            if (!inputValidator.validateCommand(clientInput))
                throw new InvalidCommandException("Error: invalid command!");

            String command = clientInput.getType();

            if (command.equals("exit")) {
                exit = true;
                serverResponse.setResponse("OK");
                return serverResponse;
            }

            String key = clientInput.getKey();

            if (command.equals("set")) {
                db.put(key,clientInput.getValue());
                serverResponse.setResponse("OK");
            }
            else if (command.equals("delete")) {
                if (db.get(key) == null)
                    throw new EmptyCellException("Error: trying to delete empty cell!");

                db.remove(key);
                serverResponse.setResponse("OK");
            }
            else if (command.equals("get")) {
                if (db.get(key) == null)
                    throw new EmptyCellException("Error: trying to read empty cell!");

                serverResponse.setResponse("OK");
                serverResponse.setValue(db.get(key));
            }
        }
        catch (EmptyCellException e) {
            serverResponse.setResponse("ERROR");
            serverResponse.setReason("No such key");
        }
        catch (InvalidCommandException e) {
            serverResponse.setResponse("ERROR");
            serverResponse.setReason(e.getMessage());
        }
        finally {
            return serverResponse;
        }
    }
}

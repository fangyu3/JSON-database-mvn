package server;

import client.ClientInput;
import com.google.gson.Gson;
import server.exception.EmptyCellException;
import server.exception.InvalidCommandException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread{

    private final Socket socket;
    private final Database db;

    public Session(Socket socketForClient, Database db) {
        socket = socketForClient;
        this.db = db;
    }

    public void run() {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
        ) {
            String clientInputStr = inputStream.readUTF();
            ClientInput clientInput = new Gson().fromJson(clientInputStr, ClientInput.class);

            ServerResponse serverResponse = handleRequest(clientInput);
            outputStream.writeUTF(new Gson().toJson(serverResponse));
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerResponse handleRequest(ClientInput clientInput) {

        ServerResponse serverResponse = new ServerResponse();
        ClientInputValidator inputValidator = new ClientInputValidator();

        try {
            if (!inputValidator.validateCommand(clientInput))
                throw new InvalidCommandException("Error: invalid command!");

            String command = clientInput.getType();
            String key = clientInput.getKey();

            switch(command) {
                case "exit":
                    Main.exitProgram();
                    serverResponse.setResponse("OK");
                    break;
                case "set":
                    db.updateRecord(key,clientInput.getValue());
                    serverResponse.setResponse("OK");
                    break;
                case "delete":
                    db.deleteRecord(key);
                    serverResponse.setResponse("OK");
                    break;
                case "get":
                    serverResponse.setResponse("OK");
                    serverResponse.setValue(db.getRecord(key));
                    break;
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

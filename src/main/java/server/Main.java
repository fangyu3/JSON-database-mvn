package server;

import client.ClientInput;
import server.exception.EmptyCellException;
import server.exception.InvalidCommandException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import utility.FileIOUtil;

public class Main {

    private static final int PORT = 23456;
    private static boolean exit = false;
    private static Database db;
    private static ServerSocket server;


    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            // Load database
            db = new Database();
            System.out.println("Server started!");

            // Listens for client sockets
            while (!exit) {
                Session session = new Session(server.accept(),db);
                session.start();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exitProgram() throws IOException{
        exit = true;
        server.close();
    }
}

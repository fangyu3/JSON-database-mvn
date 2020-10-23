package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int PORT = 23456;
    private static final String SERVER_ADDRESS = "127.0.0.1";

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(SERVER_ADDRESS,PORT);
        ) {
            System.out.println("Client started!");
            try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//                Scanner scanner = new Scanner(System.in);
            ) {
//                int recordNum = scanner.nextInt();
                int recordNum = 12;
                output.writeUTF(recordNum+"");
                System.out.println("Sent: Give me a record # " + recordNum);
                String msg = input.readUTF();
                System.out.println("Received: A record # " + msg + " was sent!");
            }

        }
        catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        String[] db = args;
//
//        // Initialize array
//        for (int i=0; i<db.length; i++) {
//            db[i] = "";
//        }
//
//        Scanner scanner = new Scanner(System.in);
//
//        while(true) {
//            String command = scanner.next().toLowerCase();
//
//            if(command.equals("exit"))
//                break;
//
//            // Valid index 1-100
//            int idx = scanner.nextInt()-1;
//
//            if (idx<0 || idx>99) {
//                System.out.println("ERROR");
//                continue;
//            }
//
//            if(command.equals("set")) {
//                String newEntry = scanner.nextLine().trim();
//                db[idx] = newEntry;
//                System.out.println("OK");
//            }
//            else if(command.equals("get")) {
//
//                if(db[idx].equals("")) {
//                    System.out.println("ERROR");
//                    continue;
//                }
//                System.out.println(db[idx]);
//            }
//            else if(command.equals("delete")) {
//                db[idx] = "";
//                System.out.println("OK");
//            }
//        }
//        scanner.nextLine();
    }
}

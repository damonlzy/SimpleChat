package org.example;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    private static int port = 50000;

    public static void main(String[] args){

        System.out.println("Starting Server...");

        try{
            ServerSocket ss = new ServerSocket(port);

            while(true){
                Socket socket = ss.accept();

                System.out.println("New Client request received");

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                ClientHandler ch = new ClientHandler(socket,"User", dis, dos);
                Thread thread = new Thread(ch);

                System.out.println("Starting new client handler thread...");

                thread.start();
            }

        }catch (IOException e) {
            System.out.println("Start Server failed: " + e.getStackTrace());
        }
    }
}

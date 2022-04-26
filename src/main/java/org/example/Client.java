package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static int serverPort = 50000;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        try{
            Socket socket = new Socket(InetAddress.getByName("localhost"), serverPort);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(socket.isConnected()){
                        String message = sc.nextLine();
                        try {
                            dos.writeUTF(message);

                        } catch (IOException e) {
                            System.out.println("Send message failed: " + e.getStackTrace());
                        }
                    }
                }
            });

            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(socket.isConnected()){
                        try {
                            String message = dis.readUTF();
                            System.out.println(message);
                        } catch (IOException e) {
                            System.out.println("Read message failed: " + e.getStackTrace());
                        }

                    }
                }
            });

            sendMessage.start();
            readMessage.start();

        } catch(IOException e){
            System.out.println("Failed to connect to server: " + e.getStackTrace());
        }

    }
}

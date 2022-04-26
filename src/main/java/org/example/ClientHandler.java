package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    Socket socket;
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;


    public ClientHandler(Socket socket, String name, DataInputStream dis, DataOutputStream dos){
        this.socket = socket;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String mesReceived;
        while(true){
            try{
                mesReceived = dis.readUTF();

//                System.out.println("Message received");

                if(mesReceived.equals("logout")){
                    this.socket.close();
                    break;
                }

                System.out.println(mesReceived);


            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }

        try{
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}

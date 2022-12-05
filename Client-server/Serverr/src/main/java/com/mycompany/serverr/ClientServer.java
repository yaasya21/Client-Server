/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serverr;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author galex
 */
public class ClientServer implements Runnable{
     Map<Integer,Socket> mapClient = new TreeMap <Integer,Socket>();
    @Override
    public void run(){
        try {
            ServerSocket server = new ServerSocket(2003);
            System.out.println("Сервер працює. Чекаємо клієнтів");
            int numberClient = 1;
            Socket client = null;
            while (true) {
                client = server.accept();
                Thread clientThread = new Thread((Runnable) new ClientThread(client,this,numberClient));
                clientThread.setDaemon(true);
                clientThread.start();
                mapClient.put(numberClient, client);
                numberClient++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendMessageForAllClient(int numberClient, String clientMessage) throws IOException {
        for (Integer i : mapClient.keySet()) {
            if (i != numberClient) {
                System.out.println("Клієнт #" + i + " отримав повідомлення");

                BufferedWriter outputUser = null;

                try {
                    outputUser = new BufferedWriter(new OutputStreamWriter(mapClient.get(i).getOutputStream()));
                    outputUser.write(String.format("Клієнт #" + numberClient + " Відправив вам повідомлення:" + clientMessage + "\n"));
                    outputUser.flush();
                } catch (IOException e) {
                    try {
                        outputUser.close();
                    } catch (IOException ex) {
                        throw new IOException(ex);
                    }
                    mapClient.remove(numberClient);
                    String error = "Клієнт #" + i + " вже відключився";
                    throw new RuntimeException(error);
                }
            }
        }
    }
}

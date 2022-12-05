/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serverr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author galex
 */
public class ClientThread implements Runnable{
    Socket clientSocket;
    ClientServer clientServer;
    int numberClient;
    
    public ClientThread(Socket clientSocket, ClientServer clientServer, int numberClient) {
        this.clientSocket = clientSocket;
        this.clientServer = clientServer;
        this.numberClient = numberClient;
    }
    
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Клієнт №" + numberClient + " підключений");
            new PrintWriter(clientSocket.getOutputStream(), true).println("Клієнт№" + numberClient);
            String clientMessage = null;
            while(true) {
                clientMessage = in.readLine();
                if(!"exit".equals(clientMessage)) {
                    System.out.println("Клієнт №" + numberClient + ": " + clientMessage);
                    clientServer.sendMessageForAllClient(numberClient, clientMessage);
                }
                else {
                    in.close();
                    clientSocket.close();
                    clientServer.mapClient.remove(numberClient);
                    System.out.println("Клієнт №" + numberClient + " відключений");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

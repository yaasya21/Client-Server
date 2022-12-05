/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author galex
 */
public class connectInputMessage implements Runnable {
    private Socket serverConnect;
    private InputStream inputStreamServer;

    public connectInputMessage() {
        try {
            serverConnect = new Socket("localhost", 2003);
            inputStreamServer = serverConnect.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Сервер виключений");
        }
    }

    public InputStream getInputStreamServer() {
        return inputStreamServer;
    }

    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStreamServer));
        String serverMessage;

        while (true) {
            try {
                serverMessage = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (serverMessage != null) {
                System.out.println(serverMessage);
                break;
            }
        }

        PrintWriter out = null;
        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
        String userMessage = null;

        while(true) {
            System.out.println("Введіть повідомлення або exit для виходу:");
            try {
                userMessage = inputUser.readLine();
                out = new PrintWriter(serverConnect.getOutputStream(), true);
                out.println(userMessage);
                if("exit".equals(userMessage)) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
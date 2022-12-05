/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author galex
 */
public class receiveMessageFromServer implements Runnable {
    private InputStream inputStreamServer;

    receiveMessageFromServer(InputStream inputStream) {
        inputStreamServer = inputStream;
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
                System.out.println("Введіть повідомлення або exit для виходу:");
            }
        }
    }
}
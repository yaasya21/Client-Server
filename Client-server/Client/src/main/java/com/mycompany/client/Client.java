/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.client;

/**
 *
 * @author galex
 */
public class Client {
    public static void main(String[] args) {
        connectInputMessage connectWithServer = new connectInputMessage();

        Thread tConnectInputMessage = new Thread((Runnable) connectWithServer);
        tConnectInputMessage.start();

        receiveMessageFromServer receiveMessage = new receiveMessageFromServer(connectWithServer.getInputStreamServer());

        Thread tReceiveMessage = new Thread(receiveMessage);
        tReceiveMessage.start();
    }
}

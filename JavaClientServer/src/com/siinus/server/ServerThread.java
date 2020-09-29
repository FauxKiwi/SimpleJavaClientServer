package com.siinus.server;

import com.siinus.shared.ExceptionHandler;

import java.io.*;
import java.net.ServerSocket;

public class ServerThread extends Thread {
    private final JavaServer server;
    private final ServerSocket serverSocket;
    static ExceptionHandler exceptionHandler = null;
    static ServerProtocol protocol = null;

    ServerThread(JavaServer server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            listenSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenSocket() throws IOException {
        while (true) {
            ServerChannel channel = new ServerChannel(server);
            CommunicationThread communication = new CommunicationThread(channel, serverSocket.accept());
            server.getChannels().add(channel.setThread(communication));
            communication.start();
        }
    }

    public void stopServer() throws IOException {
        System.out.println("Stopping server ...");
        for (ServerChannel c : server.getChannels()) {
            c.thread.out.println("The server has shut down!");
            c.thread.stopThread();
        }
        System.out.println("Stopped server");
    }

    public JavaServer getServer() {
        return server;
    }
}

package com.siinus.client;

import java.io.IOException;
import java.net.Socket;

public class JavaClient {
    private Socket socket;
    private ClientThread thread;

    public static void setProtocol(ClientProtocol protocol) {
        ClientThread.protocol = protocol;
    }

    public void connect(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        thread = new ClientThread(socket);
        thread.start();
    }

    public void disconnect() {
        thread.out.println("\0disconnect");
    }

    public void send(String message) {
        thread.out.println(message);
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientThread getThread() {
        return thread;
    }

    public double getPing() {
        return thread.timeP;
    }
}

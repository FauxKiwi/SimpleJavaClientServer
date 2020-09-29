package com.siinus.server;

import com.siinus.shared.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class JavaServer {
    private ServerThread thread;
    private final LinkedList<ServerChannel> channels = new LinkedList<>();

    public static void setProtocol(ServerProtocol protocol) {
        ServerThread.protocol = protocol;
    }

    public static void setExceptionHandler(ExceptionHandler handler) {
        ServerThread.exceptionHandler = handler;
    }

    public void start(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        thread = new ServerThread(this, socket);
        thread.start();
    }

    public void stop() throws IOException {
        System.out.println("Shutting down ...");
        thread.stopServer();
        System.exit(0);
    }

    public void send(@NotNull ServerChannel channel, String message) {
        channel.thread.out.println(message);
    }

    public void broadcast(String message) {
        for (ServerChannel c : channels) {
            c.thread.out.println(message);
        }
    }

    public LinkedList<ServerChannel> getChannels() {
        return channels;
    }
}

package com.siinus.server;

import com.siinus.shared.StandardExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static com.siinus.server.ServerThread.protocol;
import static com.siinus.server.ServerThread.exceptionHandler;

public class CommunicationThread extends Thread {
    private final ServerChannel channel;
    private final Socket socket;
    PrintWriter out;

    private BufferedReader in;

    CommunicationThread(ServerChannel channel, @NotNull Socket socket) {
        this.channel = channel;
        this.socket = socket;
        this.setName(socket.getInetAddress().getHostAddress());
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            listenSocket();
        } catch (SocketException e) {
            if (e.getMessage().equals("Connection reset")) {
                System.out.println("Client forced disconnect");
                channel.server.getChannels().remove(channel);
            }
            exceptionHandler.handle(e);
        } catch (Throwable cause) {
            exceptionHandler.handle(cause);
        }
    }

    public void listenSocket() throws Throwable {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine, outputLine;
        if (protocol == null) {
            protocol = new EchoProtocol();
        }
        if (exceptionHandler == null) {
            exceptionHandler = new StandardExceptionHandler();
        }

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equals("\0disconnect")) {
                out.println(inputLine);
                channel.server.getChannels().remove(channel);
                break;
            }
            if (inputLine.startsWith("\0ping")) {
                out.println(inputLine);
                continue;
            }
            outputLine = protocol.processInput(channel, inputLine);
            out.println(outputLine);
            System.out.println(outputLine);
        }
        stopThread();
    }

    public void stopThread() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}

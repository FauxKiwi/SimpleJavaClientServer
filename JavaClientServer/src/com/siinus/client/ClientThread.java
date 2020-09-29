package com.siinus.client;


import com.siinus.shared.ExceptionHandler;
import com.siinus.shared.StandardExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket socket;
    PrintWriter out;

    static ClientProtocol protocol = null;
    static ExceptionHandler exceptionHandler = null;

    double timeP;

    ClientThread(@NotNull Socket socket) {
        this.socket = socket;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenSocket() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String inputLine, outputLine;
        if (protocol == null) {
            protocol = new EchoProtocol();
        }
        if (exceptionHandler == null) {
            exceptionHandler = new StandardExceptionHandler();
        }

        int ping = 0;
        double time1;
        double time2;
        int unreceivedPings = 0;

        out.println("\0ping 0");

        time2 = System.nanoTime() / 1000000000.0;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("\0ping")) {
                if (!inputLine.split(" ")[1].equals(ping+"")) {
                    System.out.println("Wrong ping received!");
                    unreceivedPings++;
                    if (unreceivedPings>=6) {
                        out.println("\0disconnect");
                        System.out.println("6 pings without response have been sent.");
                    }
                    continue;
                }
                if (unreceivedPings>0) {
                    unreceivedPings--;
                }
                time1 = System.nanoTime() / 1000000000.0;
                timeP = time1 - time2;
                time2 = time1;
                if (ping<Integer.MAX_VALUE) {
                    ping++;
                } else {
                    ping = Integer.MIN_VALUE;
                }
                out.println("\0ping "+(ping));
                continue;
            }
            outputLine = protocol.processInput(inputLine);
            System.out.println(outputLine);
            if (outputLine.endsWith("disconnect")) {
                break;
            }
        }
    }
}

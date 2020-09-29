package com.siinus.server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JavaServer server = new JavaServer();
        server.start(25565);

        //new TestThread(server).start();
    }
}

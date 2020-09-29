package com.siinus.server;

import java.io.IOException;

public class TestThread extends Thread {
    private JavaServer server;
    private int s = 0;

    public TestThread(JavaServer s) {
        server = s;
    }

    @Override
    public void run() {
        while (true) {
            server.broadcast("Hey! "+s);
            System.out.println(server.getChannels().size());
            try {
                sleep(1000);
                s++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

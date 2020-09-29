package com.siinus.client;

public class TestThread extends Thread {
    private JavaClient client;

    public TestThread(JavaClient c) {
        client = c;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Ping: "+((int) (client.getPing() * 1000))+" ms");
            client.send("---");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

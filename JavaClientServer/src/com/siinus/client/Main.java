package com.siinus.client;

import java.io.IOException;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        JavaClient client = new JavaClient();
        client.connect("84.149.155.13", 25565);

        System.out.println("--- Start ---");
        new TestThread(client).start();
    }
}
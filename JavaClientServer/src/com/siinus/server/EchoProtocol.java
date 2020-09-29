package com.siinus.server;

import org.jetbrains.annotations.NotNull;

public class EchoProtocol implements ServerProtocol{

    @Override
    public String processInput(@NotNull ServerChannel channel, String inputLine) {
        return channel.thread.getName() +" "+ inputLine;
    }
}

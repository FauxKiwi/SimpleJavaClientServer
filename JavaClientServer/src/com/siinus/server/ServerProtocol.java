package com.siinus.server;

public interface ServerProtocol {

    String processInput(ServerChannel channel, String inputLine);
}

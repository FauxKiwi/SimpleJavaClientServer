package com.siinus.client;

import com.siinus.client.ClientProtocol;

public class EchoProtocol implements ClientProtocol {

    @Override
    public String processInput(String inputLine) {
        return inputLine;
    }

}

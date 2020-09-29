package com.siinus.server;

public class ServerChannel {
    JavaServer server;
    CommunicationThread thread;

    ServerChannel(JavaServer s) {
        server = s;
    }

    ServerChannel setThread(CommunicationThread thread) {
        this.thread = thread;
        return this;
    }

    public String getName() {
        return thread.getName();
    }
}

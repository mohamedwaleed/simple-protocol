package my.simple.protocol;

import my.simple.protocol.server.SimpleProtocolServer;

public class Main {
    public static void main(String[] args) {
        SimpleProtocolServer simpleProtocolServer = new SimpleProtocolServer();
        simpleProtocolServer.start(50000);
    }
}

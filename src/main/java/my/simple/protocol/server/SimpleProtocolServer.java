package my.simple.protocol.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import my.simple.protocol.client.ClientProcess;
import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.server.session.Session;
import my.simple.protocol.server.session.UUIDSessionIdGenerator;
import org.apache.log4j.Logger;

public class SimpleProtocolServer {

    final static Logger logger = Logger.getLogger(SimpleProtocolServer.class);

    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                Session session = Session.createNewSession(new UUIDSessionIdGenerator(), new Date(), 30000);
                Thread thread = new Thread(new ClientProcess(socket, session));
                thread.start();
                logger.info("new client connect with session id" + session.getSessionId());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

package my.simple.protocol.client;

import my.simple.protocol.server.session.Session;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientProcess implements Runnable {
    final static Logger logger = Logger.getLogger(ClientProcess.class);
    private IClientHandler clientHandler;
    private Socket clientSocket;

    public ClientProcess(Socket socket, Session session) throws IOException {
        this.clientSocket = socket;
        this.clientSocket.setSoTimeout(session.getTimeout());
        this.clientHandler = new SimpleProtocolClientHandler(
                socket.getInputStream(),
                socket.getOutputStream(),
                session
        );
    }

    @Override
    public void run() {
        try {
            clientHandler.handle();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            try {
                clientSocket.close();
                clientHandler.closeConnection();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

}

package my.simple.protocol.client;

import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.parser.IParser;
import my.simple.protocol.parser.SimpleProtocolCommandParser;
import my.simple.protocol.commands.ByeCommand;
import my.simple.protocol.commands.Command;
import my.simple.protocol.parser.exceptions.UnknownCommandException;
import my.simple.protocol.server.session.Session;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class SimpleProtocolClientHandler implements IClientHandler<DirectedGraph> {

    final static Logger logger = Logger.getLogger(SimpleProtocolClientHandler.class);

    private IParser simpleProtocolCommandParser;
    private BufferedWriter output;
    private BufferedReader input;
    private Session session;
    private String clientName;
    private DirectedGraph directedGraph;

    public SimpleProtocolClientHandler(InputStream inputStream, OutputStream outputStream, Session session, DirectedGraph directedGraph) throws IOException {
        output = new BufferedWriter(
                new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
        );

        input = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        );
        this.session = session;
        this.simpleProtocolCommandParser =  new SimpleProtocolCommandParser(this);
        this.directedGraph = directedGraph;
    }

    @Override
    public void handle() throws IOException {
            String sessionId = session.getSessionId();
            this.sendMessage(Message.hi(sessionId));

            while (true) {
                try {
                    String receivedMessage = this.receiveMessage();
                    Command command = simpleProtocolCommandParser.parse(receivedMessage);
                    command.execute();
                    logger.info("Received message " + receivedMessage + " from client " + clientName);
                    if(command instanceof ByeCommand) {
                        break;
                    }
                } catch (UnknownCommandException unknownCommandException) {
                    this.sendMessage(Message.sorry());
                } catch (SocketTimeoutException socketTimeoutException) {
                    endSession();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
    }

    @Override
    public void endSession() throws IOException {
        long duration = session.duration(new Date());
        sendMessage(Message.byeBye(clientName, duration));
        closeConnection();
    }

    @Override
    public void welcomeClient() throws IOException {
        this.sendMessage(Message.welcome(clientName));
    }

    @Override
    public void sendMessage(String message) throws IOException {
        output.write(message);
        output.newLine();
        output.flush();
    }

    @Override
    public String receiveMessage() throws IOException {
         return input.readLine();
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void closeConnection() throws IOException {
        input.close();
        output.close();
    }

    @Override
    public DirectedGraph getSharedObject() {
        return directedGraph;
    }
}

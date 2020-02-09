package my.simple.protocol.client;

import java.io.IOException;

public interface IClientHandler {
    void handle() throws IOException;
    void sendMessage(String message) throws IOException;
    void setClientName(String clientName);
    void endSession() throws IOException;
    void welcomeClient() throws IOException;
    String receiveMessage() throws IOException;
    void closeConnection() throws IOException;
}

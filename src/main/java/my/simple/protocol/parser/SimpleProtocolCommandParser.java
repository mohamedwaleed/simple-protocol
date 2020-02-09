package my.simple.protocol.parser;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.commands.*;

public class SimpleProtocolCommandParser implements IParser {

    private IClientHandler clientHandler;

    public SimpleProtocolCommandParser(IClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public Command parse(String command) {
        String []tokens = command.split(" ");
        System.out.println(command);
        String firstToken = tokens[0];
        String secondToken = null;
        switch (firstToken) {
            case "HI,":
                String clientName = tokens[3];
                return new GreetingCommand(clientHandler, clientName);
            case "BYE":
                return new ByeCommand(clientHandler);
            case "ADD":
                secondToken = tokens[1];
                if(secondToken.equals("NODE")) {
                    String node = tokens[2];
                    return new AddNodeCommand(clientHandler, node);
                } else if(secondToken.equals("EDGE")) {
                    String nodeX = tokens[2];
                    String nodeY = tokens[3];
                    int weight = Integer.valueOf(tokens[4]);
                    return new AddEdgeCommand(clientHandler, nodeX, nodeY, weight);
                }
            case "REMOVE":
                secondToken = tokens[1];
                if(secondToken.equals("NODE")) {
                    String node = tokens[2];
                    return new RemoveNodeCommand(clientHandler, node);
                } else if(secondToken.equals("EDGE")) {
                    String nodeX = tokens[2];
                    String nodeY = tokens[3];
                    return new RemoveEdgeCommand(clientHandler, nodeX, nodeY);
                }

        }
        return new UnknownCommand();
    }
}

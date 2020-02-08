package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;

public class AddNodeCommand implements Command {

    private DirectedGraph directedGraph = DirectedGraph.getInstance();
    private IClientHandler clientHandler;
    private String node;

    public AddNodeCommand(IClientHandler clientHandler, String node) {
        this.node = node;
        this.clientHandler = clientHandler;
    }

    @Override
    public void execute() throws Exception {
        boolean isAdded = directedGraph.addNode(node);
        if(isAdded) {
            this.clientHandler.sendMessage(Message.nodeAdded());
        } else {
            this.clientHandler.sendMessage(Message.nodeExists());
        }
    }

    public String getNode() {
        return node;
    }
}

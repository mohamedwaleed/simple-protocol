package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeAlreadyExistsException;

public class AddNodeCommand implements Command {

    private DirectedGraph directedGraph;
    private IClientHandler clientHandler;
    private String node;

    public AddNodeCommand(IClientHandler<DirectedGraph> clientHandler, String node) {
        this.node = node;
        this.clientHandler = clientHandler;
        this.directedGraph = clientHandler.getSharedObject();
    }

    @Override
    public void execute() throws Exception {
        try {
            directedGraph.addNode(node);
            this.clientHandler.sendMessage(Message.nodeAdded());
        } catch (NodeAlreadyExistsException e) {
            this.clientHandler.sendMessage(Message.nodeExists());
        }
    }

    public String getNode() {
        return node;
    }
}

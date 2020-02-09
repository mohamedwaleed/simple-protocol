package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

public class RemoveNodeCommand implements Command {

    private DirectedGraph directedGraph;
    private IClientHandler clientHandler;
    private String node;

    public RemoveNodeCommand(IClientHandler<DirectedGraph> clientHandler, String node) {
        this.node = node;
        this.clientHandler = clientHandler;
        this.directedGraph = clientHandler.getSharedObject();
    }

    @Override
    public void execute() throws Exception {
        try {
            directedGraph.removeNode(node);
            this.clientHandler.sendMessage(Message.nodeRemoved());
        } catch (NodeNotFoundException e) {
            this.clientHandler.sendMessage(Message.nodeNotFound());
        }
    }

    public String getNode() {
        return node;
    }
}

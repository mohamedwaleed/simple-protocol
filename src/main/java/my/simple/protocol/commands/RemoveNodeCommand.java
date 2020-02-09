package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;

public class RemoveNodeCommand implements Command {

    private DirectedGraph directedGraph = DirectedGraph.getInstance();
    private IClientHandler clientHandler;
    private String node;

    public RemoveNodeCommand(IClientHandler clientHandler, String node) {
        this.node = node;
        this.clientHandler = clientHandler;
    }

    @Override
    public void execute() throws Exception {
        boolean isRemoved = directedGraph.removeNode(node);
        if(isRemoved) {
            this.clientHandler.sendMessage(Message.nodeRemoved());
        } else {
            this.clientHandler.sendMessage(Message.nodeNotFound());
        }
    }

    public String getNode() {
        return node;
    }
}

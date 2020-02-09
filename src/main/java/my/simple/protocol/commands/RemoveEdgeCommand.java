package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

public class RemoveEdgeCommand implements Command {

    private DirectedGraph directedGraph;
    private IClientHandler clientHandler;
    private String nodeX;
    private String nodeY;

    public RemoveEdgeCommand(IClientHandler<DirectedGraph> clientHandler, String nodeX, String nodeY) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.clientHandler = clientHandler;
        this.directedGraph = clientHandler.getSharedObject();
    }

    @Override
    public void execute() throws Exception {
        try {
            directedGraph.removeEdge(nodeX, nodeY);
            this.clientHandler.sendMessage(Message.edgeRemoved());
        } catch (NodeNotFoundException e) {
            this.clientHandler.sendMessage(Message.nodeNotFound());
        }
    }

    public String getNodeY() {
        return nodeY;
    }

    public String getNodeX() {
        return nodeX;
    }
}

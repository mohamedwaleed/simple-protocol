package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;

public class RemoveEdgeCommand implements Command {

    private DirectedGraph directedGraph = DirectedGraph.getInstance();
    private IClientHandler clientHandler;
    private String nodeX;
    private String nodeY;

    public RemoveEdgeCommand(IClientHandler clientHandler, String nodeX, String nodeY) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.clientHandler = clientHandler;
    }

    @Override
    public void execute() throws Exception {
        boolean isRemoved = directedGraph.removeEdge(nodeX, nodeY);
        if(isRemoved) {
            this.clientHandler.sendMessage(Message.edgeRemoved());
        } else {
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

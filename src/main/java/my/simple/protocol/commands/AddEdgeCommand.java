package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

public class AddEdgeCommand implements Command {

    private DirectedGraph directedGraph;
    private IClientHandler clientHandler;
    private String nodeX;
    private String nodeY;
    private int weight;

    public AddEdgeCommand(IClientHandler<DirectedGraph> clientHandler, String nodeX, String nodeY, int weight) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.weight = weight;
        this.clientHandler = clientHandler;
        this.directedGraph = clientHandler.getSharedObject();
    }

    @Override
    public void execute() throws Exception {
        try {
            directedGraph.addEdge(nodeX, nodeY, weight);
            this.clientHandler.sendMessage(Message.edgeAdded());
        } catch (NodeNotFoundException e) {
            this.clientHandler.sendMessage(Message.nodeNotFound());
        }
    }

    public String getNodeX() {
        return nodeX;
    }

    public String getNodeY() {
        return nodeY;
    }

    public int getWeight() {
        return weight;
    }
}

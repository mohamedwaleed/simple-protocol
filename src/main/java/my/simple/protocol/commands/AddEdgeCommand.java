package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;

public class AddEdgeCommand implements Command {

    private DirectedGraph directedGraph = DirectedGraph.getInstance();
    private IClientHandler clientHandler;
    private String nodeX;
    private String nodeY;
    private int weight;

    public AddEdgeCommand(IClientHandler clientHandler, String nodeX, String nodeY, int weight) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.weight = weight;
        this.clientHandler = clientHandler;
    }

    @Override
    public void execute() throws Exception {
        boolean isAdded = directedGraph.addEdge(nodeX, nodeY, weight);
        if(isAdded) {
            this.clientHandler.sendMessage(Message.edgeAdded());
        } else {
            this.clientHandler.sendMessage(Message.edgeExists());
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

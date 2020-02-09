package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

public class ShortestPathCommand implements Command {
    private DirectedGraph directedGraph = DirectedGraph.getInstance();
    private IClientHandler clientHandler;
    private String nodeX;
    private String nodeY;

    public ShortestPathCommand(IClientHandler clientHandler, String nodeX, String nodeY) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.clientHandler = clientHandler;
    }

    @Override
    public void execute() throws Exception {
        try {
            long weight = directedGraph.shortestPath(nodeX, nodeY);
            this.clientHandler.sendMessage(Message.weight(weight));
        } catch (NodeNotFoundException nodeNotFoundException) {
            this.clientHandler.sendMessage(Message.nodeNotFound());
        }
    }

    public String getNodeX() {
        return nodeX;
    }

    public String getNodeY() {
        return nodeY;
    }
}

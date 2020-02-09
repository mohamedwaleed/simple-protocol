package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

import java.util.List;

public class CloserThanCommand implements Command {

    private IClientHandler clientHandler;
    private String node;
    private int weight;
    private DirectedGraph directedGraph;

    public CloserThanCommand(IClientHandler<DirectedGraph> clientHandler, String node, int weight) {
        this.clientHandler = clientHandler;
        this.node = node;
        this.weight = weight;
        this.directedGraph = clientHandler.getSharedObject();
    }

    @Override
    public void execute() throws Exception {
        try {
            List<String> nodes = directedGraph.closerThan(node, weight);
            this.clientHandler.sendMessage(Message.nodeList(nodes));
        } catch (NodeNotFoundException nodeNotFoundException) {
            this.clientHandler.sendMessage(Message.nodeNotFound());
        }
    }

    public String getNode() {
        return node;
    }

    public int getWeight() {
        return weight;
    }
}

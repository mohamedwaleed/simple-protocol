package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;
import my.simple.protocol.client.Message;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

import java.util.List;

public class CloserThanCommand implements Command {

    private DirectedGraph directedGraph = DirectedGraph.getInstance();
    private IClientHandler clientHandler;
    private String node;
    private int weight;

    public CloserThanCommand(IClientHandler clientHandler, String node, int weight) {
        this.clientHandler = clientHandler;
        this.node = node;
        this.weight = weight;
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
}

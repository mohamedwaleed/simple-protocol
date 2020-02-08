package my.simple.protocol.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DirectedGraph {

    private static DirectedGraph directedGraph;

    private Map<String, LinkedList<Pair<String, Integer>>> graph = new HashMap<>();
    private DirectedGraph() {}

    /**
        @param node the string represents the node
        @return
         false if the node already exists
         true if the node has been added successfully
     */
    public synchronized boolean addNode(String node) {
        if(graph.containsKey(node)) {
            return false;
        }
        graph.put(node, new LinkedList<>());
        return true;
    }

    /**
     @param nodeX the string represents the node x
     @param nodeY the string represents the node y
     @param weight represents the weight of the edge
     @return
     false if the one of the nodes does not exist
     true if the edge has been added successfully
     */
    public synchronized boolean addEdge(String nodeX, String nodeY, int weight) {
        if(!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
            return false;
        }
        graph.get(nodeX).add(new Pair<>(nodeY, weight));
        return true;
    }

    public static synchronized DirectedGraph getInstance() {
        if(directedGraph == null) {
            directedGraph = new DirectedGraph();
        }
        return directedGraph;
    }

}

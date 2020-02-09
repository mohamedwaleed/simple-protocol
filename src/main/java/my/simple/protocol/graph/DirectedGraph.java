package my.simple.protocol.graph;

import my.simple.protocol.graph.excpetions.NodeNotFoundException;

import java.util.*;

public class DirectedGraph {

    private static DirectedGraph directedGraph;

    // DirectedGraph is synchronized so no need to affect the performance and use ConcurrentHashMap
    private final Map<String, LinkedList<Pair<String, Integer>>> graph = new HashMap<>();

    private DirectedGraph() {}

    /**
        @param node the string represents the node
        @return
         false if the node already exists
         true if the node has been added successfully
     */
    public boolean addNode(String node) {
        synchronized (graph) {
            if (graph.containsKey(node)) {
                return false;
            }
            graph.put(node, new LinkedList<>());
            return true;
        }
    }

    /**
     @param nodeX the string represents the node x
     @param nodeY the string represents the node y
     @param weight represents the weight of the edge
     @return
     false if the one of the nodes does not exist
     true if the edge has been added successfully
     */
    public boolean addEdge(String nodeX, String nodeY, int weight) {
        synchronized (graph) {
            if (!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
                return false;
            }
            graph.get(nodeX).add(new Pair<>(nodeY, weight));
            return true;
        }
    }

    /**
     @param node the string represents the node
     @return
     false if the node not found
     true if the node has been removed successfully
     */
    public boolean removeNode(String node) {
        synchronized (graph) {
            if (!graph.containsKey(node)) {
                return false;
            }
            graph.remove(node);
            return true;
        }
    }

    /**
     @param nodeX the string represents the node x
     @param nodeY the string represents the node y
     @return
     false if the one of the nodes does not exist
     true if the edge has been removed successfully
     */
    public boolean removeEdge(String nodeX, String nodeY) {
        synchronized (graph) {
            if (!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
                return false;
            }
            LinkedList<Pair<String, Integer>> edges = graph.get(nodeX);
            edges.remove(new Pair<>(nodeY, 0));

            graph.put(nodeX, edges);
            return true;
        }
    }

    // added for the purpose of unit test
    public void clear() {
        graph.clear();
    }

    /**
     * Calculates the shortest path from node x to node y using Dijkstra algorithm
     @param nodeX the string represents the node x
     @param nodeY the string represents the node y
     @return
     sum of the weights of the shortest path
     Integer.MAX_VALUE if there is no path between node x and node y
     @throws NodeNotFoundException if one of the nodes is not found
     */
    public int shortestPath(String nodeX, String nodeY) throws NodeNotFoundException {
        Map<String, LinkedList<Pair<String, Integer>>> graphSnapshoot;
        synchronized (graph) {
            if(!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
                throw new NodeNotFoundException();
            }
            graphSnapshoot = new HashMap<>(graph);
        }
        Map<String, Integer> distance = new HashMap<>();


        graphSnapshoot.forEach((k, v) -> distance.put(k, Integer.MAX_VALUE));

        Queue<Pair<Integer, String>> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Pair<>(0, nodeX));
        distance.put(nodeX, 0);

        while(!priorityQueue.isEmpty()) {
            Pair<Integer, String> minNode = priorityQueue.poll();
            String currentNode = minNode.getSecond();
            Integer currentNodeDistance = distance.get(currentNode);

            if(currentNode.equals(nodeY)) {
                return distance.get(nodeY);
            }

            List<Pair<String, Integer>> edges = graphSnapshoot.get(currentNode);

            for(Pair<String, Integer> edge: edges) {
                String toNode = edge.getFirst();
                Integer edgeWeight = edge.getSecond();
                    Integer newDistance = currentNodeDistance + edgeWeight;
                    if (newDistance < distance.get(toNode)) {
                        distance.put(toNode, newDistance);
                        priorityQueue.add(new Pair<>(distance.get(toNode), toNode));
                    }
            }

        }


        return Integer.MAX_VALUE;
    }


    /**
     * Find all the nodes that are closer to node x than the given weight using Dijkstra algorithm
     @param node the string represents the node x
     List of found nodes, sorted alphabetically by name, not including the starting point
     @throws NodeNotFoundException if one of the nodes is not found
     */
    public List<String> closerThan(String node, int weight) throws NodeNotFoundException {
        Map<String, LinkedList<Pair<String, Integer>>> graphSnapshoot;
        synchronized (graph) {
            if(!graph.containsKey(node)) {
                throw new NodeNotFoundException();
            }
            graphSnapshoot = new HashMap<>(graph);
        }
        Map<String, Integer> distance = new HashMap<>();


        graphSnapshoot.forEach((k, v) -> distance.put(k, Integer.MAX_VALUE));

        Queue<Pair<Integer, String>> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Pair<>(0, node));
        distance.put(node, 0);

        while(!priorityQueue.isEmpty()) {
            Pair<Integer, String> minNode = priorityQueue.poll();
            String currentNode = minNode.getSecond();
            Integer currentNodeDistance = distance.get(currentNode);

            List<Pair<String, Integer>> edges = graphSnapshoot.get(currentNode);

            for(Pair<String, Integer> edge: edges) {
                String toNode = edge.getFirst();
                Integer edgeWeight = edge.getSecond();
                Integer newDistance = currentNodeDistance + edgeWeight;
                if (newDistance < distance.get(toNode)) {
                    distance.put(toNode, newDistance);
                    priorityQueue.add(new Pair<>(distance.get(toNode), toNode));
                }
            }

        }

        List<String> nodes = new ArrayList<>();
        distance.forEach((nodeName, shortestWeight) -> {
                if(!nodeName.equals(node) && shortestWeight < weight) {
                    nodes.add(nodeName);
                }
        });
        Collections.sort(nodes);
        return nodes;
    }

    public static synchronized DirectedGraph getInstance() {
        if(directedGraph == null) {
            directedGraph = new DirectedGraph();
        }
        return directedGraph;
    }
}

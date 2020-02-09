package my.simple.protocol.graph;

import my.simple.protocol.graph.excpetions.NodeAlreadyExistsException;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DirectedGraph {

    private static DirectedGraph directedGraph;

    private final Map<String, LinkedList<Pair<String, Integer>>> graph = new HashMap<>();

    private DirectedGraph() {}

    /**
        @param node the string represents the node
        @throws NodeAlreadyExistsException
     */
    public synchronized void addNode(String node) throws NodeAlreadyExistsException {
        synchronized (graph) {
            if (graph.containsKey(node)) {
                throw new NodeAlreadyExistsException();
            }
            graph.put(node, new LinkedList<>());
        }
    }

    /**
     @param nodeX the string represents the node x
     @param nodeY the string represents the node y
     @param weight represents the weight of the edge
     @throws NodeNotFoundException
     */
    public synchronized void addEdge(String nodeX, String nodeY, int weight) throws NodeNotFoundException {
        synchronized (graph) {
            if (!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
                throw new NodeNotFoundException();
            }
            graph.get(nodeX).add(new Pair<>(nodeY, weight));
        }
    }

    /**
     @param node the string represents the node
     @throws NodeNotFoundException
     */
    public synchronized void removeNode(String node) throws NodeNotFoundException {
        synchronized (graph) {
            if (!graph.containsKey(node)) {
                throw new NodeNotFoundException();
            }
            graph.remove(node);
        }
    }

    /**
     @param nodeX the string represents the node x
     @param nodeY the string represents the node y
     @return
     false if the one of the nodes does not exist
     true if the edge has been removed successfully
     */
    public synchronized void removeEdge(String nodeX, String nodeY) throws NodeNotFoundException {
        synchronized (graph) {
            if (!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
                throw new NodeNotFoundException();
            }
            LinkedList<Pair<String, Integer>> edges = graph.get(nodeX);
            edges.remove(new Pair<>(nodeY, 0));

            graph.put(nodeX, edges);
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
    public synchronized int shortestPath(String nodeX, String nodeY) throws NodeNotFoundException {
        Map<String, LinkedList<Pair<String, Integer>>> graphSnapshoot;
        synchronized (graph) {
            if (!graph.containsKey(nodeX) || !graph.containsKey(nodeY)) {
                throw new NodeNotFoundException();
            }
            graphSnapshoot = new HashMap<>(graph);

            Map<String, Integer> distance = new HashMap<>();


            graphSnapshoot.forEach((k, v) -> distance.put(k, Integer.MAX_VALUE));

            Queue<Pair<Integer, String>> priorityQueue = new PriorityQueue<>();
            priorityQueue.add(new Pair<>(0, nodeX));
            distance.put(nodeX, 0);

            while (!priorityQueue.isEmpty()) {
                Pair<Integer, String> minNode = priorityQueue.poll();
                String currentNode = minNode.getSecond();
                Integer currentNodeDistance = distance.get(currentNode);

                if (currentNode.equals(nodeY)) {
                    return distance.get(nodeY);
                }

                List<Pair<String, Integer>> edges = graphSnapshoot.get(currentNode);

                for (Pair<String, Integer> edge : edges) {
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
    }


    /**
     * Find all the nodes that are closer to node x than the given weight using Dijkstra algorithm
     @param node the string represents the node x
     List of found nodes, sorted alphabetically by name, not including the starting point
     @throws NodeNotFoundException if one of the nodes is not found
     */
    public synchronized List<String> closerThan(String node, int weight) throws NodeNotFoundException {
        synchronized (graph) {

            Map<String, LinkedList<Pair<String, Integer>>> graphSnapshoot;
            if (!graph.containsKey(node)) {
                throw new NodeNotFoundException();
            }
            graphSnapshoot = new HashMap<>(graph);
            Map<String, Integer> distance = new HashMap<>();


            graphSnapshoot.forEach((k, v) -> distance.put(k, Integer.MAX_VALUE));

            Queue<Pair<Integer, String>> priorityQueue = new PriorityQueue<>();
            priorityQueue.add(new Pair<>(0, node));
            distance.put(node, 0);

            while (!priorityQueue.isEmpty()) {
                Pair<Integer, String> minNode = priorityQueue.poll();
                String currentNode = minNode.getSecond();
                Integer currentNodeDistance = distance.get(currentNode);

                List<Pair<String, Integer>> edges = graphSnapshoot.get(currentNode);

                for (Pair<String, Integer> edge : edges) {
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
                if (!nodeName.equals(node) && shortestWeight < weight) {
                    nodes.add(nodeName);
                }
            });
            Collections.sort(nodes);
            return nodes;
        }
    }

    public static synchronized DirectedGraph getInstance() {
        if(directedGraph == null) {
            directedGraph = new DirectedGraph();
        }
        return directedGraph;
    }
}

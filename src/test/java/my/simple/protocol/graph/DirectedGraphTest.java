package my.simple.protocol.graph;

import my.simple.protocol.graph.excpetions.NodeNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DirectedGraphTest {

    private DirectedGraph directedGraph = DirectedGraph.getInstance();

    @Before
    public void before() {
        directedGraph.clear();
    }

    @Test
    public void testShortestPath() throws NodeNotFoundException {
        directedGraph.addNode("A");
        directedGraph.addNode("B");
        directedGraph.addNode("C");
        directedGraph.addNode("D");
        directedGraph.addNode("E");
        directedGraph.addNode("F");
        directedGraph.addNode("G");

        directedGraph.addEdge("A","B", 4);
        directedGraph.addEdge("A","C", 3);
        directedGraph.addEdge("A","E", 7);
        directedGraph.addEdge("B","C", 6);
        directedGraph.addEdge("B","D", 5);
        directedGraph.addEdge("C","D", 11);
        directedGraph.addEdge("C","E", 8);
        directedGraph.addEdge("E","D", 2);
        directedGraph.addEdge("E","G", 5);
        directedGraph.addEdge("D","F", 2);
        directedGraph.addEdge("D","G", 10);
        directedGraph.addEdge("G","F", 3);

        int actualWeight = directedGraph.shortestPath("A", "F");
        int expectedWeight = 11;
        Assert.assertEquals(expectedWeight, actualWeight);
    }

    @Test
    public void testCloserThan() throws NodeNotFoundException {
        directedGraph.addNode("A");
        directedGraph.addNode("B");
        directedGraph.addNode("C");
        directedGraph.addNode("D");
        directedGraph.addNode("E");
        directedGraph.addNode("F");
        directedGraph.addNode("G");

        directedGraph.addEdge("A","B", 4);
        directedGraph.addEdge("A","C", 3);
        directedGraph.addEdge("A","E", 7);
        directedGraph.addEdge("B","C", 6);
        directedGraph.addEdge("B","D", 5);
        directedGraph.addEdge("C","D", 11);
        directedGraph.addEdge("C","E", 8);
        directedGraph.addEdge("E","D", 2);
        directedGraph.addEdge("E","G", 5);
        directedGraph.addEdge("D","F", 2);
        directedGraph.addEdge("D","G", 10);
        directedGraph.addEdge("G","F", 3);

        List<String> actualNodeList = directedGraph.closerThan("A", 8);
        List<String> expectedNodeList = Arrays.asList("B", "C", "E");
        Assert.assertEquals(expectedNodeList, actualNodeList);
    }
}

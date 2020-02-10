package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class ShortestPathCommandTest {

    @Before
    public void beforeEach() {
        DirectedGraph.getInstance().clear();
    }

    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        String nodeX = "A";
        String nodeY = "B";

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);
        Mockito.when(directedGraph.shortestPath(nodeX, nodeY)).thenReturn(10);


        ShortestPathCommand shortestPathCommand = new ShortestPathCommand(simpleProtocolClientHandler, nodeX, nodeY);
        shortestPathCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.weight(10));
        Mockito.verify(directedGraph, Mockito.times(1)).shortestPath(nodeX, nodeY);
    }

    @Test
    public void testExecuteWhenThrowsException() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        String nodeX = "A";
        String nodeY = "B";


        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);
        Mockito.when(directedGraph.shortestPath(nodeX, nodeY)).thenThrow(new NodeNotFoundException());

        ShortestPathCommand shortestPathCommand = new ShortestPathCommand(simpleProtocolClientHandler, nodeX, nodeY);
        shortestPathCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeNotFound());
        Mockito.verify(directedGraph, Mockito.times(1)).shortestPath(nodeX, nodeY);
    }
}

package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AddEdgeCommandTest {
    @Before
    public void beforeEach() {
        DirectedGraph.getInstance().clear();
    }

    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);

        String nodeX = "A";
        String nodeY = "B";
        int weight = 1;

        AddEdgeCommand addEdgeCommand = new AddEdgeCommand(simpleProtocolClientHandler, nodeX, nodeY, weight);
        addEdgeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.edgeAdded());
        Mockito.verify(directedGraph, Mockito.times(1)).addEdge(nodeX, nodeY, weight);
    }

    @Test
    public void testExecuteWhenThrowsException() throws Exception {
        String nodeX = "A";
        String nodeY = "B";
        int weight = 1;

        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = DirectedGraph.getInstance();

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);

        AddEdgeCommand addEdgeCommand = new AddEdgeCommand(simpleProtocolClientHandler, nodeX, nodeY, weight);
        addEdgeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeNotFound());
    }
}

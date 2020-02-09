package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;
import org.junit.Test;
import org.mockito.Mockito;

public class AddEdgeCommandTest {
    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);

        String nodeX = "A";
        String nodeY = "B";
        int weight = 1;

        directedGraph.addNode(nodeX);
        directedGraph.addNode(nodeY);

        AddEdgeCommand byeCommand = new AddEdgeCommand(simpleProtocolClientHandler, nodeX, nodeY, weight);
        byeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.edgeAdded());
        Mockito.verify(directedGraph, Mockito.times(1)).addEdge(nodeX, nodeY, weight);
    }

    @Test
    public void testExecuteWhenThrowsException() throws Exception {
        String nodeX = "A";
        String nodeY = "B";
        int weight = 1;

        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);

        AddEdgeCommand byeCommand = new AddEdgeCommand(simpleProtocolClientHandler, nodeX, nodeY, weight);
        byeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeNotFound());
        Mockito.verify(directedGraph, Mockito.times(1)).addEdge(nodeX, nodeY, weight);
    }
}

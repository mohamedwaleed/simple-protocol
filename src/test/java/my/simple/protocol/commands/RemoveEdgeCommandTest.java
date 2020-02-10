package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RemoveEdgeCommandTest {

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

        RemoveEdgeCommand removeEdgeCommand = new RemoveEdgeCommand(simpleProtocolClientHandler, nodeX, nodeY);
        removeEdgeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.edgeRemoved());
        Mockito.verify(directedGraph, Mockito.times(1)).removeEdge(nodeX, nodeY);
    }

    @Test
    public void testExecuteWhenThrowsException() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = DirectedGraph.getInstance();

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);

        String nodeX = "A";
        String nodeY = "B";

        RemoveEdgeCommand removeEdgeCommand = new RemoveEdgeCommand(simpleProtocolClientHandler, nodeX, nodeY);
        removeEdgeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeNotFound());
    }
}

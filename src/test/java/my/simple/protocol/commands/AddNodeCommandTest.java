package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AddNodeCommandTest {

    @Before
    public void beforeEach() {
        DirectedGraph.getInstance().clear();
    }

    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);

        String node = "A";

        AddNodeCommand addNodeCommand = new AddNodeCommand(simpleProtocolClientHandler, node);
        addNodeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeAdded());
        Mockito.verify(directedGraph, Mockito.times(1)).addNode(node);
    }

    @Test
    public void testExecuteWhenThrowsException() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = DirectedGraph.getInstance();
        directedGraph.addNode("A");

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);
        String node = "A";

        AddNodeCommand addNodeCommand = new AddNodeCommand(simpleProtocolClientHandler, node);
        addNodeCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeExists());
    }
}

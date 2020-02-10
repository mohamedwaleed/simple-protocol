package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import my.simple.protocol.graph.excpetions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

public class CloserThanCommandTest {

    @Before
    public void beforeEach() {
        DirectedGraph.getInstance().clear();
    }

    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        String node = "A";
        int weight = 1;
        ArrayList<String> nodes = new ArrayList<>(Arrays.asList("A", "B", "C"));

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);
        Mockito.when(directedGraph.closerThan(node, weight)).thenReturn(nodes);


        CloserThanCommand closerThanCommand = new CloserThanCommand(simpleProtocolClientHandler, node, weight);
        closerThanCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeList(nodes));
        Mockito.verify(directedGraph, Mockito.times(1)).closerThan(node, weight);
    }

    @Test
    public void testExecuteWhenThrowsException() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        DirectedGraph directedGraph = Mockito.mock(DirectedGraph.class);

        String node = "A";
        int weight = 1;

        Mockito.when(simpleProtocolClientHandler.getSharedObject()).thenReturn(directedGraph);
        Mockito.when(directedGraph.closerThan(node, weight)).thenThrow(new NodeNotFoundException());


        CloserThanCommand closerThanCommand = new CloserThanCommand(simpleProtocolClientHandler, node, weight);
        closerThanCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).sendMessage(Message.nodeNotFound());
        Mockito.verify(directedGraph, Mockito.times(1)).closerThan(node, weight);
    }
}

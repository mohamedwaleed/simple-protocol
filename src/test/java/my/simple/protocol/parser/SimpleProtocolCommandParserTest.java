package my.simple.protocol.parser;

import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.commands.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class SimpleProtocolCommandParserTest {

    @Test
    public void testParseGreetingCommand() throws IOException {
        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(null);

        String stringCommand = "HI, I AM 7f9a3769-227b-48a6-a608-ca235edd09aa";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedClientName = "7f9a3769-227b-48a6-a608-ca235edd09aa";
        Assert.assertTrue(command instanceof GreetingCommand);
        Assert.assertEquals(((GreetingCommand) command).getClientName(), expectedClientName);
    }

    @Test
    public void testParseByeCommand() throws IOException {
        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(null);

        String stringCommand = "BYE MATE!";
        Command command = simpleProtocolCommandParser.parse(stringCommand);
        Assert.assertTrue(command instanceof ByeCommand);
    }

    @Test
    public void testParseAddNodeCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "ADD NODE Phase2-Node-0";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNode = "Phase2-Node-0";
        Assert.assertTrue(command instanceof AddNodeCommand);
        Assert.assertEquals(((AddNodeCommand) command).getNode(), expectedNode);
    }

    @Test
    public void testParseRemoveNodeCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "REMOVE NODE Phase2-Node-0";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNode = "Phase2-Node-0";
        Assert.assertTrue(command instanceof RemoveNodeCommand);
        Assert.assertEquals(((RemoveNodeCommand) command).getNode(), expectedNode);
    }

    @Test
    public void testParseAddEdgeCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "ADD EDGE Phase4-Node-354 Phase4-Node-531 8";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNodeX = "Phase4-Node-354";
        String expectedNodeY = "Phase4-Node-531";
        int expectedWeight = 8;
        Assert.assertTrue(command instanceof AddEdgeCommand);
        Assert.assertEquals(((AddEdgeCommand) command).getNodeX(), expectedNodeX);
        Assert.assertEquals(((AddEdgeCommand) command).getNodeY(), expectedNodeY);
        Assert.assertEquals(((AddEdgeCommand) command).getWeight(), expectedWeight);
    }

    @Test
    public void testParseRemoveEdgeCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "REMOVE EDGE Phase4-Node-354 Phase4-Node-531";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNodeX = "Phase4-Node-354";
        String expectedNodeY = "Phase4-Node-531";
        Assert.assertTrue(command instanceof RemoveEdgeCommand);
        Assert.assertEquals(((RemoveEdgeCommand) command).getNodeX(), expectedNodeX);
        Assert.assertEquals(((RemoveEdgeCommand) command).getNodeY(), expectedNodeY);
    }

    @Test
    public void testParseShortestPathCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "SHORTEST PATH Phase4-Node-354 Phase4-Node-531";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNodeX = "Phase4-Node-354";
        String expectedNodeY = "Phase4-Node-531";
        Assert.assertTrue(command instanceof ShortestPathCommand);
        Assert.assertEquals(((ShortestPathCommand) command).getNodeX(), expectedNodeX);
        Assert.assertEquals(((ShortestPathCommand) command).getNodeY(), expectedNodeY);
    }

    @Test
    public void testParseCloserThanCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "CLOSER THAN 8 Phase4-Node-354";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNode = "Phase4-Node-354";
        int expectedWeight = 8;
        Assert.assertTrue(command instanceof CloserThanCommand);
        Assert.assertEquals(((CloserThanCommand) command).getNode(), expectedNode);
        Assert.assertEquals(((CloserThanCommand) command).getWeight(), expectedWeight);
    }

    @Test
    public void testParseUnknownCommand() throws IOException {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(simpleProtocolClientHandler);

        String stringCommand = "UNKNOWN";
        Command command = simpleProtocolCommandParser.parse(stringCommand);
        Assert.assertTrue(command instanceof UnknownCommand);
    }
}

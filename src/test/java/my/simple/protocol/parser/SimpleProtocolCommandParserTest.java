package my.simple.protocol.parser;

import my.simple.protocol.commands.*;
import org.junit.Assert;
import org.junit.Test;

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
        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(null);

        String stringCommand = "ADD NODE Phase2-Node-0";
        Command command = simpleProtocolCommandParser.parse(stringCommand);

        String expectedNode = "Phase2-Node-0";
        Assert.assertTrue(command instanceof AddNodeCommand);
        Assert.assertEquals(((AddNodeCommand) command).getNode(), expectedNode);
    }

    @Test
    public void testParseAddEdgeCommand() throws IOException {
        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(null);

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
    public void testParseUnknownCommand() throws IOException {
        SimpleProtocolCommandParser simpleProtocolCommandParser =
                new SimpleProtocolCommandParser(null);

        String stringCommand = "UNKNOWN";
        Command command = simpleProtocolCommandParser.parse(stringCommand);
        Assert.assertTrue(command instanceof UnknownCommand);
    }
}

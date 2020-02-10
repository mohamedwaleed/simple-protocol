package my.simple.protocol.commands;

import my.simple.protocol.client.Message;
import my.simple.protocol.client.SimpleProtocolClientHandler;
import my.simple.protocol.graph.DirectedGraph;
import org.junit.Test;
import org.mockito.Mockito;

public class GreetingCommandTest {
    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);

        String clientName = "client_name";

        GreetingCommand greetingCommand = new GreetingCommand(simpleProtocolClientHandler, clientName);
        greetingCommand.execute();

        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).setClientName(clientName);
        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).welcomeClient();
    }
}

package my.simple.protocol.commands;

import my.simple.protocol.client.SimpleProtocolClientHandler;
import org.junit.Test;
import org.mockito.Mockito;

public class ByeCommandTest {
    @Test
    public void testExecute() throws Exception {
        SimpleProtocolClientHandler simpleProtocolClientHandler = Mockito.mock(SimpleProtocolClientHandler.class);
        ByeCommand byeCommand = new ByeCommand(simpleProtocolClientHandler);
        byeCommand.execute();
        Mockito.verify(simpleProtocolClientHandler, Mockito.times(1)).endSession();
    }
}

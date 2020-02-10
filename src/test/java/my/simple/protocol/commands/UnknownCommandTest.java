package my.simple.protocol.commands;

import my.simple.protocol.parser.exceptions.UnknownCommandException;
import org.junit.Test;

public class UnknownCommandTest {

    @Test(expected = UnknownCommandException.class)
    public void testExecute() throws UnknownCommandException {
        UnknownCommand unknownCommand = new UnknownCommand();
        unknownCommand.execute();
    }
}

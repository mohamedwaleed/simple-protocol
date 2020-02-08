package my.simple.protocol.commands;

import my.simple.protocol.parser.exceptions.UnknownCommandException;

public class UnknownCommand implements Command {

    @Override
    public void execute() throws UnknownCommandException {
        throw new UnknownCommandException();
    }
}

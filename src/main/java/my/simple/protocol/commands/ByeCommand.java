package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;

public class ByeCommand implements Command {

    private IClientHandler clientHandler;

    public ByeCommand(IClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
    @Override
    public void execute() throws Exception {
        clientHandler.endSession();
    }
}

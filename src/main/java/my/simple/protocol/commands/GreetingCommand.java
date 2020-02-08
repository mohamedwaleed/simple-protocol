package my.simple.protocol.commands;

import my.simple.protocol.client.IClientHandler;

public class GreetingCommand implements Command {

    private IClientHandler clientHandler;
    private String clientName;

    public GreetingCommand(IClientHandler clientHandler, String clientName) {
        this.clientHandler = clientHandler;
        this.clientName = clientName;
    }

    @Override
    public void execute() throws Exception {
        clientHandler.setClientName(clientName);
        clientHandler.welcomeClient();
    }

    public String getClientName() {
        return clientName;
    }
}

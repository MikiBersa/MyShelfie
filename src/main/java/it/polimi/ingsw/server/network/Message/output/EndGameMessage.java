package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

public class EndGameMessage extends MessageByServer {
    public EndGameMessage() {
        super();
    }

    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitEndGameMessage(this, controller);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

public class ResponseChatMessage extends MessageByServer {
    private final String message;
    private final String username;

    private final String destination;

    public ResponseChatMessage(String message, String username, String destination) {
        super();
        this.message = message;
        this.username = username;
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    // utile per la gui
    public String getDestination() {
        return destination;
    }

    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitResponseChatMessage(this, controller);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{username = " + username + "message = " + message + '}';
    }
}

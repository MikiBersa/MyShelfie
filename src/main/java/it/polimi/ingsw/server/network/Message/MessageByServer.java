package it.polimi.ingsw.server.network.Message;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;

import java.io.Serializable;

/**
 * Message created by the server to notify the client
 */
public abstract class MessageByServer implements Serializable {

    /**
     * Create a message of the server
     */
    public MessageByServer() {
    }

    /**
     * Visitor pattern method used to identify a message by the server
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    public abstract void accept(ClientVisitor clientVisitor, ClientController controller);
}

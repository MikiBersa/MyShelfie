package it.polimi.ingsw.server.network.Message;

import it.polimi.ingsw.server.ServerVisitor;

import java.io.Serializable;

/**
 * Super class of all the messages of the client
 */
public abstract class MessageByClient implements Serializable {
    /**
     * Create a message of the client
     */
    public MessageByClient() {
    }

    /**
     * Visitor pattern method used to identify a message by the client
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    public abstract void sendToRouter(ServerVisitor serverVisitor);

}

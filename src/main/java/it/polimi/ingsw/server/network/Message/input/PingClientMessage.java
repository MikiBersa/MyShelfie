package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Message to answer the ping message from the server
 */
public class PingClientMessage extends MessageByClient {

    /**
     * Method used to handle the PingClientMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.ping();
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Message used to handle in the server the timeout or terminate the client process
 */
public class DisconnectionMessage extends MessageByClient {

    /**
     * Create a DisconnectionMessage
     */
    public DisconnectionMessage() {
        super();
    }

    /**
     * Method used to handle the DisconnectionMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.disconnection();
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " disconnection";
    }

}

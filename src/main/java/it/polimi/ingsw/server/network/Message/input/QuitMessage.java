package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Message used by the client to disconnect from the game
 */
public class QuitMessage extends MessageByClient {

    /**
     * Create a QuitMessage
     */
    public QuitMessage() {
        super();
    }

    /**
     * Method used to handle the QuitMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.quitMessage();
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

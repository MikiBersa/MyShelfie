package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to notify if the number of players chosen by the user is correct
 */
public class ResponseNumberOfPlayerMessage extends MessageByServer {
    private final boolean status;

    /**
     * Create the ResponseNumberOfPlayerMessage
     *
     * @param status true if the number is correct
     */
    public ResponseNumberOfPlayerMessage(boolean status) {
        super();
        this.status = status;
    }

    /**
     * Method used to handle the ResponseNumberOfPlayer in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitResponseNumberOfPlayerMessage(this, controller);
    }

    /**
     * Get the status
     *
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{status = " + status + '}';
    }
}

package it.polimi.ingsw.server.network.Message;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;

/**
 * Message used by the client to notify itself that an error occurred
 */
public class ErrorMessage extends MessageByServer {
    private final String description;

    /**
     * Create an ErrorMessage
     *
     * @param description the description of the error
     */
    public ErrorMessage(String description) {
        this.description = description;
    }

    /**
     * Method used to handle the ErrorMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        controller.handleErrorMessage(this);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + description;
    }

    /**
     * Get the description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}

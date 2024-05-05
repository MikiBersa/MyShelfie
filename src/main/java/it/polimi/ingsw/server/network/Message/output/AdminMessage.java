package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to notify a client that the player is the admin and has to choose the number of players of the game
 */
public class AdminMessage extends MessageByServer {
    private final String message;
    private final boolean status;

    /**
     * Create an AdminMessage with a message and a status true if the player can choose the number of players of the game
     *
     * @param message a message that notify the player its privileges
     * @param status  true if the player can choose the number of players of the game
     */
    public AdminMessage(String message, boolean status) {
        super();
        this.message = message;
        this.status = status;
    }

    /**
     * Get the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the status
     *
     * @return get the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Method used to handle the AdminMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitAdminMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{message = " + message + ", status = " + status + '}';
    }
}

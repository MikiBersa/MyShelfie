package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to notify if the player is added to the game
 */
public class AddedInGameMessage extends MessageByServer {
    private final boolean status;


    /**
     * Create an AddedInGame message with a status
     *
     * @param status true if the player is added to the game
     */
    public AddedInGameMessage(boolean status) {
        super();
        this.status = status;
    }

    /**
     * Get the status of the message
     *
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Method used to handle the AddedInGameMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitAddedInGameMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{addedInGame = " + status + '}';
    }
}

package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to set the personal card of a player
 */
public class PersonalCardMessage extends MessageByServer {
    private final int idPersonalCard;

    /**
     * Create the personal card of a player
     *
     * @param idPersonalCard the id of the personal card
     */
    public PersonalCardMessage(int idPersonalCard) {
        super();
        this.idPersonalCard = idPersonalCard;
    }

    /**
     * Get the id of the personal card
     *
     * @return the id of the personal card
     */
    public int getIdPersonalCard() {
        return idPersonalCard;
    }

    /**
     * Method used to handle the PersonalCardMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitPersonalCardMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{idPersonalCard = " + idPersonalCard + '}';
    }
}

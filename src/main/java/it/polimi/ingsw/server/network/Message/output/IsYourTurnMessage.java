package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to notify the turn of some player
 */
public class IsYourTurnMessage extends MessageByServer {
    private final String username;
    private final int idPlayer;
    // IF STATUS TRUE -> IT'S YOUR TURN
    //
    // IF STATUS FALSE -> IT'S NOT YOUR TURN
    // -> DON'T APPLY THEN
    // -> ANOTHER'S TURN

    /**
     * Create a IsYourTurnMessage
     *
     * @param idPlayer the id of the player's turn
     * @param username the username of the player
     */
    public IsYourTurnMessage(int idPlayer, String username) {
        super();
        this.idPlayer = idPlayer;
        this.username = username;
    }

    /**
     * Get the id of the player
     *
     * @return the id of the player
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * Get the username of the player
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method used to handle the IsYourTurnMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitIsYourTurnMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{idPlayer = " + idPlayer + '}';
    }
}

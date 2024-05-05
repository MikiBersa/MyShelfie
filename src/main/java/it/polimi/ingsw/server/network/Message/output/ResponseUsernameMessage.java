package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to notify if the username selected by the player is correct
 */
public class ResponseUsernameMessage extends MessageByServer {
    private final String username;
    private final boolean status;
    private final int idPlayer;

    /**
     * Create a ResponseUsernameMessage
     *
     * @param idPlayer the id of the player
     * @param username the username selected
     * @param status   true if it is correct, false otherwise
     */
    public ResponseUsernameMessage(int idPlayer, String username, boolean status) {
        super();
        this.idPlayer = idPlayer;
        this.username = username;
        this.status = status;
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
     * @return the username
     */
    public String getUsername() {
        return username;
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
     * Method used to handle the ResponseUsernameMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitResponseUsernameMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{status = " + status + ", username = " + username + '}';
    }
}

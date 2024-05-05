package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to notify all the players that a client disconnected
 */
public class ResponseQuitMessage extends MessageByServer {
    private final String username;

    /**
     * Create a ResponseQuitMessage
     *
     * @param username the username of the player that disconnected
     */
    public ResponseQuitMessage(String username) {
        this.username = username;
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
     * Method used to handle the ResponseQuitMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitQuitMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return "user " + this.username + " has just disconnected";
    }
}

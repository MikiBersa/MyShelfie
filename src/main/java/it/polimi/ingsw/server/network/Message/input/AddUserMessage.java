package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Message used to add a player to the game
 */
public class AddUserMessage extends MessageByClient {
    private final String username;

    /**
     * Create the message with a specified username
     *
     * @param username username used by the player
     */
    public AddUserMessage(String username) {
        super();
        this.username = username;
    }

    /**
     * toString method
     *
     * @return the string of the message
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{username = " + username + '}';
    }

    /**
     * Method used to handle the AddUserMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.addUser(username);
    }
}

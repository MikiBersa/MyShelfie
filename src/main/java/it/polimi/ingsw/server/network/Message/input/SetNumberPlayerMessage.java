package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Message used to set the number of players in a game by the admin user
 */
public class SetNumberPlayerMessage extends MessageByClient {
    private final int numberPlayers;

    /**
     * Create a SetNumberPlayerMessage with the number of players of the game
     *
     * @param numberPlayers the number of players of the game
     */
    public SetNumberPlayerMessage(int numberPlayers) {
        super();
        this.numberPlayers = numberPlayers;
    }

    /**
     * Method used to handle the SetNumberPlayerMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.createGame(numberPlayers);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{numberPlayers = " + numberPlayers + '}';
    }
}

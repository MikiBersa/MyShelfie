package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

import java.util.List;

/**
 * Message used to notify the client the number of players of the game and the name of the opponents
 */
public class CreationGameMessage extends MessageByServer {
    private final int numberOfPlayers;
    private final List<Pair<String, Integer>> listOfPlayers;

    /**
     * Create a CreationGameMessage with the number of players and the name list of the players
     *
     * @param numberOfPlayers the number of players
     * @param listOfPlayers   the name list of the players
     */
    public CreationGameMessage(int numberOfPlayers, List<Pair<String, Integer>> listOfPlayers) {
        super();
        this.numberOfPlayers = numberOfPlayers;
        this.listOfPlayers = listOfPlayers;
    }

    /**
     * Get the number of players
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Get the name list of the players
     *
     * @return the name list of the players
     */
    public List<Pair<String, Integer>> getListOfPlayers() {
        return listOfPlayers;
    }

    /**
     * Method used to handle the CreationGameMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitCreationGameMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<String, Integer> pair : listOfPlayers) {
            stringBuilder.append(',').append(pair.getKey()).append(" Index = ").append(pair.getValue());
        }

        return getClass().getSimpleName() + "{numberPlayers = " + numberOfPlayers + stringBuilder + '}';
    }
}

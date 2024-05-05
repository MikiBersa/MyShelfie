package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

/**
 * Message used to notify the client that a players has achieved a common goal card
 */
public class CommonPointsMessage extends MessageByServer {
    // This because not only the player that has completed the objective receive the message
    // but also the others because the max points achievable with a common goal card changes
    private final int playerThatHasCompletedTheObjective;
    private final int commonPoints;
    private final int idCommonCard;

    /**
     * Create a CommonPointsMessage
     *
     * @param parameters the parameters of the message
     */
    public CommonPointsMessage(Pair<Integer, Pair<Integer, Integer>> parameters) {
        super();
        this.playerThatHasCompletedTheObjective = parameters.getKey();
        this.idCommonCard = parameters.getValue().getKey();
        this.commonPoints = parameters.getValue().getValue();
    }

    /**
     * Method used to handle the CommonPointsMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitCommonPointsMessage(this, controller);
    }

    /**
     * Get the id of the player that has completed the objective
     *
     * @return the id
     */
    public int getPlayerThatHasCompletedTheObjective() {
        return playerThatHasCompletedTheObjective;
    }

    /**
     * Get the points achieved
     *
     * @return the points
     */
    public int getCommonPoints() {
        return commonPoints;
    }

    /**
     * Get the id of the card
     *
     * @return the id
     */
    public int getIdCommonCard() {
        return idCommonCard;
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{ idPlayer = " + playerThatHasCompletedTheObjective + ", points = " + commonPoints
                + ", idCommonCard = " + idCommonCard + '}';
    }
}

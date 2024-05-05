package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

import java.util.List;

/**
 * Message used to notify all the players about the final points
 */
public class PointsEndGameMessage extends MessageByServer {
    private final List<Pair<String, Integer>> points;

    /**
     * Create a PointsEndGameMessage
     *
     * @param points the points of all the players
     */
    public PointsEndGameMessage(List<Pair<String, Integer>> points) {
        super();
        this.points = points;
    }

    /**
     * Method used to handle the PointsEndGameMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitPointsEndGameMessage(this, controller);
    }

    /**
     * Get the points
     *
     * @return the list of points of all the players
     */
    public List<Pair<String, Integer>> getPoints() {
        return points;
    }
}

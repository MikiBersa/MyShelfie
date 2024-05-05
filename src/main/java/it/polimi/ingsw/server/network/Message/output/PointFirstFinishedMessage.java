package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

/**
 * Message used to notify the first player that has completed the bookshelf
 */
public class PointFirstFinishedMessage extends MessageByServer {
    // This because not only the player that has completed the objective receive the message
    // but also the others because the max points achievable with a common goal card changes
    private final int idPlayer;
    private final int pointFirst;

    /**
     * Create a PointFirstFinishedMessage
     *
     * @param parameters the parameters
     */
    public PointFirstFinishedMessage(Pair<Integer, Integer> parameters) {
        super();
        this.idPlayer = parameters.getKey();
        this.pointFirst = parameters.getValue();
    }

    /**
     * Method used to handle the PointFirstFinishedMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitFirstPointMessage(this, controller);
    }

    public int getPointFirst() {
        return pointFirst;
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
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{ idPlayer = " + idPlayer + '}';
    }
}

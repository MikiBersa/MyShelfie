package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

/**
 * Message used to notify the players about the winner of the game
 */
public class WinnerMessage extends MessageByServer {
    private final String winner;

    /**
     * Create a WinnerMessage
     *
     * @param winner the winner
     */
    public WinnerMessage(Pair<Integer, String> winner) {
        super();
        this.winner = winner.getValue();
    }

    /**
     * Get the winner of the game
     *
     * @return the winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Method used to handle the WinnerMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitWinnerMessage(this, controller);
    }
}

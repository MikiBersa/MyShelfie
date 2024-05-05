package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to set the board
 */
public class BoardMessage extends MessageByServer {
    private final Tile[][] board;

    /**
     * Create a BoardMessage with the board
     *
     * @param board the board
     */
    public BoardMessage(Tile[][] board) {
        super();
        this.board = board;
    }

    /**
     * Get the board
     *
     * @return the board
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Method used to handle the BoardMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitBoardMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.MessageByServer;

import java.util.List;

/**
 * Message used to notify if the order and the column chosen by the user are correct
 */
public class ResponseOrderAndBookshelfMessage extends MessageByServer {
    private final boolean status;
    private final List<Tile> tileList;

    /**
     * Create ResponseOrderAndBookshelfMessage
     *
     * @param status   true if it is correct
     * @param tileList the tiles chosen by the player
     */
    public ResponseOrderAndBookshelfMessage(boolean status, List<Tile> tileList) {
        super();
        this.status = status;
        this.tileList = tileList;
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
     * Get the list of tiles chosen
     *
     * @return the list of tiles chosen
     */
    public List<Tile> getTileList() {
        return tileList;
    }

    /**
     * Method used to handle the ResponseOrderAndBookshelfMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitResponseOrderAndBookshelfMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (tileList != null) {
            for (int i = 0; i < tileList.size(); i++) {
                stringBuilder.append("id = ").append(tileList.get(i).getId()).append(' ').append("color = ").append(tileList.get(i).getColor());
                if (i != tileList.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        return getClass().getSimpleName() + " status: "+status+" {tiles = [" + stringBuilder + "]}";
    }
}

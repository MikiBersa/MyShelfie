package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.MessageByServer;

import java.util.List;

/**
 * Message used to notify if the player has chosen in the correct way
 */
public class ResponseListTileMessage extends MessageByServer {
    private final boolean tilesAreOk;
    private final List<Tile> tileList;

    /**
     * Create a ResponseListTileMessage
     *
     * @param tilesAreOk true if the tiles chosen are correct
     * @param tileList   the list of tiles chosen
     */
    public ResponseListTileMessage(boolean tilesAreOk, List<Tile> tileList) {
        super();
        this.tilesAreOk = tilesAreOk;
        this.tileList = tileList;
    }

    /**
     * Get the correctness of the selection of the tiles
     *
     * @return true if the tiles are correct
     */
    public boolean getTilesAreOk() {
        return tilesAreOk;
    }

    /**
     * Get the list of the tiles
     *
     * @return the list of the tiles
     */
    public List<Tile> getTileList() {
        return tileList;
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
        return getClass().getSimpleName() + "{tiles = [" + stringBuilder + "]}";
    }

    /**
     * Method used to handle the ResponseListTileMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitResponseListTileMessage(this, controller);
    }
}

package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.MessageByClient;

import java.util.List;

/**
 * Message used to notify the server the order of the list of tiles put in the bookshelf and the column chosen by the player
 */
public class PutTilesInBookshelfMessage extends MessageByClient {
    private final List<Tile> tiles;
    private final int column;

    /**
     * Create a PutTilesInBookshelf message with the tiles and column selected
     *
     * @param tiles  the tiles selected by the player
     * @param column the column selected by the player
     */
    public PutTilesInBookshelfMessage(List<Tile> tiles, int column) {
        super();
        this.tiles = tiles;
        this.column = column;

    }

    /**
     * Method used to handle the PutTilesInBookshelfMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.putTilesInBookshelf(column, tiles);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile tile : tiles) {
            stringBuilder.append('{').append(tile.toString()).append('}');
        }
        return getClass().getSimpleName() + "{tiles = [" + stringBuilder + "]} " + "column: " + column;
    }
}

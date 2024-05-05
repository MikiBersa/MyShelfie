package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.*;

/**
 * XTiles
 */
public class XTiles extends CommonObjectiveCard {

    /**
     * XTiles constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public XTiles(int id, int numPlayers) {
        super(id, numPlayers);
    }

    /**
     * A method that calculates the common objective card's points based on the specific pattern
     * @param bookshelf bookshelf
     * @return points
     */
    @Override
    public int calculatePoints(Bookshelf bookshelf) {
        Tile[][] matrix = bookshelf.getPositionTiles();
        boolean accomplished = false;

        // Dalla prima riga, dalla prima colonna, dall'ultima riga e dall'ultima colonna
        // non possono essere delle celle centrali della X perchè se no si va fuori dalla bookshelf
        for (int i = 1; i < BOOKSHELF_HEIGHT - 1; i++) {
            for (int j = 1; j < BOOKSHELF_WIDTH - 1; j++) {
                Tile center = matrix[i][j];
                if (center != null) {
                    Tile tl = matrix[i - 1][j - 1];
                    Tile tr = matrix[i - 1][j + 1];
                    Tile bl = matrix[i + 1][j - 1];
                    Tile br = matrix[i + 1][j + 1];

                    if (tl == null || tr == null || bl == null || br == null) continue;

                    Color color = center.getColor();
                    if (tl.getColor().equals(color) && tr.getColor().equals(color) &&
                            bl.getColor().equals(color) && br.getColor().equals(color)) {
                        accomplished = true;
                    }
                }
            }
        }

        if (accomplished) {
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }
        return 0;
    }

    /**
     * toString of XTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Five tiles of the same type forming an X. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of XTiles
     * @return String
     */
    @Override
    public String getDescription(){
        return "Five tiles of the same type forming an X";
    }
}

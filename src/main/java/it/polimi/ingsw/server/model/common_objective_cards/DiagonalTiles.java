package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.*;

/**
 * DiagonalTiles
 */
public class DiagonalTiles extends CommonObjectiveCard {

    /**
     * DiagonalTiles Constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public DiagonalTiles(int id, int numPlayers) {
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

        // Diagonale da sinistra verso destra
        for (int i = 0, j = 0; i < BOOKSHELF_HEIGHT - 4; i++) {
            Tile startTile = matrix[i][j];
            if (startTile != null) {
                Color color = startTile.getColor();

                int k = 1;
                while (k < 5 && matrix[i + k][j + k] != null &&
                        matrix[i + k][j + k].getColor().equals(color)) {
                    k++;
                }

                if (k == 5) {
                    accomplished = true;
                }
            }
        }

        // Diagonale da destra verso sinistra
        for (int i = 0, j = BOOKSHELF_WIDTH - 1; i < BOOKSHELF_HEIGHT - 4; i++) {
            Tile startTile = matrix[i][j];
            if (startTile != null) {
                Color color = startTile.getColor();

                int k = 1;
                while (k < 5 && matrix[i + k][j - k] != null &&
                        matrix[i + k][j - k].getColor().equals(color)) {
                    k++;
                }

                if (k == 5) {
                    accomplished = true;
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
     * getDescription of DiagonalTiles
     * @return String
     */
    public String getDescription(){
        return "Five tiles of the same type\nforming a diagonal";
    }

    /**
     * toString of DiagonalTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Five tiles of the same type forming a diagonal. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }
}

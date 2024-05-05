package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * FourEqualsCorners
 */
public class FourEqualsCorners extends CommonObjectiveCard {

    /**
     * FourEqualsCorners constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public FourEqualsCorners(int id, int numPlayers) {
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
        Tile tl = matrix[0][0];
        Tile tr = matrix[0][BOOKSHELF_WIDTH - 1];
        Tile bl = matrix[BOOKSHELF_HEIGHT - 1][0];
        Tile br = matrix[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1];

        if (tl == null || tr == null || bl == null || br == null) return 0;

        if (tl.getColor().equals(tr.getColor())
                && tl.getColor().equals(bl.getColor())
                && tl.getColor().equals(br.getColor())) {
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }
        return 0;
    }

    /**
     * getDescription of FourEqualsCorners
     * @return String
     */
    @Override
    public String getDescription(){
        return "Four tiles of the same type\nin the four corners of the bookshelf";
    }

    /**
     * toString of FourEqualsTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Four tiles of the same type in the four corners of the bookshelf. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }
}

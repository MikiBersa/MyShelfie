package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.*;

/**
 * EightEqualsTiles
 */
public class EightEqualsTiles extends CommonObjectiveCard {

    /**
     * EightEqualsTiles constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public EightEqualsTiles(int id, int numPlayers) {
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
        int[] numberOfTilesOfAColor = new int[Color.values().length];

        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
                if (matrix[i][j] != null) {
                    numberOfTilesOfAColor[matrix[i][j].getColor().ordinal()]++;
                }
            }
        }

        for (int numberOfTiles : numberOfTilesOfAColor) {
            if (numberOfTiles == 8) {
                int ans = points.get(playersThatCompletedObjective);
                playersThatCompletedObjective++;
                return ans;
            }
        }

        return 0;
    }

    /**
     * getDescription EightEqualsTiles
     * @return String
     */
    @Override
    public String getDescription(){
        return "Eight tiles of the same type. There’s no \nrestriction about the position of these tiles";
    }

    /**
     * toString of EightEqualsTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Eight tiles of the same type. There’s no " +
                "restriction about the position of these tiles \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }
}

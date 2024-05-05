package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.*;

/**
 * ColumnAllDifferent
 */
public class ColumnAllDifferent extends CommonObjectiveCard {
    /**
     * ColumnAllDifferent constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public ColumnAllDifferent(int id, int numPlayers) {
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
        int columnsTrue = 0;

        //here we have the logic of Max3Column
        //ArrayList<Hashtable<Constant.Color, Boolean>> counterType = new ArrayList<>(BOOKSHELF_WIDTH);

        for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
            //Hashtable<Constant.Color, Boolean> hashtable = new Hashtable<>();
            boolean[] colorSeen = new boolean[Color.values().length];

            for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
                if (matrix[i][j] == null) break;

                //hashtable.put(matrix[i][j].getColor(), true);
                colorSeen[matrix[i][j].getColor().ordinal()] = true;
            }

            int totalSeen = 0;
            for (int i = 0; i < Color.values().length; i++) {
                if (colorSeen[i]) {
                    totalSeen++;
                }
            }

            if (totalSeen == Color.values().length) {
                columnsTrue++;
            }
        }

        if (columnsTrue >= 2) {

            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;

        }

        return 0;
    }

    /**
     * getDescription of ColumnAllDifferent
     * @return String
     */
    public String getDescription(){
        return "Two columns each formed by 6\ndifferent types of tiles";
    }

    /**
     * toString of ColumnAllDifferent
     * @return String
     */
    @Override
    public String toString() {
        return "Two columns each formed by 6 " +
                "different types of tiles.  \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }
}

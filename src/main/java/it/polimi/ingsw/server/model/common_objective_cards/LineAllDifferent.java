package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.*;

/**
 * LineAllDifferent
 */
public class LineAllDifferent extends CommonObjectiveCard {

    /**
     * GroupsOfAtLeastTwoTiles constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public LineAllDifferent(int id, int numPlayers) {
        super(id, numPlayers);
    }

    //parametro bookshelf dell'utente

    /**
     * A method that calculates the common objective card's points based on the specific pattern
     * @param bookshelf bookshelf
     * @return points
     */
    @Override
    public int calculatePoints(Bookshelf bookshelf) {
        Tile[][] matrix = bookshelf.getPositionTiles();
        int linesTrue = 0;

        //here we have the logic of Max3Column
        //ArrayList<Hashtable<Constant.Color, Boolean>> counterType = new ArrayList<>(BOOKSHELF_HEIGHT);

        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            boolean[] colorSeen = new boolean[Color.values().length];

            for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
                if (matrix[i][j] == null) break;

                colorSeen[matrix[i][j].getColor().ordinal()] = true;
            }


            int totalSeen = 0;
            for (int j = 0; j < Color.values().length; j++) {
                if (colorSeen[j]) {
                    totalSeen++;
                }
            }

            if (totalSeen == Color.values().length - 1) {
                linesTrue++;
            }

            //counterType.add(i, hashtable);
        }

        if (linesTrue >= 2) {
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }

        return 0;
    }

    /**
     * toString of LineAllDifferent
     * @return String
     */
    @Override
    public String toString() {
        return "Two lines each formed by 5 different types of tiles. " +
                "One line can show the same or a different combination of the other line. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of LineAllDifferent
     * @return String
     */
    @Override
    public String getDescription(){
        return "Two lines each formed by 5 different\ntypes of tiles. One line can show the same or\na different combination of the other line";
    }
}

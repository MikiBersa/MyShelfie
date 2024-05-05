package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.*;

/**
 * SquareTiles
 */
public class SquareTiles extends CommonObjectiveCard {

    /**
     * SquareTiles constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public SquareTiles(int id, int numPlayers) {
        super(id, numPlayers);
    }

    // I quadrati sono già separati perchè uso la matrice selected che memorizza le caselle
    // già utilizzate per formare un altro quadrato

    /**
     * A method that calculates the common objective card's points based on the specific pattern
     * @param bookshelf bookshelf
     * @return points
     */
    @Override
    public int calculatePoints(Bookshelf bookshelf) {
        Tile[][] matrix = bookshelf.getPositionTiles();
        int numberSquares = 0;
        boolean[][] selected = new boolean[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];

        // Il quadrato non può partire dall'ultima riga e dall'ultima colonna
        // tl = TOP-LEFT
        for (int i = 0; i < BOOKSHELF_HEIGHT - 1; i++) {
            for (int j = 0; j < BOOKSHELF_WIDTH - 1; j++) {
                Tile tl = matrix[i][j];

                if (tl != null && !selected[i][j]) {
                    Tile tr = matrix[i][j + 1];
                    Tile bl = matrix[i + 1][j];
                    Tile br = matrix[i + 1][j + 1];

                    if (tr == null || bl == null || br == null ||
                            selected[i][j + 1] || selected[i + 1][j] || selected[i + 1][j + 1]) continue;

                    Color color = tl.getColor();
                    if (tr.getColor().equals(color) && bl.getColor().equals(color) &&
                            br.getColor().equals(color)) {
                        selected[i][j] = true;
                        selected[i][j + 1] = true;
                        selected[i + 1][j] = true;
                        selected[i][j + 1] = true;
                        numberSquares++;
                    }
                }

            }
        }

        if (numberSquares >= 2) {
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }

        return 0;
    }

    /**
     * toString of SquareTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Two groups each containing 4 tiles of " +
                "the same type in a 2x2 square. The tiles " +
                "of one square can be different from " +
                "those of the other square. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of SquareTiles
     * @return String
     */
    @Override
    public String getDescription(){
        return "Two groups each containing 4 tiles of \n" +
                "the same type in a 2x2 square. \nThe tiles " +
                "of one square can be different from \n" +
                "those of the other square.";
    }
}

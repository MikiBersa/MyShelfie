package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.CalculatePointsCommon;
import it.polimi.ingsw.server.model.cards.Tile;
import javafx.util.Pair;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * AdjacentTiles
 */
public class AdjacentTiles implements CalculatePointsCommon {

    /**
     * Algorithm that calculates the points based upon how many adjacent tiles
     * there are
     * @param bookshelf bookshelf
     * @return totalPoints points
     */
    @Override
    public int calculatePoints(Bookshelf bookshelf) {
        Tile[][] matrix = bookshelf.getPositionTiles();
        boolean[][] visited = new boolean[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        int totalPoints = 0;

        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
                if (matrix[i][j] != null) {
                    int res = CommonAlgorithms.bfs(new Pair<>(i, j), matrix, visited, matrix[i][j].getColor());
                    totalPoints += pointsForTiles(res);
                }
            }
        }

        return totalPoints;
    }

    /**
     * Method that assigns the points based upon the number of adjacent tiles
     * @param numberOfTiles number of adjacent tiles
     * @return points
     */
    private int pointsForTiles(int numberOfTiles) {
        if (numberOfTiles < 3) return 0;
        if (numberOfTiles == 3) return 2;
        if (numberOfTiles == 4) return 3;
        if (numberOfTiles == 5) return 5;
        return 8;
    }
}

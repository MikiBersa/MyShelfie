package it.polimi.ingsw.server.model;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.cards.Tile;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

import static it.polimi.ingsw.Constant.*;

/**
 * CommonAlgorithms to control of neighboring cells
 */
public final class CommonAlgorithms {
    /**
     * Control method for the adjacent tiles based upon the BFS (Breath Search First) algorithm
     * @param initialPoint points where starts the breath research
     * @param matrix tiles matrix
     * @param visited boolean matrix that indicates if a coordinate was already visited
     * @param color color searched
     * @return number of adjacent tiles with the same color
     */
    public static int bfs(Pair<Integer, Integer> initialPoint, Tile[][] matrix, boolean[][] visited, Constant.Color color) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(initialPoint);
        visited[initialPoint.getKey()][initialPoint.getValue()] = true;
        int numberOfAdjacentCellsOfTheSameColor = 1;

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> pointProcessed = queue.poll();

            for (int k = 0; k < 4; k++) {
                int newJ = pointProcessed.getValue() + DX[k];
                int newI = pointProcessed.getKey() + DY[k];

                if (newI < 0 || newJ < 0 || newI == BOOKSHELF_HEIGHT || newJ == BOOKSHELF_WIDTH ||
                        visited[newI][newJ] || matrix[newI][newJ] == null || !matrix[newI][newJ].getColor().equals(color))
                    continue;
                visited[newI][newJ] = true;
                queue.add(new Pair<>(newI, newJ));
                numberOfAdjacentCellsOfTheSameColor++;
            }
        }

        return numberOfAdjacentCellsOfTheSameColor;
    }
}

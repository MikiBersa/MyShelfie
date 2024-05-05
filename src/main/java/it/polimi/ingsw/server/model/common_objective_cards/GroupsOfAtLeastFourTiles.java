package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.CommonAlgorithms;
import it.polimi.ingsw.server.model.cards.Tile;
import javafx.util.Pair;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * GroupsOfAtLeastFourTiles
 */
public class GroupsOfAtLeastFourTiles extends CommonObjectiveCard {

    /**
     * GroupsOfAtLeastFourTiles constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public GroupsOfAtLeastFourTiles(int id, int numPlayers) {
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
        boolean[][] visited = new boolean[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        int groupsWithAtLeastFourTiles = 0;

        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
                if (matrix[i][j] != null && !visited[i][j]) {
                    int totalAdjacentTiles = CommonAlgorithms.bfs(new Pair<>(i, j), matrix, visited, matrix[i][j].getColor());
                    groupsWithAtLeastFourTiles += totalAdjacentTiles >= 4 ? 1 : 0;
                }
            }
        }

        if (groupsWithAtLeastFourTiles >= 4) {
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }

        return 0;
    }

    /**
     * toString of GroupsOfAtLeastFourTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Four groups each containing at least " +
                "4 tiles of the same type (not necessarily " +
                "in the depicted shape). " +
                "The tiles of one group can be different " +
                "from those of another group. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of GroupsOfAtLeastFourTiles
     * @return String
     */
    @Override
    public String getDescription(){
        return "Four groups each containing at least\n" +
                "4 tiles of the same type (not necessarily \n" +
                "in the depicted shape).\n " +
                "The tiles of one group can be different \n" +
                "from those of another group";
    }
}

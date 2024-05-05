package it.polimi.ingsw.server.model.common_objective_cards;

/**
 * CommonObjectiveCardFactory
 */
public class CommonObjectiveCardFactory {
    /**
     * getCommonObjectiveCard
     * @param id id
     * @param numPlayers numPlayers
     * @return CommonObjectiveCard
     */
    public static CommonObjectiveCard getCommonObjectiveCard(int id, int numPlayers) {
        if (id == 0) return new XTiles(0, numPlayers);
        if (id == 1) return new ColumnAllDifferent(1, numPlayers);
        if (id == 2) return new DiagonalTiles(2, numPlayers);
        if (id == 3) return new EightEqualsTiles(3, numPlayers);
        if (id == 4) return new FourEqualsCorners(4, numPlayers);
        if (id == 5) return new GroupsOfAtLeastFourTiles(5, numPlayers);
        if (id == 6) return new GroupsOfAtLeastTwoTiles(6, numPlayers);
        if (id == 7) return new LineAllDifferent(7, numPlayers);
        if (id == 8) return new Max3Column(8, numPlayers);
        if (id == 9) return new Max3Line(9, numPlayers);
        if (id == 10) return new PyramidTiles(10, numPlayers);
        if (id == 11) return new SquareTiles(11, numPlayers);
        return null;
    }
}

package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.User;
import it.polimi.ingsw.server.model.cards.CalculatePointsCommon;
import it.polimi.ingsw.server.model.cards.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CommonObjectiveCard
 */
public abstract class CommonObjectiveCard extends Card implements CalculatePointsCommon, Serializable {
    final List<User> usersThatCompletedTheObjective;
    //in the first position we have the maximum point and in the last the minimum
    List<Integer> points;
    int playersThatCompletedObjective;

    /**
     * CommonObjectiveCard constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public CommonObjectiveCard(int id, int numPlayers) {
        super(id);
        if (numPlayers == 2) {
            points = Constant.COMMON_POINTS_FOR_TWO_PLAYERS;
        } else if (numPlayers == 3) {
            points = Constant.COMMON_POINTS_FOR_THREE_PLAYERS;
        } else if (numPlayers == 4) {
            points = Constant.COMMON_POINTS_FOR_FOUR_PLAYERS;
        }
        usersThatCompletedTheObjective = new ArrayList<>();
        playersThatCompletedObjective = 0;
    }

    /**
     * Method that updates the players that completed an objective
     */
    public void completedObjective() {
        playersThatCompletedObjective++;
    }

    /**
     * getPoints
     * @return points
     */
    public int getPoints(){
        return points.get(playersThatCompletedObjective);
    }

    /**
     * A method that calculates the common objective card's points based on the specific pattern
     * @param bookshelf bookshelf
     * @return points
     */
    public abstract int calculatePoints(Bookshelf bookshelf);
    /**
     * getDescription of CommonObjectiveCard
     * @return String
     */
    public abstract String getDescription();

    /**
     * toString of CommonObjectiveCard
     * @return String
     */
    @Override
    public abstract String toString();
}

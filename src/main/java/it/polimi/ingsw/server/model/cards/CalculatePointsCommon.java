package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Bookshelf;

/**
 * interface for calculation of commonCards player's points
 */
public interface CalculatePointsCommon {

    /**
     * Method to calculate points of the card
     * @param bookshelf bookshelf
     * @return points
     */
    int calculatePoints(Bookshelf bookshelf);
}

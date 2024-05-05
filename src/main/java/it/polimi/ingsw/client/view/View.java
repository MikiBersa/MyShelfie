package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.cards.Tile;
import javafx.util.Pair;

import java.util.List;

/**
 * Interface of View
 */
public interface View {

    /**
     * Ask the server info
     */
    void askServerInfo();

    /**
     * Ask the username of the player
     */
    void askUsername();

    /**
     * Ask the number of players of the game
     */
    void askNumberOfPlayers();

    /**
     * Print the message to take the tiles
     */
    void askTilesToTake();

    /**
     * Print the message to ask for the order of the tiles and the column of the bookshelf
     *
     * @param listOfTiles tiles selected previously
     */
    void askOrderOfTilesAndColumn(List<Tile> listOfTiles);

    /**
     * Show the board of the game
     */
    void showBoard();

    /**
     * Show the personal card of the player
     */
    void showPersonalCard();

    /**
     * Show the common goal cards of the game
     */
    void showCommonCards();

    /**
     * Show the bookshelves of all the players
     */
    void showBookshelves();

    /**
     * Show the winner of the game
     *
     * @param winner the winner of the game
     */
    void showWinner(String winner);

    /**
     * Shutdown
     */
    void shutdown();

    /**
     * Show the final points of all the players
     *
     * @param points the points of all the players
     */
    void showFinalPoints(List<Pair<String, Integer>> points);
}

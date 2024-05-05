package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.cards.Tile;
import javafx.util.Pair;

import java.util.List;

/**
 * Interface to create the visitor for the server side in order to handle the message to the server from client
 */
public interface ServerVisitor {
    /**
     * Add user in the game
     *
     * @param username username of the player to add
     */
    void addUser(String username);

    /**
     * Method handlers the action of taking tile from the board
     *
     * @param selectedCoordinates coordinates of selections
     */
    void takeCards(List<Pair<Integer, Integer>> selectedCoordinates);

    /**
     * Method handlers the action of putting tile in the bookshelf
     *
     * @param column   The column in which the player wants to put the tile in his bookshelf
     * @param tileList The list of tiles selected in the takeCards method
     */
    void putTilesInBookshelf(int column, List<Tile> tileList);

    /**
     * Creation of the game
     *
     * @param players The number of players in the game, which is chosen by the first player
     */
    void createGame(int players);

    /**
     * It sends the ChatMessage to the client
     *
     * @param message     message that has sent by Client to other Client
     * @param destination The destination od the message
     */
    void sendMessageChat(String message, String destination);

    /**
     * Quit message send by the client to interrupt his game
     */
    void quitMessage();

    /**
     * Disconnection is a message that is sent by server to server in order to handle the unintentional disconnection
     */
    void disconnection();

    /**
     * Ping message to control if the client is still connected
     */
    void ping();
}

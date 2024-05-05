package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.ErrorMessage;
import it.polimi.ingsw.server.network.Message.output.*;
import javafx.util.Pair;

import java.util.List;

/**
 * Interface of the client controller
 */
public interface ClientController {

    /**
     * Open the socket in the InterfaceNetwork class
     *
     * @param ip   the ip of the server
     * @param port the port where the process is running
     */
    void openSocket(String ip, int port);

    /**
     * Send username
     *
     * @param username the username
     */
    void sendUsername(String username);

    /**
     * Send the number of players
     *
     * @param players the number of players
     */
    void sendNumberOfPlayers(int players);

    /**
     * Send the list of coordinates chosen by the client
     *
     * @param listOfTiles list of coordinates
     */
    void sendListOfTiles(List<Pair<Integer, Integer>> listOfTiles);

    /**
     * Send order of the tiles and the column of the bookshelf
     *
     * @param listOfTiles the list of tile
     * @param column      the column of the bookshelf
     */
    void sendOrderOfTilesAndColumn(List<Tile> listOfTiles, int column);

    /**
     * Send a chat message
     *
     * @param message     the message
     * @param destination the destination of the message
     */
    void sendChatMessage(String message, String destination);

    /**
     * Send a disconnection message
     */
    void sendDisconnectMessage();

    /**
     * If the status is true set the id and username otherwise ask a new username
     *
     * @param message the message received by the server
     */
    void handleResponseUsernameMessage(ResponseUsernameMessage message);

    /**
     * If the status is true the player has the privileges to choose the number of players
     *
     * @param message the message received by the server
     */
    void handleAdminMessage(AdminMessage message);

    /**
     * If the status is false ask a new number of player because the last one was incorrect
     *
     * @param message the message received by the server
     */
    void handleResponseNumberOfPlayerMessage(ResponseNumberOfPlayerMessage message);

    /**
     * if the status is true the client is added in the game, otherwise it is not added
     *
     * @param message the message received by the server
     */
    void handleAddedInGameMessage(AddedInGameMessage message);

    /**
     * Set the number of players and the creation of the bookshelves
     *
     * @param message the message received by the server
     */
    void handleCreationGameMessage(CreationGameMessage message);

    /**
     * Set the board
     *
     * @param message the message received by the server
     */
    void handleBoardMessage(BoardMessage message);

    /**
     * Set the personal card
     *
     * @param message the message received by the server
     */
    void handlePersonalCardMessage(PersonalCardMessage message);

    /**
     * Set the common goal cards
     *
     * @param message the message received by the server
     */
    void handleCommonGoalCardMessage(CommonGoalCardMessage message);

    /**
     * if the status is true the player has to choose the tiles, otherwise it is not his/her turn
     *
     * @param message the message received by the server
     */
    void handleIsYourTurnMessage(IsYourTurnMessage message);

    /**
     * If the tiles are ok the player has to choose the order and the column of the bookshelf
     * otherwise he has to select other tiles
     *
     * @param message the message received by the server
     */
    void handleResponseListTileMessage(ResponseListTileMessage message);

    /**
     * If the status is false the player has to redo the order and select the column
     *
     * @param message the message received by the server
     */
    void handleResponseOrderAndBookshelfMessage(ResponseOrderAndBookshelfMessage message);

    /**
     * Call the updateCommonPoints in the virtual model in order to handle the message in the correct way
     *
     * @param message the message received by the server
     */
    void handleCommonPointsMessage(CommonPointsMessage message);

    /**
     * Update of the bookshelves
     *
     * @param message the message received by the server
     */
    void handleUpdateBookshelfMessage(UpdateBookshelfMessage message);

    /**
     * Finish game function
     *
     * @param message the message received by the server
     */
    void handleEndGameMessage(EndGameMessage message);

    /**
     * Update the points of the player that has finished the bookshelf as first
     *
     * @param message the message received by the server
     */
    void handlePointFirstFinishedMessage(PointFirstFinishedMessage message);

    /**
     * Show the final points of all the players
     *
     * @param message the message received by the server
     */
    void handlePointsEndGameMessage(PointsEndGameMessage message);

    /**
     * Show the winner and disconnect the client
     *
     * @param message the message received by the server
     */
    void handleWinnerMessage(WinnerMessage message);

    /**
     * Show the chat message received
     *
     * @param message the message received by the server
     */
    void handleResponseChatMessage(ResponseChatMessage message);

    /**
     * Disconnect the client
     *
     * @param message the message received by the server
     */
    void handleQuitMessage(ResponseQuitMessage message);

    /**
     * Disconnect the client due to an error
     */
    void handleErrorMessage(ErrorMessage message);

    /**
     * Handle of the ping message by the server and answer with the ping of the client
     */
    void handlePingMessage();

    /**
     * disconnection function of the client, close of the socket and the asynchronous input read
     */
    void disconnect();
}

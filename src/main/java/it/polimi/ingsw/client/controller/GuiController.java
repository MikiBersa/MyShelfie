package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.network.InterfaceNetwork;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.ErrorMessage;
import it.polimi.ingsw.server.network.Message.output.*;
import javafx.application.Platform;
import javafx.util.Pair;

import java.util.List;

/**
 * GuiController
 */
public class GuiController implements ClientController {
    private InterfaceNetwork interfaceNetwork;
    private final VirtualModel virtualModel;
    private final GUI gui;

    /**
     * Create a CliController and add the CLI to the observers of the virtualModel
     */
    public GuiController(GUI view, VirtualModel virtualModel) {
        this.gui = view;
        this.virtualModel = virtualModel;
        virtualModel.addObserver(view);
    }

    /**
     * GetVirtualModel
     * @return VirtualModel
     */
    public VirtualModel getVirtualModel(){return virtualModel;}

    /**
     * Open the socket in the InterfaceNetwork class
     *
     * @param ip   the ip of the server
     * @param port the port where the process is running
     */
    public void openSocket(String ip, int port) {
        interfaceNetwork = new InterfaceNetwork(this, ip, port);
    }

    /**
     * Send username
     *
     * @param username the username
     */
    public void sendUsername(String username) {
        interfaceNetwork.sendUsername(username);
    }

    /**
     * Send the number of players
     *
     * @param players the number of players
     */
    public void sendNumberOfPlayers(int players) {
        interfaceNetwork.sendNumberOfPlayers(players);
    }

    /**
     * Send the list of coordinates chosen by the client
     *
     * @param listOfTiles list of coordinates
     */
    public void sendListOfTiles(List<Pair<Integer, Integer>> listOfTiles) {
        interfaceNetwork.sendListOfTiles(listOfTiles);
    }

    /**
     * Send order of the tiles and the column of the bookshelf
     *
     * @param listOfTiles the list of tile
     * @param column      the column of the bookshelf
     */
    public void sendOrderOfTilesAndColumn(List<Tile> listOfTiles, int column) {
        interfaceNetwork.sendListOfTilesInOrderAndColumn(listOfTiles, column);
    }

    /**
     * Send a chat message
     *
     * @param message     the message
     * @param destination the destination of the message
     */
    public void sendChatMessage(String message, String destination) {
        interfaceNetwork.sendChatMessage(message, destination);
    }

    /**
     * Send a disconnection message
     */
    public void sendDisconnectMessage() {
        interfaceNetwork.sendQuitMessage();
    }

    /**
     * If the status is true set the id and username otherwise ask a new username
     *
     * @param message the message received by the server
     */
    public void handleResponseUsernameMessage(ResponseUsernameMessage message) {
        if (message.getStatus()) {
            virtualModel.createVirtualMainUser(message.getIdPlayer(), message.getUsername());
            gui.askLobby();
        } else {
            gui.showAlertError("Username incorrect, the username is already used");
        }
    }

    /**
     * If the status is true the player has the privileges to choose the number of players
     *
     * @param message the message received by the server
     */
    public void handleAdminMessage(AdminMessage message) {
        if (message.getStatus()) {
            gui.askNumberOfPlayers();
        } else {
            gui.askLobby();
        }
    }

    /**
     * If the status is false ask a new number of player because the last one was incorrect
     *
     * @param message the message received by the server
     */
    public void handleResponseNumberOfPlayerMessage(ResponseNumberOfPlayerMessage message) {
        if (!message.getStatus()) {
            gui.showAlertError("Incorrect number of players, choose between 2 and 4");
        }
    }

    /**
     * if the status is true the client is added in the game, otherwise it is not added
     *
     * @param message the message received by the server
     */
    public void handleAddedInGameMessage(AddedInGameMessage message) {
        if (message.getStatus()) {
            gui.askLobby(); // una lobby momentanea gli si potrebbe passare la label da mettere al posto dello showMessage
        } else {
            gui.showAlertError("We are sorry but you are not added in the game");
            interfaceNetwork.shutdown();
            Platform.exit();
        }
    }

    /**
     * Set the number of players and the creation of the bookshelves
     *
     * @param message the message received by the server
     */
    public void handleCreationGameMessage(CreationGameMessage message) {
        virtualModel.setNumberOfPlayersAndInitialize(message.getNumberOfPlayers(), message.getListOfPlayers());
        gui.showBoardGame(virtualModel);
    }

    /**
     * Set the board
     *
     * @param message the message received by the server
     */
    public void handleBoardMessage(BoardMessage message) {
        virtualModel.setBoard(message.getBoard());
    }

    /**
     * Set the personal card
     *
     * @param message the message received by the server
     */
    public void handlePersonalCardMessage(PersonalCardMessage message) {
        virtualModel.setPersonalCard(message.getIdPersonalCard());
    }

    /**
     * Set the common goal cards
     *
     * @param message the message received by the server
     */
    public void handleCommonGoalCardMessage(CommonGoalCardMessage message) {
        virtualModel.setCommonGoalCards(message.getCommonObjectiveCards());
        virtualModel.setReadyGame(true);
    }

    /**
     * if the status is true the player has to choose the tiles, otherwise it is not his/her turn
     *
     * @param message the message received by the server
     */
    public void handleIsYourTurnMessage(IsYourTurnMessage message) {
        if (message.getIdPlayer() == virtualModel.getVirtualMainUser().getId()) {
            gui.showAlertInfo("It's your turn");
            virtualModel.setTakeTiles(true);
            gui.askTilesToTake();
        } else {
            gui.showAlertInfo("It's " + message.getUsername() + "'s turn");
        }
    }

    /**
     * If the tiles are ok the player has to choose the order and the column of the bookshelf
     * otherwise he has to select other tiles
     *
     * @param message the message received by the server
     */
    public void handleResponseListTileMessage(ResponseListTileMessage message) {
        if (message.getTilesAreOk()) {
            virtualModel.setTakeTiles(false);
            virtualModel.setOrdering(true);
            // eliminabile la riga di codice gestito
            virtualModel.setTileList(message.getTileList());
            gui.askOrderOfTilesAndColumn(message.getTileList());
        } else {
            gui.showAlertError("The selection is not correct");
        }
    }

    /**
     * If the status is false the player has to redo the order and select the column
     *
     * @param message the message received by the server
     */
    public void handleResponseOrderAndBookshelfMessage(ResponseOrderAndBookshelfMessage message) {
        if (message.getStatus()) {
            virtualModel.setOrdering(false);
        } else {
            gui.askOrderOfTilesAndColumn(message.getTileList());
            gui.showAlertError("The order or the column is not correct");
        }
    }

    /**
     * Call the updateCommonPoints in the virtual model in order to handle the message in the correct way
     *
     * @param message the message received by the server
     */
    public void handleCommonPointsMessage(CommonPointsMessage message) {
        virtualModel.updateCommonPoints(message.getPlayerThatHasCompletedTheObjective(), message.getIdCommonCard(), message.getCommonPoints());
    }

    /**
     * Update of the bookshelves
     *
     * @param message the message received by the server
     */
    public void handleUpdateBookshelfMessage(UpdateBookshelfMessage message) {
        virtualModel.updateBookshelf(message.getUsername(), message.getBookshelf());
    }

    /**
     * Finish game function
     *
     * @param message the message received by the server
     */
    public void handleEndGameMessage(EndGameMessage message) {
        gui.showAlertInfo("END GAME");
    }

    /**
     * Update the points of the player that has finished the bookshelf as first
     *
     * @param message the message received by the server
     */
    public void handlePointFirstFinishedMessage(PointFirstFinishedMessage message) {
        virtualModel.updateFinishGamePoints(message.getIdPlayer(), message.getPointFirst());
    }

    /**
     * Show the final points of all the players
     *
     * @param message the message received by the server
     */
    public void handlePointsEndGameMessage(PointsEndGameMessage message) {
        virtualModel.setFinalPoint(message.getPoints());
        gui.showRanking();
    }

    /**
     * Show the winner and disconnect the client
     *
     * @param message the message received by the server
     */
    public void handleWinnerMessage(WinnerMessage message) {
        gui.showWinner(message.getWinner());
        interfaceNetwork.shutdown();
    }

    /**
     * Show the chat message received
     *
     * @param message the message received by the server
     */
    public void handleResponseChatMessage(ResponseChatMessage message) {
        gui.chatMessage(message.getUsername(), message.getDestination(), message.getMessage());
    }

    /**
     * Disconnect the client
     *
     * @param message the message received by the server
     */
    public void handleQuitMessage(ResponseQuitMessage message) {
        gui.showAlertInfo("Disconnection message by " + message.getUsername());
        interfaceNetwork.shutdown();
        gui.shutdown();
    }

    /**
     * Disconnect the client due to an error
     */
    @Override
    public void handleErrorMessage(ErrorMessage message) {
        disconnect();
        Platform.exit();
    }

    /**
     * disconnection function of the client, close of the socket and the asynchronous input read
     */
    public void disconnect() {
        if(interfaceNetwork != null) interfaceNetwork.shutdown();
    }

    /**
     * Handle of the ping message by the server and answer with the ping of the client
     */
    @Override
    public void handlePingMessage() {
        interfaceNetwork.sendPingClientMessage();
    }

}


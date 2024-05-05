package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.network.InterfaceNetwork;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.ErrorMessage;
import it.polimi.ingsw.server.network.Message.output.*;
import javafx.util.Pair;

import java.util.List;

/**
 * Controller of the client and the CLI
 */
public class CliController implements ClientController {
    private InterfaceNetwork interfaceNetwork;
    private final VirtualModel virtualModel = new VirtualModel();
    private final Cli cli = new Cli(this, virtualModel);

    /**
     * Create a CliController and add the CLI to the observers of the virtualModel
     */
    public CliController() {
        virtualModel.addObserver(cli);
    }

    /**
     * start function of the controller where it is asked for server info and username of the player
     */
    public void start() {
        cli.showMessage("Welcome to MyShelfie");
        cli.askServerInfo();
        cli.askUsername();
    }

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
     * @param listOfCoordinates list of coordinates
     */
    public void sendListOfTiles(List<Pair<Integer, Integer>> listOfCoordinates) {
        interfaceNetwork.sendListOfTiles(listOfCoordinates);
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
        } else {
            cli.showMessage("Incorrect username, it is already used");
            cli.askUsername();
        }
    }

    /**
     * If the status is true the player has the privileges to choose the number of players
     *
     * @param message the message received by the server
     */
    public void handleAdminMessage(AdminMessage message) {
        if (message.getStatus()) {
            cli.askNumberOfPlayers();
        } else {
            cli.showMessage(message.getMessage());
        }
    }

    /**
     * If the status is false ask a new number of player because the last one was incorrect
     *
     * @param message the message received by the server
     */
    public void handleResponseNumberOfPlayerMessage(ResponseNumberOfPlayerMessage message) {
        if (!message.getStatus()) {
            cli.showMessage("Incorrect number of players, choose between 2 and 4");
            cli.askNumberOfPlayers();
        }
    }

    /**
     * if the status is true the client is added in the game, otherwise it is not added
     *
     * @param message the message received by the server
     */
    public void handleAddedInGameMessage(AddedInGameMessage message) {
        if (message.getStatus()) {
            cli.showMessage("You are added in the game. From now on you can input something only pressing the letter 'i' and then press enter");
            cli.asynchronousInput();
        } else {
            cli.showMessage("We are sorry but you are not added in the game");
            interfaceNetwork.shutdown();
        }
    }

    /**
     * Set the number of players and the creation of the bookshelves
     *
     * @param message the message received by the server
     */
    public void handleCreationGameMessage(CreationGameMessage message) {
        virtualModel.setNumberOfPlayersAndInitialize(message.getNumberOfPlayers(), message.getListOfPlayers());
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
            virtualModel.setTakeTiles(true);
            cli.askTilesToTake();
        } else {
            cli.showMessage("It's " + message.getUsername() + "'s turn");
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
            virtualModel.setTileList(message.getTileList());
            cli.askOrderOfTilesAndColumn(message.getTileList());
        } else {
            cli.showMessage("The selection is not correct");
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
            cli.showMessage("The order is not correct");
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
        cli.showMessage("The game has finished. Calculating points...");
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
        cli.showFinalPoints(message.getPoints());
    }

    /**
     * Show the winner and disconnect the client
     *
     * @param message the message received by the server
     */
    public void handleWinnerMessage(WinnerMessage message) {
        cli.showWinner(message.getWinner());
        disconnect();
    }

    /**
     * Show the chat message received
     *
     * @param message the message received by the server
     */
    public void handleResponseChatMessage(ResponseChatMessage message) {
        cli.showMessage(message.getUsername() + ":" + message.getMessage());
    }

    /**
     * Disconnect the client
     *
     * @param message the message received by the server
     */
    public void handleQuitMessage(ResponseQuitMessage message) {
        cli.showMessage("Disconnection message by " + message.getUsername());
        interfaceNetwork.shutdown();
        cli.shutdown();
    }

    /**
     * Disconnect the client due to an error
     */
    public void handleErrorMessage(ErrorMessage message) {
        cli.showMessage("Attention: " + message.getDescription() + "\nClosing the client");
        disconnect();
    }

    /**
     * Handle of the ping message by the server and answer with the ping of the client
     */
    public void handlePingMessage() {
        interfaceNetwork.sendPingClientMessage();
    }

    /**
     * disconnection function of the client, close of the socket and the asynchronous input read
     */
    public void disconnect() {
        cli.shutdown();
        interfaceNetwork.shutdown();
    }
}

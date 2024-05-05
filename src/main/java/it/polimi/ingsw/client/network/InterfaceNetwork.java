package it.polimi.ingsw.client.network;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.client.visitor.ConcreteClientVisitor;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import it.polimi.ingsw.server.network.Message.input.*;
import it.polimi.ingsw.utils.Log;
import javafx.util.Pair;

import java.util.List;
import java.util.logging.Logger;

/**
 * The InterfaceNetwork class is a connection between the client controller and the socket TCP/IP
 */
public class InterfaceNetwork {
    private final SocketClient socketClient;
    private final ClientController clientController;
    private final Logger logger = Log.getInstance();
    private final ClientVisitor clientVisitor = new ConcreteClientVisitor();

    /**
     * Create an InterfaceNetwork object
     *
     * @param clientController the controller
     * @param ip               ip of the server
     * @param port             port of the process
     */
    public InterfaceNetwork(ClientController clientController, String ip, int port) {
        this.clientController = clientController;
        socketClient = new SocketClient(ip, port, this);
        socketClient.receiveMessage();
    }

    /**
     * Create and send the AddUserMessage with the username
     *
     * @param username the username chosen by the player
     */
    public void sendUsername(String username) {
        AddUserMessage message = new AddUserMessage(username);
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Create and send the SetNumberOfPlayersMessage with the number of players
     *
     * @param players the number of players
     */
    public void sendNumberOfPlayers(int players) {
        SetNumberPlayerMessage message = new SetNumberPlayerMessage(players);
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Create and send the TakeCardsMessage with the list of tiles selected
     *
     * @param listOfTiles the list of tiles
     */
    public void sendListOfTiles(List<Pair<Integer, Integer>> listOfTiles) {
        TakeCardsMessage message = new TakeCardsMessage(listOfTiles);
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Create and send the PutTilesInBookshelfMessage with the list of tiles and the column selected
     *
     * @param listOfTiles the list of tiles
     * @param column      the column
     */
    public void sendListOfTilesInOrderAndColumn(List<Tile> listOfTiles, int column) {
        PutTilesInBookshelfMessage message = new PutTilesInBookshelfMessage(listOfTiles, column);
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Create and send the ChatMessage with the content and the destination
     *
     * @param content     the content
     * @param destination the destination
     */
    public void sendChatMessage(String content, String destination) {
        ChatMessage message = new ChatMessage(content, destination);
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Create and send a QuitMessage
     */
    public void sendQuitMessage() {
        QuitMessage message = new QuitMessage();
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Create and send a PingClientMessage
     */
    public void sendPingClientMessage() {
        PingClientMessage message = new PingClientMessage();
        logger.info("Send message: " + message);
        socketClient.sendMessage(message);
    }

    /**
     * Read the message and apply the visitor pattern
     *
     * @param message the message received by the server
     */
    public void read(MessageByServer message) {
        logger.info("Received message: " + message.toString());
        message.accept(clientVisitor, clientController);
    }

    /**
     * Closing of the socket by the client
     */
    public void shutdown() {
        socketClient.shutdown();
    }
}

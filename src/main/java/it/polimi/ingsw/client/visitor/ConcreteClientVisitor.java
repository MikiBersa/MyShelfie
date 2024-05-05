package it.polimi.ingsw.client.visitor;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.server.network.Message.output.*;

/**
 * Client visitor pattern: in this way every message can be managed by the ClientController
 */
public class ConcreteClientVisitor implements ClientVisitor {
    /**
     * Method to handle the username message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitResponseUsernameMessage(ResponseUsernameMessage message, ClientController ClientController) {
        ClientController.handleResponseUsernameMessage(message);
    }

    /**
     * Method to handle the admin message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitAdminMessage(AdminMessage message, ClientController ClientController) {
        ClientController.handleAdminMessage(message);
    }

    /**
     * Method to handle the number of players message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitResponseNumberOfPlayerMessage(ResponseNumberOfPlayerMessage message, ClientController ClientController) {
        ClientController.handleResponseNumberOfPlayerMessage(message);
    }

    /**
     * Method to handle the added in game message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitAddedInGameMessage(AddedInGameMessage message, ClientController ClientController) {
        ClientController.handleAddedInGameMessage(message);
    }

    /**
     * Method to handle the creation game message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitCreationGameMessage(CreationGameMessage message, ClientController ClientController) {
        ClientController.handleCreationGameMessage(message);
    }

    /**
     * Method to handle the board message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitBoardMessage(BoardMessage message, ClientController ClientController) {
        ClientController.handleBoardMessage(message);
    }

    /**
     * Method to handle the personal card message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitPersonalCardMessage(PersonalCardMessage message, ClientController ClientController) {
        ClientController.handlePersonalCardMessage(message);
    }

    /**
     * Method to handle the common card message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitCommonGoalCardMessage(CommonGoalCardMessage message, ClientController ClientController) {
        ClientController.handleCommonGoalCardMessage(message);
    }

    /**
     * Method to handle the "your turn" message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitIsYourTurnMessage(IsYourTurnMessage message, ClientController ClientController) {
        ClientController.handleIsYourTurnMessage(message);
    }

    /**
     * Method to handle the list tile message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitResponseListTileMessage(ResponseListTileMessage message, ClientController ClientController) {
        ClientController.handleResponseListTileMessage(message);
    }

    /**
     * Method to handle the order and bookshelf message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitResponseOrderAndBookshelfMessage(ResponseOrderAndBookshelfMessage message, ClientController ClientController) {
        ClientController.handleResponseOrderAndBookshelfMessage(message);
    }

    /**
     * Method to handle the first point message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitFirstPointMessage(PointFirstFinishedMessage message, ClientController ClientController) {
        ClientController.handlePointFirstFinishedMessage(message);
    }

    /**
     * Method to handle the common points message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitCommonPointsMessage(CommonPointsMessage message, ClientController ClientController) {
        ClientController.handleCommonPointsMessage(message);
    }

    /**
     * Method to handle the update bookshelf message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitUpdateBookshelfMessage(UpdateBookshelfMessage message, ClientController ClientController) {
        ClientController.handleUpdateBookshelfMessage(message);
    }

    /**
     * Method to handle the end game message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitEndGameMessage(EndGameMessage message, ClientController ClientController) {
        ClientController.handleEndGameMessage(message);
    }

    /**
     * Method to handle the  points end game message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitPointsEndGameMessage(PointsEndGameMessage message, ClientController ClientController) {
        ClientController.handlePointsEndGameMessage(message);
    }

    /**
     * Method to handle the winner message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitWinnerMessage(WinnerMessage message, ClientController ClientController) {
        ClientController.handleWinnerMessage(message);
    }

    /**
     * Method to handle the chat message
     * @param message message
     * @param ClientController Client Controller
     */
    @Override
    public void visitResponseChatMessage(ResponseChatMessage message, ClientController ClientController) {
        ClientController.handleResponseChatMessage(message);
    }

    /**
     * Method to handle the quit message
     * @param message message
     * @param ClientController ClientController
     */
    @Override
    public void visitQuitMessage(ResponseQuitMessage message, ClientController ClientController){
        ClientController.handleQuitMessage(message);
    }

    /**
     * Method to handle the ping message
     * @param message message
     * @param clientController clientController
     */
    @Override
    public void ping(PingServerMessage message, ClientController clientController) {
        clientController.handlePingMessage();
    }
}

package it.polimi.ingsw.client.visitor;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.server.network.Message.output.*;

/**
 * Interface of the visitor pattern
 */
public interface ClientVisitor {

    /**
     * Method to handle the username message
     * @param message message
     * @param ClientController ClientController
     */
    void visitResponseUsernameMessage(ResponseUsernameMessage message, ClientController ClientController);

    /**
     * Method to handle the admin message
     * @param message message
     * @param ClientController ClientController
     */
    void visitAdminMessage(AdminMessage message, ClientController ClientController);

    /**
     * Method to handle the number of players message
     * @param message message
     * @param ClientController ClientController
     */
    void visitResponseNumberOfPlayerMessage(ResponseNumberOfPlayerMessage message, ClientController ClientController);

    /**
     * Method to handle the added in game message
     * @param message message
     * @param ClientController ClientController
     */
    void visitAddedInGameMessage(AddedInGameMessage message, ClientController ClientController);

    /**
     * Method to handle the creation game message
     * @param message message
     * @param ClientController ClientController
     */
    void visitCreationGameMessage(CreationGameMessage message, ClientController ClientController);

    /**
     * Method to handle the board message
     * @param message message
     * @param ClientController ClientController
     */
    void visitBoardMessage(BoardMessage message, ClientController ClientController);

    /**
     * Method to handle the personal card message
     * @param message message
     * @param ClientController ClientController
     */
    void visitPersonalCardMessage(PersonalCardMessage message, ClientController ClientController);

    /**
     * Method to handle the first point message
     * @param message message
     * @param ClientController ClientController
     */
    void visitFirstPointMessage(PointFirstFinishedMessage message, ClientController ClientController);

    /**
     * Method to handle the common card message
     * @param message message
     * @param ClientController ClientController
     */
    void visitCommonGoalCardMessage(CommonGoalCardMessage message, ClientController ClientController);

    /**
     * Method to handle the "your turn" message
     * @param message message
     * @param ClientController ClientController
     */
    void visitIsYourTurnMessage(IsYourTurnMessage message, ClientController ClientController);

    /**
     * Method to handle the list tile message
     * @param message message
     * @param ClientController ClientController
     */
    void visitResponseListTileMessage(ResponseListTileMessage message, ClientController ClientController);

    /**
     * Method to handle the order and bookshelf message
     * @param message message
     * @param ClientController ClientController
     */
    void visitResponseOrderAndBookshelfMessage(ResponseOrderAndBookshelfMessage message, ClientController ClientController);

    /**
     * Method to handle the common points message
     * @param message message
     * @param ClientController ClientController
     */
    void visitCommonPointsMessage(CommonPointsMessage message, ClientController ClientController);

    /**
     * Method to handle the update bookshelf message
     * @param message message
     * @param ClientController ClientController
     */
    void visitUpdateBookshelfMessage(UpdateBookshelfMessage message, ClientController ClientController);

    /**
     * Method to handle the end game message
     * @param message message
     * @param ClientController ClientController
     */
    void visitEndGameMessage(EndGameMessage message, ClientController ClientController);

    /**
     * Method to handle the  points end game message
     * @param message message
     * @param ClientController ClientController
     */
    void visitPointsEndGameMessage(PointsEndGameMessage message, ClientController ClientController);

    /**
     * Method to handle the winner message
     * @param message message
     * @param ClientController ClientController
     */
    void visitWinnerMessage(WinnerMessage message, ClientController ClientController);

    /**
     * Method to handle the chat message
     * @param message message
     * @param ClientController Client Controller
     */
    void visitResponseChatMessage(ResponseChatMessage message, ClientController ClientController);

    /**
     * Method to handle the quit message
     * @param message message
     * @param ClientController ClientController
     */
    void visitQuitMessage(ResponseQuitMessage message, ClientController ClientController);

    /**
     * Method to handle the ping message
     * @param message message
     * @param clientController clientController
     */
    void ping(PingServerMessage message, ClientController clientController);
}

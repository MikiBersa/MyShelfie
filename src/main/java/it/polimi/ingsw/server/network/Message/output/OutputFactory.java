package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

import java.util.List;

import static it.polimi.ingsw.Constant.MessageNumber.*;

/**
 * A factory that for every update in the Model creates a message to send to the client
 */
public class OutputFactory {

    /**
     * The method that creates a Message for every update of the Model
     *
     * @param messageNumber the id of the message
     * @param message       the content of the message
     * @return the message to send to the player
     * @throws Exception if the content is null
     */
    public static MessageByServer getParser(int messageNumber, Object message) throws Exception {
        if (message == null) throw new Exception("null messageNumber");
        // TENERE QUESTI VALORI NELLE COSTANTI SERVONO SOLO QUESTI
        if (messageNumber == RETURN_BOARD.ordinal())
            return new BoardMessage((Tile[][]) message);
        if (messageNumber == RETURN_ID_PERSONAL_CARDS.ordinal())
            return new PersonalCardMessage((int) message);
        if (messageNumber == RETURN_ID_COMMON_CARDS.ordinal())
            return new CommonGoalCardMessage((CommonObjectiveCard[]) message);
        if (messageNumber == RETURN_BOOKSHELF.ordinal())
            return new UpdateBookshelfMessage((Pair<String,Bookshelf>) message);
        if (messageNumber == RETURN_POINTS_COMMON.ordinal())
            return new CommonPointsMessage((Pair<Integer, Pair<Integer, Integer>>) message);
        if (messageNumber == RETURN_POINT_FINAL.ordinal())
            return new PointsEndGameMessage((List<Pair<String, Integer>>) message);
        if(messageNumber == RETURN_POINT_FINISHED.ordinal())
            return new PointFirstFinishedMessage((Pair<Integer, Integer>) message);
        if (messageNumber == RETURN_WINNER.ordinal())
            return new WinnerMessage((Pair<Integer, String>) message);
        if (messageNumber == RETURN_QUIT.ordinal())
            return new ResponseQuitMessage((String) message);

        return null;
    }

}
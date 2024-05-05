package it.polimi.ingsw.server.network.Message.input;

import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;
import javafx.util.Pair;

import java.util.List;

/**
 * Message used to notify the server the list of coordinates selected by the player
 */
public class TakeCardsMessage extends MessageByClient {
    private final List<Pair<Integer, Integer>> selection;

    /**
     * Create a TakeCardsMessage with the selection of the player
     *
     * @param selection the selection of the player
     */
    public TakeCardsMessage(List<Pair<Integer, Integer>> selection) {
        super();
        this.selection = selection;
    }

    /**
     * Method used to handle the TakeCardsMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.takeCards(selection);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<Integer, Integer> p : selection) {
            stringBuilder.append('(').append(p.getKey()).append(',').append(p.getValue()).append(')');
        }
        return getClass().getSimpleName() + "{cards selected = [" + stringBuilder + "]}";
    }
}

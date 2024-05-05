package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import javafx.util.Pair;

/**
 * Message used to notify the changes of a bookshelf
 */
public class UpdateBookshelfMessage extends MessageByServer {
    private final Bookshelf bookshelf;
    private final String username;

    /**
     * Create a UpdateBookshelfMessage
     *
     * @param parameters the parameters
     */
    public UpdateBookshelfMessage(Pair<String, Bookshelf> parameters) {
        super();
        this.bookshelf = parameters.getValue();
        this.username = parameters.getKey();
    }

    /**
     * Method used to handle the UpdateBookshelfMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitUpdateBookshelfMessage(this, controller);
    }

    /**
     * Get the bookshelf
     *
     * @return the bookshelf
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * Get the username of the player
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{UpdateBookshelfMessage for username = " + username + '}';
    }
}

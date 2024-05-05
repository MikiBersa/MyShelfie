package it.polimi.ingsw.server.network.Message.input;
import it.polimi.ingsw.server.ServerVisitor;
import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Message to send a string to the other players
 */
public class ChatMessage extends MessageByClient {
    private final String message;
    // destination could be "all" or the username of a player
    private final String destination;

    /**
     * Create a ChatMessage with a content and a destination
     *
     * @param message     the content of the message
     * @param destination the destination
     */
    public ChatMessage(String message, String destination) {
        this.message = message;
        this.destination = destination;
    }

    /**
     * Method used to handle the ChatMessage in the server
     *
     * @param serverVisitor concrete visitor pattern to use
     */
    @Override
    public void sendToRouter(ServerVisitor serverVisitor) {
        serverVisitor.sendMessageChat(message, destination);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{message = " + message + ", destination = " + destination + '}';
    }
}

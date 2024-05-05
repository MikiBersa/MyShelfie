package it.polimi.ingsw.server.network.Observer.oberserverOut;

/**
 * Observable for the pattern observer-observable
 */
public interface Observable {
    /**
     * notify the sending of the message to client from server
     * @param idPlayer id of the player
     * @param messageNumber the message number
     * @param message the message
     */
    void notify(int idPlayer, int messageNumber, Object message);
}
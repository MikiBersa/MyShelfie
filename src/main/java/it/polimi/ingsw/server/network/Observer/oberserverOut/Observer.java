package it.polimi.ingsw.server.network.Observer.oberserverOut;

/**
 * Observer for the pattern observer-observable
 */
public interface Observer {
    /**
     * function to update that server has sent a message to the client
     * @param idPlayer id of player that has sent the message
     * @param messageNumber the messageNumber of the message
     * @param message the message sent by client
     */
    void update(int idPlayer, int messageNumber, Object message);
}

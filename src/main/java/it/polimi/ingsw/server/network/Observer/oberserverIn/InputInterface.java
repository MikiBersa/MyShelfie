package it.polimi.ingsw.server.network.Observer.oberserverIn;

import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Interface for the pattern observer-observable
 */
public interface InputInterface {
    /**
     * function to update that che client has sent a message to the server
     * @param id id of player that has sent the message
     * @param message the message sent by client
     */
    void update(int id, MessageByClient message);
}

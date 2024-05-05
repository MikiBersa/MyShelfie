package it.polimi.ingsw.server.network.Observer.oberserverIn;

import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * ObservableIn interface for pattern observer-observable
 */
public interface ObservableIn {
    /**
     * notify the receiving message from client to sever
     * @param idPlayer id of the player who has sent the message
     * @param message message sent by player
     */
    void notifyToServer(int idPlayer, MessageByClient message);
}

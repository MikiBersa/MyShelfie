package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * OutputInterface represents the output
 */
public interface OutputInterface {
    /**
     * send to client
     * @param id the id of client to send
     * @param message message from server to send
     */
    void send(int id, MessageByServer message);

}

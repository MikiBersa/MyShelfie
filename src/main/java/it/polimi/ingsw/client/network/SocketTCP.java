package it.polimi.ingsw.client.network;

import it.polimi.ingsw.server.network.Message.MessageByClient;

/**
 * Socket interface of the client
 */
public interface SocketTCP {
    /**
     * Send a message
     *
     * @param message the message
     */
    void sendMessage(MessageByClient message);

    /**
     * Receive the message
     */
    void receiveMessage();

    /**
     * Shutdown the SocketTCP
     */
    void shutdown();
}

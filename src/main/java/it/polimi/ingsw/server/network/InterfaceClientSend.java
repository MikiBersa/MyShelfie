package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.network.Message.MessageByServer;

import java.io.InputStream;
import java.net.Socket;

/**
 * Interface that represents the socket of the client
 */
public interface InterfaceClientSend {

    /**
     * send message to client from server
     * @param message message to send
     */
    void send(MessageByServer message);

    /**
     * close socket
     */
    void closeSocket();

    /**
     * get socket
     * @return the socket of client
     */
    Socket getSocket();
}

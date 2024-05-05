package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.network.Message.output.PingServerMessage;

import java.util.Hashtable;

/**
 * Pinger has the aim to manage line failures
 */
public class Pinger implements Runnable{
    private Hashtable<Integer, InterfaceClientSend> clients;

    /**
     * Constructor of Pinger
     * @param clients the Hashtable that contains the all player who are connected to the server in order to send the ping
     */
    public Pinger(Hashtable<Integer, InterfaceClientSend> clients) {
        this.clients = clients;
    }

    /**
     * function to send the message ping
     */
    @Override
    public void run() {
        for (Integer pos : clients.keySet()) {
            InterfaceClientSend client = clients.get(pos);
            client.send(new PingServerMessage());
        }
    }
}

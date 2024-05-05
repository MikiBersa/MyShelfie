package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.network.InterfaceNetwork;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import it.polimi.ingsw.server.network.Message.output.OutputFactory;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observer;
import it.polimi.ingsw.server.network.OutputInterface;

import java.io.Serializable;

/**
 * VirtualView represents the view in the server that is the interface between model and interfaceNetwork
 */
public class VirtualView implements Observer, Serializable {

    private final OutputInterface interfaceNetwork;

    /**
     * Constructor VirtualView
     * @param interfaceNetwork references to the InterfaceNetwork
     */
    public VirtualView(InterfaceNetwork interfaceNetwork) {
        this.interfaceNetwork = interfaceNetwork;
    }


    /**
     * Method to send message to the client created by a modification of the model
     * @param idPlayer id of player that has sent the message
     * @param messageNumber the messageNumber of the message
     * @param message the message sent by client
     */
    @Override
    public void update(int idPlayer, int messageNumber, Object message) {

        // here could be the factory method because it is modified at runtime depending on the events
        try {
            MessageByServer sendToClientMessage = OutputFactory.getParser(messageNumber, message);
            this.interfaceNetwork.send(idPlayer, sendToClientMessage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


}

package it.polimi.ingsw.server.network.Observer.oberserverOut;

import java.util.ArrayList;
import java.util.List;

/**
 * ManagementObserver has the aim to manage the input message to update the receiving message from server to client
 */
public class ManagementObserver implements Observable {
    private final List<Observer> observers;

    /**
     * Constructor ManagementObserver
     */
    public ManagementObserver(){
        this.observers = new ArrayList<>();
    }

    /**
     * add the listeners
     * @param observer the listeners that wants to keep update
     */
    public void attach(Observer observer){
        this.observers.add(observer);
    }

    /**
     * notify the sending of the message to client from server
     * @param idPlayer id of the player
     * @param messageNumber the message number
     * @param message the message
     */
    @Override
    public void notify(int idPlayer, int messageNumber, Object message) {
        for(Observer observer : observers){
            observer.update(idPlayer, messageNumber, message);
        }
    }
}

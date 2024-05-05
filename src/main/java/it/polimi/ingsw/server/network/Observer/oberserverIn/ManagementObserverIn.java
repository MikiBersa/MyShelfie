package it.polimi.ingsw.server.network.Observer.oberserverIn;

import it.polimi.ingsw.server.network.Message.MessageByClient;

import java.util.ArrayList;
import java.util.List;

/**
 * ManagementObserverIn has the aim to manage the input message to update the receiving message
 */
public class ManagementObserverIn implements ObservableIn {
    private final List<InputInterface> observers;

    /**
     * ManagementObserverIn constructor
     */
    public ManagementObserverIn(){
        this.observers = new ArrayList<>();
    }

    /**
     * add the listeners
     * @param observer the listeners that wants to keep update
     */
    public void attach(InputInterface observer){
        this.observers.add(observer);
    }

    /**
     * notify the server of receiving message form client
     * @param id id of the player who has sent the message
     * @param message message sent by player
     */
    @Override
    public void notifyToServer(int id, MessageByClient message) {
        for(InputInterface observer : observers){
            observer.update(id, message);
        }
    }
}

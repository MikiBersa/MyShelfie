package it.polimi.ingsw.client.observer;

import it.polimi.ingsw.client.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Observable class of the virtual model
 */
public class VirtualModelObservable {
    protected final List<View> viewObservers = new ArrayList<>();

    /**
     * Add the observer to the list
     *
     * @param obs observer to add to the list
     */
    public void addObserver(View obs) {
        viewObservers.add(obs);
    }

    /**
     * Notify method of the observer class
     *
     * @param lambda the lambda to execute when this method is called
     */
    public void notifyUpdateModel(Consumer<View> lambda) {
        for (View viewObserver : viewObservers) {
            lambda.accept(viewObserver);
        }
    }
}

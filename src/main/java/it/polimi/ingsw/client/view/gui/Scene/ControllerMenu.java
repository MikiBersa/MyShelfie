package it.polimi.ingsw.client.view.gui.Scene;

import it.polimi.ingsw.client.observer.VirtualModelObservable;
import it.polimi.ingsw.client.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * ControllerMenu
 */
public class ControllerMenu implements GenericSceneController {
    /**
     * Method to start the game
     * @param ae ActionEvent
     */
    public void startGame(ActionEvent ae) {
        SceneController.showAskServerInfo();
    }

    /**
     * Method to exit the application
     * @param ae ActionEvent
     */
    public void exit(ActionEvent ae) {
        Platform.exit();
        System.exit(0);
    }
}

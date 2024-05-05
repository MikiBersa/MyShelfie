package it.polimi.ingsw.client.view.gui.Scene;

import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.observer.VirtualModelObservable;
import it.polimi.ingsw.client.view.gui.DataSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * ControllerAskUsername
 */
public class ControllerAskUsername implements GenericSceneController{
    private final DataSingleton data = DataSingleton.getInstance();
    @FXML
    private TextField textUsername;

    /**
     * Method to send the username to the server
     * @param ae ActionEvent
     */
    public void enter(ActionEvent ae) {
        GuiController guiController = data.getController();
        guiController.sendUsername(textUsername.getText());
    }
}

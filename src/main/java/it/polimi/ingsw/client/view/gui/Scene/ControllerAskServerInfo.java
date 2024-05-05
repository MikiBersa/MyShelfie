package it.polimi.ingsw.client.view.gui.Scene;

import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.view.gui.DataSingleton;
import it.polimi.ingsw.client.view.gui.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ControllerAskServerInfo
 */
public class ControllerAskServerInfo implements GenericSceneController {
    private final DataSingleton data = DataSingleton.getInstance();
    @FXML
    private TextField textIPAddress;
    @FXML
    private TextField textPort;

    /**
     * Method to create the socket between client and server
     * @param ae ActionEvent
     */
    public void enter(ActionEvent ae) {
        GuiController guiController = data.getController();
        guiController.openSocket(textIPAddress.getText(), Integer.parseInt(textPort.getText()));
        SceneController.showAskUsername();
    }
}

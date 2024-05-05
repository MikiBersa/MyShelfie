package it.polimi.ingsw.client.view.gui.Scene;

import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.view.gui.DataSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ControllerAskNumberOfPlayers
 */
public class ControllerAskNumberOfPlayers implements Initializable, GenericSceneController {
    private final DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ChoiceBox<Integer> numberOfPlayers;

    /**
     * Method to send the number of players to the server
     * @param ae ActionEvent
     */
    public void Enter(ActionEvent ae) {
        GuiController guiController = data.getController();
        guiController.sendNumberOfPlayers(numberOfPlayers.getValue());
    }

    /**
     * Method to initialize the AskNumberOfPlayers GUI
     * @param url URL
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfPlayers.getItems().addAll(2, 3, 4);
        numberOfPlayers.setValue(2);
    }
}

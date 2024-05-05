package it.polimi.ingsw.client.view.gui.Scene;

import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.model.VirtualMainUser;
import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.model.VirtualUser;
import it.polimi.ingsw.client.view.gui.DataSingleton;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ControllerRanking
 */

public class ControllerRanking implements Initializable {
    private final DataSingleton data = DataSingleton.getInstance();
    private VirtualModel virtualModel;
    @FXML
    private Label points;

    /**
     * Method to return to the main menu
     * @param ae ActionEvent
     */
    public void Menu(ActionEvent ae) {
        // RIPRISTINO IL GAME
        VirtualModel virtualModel = new VirtualModel();
        GUI view = new GUI(virtualModel);
        GuiController guiController = new GuiController(view, virtualModel);
        data.setController(guiController);

        SceneController.showMenu();
    }

    /**
     * Method to display the rankings of the game with the users and their points
     */
    private void ranking(){
        StringBuilder stringBuilder = new StringBuilder();
        VirtualMainUser virtualMainUser = virtualModel.getVirtualMainUser();
        stringBuilder.append("Points:\n");
        stringBuilder.append(virtualMainUser.getUsername()).append(": ").append(virtualMainUser.getFinalPoint()).append("\n");
        for (VirtualUser user : virtualModel.getVirtualUsers()) {
            stringBuilder.append(user.getUsername()).append(": ").append(user.getFinalPoint()).append("\n");
        }
        points.setText(stringBuilder.toString());
    }

    /**
     * Method to initialize the Ranking GUI
     * @param url URL
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        virtualModel = data.getController().getVirtualModel();
        ranking();
    }
}

package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.view.gui.Scene.ControllerBoardGame;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * SceneController
 */
public class SceneController {
    private static Stage stage;

    /**
     * setStage
     * @param stage stage
     */
    public static void setStage(Stage stage) {
        SceneController.stage = stage;
    }

    /**
     * Method to change the scene to menu
     */
    public static void showMenu() {
        JavaFXGUI.reStart();

        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/Menu.fxml"));
        try {
            Parent parent = loader.load();
            stage.getScene().setRoot(parent);
            stage.show();
            stage.setOnCloseRequest(windowEvent -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the scene to askServerInfo
     */
    public static void showAskServerInfo() {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/AskServerInfo.fxml"));
        try {
            Parent parent = loader.load();
            stage.getScene().setRoot(parent);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the scene to askUsername
     */
    public static void showAskUsername() {
        stage.setOnCloseRequest(windowEvent -> {
            disconnectionGui();
        });
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/AskUsername.fxml"));
        try {
            Parent parent = loader.load();
            stage.getScene().setRoot(parent);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the scene to NumberOfPlayers
     */
    public static void showNumberOfPlayers() {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/AskNumberOfPlayers.fxml"));
        try {
            Parent parent = loader.load();
            stage.getScene().setRoot(parent);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the scene to Lobby
     */
    public static void showLobby() {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/Lobby.fxml"));
        try {
            Parent parent = loader.load();
            stage.getScene().setRoot(parent);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the scene to boardGame
     */
    public static void showBoardGame(GUI gui, VirtualModel virtualModel) {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/BoardGame.fxml"));
        ControllerBoardGame controller;
        DataSingleton dataSingleton = DataSingleton.getInstance();

        try {
            Parent parent = loader.load();

            controller = loader.getController();
            controller.init(gui, virtualModel);

            dataSingleton.setControllerBoardGame(controller);

            stage.getScene().setRoot(parent);
            stage.setWidth(1400);
            stage.setHeight(900);
            stage.setX(0);
            stage.setY(0);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the scene to Ranking
     */
    public static void showRanking() {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/Ranking.fxml"));
        try {
            Parent parent = loader.load();
            stage.getScene().setRoot(parent);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setX(0);
            stage.setY(0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to display the alert error
     */
    public static void showAlertError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Error");
        a.setContentText(message);
        a.show();
    }

    /**
     * Method to display the alert info
     */
    public static void showAlertInfo(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Information");
        a.setContentText(message);
        a.show();
    }

    /**
     * Method to close the GUI and disconnect
     */
    private static void disconnectionGui() {
        DataSingleton.getInstance().getController().disconnect();
        Platform.exit();
    }

}

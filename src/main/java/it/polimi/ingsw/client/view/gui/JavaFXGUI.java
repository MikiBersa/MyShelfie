package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.model.VirtualModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class used to initialize the gui application
 */
public class JavaFXGUI extends Application {

    /**
     * Method to start the application
     * @param stage stage
     */
    @Override
    public void start(Stage stage) {
        startGuiClient();

        SceneController.setStage(stage);
        Image icon = new Image(String.valueOf(getClass().getResource("/images/PublisherMaterial/Icon.png")));
        stage.getIcons().add(icon);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(JavaFXGUI.class.getResource("/fxml/Menu.fxml"));
        try {
            Parent rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setTitle("MyShelfie");
            stage.setScene(scene);
            stage.setOnCloseRequest(windowEvent -> {
                Platform.exit();
                System.exit(0);
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Helper method
     */
    private void startGuiClient(){
        DataSingleton data = DataSingleton.getInstance();
        VirtualModel virtualModel = new VirtualModel();
        GUI view = new GUI(virtualModel);
        GuiController guiController = new GuiController(view, virtualModel);
        data.setController(guiController);
    }

    /**
     * Method to restart the game resetting the VirtualModel
     */
    public static void reStart(){
        DataSingleton data;
        DataSingleton.setInstance(new DataSingleton());
        data = DataSingleton.getInstance();

        VirtualModel virtualModel = new VirtualModel();
        GUI view = new GUI(virtualModel);
        GuiController guiController = new GuiController(view, virtualModel);
        data.setController(guiController);
    }
}


package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.view.gui.Scene.ControllerBoardGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * DataSingleton
 */
public class DataSingleton {
    private static DataSingleton instance;
    private static ControllerBoardGame controllerBoardGame;
    private GuiController guiController;

    /**
     * DataSingleton constructor
     */
    DataSingleton() {
        instance = null;
        controllerBoardGame = null;
        guiController = null;
    }

    /**
     * Method to get the instance of the dataSingleton
     * @return DataSingletonS
     */
    public static DataSingleton getInstance() {
        if(instance == null){
            instance = new DataSingleton();
        }
        return instance;
    }

    /**
     * setInstance
     * @param dataSingleton dataSingleton
     */
    public static void setInstance(DataSingleton dataSingleton){
        instance = dataSingleton;
    }

    /**
     * getController
     * @return GUIController
     */
    public GuiController getController() {
        return guiController;
    }

    /**
     * setController
     * @param guiController guiController
     */
    public void setController(GuiController guiController) { this.guiController = guiController; }

    /**
     * setControllerBoardGame
     * @param controllerBoardGame controllerBoardGame
     */
    public void setControllerBoardGame(ControllerBoardGame controllerBoardGame) { DataSingleton.controllerBoardGame = controllerBoardGame; }

    /**
     * getControllerBoardGame
     * @return Return the reference of the ControllerBoardGame
     */
    public Future<ControllerBoardGame> getControllerBoardGame() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            while (controllerBoardGame == null) {
                Thread.sleep(500);
            }
            return DataSingleton.controllerBoardGame;
        });
    }
}

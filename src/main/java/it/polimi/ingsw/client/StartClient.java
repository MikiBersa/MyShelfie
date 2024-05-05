package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.CliController;
import it.polimi.ingsw.client.view.gui.JavaFXGUI;
import it.polimi.ingsw.utils.Log;
import javafx.application.Application;

/**
 * StartClient
 */
public class StartClient {
    /**
     * Main method
     * @param args parameters of the program
     */
    public static void main(String[] args) {
        Log.createLog("Client");
        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }
        if (cliParam) {
            CliController cliController = new CliController();
            cliController.start();
        } else {
            Application.launch(JavaFXGUI.class);
        }
    }
}

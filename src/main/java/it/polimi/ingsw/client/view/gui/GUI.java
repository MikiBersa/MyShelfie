package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.Scene.ControllerBoardGame;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.utils.Log;
import javafx.application.Platform;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * GUI
 */
public class GUI implements View {
    private final VirtualModel virtualModel;

    private Future<ControllerBoardGame> controllerBoardGame;

    private ControllerBoardGame controller;
    private boolean doneCommon;
    private Logger loggerClient = Log.getInstance();

    /**
     * GUI constructor
     * @param virtualModel virtualModel
     */
    public GUI(VirtualModel virtualModel){
        this.virtualModel = virtualModel;
    }

    /**
     * Method to run askServerInfo GUI
     */
    @Override
    public void askServerInfo() {
        Platform.runLater(SceneController::showAskServerInfo);
    }

    /**
     * Method to run askUsername GUI
     */
    @Override
    public void askUsername() {
        Platform.runLater(SceneController::showAskUsername);
    }

    /**
     * Method to run askLobby GUI
     */
    public void askLobby(){
        Platform.runLater(SceneController::showLobby);
    }

    /**
     * Method to run askNumberOfPlayers GUI
     */
    @Override
    public void askNumberOfPlayers() {
        Platform.runLater(SceneController::showNumberOfPlayers);
    }

    /**
     * Method to run BoardGame GUI
     * @param virtualModel virtualModel
     */
    public void showBoardGame(VirtualModel virtualModel) {
        Platform.runLater(() -> {
            SceneController.showBoardGame(this, virtualModel);
        });
    }

    /**
     * Method to run showRanking GUI
     */
    public void showRanking(){
        Platform.runLater(SceneController::showRanking);
    }

    /**
     * Method to initialize BoardGUI
     */
    @Override
    public void showBoard() {
        // USARE TIPO UN FUTURE
        DataSingleton dataSingleton = DataSingleton.getInstance();
        controllerBoardGame = dataSingleton.getControllerBoardGame();
        loggerClient.info(controllerBoardGame.toString());

        Tile[][] board = virtualModel.getBoard();

        try {
            controller = controllerBoardGame.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        loggerClient.info(controller.toString());
        loggerClient.info("SETTO LA BAORD DELLA GUI");

        for (int i = 0; i < Constant.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Constant.BOARD_WIDTH; j++) {
                //System.out.println(board[i][j]);
                if (board[i][j] != null) controller.setTileBoardGui(i, j, board[i][j]);
                else controller.setTileBoardGui(i, j, null);
            }
        }
    }

    /**
     * Method to initialize personal cards on GUI
     */
    @Override
    public void showPersonalCard() {
       // controller.setPersonalCard(3);
        if(controllerBoardGame.isDone()){
            controller.setPersonalCard(virtualModel.getPersonalCard());
        }
    }

    /**
     * Method to initialize common cards on GUI
     */
    @Override
    public void showCommonCards() {
        if(controllerBoardGame.isDone() && !doneCommon){
            controller.setPersonalCard(virtualModel.getPersonalCard());
            controller.setCommonCard(virtualModel.getCommonGoalCardsId()[0], virtualModel.getCommonGoalCardsId()[1]);
            doneCommon = true;
        }else if(doneCommon){
            controller.setPoints();
        }
    }

    /**
     * Method to initialize bookshelves on GUI
     */
    @Override
    public void showBookshelves() {
        // aggiorno la bookshlef del giocatore
        Tile[][] bookshelfMainUser = virtualModel.getVirtualMainUser().getBookshelf().getPositionTiles();

        // fare anche il'aggiornamento della bookshelf avversaria che c'è in quel momento in schermata
        if (controllerBoardGame.isDone()) {
            // aggiorno la mia bookshelf
            for (int row = 0; row < BOOKSHELF_HEIGHT; row++) {
                for (int col = 0; col < BOOKSHELF_WIDTH; col++) {
                    if (bookshelfMainUser[row][col] != null) {
                        controller.updateBookshelfMainUser(row, col, bookshelfMainUser[row][col].getImageId());
                    }
                }
            }

            // aggiornamento della bookshelf other indipendentemente dal fatto che sia stata modificata
            controller.updateBookshelfOther();
        }
    }

    /**
     * Method to set the turn and asking for tiles
     */
    @Override
    public void askTilesToTake() {
        // QUI SETTO IL MIO TURN
        if(controllerBoardGame.isDone()){
            // mi per mette di attivare le funzionalità di gioco del mio turno
            controller.setMyTurn(true);
        }
    }

    /**
     * Method to send chosen tiles
     * @param list list of coordinates
     */
    public void sendChoosenTiles(List<Pair<Integer, Integer>> list){
        GuiController guiController = DataSingleton.getInstance().getController();

        // CICLO DI SELEZIONE:
        // [.] PRIMA LE SELEZIONO IL CONTORNO (MANDO AL SERVER PER LA VERIFICA)
            // [.] RICEVO: ResponseListTileMessage -> li vedo: ok tolgo dalla baord non ok mando messaggio di errore popup (già fatto in ClientController)
        // POI SE TUTTO OK LE TOLGO
        // RICEVO LE TILE DA METTERE IN ALTO A SX DELLA MIA BOOKSHELF

        guiController.sendListOfTiles(list);

    }

    /**
     * Method to ask the correct order of tiles and the specific column
     * @param listOfTiles list of tiles
     */
    @Override
    public void askOrderOfTilesAndColumn(List<Tile> listOfTiles) {
        // QUI HO LA LISTA DA METTERE SOPRA LA BAORD DEL BOOKSHELF
        if(controllerBoardGame.isDone()){
            controller.setSelectionNumber(listOfTiles.size());
            for(int i = 0; i<listOfTiles.size();i++){
                // togliere le tile scelte
                // controller.removeTileFromBoard(listOfTiles.get(i).getId());
                // metterle in alto a sx
                controller.setSelectionsArea(i, listOfTiles.get(i), false);
            }

        }
    }

    /**
     * Method to send the ordered tiles and column selected
     * @param column column selected
     * @param selections tiles selected
     */
    public void sendOrderAndColumn(int column, List<Tile> selections){
        GuiController guiController = DataSingleton.getInstance().getController();
        guiController.sendOrderOfTilesAndColumn(selections, column);
    }

    public List<Tile> getSelections(){
        return virtualModel.getListOfTiles();
    }

    /**
     * Method to alert the winner with a message
     * @param winner string
     */
    @Override
    public void showWinner(String winner) {
        showAlertInfo("The winner is: "+winner);
    }

    /**
     * Method to set the final points of the game
     * @param points points
     */
    @Override
    public void showFinalPoints(List<Pair<String, Integer>> points) {
       controller.setPointFinal(points);
    }

    /**
     * Method to show the menu GUI
     */
    @Override
    public void shutdown() {
        Platform.runLater(SceneController::showMenu);
    }

    /**
     * Method to show an error with an alert
     * @param message message
     */
    public void showAlertError(String message) {
        Platform.runLater(() -> {
            SceneController.showAlertError(message);
        });

        System.err.println(message);
    }

    /**
     * Method to show an info with an alert
     * @param message message
     */
    public void showAlertInfo(String message) {
        Platform.runLater(() -> {
            SceneController.showAlertInfo(message);
        });
    }

    /**
     * Method to get if the tile are taken
     * @return boolean value
     */
    public boolean isGetTiles() {
        return virtualModel.getTakeTiles();
    }

    /**
     * Method to get if the tiles are ordered
     * @return boolean value
     */
    public boolean isOrdering() {
        return virtualModel.getOrdering();
    }

    /**
     * Method to initialize the chat on the GUI
     * @param author author of the message
     * @param destination destination of the message
     * @param message message
     */
    public void chatMessage(String author, String destination, String message) {
        Platform.runLater(() -> {
            controller.addMessage(author, destination, message);
        });
    }
}

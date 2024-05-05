package it.polimi.ingsw.client.view.gui.Scene;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.client.controller.GuiController;
import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.model.VirtualUser;
import it.polimi.ingsw.client.observer.VirtualModelObservable;
import it.polimi.ingsw.client.view.gui.DataSingleton;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.Constant.*;

/**
 * ControllerBoardGame controller of the board game
 */
public class ControllerBoardGame implements GenericSceneController{
    @FXML
    private GridPane gridPaneBoard;

    @FXML
    private GridPane gridPaneBookshelf;

    @FXML
    private GridPane gridPaneBookshelfOthers;

    @FXML
    private GridPane choice;

    @FXML
    private BorderPane borderPaneBoard;

    @FXML
    private ImageView personalCard;

    @FXML
    private ImageView commonCard1;

    @FXML
    private ImageView commonCard2;

    @FXML
    private Button leftButtonBookshelf;

    @FXML
    private Button rightButtonBookshelf;

    @FXML
    private Button confermation;
    @FXML
    private Label first;
    @FXML
    private Label second;
    @FXML
    private Label third;
    @FXML
    private Button sendButton;
    @FXML
    private TextField messageText;
    @FXML
    private ChoiceBox<String> destinationChoiceBox;
    @FXML
    private Label descOne;
    @FXML
    private Label descTwo;
    @FXML
    private TextArea textAreaConversation;
    @FXML
    private Label namePlayerLabel;
    @FXML
    private Label textPoints;
    @FXML
    private ImageView commonPoints1;
    @FXML
    private ImageView commonPoints2;
    @FXML
    private ImageView finishedPoint;
    @FXML
    private ImageView finishedPoint1;

    @FXML
    private ImageView finishedPointOther;

    @FXML
    private Button clear;

    // first parameter is the recipient the second is the whole conversation
    private final HashMap<String, String> chatHashmap;
    private final GuiController guiController = DataSingleton.getInstance().getController();
    private String username;
    private boolean myTurn;
    private GUI gui;
    private ArrayList<Pair<Integer, Integer>> selections;
    private HashMap<Integer, Pair<Integer, Integer>> posTileBoard;

    private int colBookShelf;
    private int selectionNumber;
    private int posChoice;

    // replaceable by gui.getSelections() from the virtualModel
    private List<Tile> selectionsTile;
    private List<Tile> selectionsTileToSend;
    private VirtualModel virtualModel;
    private VirtualUser virtualUserOtherBookshelfDisplayed;

    private boolean selectColumn;

    /**
     * Constructor of ControllerBoardGame
     */
    public ControllerBoardGame() {
        chatHashmap = new HashMap<>();
        selections = new ArrayList<>();
        posTileBoard = new HashMap<>();
        colBookShelf = -1;
        posChoice = 1;
        selectionNumber = 0;
        selectionsTile = new ArrayList<>();
        selectionsTileToSend = new ArrayList<>();
        selectColumn = false;
    }

    /**
     * Method to initialize the object and associated events to the Board GUI
     * @param gui GUI
     * @param virtualModel virtualModel
     */
    public void init(GUI gui, VirtualModel virtualModel) {
        this.gui = gui;
        this.virtualModel = virtualModel;


        // set background image
        Background background = new Background(new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("/images/misc/sfondo_dim.jpg")), 0, 0, true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));

        borderPaneBoard.setBackground(background);

        // Creating boxes in the GridPane with ImageView
        for (int row = 0; row < Constant.BOARD_HEIGHT; row++) {
            for (int column = 0; column < Constant.BOARD_WIDTH; column++) {
                // Creating a box with ImageView
                StackPane cell = createCell(row, column,  gridPaneBoard);

                // Adding the box to the GridPane
                gridPaneBoard.add(cell, column, row);
            }
        }

        // creation of bookshelf boxes
        for (int row = 0; row < Constant.BOOKSHELF_HEIGHT; row++) {
            for (int column = 0; column < Constant.BOOKSHELF_WIDTH; column++) {
                StackPane cell = createCell(row, column, gridPaneBookshelf);
                StackPane cell2 = createCell(row, column, gridPaneBookshelfOthers);
                gridPaneBookshelf.add(cell, column, row);
                gridPaneBookshelfOthers.add(cell2, column, row);
            }
        }

        // set to create order selection
        for(int column = 0; column < 3; column++){
            StackPane cell = createCell(0, column,  choice);
            choice.add(cell, column, 0);
        }

        // label per far vedere l'ordine di selezione -> anche di partenza impostati a null
        first.setText("");
        second.setText("");
        third.setText("");

        // init Image of Card
        initCard();

        // populate the choicebox for chat
        initChat();

        // init punteggio:
        setPoints();

        VirtualUser firstVirtualUser = virtualModel.getFirstVirtualUser();
        namePlayerLabel.setText(firstVirtualUser.getUsername());
        setBookshelfOther(firstVirtualUser.getBookshelf());
        virtualUserOtherBookshelfDisplayed = firstVirtualUser;
        if(firstVirtualUser.isSetShowFirst()){
            finishedPointOther.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/end_game.jpg"))));
        }else {
            finishedPointOther.setImage(null);
        }

        leftButtonBookshelf.setOnAction(event -> {
            VirtualUser virtualUser = virtualModel.getPreviousVirtualUser();
            virtualUserOtherBookshelfDisplayed = virtualUser;

            namePlayerLabel.setText(virtualUser.getUsername());
            setBookshelfOther(virtualUser.getBookshelf());
            if(virtualUser.isSetShowFirst()){
                finishedPointOther.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/end_game.jpg"))));
            }else {
                finishedPointOther.setImage(null);
            }
        });

        rightButtonBookshelf.setOnAction(event -> {
            VirtualUser virtualUser = virtualModel.getNextVirtualUser();
            virtualUserOtherBookshelfDisplayed = virtualUser;

            namePlayerLabel.setText(virtualUser.getUsername());
            setBookshelfOther(virtualUser.getBookshelf());
            if(virtualUser.isSetShowFirst()){
                finishedPointOther.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/end_game.jpg"))));
            }else {
                finishedPointOther.setImage(null);
            }
        });


        confermation.setOnAction(event -> {
            if (gui.isGetTiles()) {
                // send
                gui.sendChoosenTiles(selections);
                // cleaning
                selections = new ArrayList<>();
            } else if (gui.isOrdering()) {
                // I CONFIRM THE ORDER and THE COLUMN
                gui.sendOrderAndColumn(colBookShelf, selectionsTileToSend);
                cleanOrderingArea();
            }
        });

        clear.setOnAction(event -> {
            if (gui.isGetTiles()) {
                selections = new ArrayList<>();
                gui.showAlertInfo("You've just cleared your selection of tiles in the board, so you can reselect your favorite tiles");
                System.err.println("CLEAN selections");
            } else if (gui.isOrdering()) {
                selectionsTileToSend = new ArrayList<>();
                gui.showAlertInfo("You've just cleared your order selection of tiles in the bookshelf and column, so you can reselect your favorite order");
                System.err.println("CLEAN selectionsTileToSend");
                first.setText("");
                second.setText("");
                third.setText("");
                posChoice = 1;
                returnCss(colBookShelf, STILE_PREDEFINITO, gridPaneBookshelf);
                colBookShelf = -1;
                selectColumn = false;
            }else{
                System.err.println("no actions");
            }
        });

    }

    /**
     * Helper method to create the cells
     * @param row row
     * @param column column
     * @param gridPane gridPane
     * @return StackPane
     */
    private StackPane createCell(int row, int column, GridPane gridPane) {
        // DIMENSIONS
        double rowConstraints = gridPane.getRowConstraints().get(row).getPrefHeight() * 0.8;
        double columnConstraints = gridPane.getColumnConstraints().get(column).getPrefWidth() * 0.6;

        StackPane cell = new StackPane();
        cell.setPrefSize(columnConstraints, rowConstraints); // Imposta la dimensione prefissata della casella

        // Creating the ImageView inside the box
        ImageView imageView = new ImageView();

        imageView.setFitWidth(columnConstraints); // Set the width of the ImageView to the width of the box
        imageView.setFitHeight(rowConstraints); // Set the height of the ImageView to the height of the box

        cell.getChildren().add(imageView);

        // Add event handler for image
        if(gridPane.getId().equals("gridPaneBookshelfOthers")) return cell;

        if(!gridPane.getId().equals("gridPaneBookshelf")) {
            boolean controller = gridPane.getId().equals("gridPaneBoard");

            imageView.setOnMouseClicked(
                    controller ? this.actionBoard(cell, gridPane) : this.actionChoice(cell, gridPane)
            );
        }

        if(gridPane.getId().equals("gridPaneBoard")) {
            cell.setOnMouseEntered(event -> {
                // BOOKSHELF -> LIGHT UP THE COLUMN

                int clickedRow = GridPane.getRowIndex(cell);
                int clickedColumn = GridPane.getColumnIndex(cell);

                boolean contain = posTileBoard.containsValue(new Pair<>(clickedRow, clickedColumn));

                if(contain && !cell.getStyle().equals(STILE_CLICCATO)) cell.setStyle(STILE_OVER);
            });

            cell.setOnMouseExited(event -> {
                if(!cell.getStyle().equals(STILE_CLICCATO)) cell.setStyle(STILE_PREDEFINITO);
            });
        }

        if(gridPane.getId().equals("gridPaneBookshelf")) {
            // click for the bookshelf
            cell.setOnMouseClicked(event -> {
                int clickedColumn = GridPane.getColumnIndex(cell);

                colBookShelf = clickedColumn;

                System.out.println("cliccato su: "+colBookShelf);

                if (gui.isOrdering()) {
                    selectColumn = !selectColumn;

                    if (!selectColumn) {
                        returnCss(clickedColumn, STILE_PREDEFINITO, gridPaneBookshelf);
                        colBookShelf = -1;
                    } else {
                        colBookShelf = clickedColumn;
                        returnCss(clickedColumn, STILE_SFONDO, gridPaneBookshelf);
                    }

                }
                else {
                    gui.showAlertError("You can't do your choice, It isn't the ordering turn");
                }

            });

            cell.setOnMouseEntered(event -> {
                int clickedColumn = GridPane.getColumnIndex(cell);
                returnCss(clickedColumn, STILE_SFONDO, gridPaneBookshelf);
            });

            cell.setOnMouseExited(event -> {
                int clickedColumn = GridPane.getColumnIndex(cell);
                if(clickedColumn != colBookShelf) returnCss(clickedColumn, STILE_PREDEFINITO, gridPaneBookshelf);
            });

        }

        return cell;
    }

    /**
     * Method to set the CSS
     * @param column column
     * @param css css
     * @param gridPane gridPane
     */
    private void returnCss(int column, String css, GridPane gridPane){
        for(int i = 0; i< BOOKSHELF_HEIGHT; i++) {
            StackPane node = (StackPane) gridPane.getChildren().get(i * (gridPane.getColumnCount()) + column);
            node.setStyle(css);
        }
    }

    /**
     * Method to display effects on the board
     * @param cell cell
     * @param gridPane gridPane
     * @return EventHandler<T>
     * @param <T> Type
     */
    private <T extends Event> EventHandler<T> actionBoard(StackPane cell, GridPane gridPane){

        return (T) -> {
            int clickedRow = GridPane.getRowIndex(cell);
            int clickedColumn = GridPane.getColumnIndex(cell);

            System.out.println("actionBoard: clickedRow " + clickedRow + ", clickedColumn " + clickedColumn+" id: "+gridPane.getId());

            if(gui.isGetTiles()){

                if (cell.getStyle().equals(STILE_CLICCATO)) {
                    cell.setStyle(STILE_PREDEFINITO);
                } else {
                    cell.setStyle(STILE_CLICCATO);
                }

                // check if it is already selected
                if(!selectioned(clickedRow, clickedColumn, true)){
                    // set the style
                    selections.add(new Pair<>(clickedRow, clickedColumn));
                }else {
                    // remove it from the list
                    selectioned(clickedRow, clickedColumn, false);
                }
            }
            else {
                gui.showAlertError("You can't do this choice, It isn't the taking turn");
            }
        };
    }

    /**
     * Method to verify if a cell is selected
     * @param row row
     * @param col column
     * @param onlySelection onlySelection
     * @return boolean value
     */
    private boolean selectioned(int row, int col, boolean onlySelection){
        int i = 0;
        for(Pair<Integer, Integer> selection : selections){
            if(selection.getKey() == row && selection.getValue() == col){
                if(onlySelection){
                    return true;
                }else {
                    i = selections.indexOf(selection);
                }
            }
        }

        if(!onlySelection) selections.remove(i);

        return false;
    }

    /**
     * Method to display the effect when a choice is selected
     * @param cell cell
     * @param gridPane gridPane
     * @return EventHandler<T>
     * @param <T> Type
     */
    private <T extends Event> EventHandler<T> actionChoice(StackPane cell, GridPane gridPane) {

        return (T) -> {
            int clickedRow = GridPane.getRowIndex(cell);
            int clickedColumn = GridPane.getColumnIndex(cell);

            System.out.println("actionCoice: clickedRow " + clickedRow + ", clickedColumn " + clickedColumn + " id: " + gridPane.getId());

            // choice management
            if (gui.isOrdering()){
                orderSelection(clickedColumn);
            }
            else {
                gui.showAlertError("You can't do your choice, It isn't the ordering turn");
            }
        };
    }

    /**
     * Method to manage all cases of selection
     * @param column column
     */
    private void orderSelection(int column) {

        if (posChoice > selectionNumber || posChoice > 3) {
            if(controllSelectioned(column)){
                gui.showAlertInfo("Tile has already been selected");
            }else{
                gui.showAlertError("TOO MANY SELECTIONS");
            }
            return;
        }

        if(controllSelectioned(column)){
            gui.showAlertInfo("Tile has already been selected");
            return;
        }

        // position max control
        if(column == 0){
            first.setText(String.valueOf(posChoice));
        }else if(column == 1){
            second.setText(String.valueOf(posChoice));
        } else if (column == 2) {
            third.setText(String.valueOf(posChoice));
        }

        // idea to reorder tiles of selections sent by message in virtualModel
        // row corresponds to the selectionsTile position
        int pos = posChoice-1;

        selectionsTileToSend.add(pos,selectionsTile.get(column));

        posChoice++;
    }

    /**
     * Method to control if the tile is already selected
     * @param column column
     * @return boolean value
     */
    private boolean controllSelectioned(int column){
        Tile tile = selectionsTile.get(column);
        for(Tile sel : selectionsTileToSend){
            if(sel.getId() == tile.getId()){
                System.err.println("TILE ALREADY SELECTED FOR ORDER");
                return true;
            }
        }

        return false;
    }

    /**
     * Set Tile in the board
     * @param row Row coordinate
     * @param column Column coordinate
     * @param tile Tile to put in the Board
     */
    public void setTileBoardGui(int row, int column, Tile tile) {

        StackPane node = (StackPane) gridPaneBoard.getChildren().get(row * (gridPaneBoard.getColumnCount()) + column);
        ImageView imageView = (ImageView) node.getChildren().get(0);


        if (tile != null) {
            imageView.setImage(new Image(String.valueOf(getClass().getResource("/images/item_tiles/" + tile.getImageId() + ".png"))));
            posTileBoard.put(tile.getId(), new Pair<>(row, column));
        } else imageView.setImage(null);


    }

    /**
     * This method ahs the aim to update the bookshelf
     * @param row Row coordinate
     * @param column Column coordinate
     * @param id id name of the image to put in the bookshelf with this coordinate
     */
    public void updateBookshelfMainUser(int row, int column, String id) {


        StackPane node2 = (StackPane) gridPaneBookshelf.getChildren().get(row * (gridPaneBookshelf.getColumnCount()) + column);
        ImageView imageView2 = (ImageView) node2.getChildren().get(0);

        imageView2.setImage(new Image(String.valueOf(getClass().getResource("/images/item_tiles/" + id + ".png"))));
    }

    /**
     * Method to clear the order of the tiles selected
     */
    private void cleanOrderingArea(){
        first.setText("");
        second.setText("");
        third.setText("");
        returnCss(colBookShelf, STILE_PREDEFINITO, gridPaneBookshelf);
        colBookShelf = -1;
        posChoice = 1;
        selectionNumber = 0;
        selectColumn = false;

        selectionsTile = new ArrayList<>();
        selectionsTileToSend = new ArrayList<>();

        for(int i = 0; i<3; i++){
            setSelectionsArea(i, null, true);
        }
    }

    /**
     * Select the area to choose
     * @param pos pos in the selection area of the tile
     * @param tile tile to choose the order to put in the bookshelf
     * @param clean if true it'll clean the area
     */
    public void setSelectionsArea(int pos, Tile tile, boolean clean){
        System.out.println(choice.getChildren());

        if(!clean) {
            removeTileFromBoard(tile.getId());
            selectionsTile.add(tile);
        }


        StackPane node = (StackPane) choice.getChildren().get(pos);
        ImageView imageView = (ImageView) node.getChildren().get(0);

        if(!clean) imageView.setImage(new Image(String.valueOf(getClass().getResource("/images/item_tiles/"+tile.getImageId()+".png"))));
        else imageView.setImage(null);
    }

    /**
     * Method to remove the tile selected from the board
     * @param idTile tile's id
     */
    private void removeTileFromBoard(int idTile) {
        Pair<Integer, Integer> coord = posTileBoard.get(idTile);

        StackPane node = (StackPane) gridPaneBoard.getChildren().get(coord.getKey() * (gridPaneBoard.getColumnCount()) + coord.getValue());
        node.setStyle(STILE_PREDEFINITO);

        ImageView imageView = (ImageView) node.getChildren().get(0);

        imageView.setImage(null);

    }

    // when I request a particular bookshelf I go to set it -> I CALL IT FROM THE VIRTUAL MODEL

    /**
     * Set the bookshelf of the other player to see his bookshelf
     * @param bookshelf Bookshelf of the other player
     */
    public void setBookshelfOther(Bookshelf bookshelf) {
        Tile[][] bookshelfMatrix = bookshelf.getPositionTiles();
        StackPane node;
        ImageView imageView;

        for (int row = 0; row < Constant.BOOKSHELF_HEIGHT; row++) {
            for (int column = 0; column < Constant.BOOKSHELF_WIDTH; column++) {
                node = (StackPane) gridPaneBookshelfOthers.getChildren().get(row * (gridPaneBookshelfOthers.getColumnCount()) + column);
                imageView = (ImageView) node.getChildren().get(0);
                Tile tile = bookshelfMatrix[row][column];
                if (tile != null) {
                    imageView.setImage(new Image(String.valueOf(getClass().getResource("/images/item_tiles/" + tile.getImageId() + ".png"))));
                } else {
                    imageView.setImage(null);
                }
            }
        }





    }

    /**
     * set Personal Card
     * @param id Id of the PersonalCard to set
     */
    public void setPersonalCard(int id) {
        personalCard.setImage(new Image(String.valueOf(getClass().getResource("/images/personal_goal_cards/" + id + ".png"))));
    }

    /**
     * Set the two CommonCards of the game
     * @param id1 Id of the first CommonCard
     * @param id2 Id of the second CommonCard
     */
    public void setCommonCard(CommonObjectiveCard id1, CommonObjectiveCard id2){
        commonCard1.setImage(new Image(String.valueOf(getClass().getResource("/images/common_goal_cards/"+id1.getId()+".jpg"))));
        commonCard2.setImage(new Image(String.valueOf(getClass().getResource("/images/common_goal_cards/"+id2.getId()+".jpg"))));
        commonPoints1.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/scoring_8.jpg"))));
        commonPoints2.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/scoring_8.jpg"))));

        Platform.runLater(() -> {
            descTwo.setText(id2.getDescription());
            descOne.setText(id1.getDescription());
        });
    }

    /**
     * Set the username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to init the chat's parameters
     */
    private void initChat() {

        ArrayList<String> usernames = new ArrayList<>(virtualModel.getListOfUsername());
        usernames.add("all");

        // populate the chat box
        destinationChoiceBox.getItems().addAll(usernames);

        // default screen
        destinationChoiceBox.setValue("all");
        textAreaConversation.setEditable(false);

        destinationChoiceBox.setOnAction(actionEvent -> {
            String user = destinationChoiceBox.getValue();
            textAreaConversation.setText(chatHashmap.get(user));
        });

        sendButton.setOnAction(event -> {
                sendMessage();
        });

        messageText.setOnKeyPressed(event -> {
            if (event.getCode().getName().equals("Enter")) {
                sendMessage();
            }
        });
    }

    /**
     * Method to send a message on the chat
     */
    private void sendMessage(){
        String destination = destinationChoiceBox.getValue();
        String message = messageText.getText();

        String conversation = chatHashmap.get(destination);
        String newMessage = virtualModel.getVirtualMainUser().getUsername() + ": " + message + '\n';
        conversation = (conversation == null) ? "" : conversation;
        chatHashmap.put(destination, conversation + newMessage);
        messageText.setText("");

        if (destinationChoiceBox.getValue().equals(destination)) {
            textAreaConversation.setText(chatHashmap.get(destination));
        }

        guiController.sendChatMessage(message, destination);
    }

    /**
     * Init the commonCards and personalCard
     */
    public void initCard(){
        personalCard.setImage(new Image(String.valueOf(getClass().getResource("/images/personal_goal_cards/EMPTY.jpg"))));
        commonCard1.setImage(new Image(String.valueOf(getClass().getResource("/images/common_goal_cards/box.jpg"))));
        commonCard2.setImage(new Image(String.valueOf(getClass().getResource("/images/common_goal_cards/box.jpg"))));
        commonPoints1.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/scoring_back_EMPTY.jpg"))));
        commonPoints2.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/scoring_back_EMPTY.jpg"))));
    }

    /**
     * set points in the label of score
     */
    public void setPoints() {
        StringBuilder stringBuilder = new StringBuilder();
        List<VirtualUser> users = virtualModel.getVirtualUsers();

        stringBuilder.append("Points:\n");

        VirtualUser virtualUser = virtualModel.getVirtualMainUser();


        stringBuilder.append(virtualUser.getUsername()).append(": ").append(virtualUser.getCommonPoints());

        for (VirtualUser user : users) {
            stringBuilder.append("\n").append(user.getUsername()).append(": ").append(user.getCommonPoints());
        }

        Platform.runLater(()->{
            textPoints.setText(stringBuilder.toString());
        });

        gui.showAlertInfo(stringBuilder.toString());

        // image of CommonCards setting

        CommonObjectiveCard[] commonObjectiveCards = virtualModel.getCommonGoalCardsId();

        if (commonObjectiveCards[0]!=null && commonObjectiveCards[1]!=null) {
            commonPoints1.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/scoring_" + commonObjectiveCards[0].getPoints() + ".jpg"))));
            commonPoints2.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/scoring_" + commonObjectiveCards[1].getPoints() + ".jpg"))));
        }

        controllFirstPoint();
    }

    /**
     * Method to control and notify all users if a user has completed the first common goal card
     */
    private void controllFirstPoint(){
        VirtualUser virtualUser = virtualModel.getVirtualMainUser();
        StringBuilder stringBuilder = new StringBuilder();
        List<VirtualUser> users = virtualModel.getVirtualUsers();

        if(virtualUser.getFirstFinishedPoint() == 1 && !virtualUser.isSetShowFirst()){
            stringBuilder.append("You have received the first finished point!!!");
            virtualUser.setSetShowFirst(true);
            finishedPoint1.setImage(new Image(String.valueOf(getClass().getResource("/images/scoring_tokens/end_game.jpg"))));
            finishedPoint.setImage(null);
            gui.showAlertInfo(stringBuilder.toString());
            return;
        }
        for (VirtualUser user : users) {
            if(user.getFirstFinishedPoint() == 1 && !user.isSetShowFirst()){
                stringBuilder.append(user.getUsername()).append(" has received the first finished point!!!");
                user.setSetShowFirst(true);
                finishedPoint.setImage(null);
                gui.showAlertInfo(stringBuilder.toString());
                return;
            }
        }
    }

    /**
     * Set the point of the game
     * @param points list of the users' points
     */
    public void setPointFinal(List<Pair<String, Integer>> points){

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Points:");

        for (Pair<String, Integer> user : points) {
            stringBuilder.append("\n").append(user.getKey()).append(": ").append(user.getValue());
        }


        Platform.runLater(() -> {
            textPoints.setText(stringBuilder.toString());
        });

        gui.showAlertInfo(stringBuilder.toString());
    }

    /**
     * Add message in the chat area
     * @param author Who send the message
     * @param destination The receiver of the message
     * @param message Message sent by author
     */
    public void addMessage(String author, String destination, String message) {
        if (!author.equals(virtualModel.getVirtualMainUser().getUsername())) {
            if (destination.equals("all")) {
                // destination is equal all
                String conversation = chatHashmap.get(destination);
                String newMessage = author + ": " + message + '\n';
                conversation = (conversation == null) ? "" : conversation;
                chatHashmap.put(destination, conversation + newMessage);

                if (destinationChoiceBox.getValue().equals(destination)) {
                    textAreaConversation.setText(chatHashmap.get(destination));
                }
            } else {
                String conversation = chatHashmap.get(author);
                String newMessage = author + ": " + message + '\n';
                conversation = (conversation == null) ? "" : conversation;
                chatHashmap.put(author, conversation + newMessage);

                if (destinationChoiceBox.getValue().equals(author)) {
                    textAreaConversation.setText(chatHashmap.get(author));
                }
            }
        }
    }

    /**
     * SetMyTurn
     * @param myTurn myTurn
     */
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * setSelectionNumber
     * @param num num
     */
    public void setSelectionNumber(int num) {
        this.selectionNumber = num;
    }

    /**
     * Method to update the other bookshelves of the other users
     */
    public void updateBookshelfOther() {
        setBookshelfOther(virtualUserOtherBookshelfDisplayed.getBookshelf());
    }
}

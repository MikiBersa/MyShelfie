package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.User;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.InterfaceNetwork;
import it.polimi.ingsw.server.network.Message.MessageByClient;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import it.polimi.ingsw.server.network.Message.output.*;
import it.polimi.ingsw.server.network.Observer.oberserverIn.InputInterface;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observable;
import it.polimi.ingsw.utils.store.StoreReader;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.Constant.MessageNumber.RETURN_QUIT;

/**
 * Router handles the communication between InterfaceNetwork and server, so it handles the message
 */
public class Router implements InputInterface, ServerVisitor {
    private Controller controller;
    private int idPlayer;

    private ArrayList<User> playersLobby;

    private boolean createdGame;

    private int nextIdTurn;

    private int creatorIdGame;

    private final InterfaceNetwork interfaceNetwork;

    private final Observable menagmentObserver;

    private List<Tile> temporarySelection;

    /**
     * Constructor Router
     *
     * @param interfaceNetwork references to the interfaceNetwork
     * @param observable       to handle the new message sent by client
     */
    public Router(InterfaceNetwork interfaceNetwork, Observable observable) {
        controller = new Controller();
        this.interfaceNetwork = interfaceNetwork;
        this.menagmentObserver = observable;
        this.createdGame = false;
        this.playersLobby = new ArrayList<>();
        this.temporarySelection = new ArrayList<>();

        this.creatorIdGame = -1;
        this.nextIdTurn = -1;
    }

    //make a sorter with an observer
    //here update and read message type and decide who to send from messageNumber

    /**
     * Method handles the receiving message
     *
     * @param id      id of player that has sent the message
     * @param message the message sent by client
     */
    @Override
    public void update(int id, MessageByClient message) {
        this.idPlayer = id;
        message.sendToRouter(this);
    }

    /**
     * It sends the ChatMessage to the client
     *
     * @param message     message that has sent by Client to other Client
     * @param destination The destination od the message
     */
    @Override
    public void sendMessageChat(String message, String destination) {

        MessageByServer messageKoSent;
        MessageByServer messageToClients;

        String username = controller.getUsername(idPlayer);

        if (!createdGame) {
            messageKoSent = new ResponseChatMessage("KO GAME_NOT_CREATED", username, username);
            this.interfaceNetwork.send(this.idPlayer, messageKoSent);
            return;
        }

        // messageOkSent = new ResponseChatMessage(this.idPlayer, RETURN_STRING.ordinal(), "OK SENT MESSAGE", username,true);

        messageToClients = new ResponseChatMessage(message, username, destination);

        // this.interfaceNetwork.sendPersonalized(this.idPlayer, messageToClients, messageOkSent);
        if (destination.equals("all")) {
            this.interfaceNetwork.send(-1, messageToClients);
        } else {
            User user = controller.getUserPos(destination);
            this.interfaceNetwork.send(user.getId(), messageToClients);
            this.interfaceNetwork.send(this.idPlayer, messageToClients);
        }

    }


    // I SEND WHEN USER ASK TO SEND THE GAME
    // FIRST STAYS IN THE STALL ZONE -> LI SHOULD CHECK FOR NON-EXISTENCE

    /**
     * Creation of the game
     *
     * @param players The number of players in the game, which is chosen by the first player
     */
    @Override
    public void createGame(int players) {

        MessageByServer messageToClient;
        String messageToClientString;

        boolean start = false;

        if(creatorIdGame != idPlayer) {

            messageToClient = new AdminMessage("KO YOU_CAN_NOT_CREATE_GAME", false);
            this.interfaceNetwork.send(idPlayer, messageToClient);
            return;
        }

        if(createdGame){
            // GAME_ALREADY_CREATED
            messageToClient = new ResponseNumberOfPlayerMessage(false);
            this.interfaceNetwork.send(idPlayer, messageToClient);
            return;
        }

        if(players < 2 || players > 4){
            messageToClient = new ResponseNumberOfPlayerMessage(false);

            this.interfaceNetwork.send(creatorIdGame, messageToClient);

            return;
        }

        messageToClient = new ResponseNumberOfPlayerMessage(true);
        this.interfaceNetwork.send(idPlayer, messageToClient);


        if(controller.createGame(players, this.menagmentObserver)){

            this.createdGame = true;
            int returnNumber;

            boolean toRemove = false;

            // tutti quelli nella lobby li inserisco
            for(User user : playersLobby){
                returnNumber = controller.addUser(user.getId(), user.getUsername());
                start = controller.full();

                boolean status = false;

                if(returnNumber != -2){
                    messageToClientString = "OK YOU_ADDED_TO_GAME";
                    status = true;
                }else {
                    // IN QUESTO CASO L'UTENTE NON DOVREBBE RICEVERE I BROADCAST
                    messageToClientString = "KO OVER_LIMIT_PLAYER";
                    // li rimuovo dalle liste
                    toRemove = true;
                }

                messageToClient = new AddedInGameMessage(status);
                this.interfaceNetwork.send(user.getId(), messageToClient);

                if(toRemove){
                    this.interfaceNetwork.RemoveFromNetwork(user.getId());
                    toRemove = false;
                }
            }
            //qui dire sei stato inserito nel game
        }


        if (start) {

            // invio del setup
            startGame();
        }
    }

    /**
     * Method adds the player in the game
     *
     * @param username The username of the player to add in the game
     */
    @Override
    public void addUser(String username) {
        // if it returns -1 -> there is no game then it is created
        // if it returns 0 -> username already created
        // if it returns 1 -> it's ok

        boolean start = false;
        int returnControllerUsername;
        MessageByServer messageToClientMessage;

        /*

         connects -> gets id -> asks for username
             -> server: check game existence
                         if it exists -> check username
                                         -> already used returns KO ALREADY_USERNAME_USED
                                         -> okay return OK
                         if it doesn't exist -> it returns me to the client ok username and the game has been created

             you could create a new parameter like ack -> which tells me if the message is ok or not
          */
        if(!createdGame){
            boolean controllo = userNameCheck(username);
            // I say to create if there isn't already a game being created

            if(controllo){

                messageToClientMessage = new ResponseUsernameMessage(idPlayer, username, true);
                this.interfaceNetwork.send(idPlayer, messageToClientMessage);

                if (creatorIdGame == -1) {
                    // HERE I WILL GET THERE IF THE GAME HAS BEEN CREATED

                    messageToClientMessage = new AdminMessage( "OK YOU_CAN_CREATE_GAME", true);
                    this.interfaceNetwork.send(idPlayer, messageToClientMessage);

                    this.creatorIdGame = idPlayer;

                }else {

                    messageToClientMessage = new AdminMessage("OK IN_LOBBY", false);
                    this.interfaceNetwork.send(idPlayer, messageToClientMessage);

                }

                // I add the player in the lobby
                this.playersLobby.add(new User(idPlayer, username));

            }else{
                messageToClientMessage = new ResponseUsernameMessage(idPlayer, username, false);
                this.interfaceNetwork.send(idPlayer, messageToClientMessage);
            }

        } else {
            // I'm going to add users in the game
            returnControllerUsername = controller.addUser(idPlayer, username);

            if (returnControllerUsername == 1) {
                // HERE I GET THERE IF THE GAME HAS BEEN CREATED
                messageToClientMessage = new ResponseUsernameMessage(idPlayer, username, true);
                this.interfaceNetwork.send(idPlayer, messageToClientMessage);

                messageToClientMessage = new AddedInGameMessage(true);
                this.interfaceNetwork.send(idPlayer, messageToClientMessage);


                start = this.controller.full();

            }else if(returnControllerUsername == 0) {

                System.err.println("ALREADY_USERNAME_USED addUser");

                messageToClientMessage = new ResponseUsernameMessage(idPlayer, username, false);
                this.interfaceNetwork.send(idPlayer, messageToClientMessage);

            }else if(returnControllerUsername == -2){
                System.err.println("OVER_LIMIT_PLAYER addUser");

                messageToClientMessage = new AddedInGameMessage(false);
                this.interfaceNetwork.send(idPlayer, messageToClientMessage);
                this.interfaceNetwork.RemoveFromNetwork(this.idPlayer);
            }

        }

        if (start) {
            // all the player are connected and added in the game
            startGame();
        }
    }

    /**
     * Method starts the game
     */
    private void startGame() {
        MessageByServer messageToClient;
        MessageByServer messageToNotTurn;

        List<Pair<String, Integer>> listOfPlayers = controller.getPlayers();

        // I SEND TO EVERYONE THE GAME CREATION WITH THE NUMBER OF PLAYERS
        messageToClient = new CreationGameMessage(listOfPlayers.size(), listOfPlayers);

        this.interfaceNetwork.send(-1, messageToClient);
        int id;

        if (controller.understandRestore()) {
            System.out.println("I AM IN RESTORE");
            id = controller.startStoreGame();
        }else{
            StoreReader.eliminate();
            id = controller.startGame();
        }

        System.out.println("TURN START: " + id);


        if (id != -1) {
            // MESSAGE CONSTANT END SETUP - BEGIN GAME

            // WHOSE TURN SEND THE TURN
            messageToClient = new IsYourTurnMessage(id, "your");
            String username = controller.getUsername(id);

            messageToNotTurn = new IsYourTurnMessage(-1, username);
            this.interfaceNetwork.sendPersonalized(id, messageToNotTurn, messageToClient);

            this.nextIdTurn = id;

            // save in the store
            controller.setTurnStorage(id);

        } else {
            System.err.println("ERROR IN STARTING THE GAME MATCH: controller.startGame");
        }
    }

    /**
     * Method handlers the action of taking tile from the board
     *
     * @param selectedCoordinates coordinates of selections
     */
    @Override
    public void takeCards(List<Pair<Integer, Integer>> selectedCoordinates) {

        MessageByServer messageToClient;
        if (idPlayer != nextIdTurn) {
            messageToClient = new IsYourTurnMessage(this.idPlayer, "IT'S NOT YOUR TURN");
            this.interfaceNetwork.send(idPlayer, messageToClient);
            return;
        }

        List<Tile> returnFunction  = controller.takeCards(idPlayer, selectedCoordinates);
        boolean status = false;

        if(returnFunction != null){
            controller.setSelections(returnFunction);
            status = true;
        } else {
            System.err.println("RETURN NULL FROM LIST TILE SELECTION: takeCards");
        }

        this.temporarySelection = returnFunction;

        messageToClient = new ResponseListTileMessage(status, returnFunction);
        this.interfaceNetwork.send(this.idPlayer, messageToClient);
    }

    // return the already sorted tiles to the server
    // deletable user identifier I take it from the socket

    /**
     * Method handlers the action of putting tile in the bookshelf
     *
     * @param column   The column in which the player wants to put the tile in his bookshelf
     * @param tileList The list of tiles selected in the takeCards method
     */
    @Override
    public void putTilesInBookshelf(int column, List<Tile> tileList) {

        if (idPlayer != nextIdTurn) {
            MessageByServer messageToClientMessage = new IsYourTurnMessage(this.idPlayer, "IT'S NOT YOUR TURN");
            this.interfaceNetwork.send(idPlayer, messageToClientMessage);
            return;
        }

        String messageToClient = null;
        boolean control = false;


        int cod = controller.putTilesInBookshelf(this.idPlayer, column, tileList);
        if(cod == 1){
            messageToClient = "true";
            control = true;
            this.temporarySelection = tileList;

        }else if(cod == 0){
            messageToClient = "THEY AREN'T THE SAME";
            System.err.println(this.idPlayer+": RETURN FALSE FROM putTilesInBookshelf "+messageToClient);
        }else if(cod == -1){
            messageToClient = "YOU HAVEN'T CHOSEN THE SELECTION TILES";
            System.err.println(this.idPlayer+": RETURN FALSE FROM putTilesInBookshelf "+messageToClient);
        }else if(cod == -2){
            messageToClient = "YOU HAVEN'T SPACE IN YOUR BOOKSHELF";
            System.err.println(this.idPlayer + ": RETURN FALSE FROM putTilesInBookshelf " + messageToClient);
        }

        //definitive response of the call but in the meantime if all is well the model upgrades have already been sent
        MessageByServer messageToClientMessage = new ResponseOrderAndBookshelfMessage(control, this.temporarySelection);
        this.interfaceNetwork.send(idPlayer, messageToClientMessage);


        if (control) finishedTurn();
    }

    /**
     * Quit message send by the client to interrupt his game
     */
    @Override
    public void quitMessage() {
        disconnetingFinishGame();
    }

    /**
     * Disconnection is a message that is sent by server to server in order to handle the unintentional disconnection
     */
    @Override
    public void disconnection() {
        disconnetingFinishGame();
    }

    /**
     * This method handles the finishing of game by disconnection
     */
    public void disconnetingFinishGame() {
        try {

            MessageByServer messageToClient = OutputFactory.getParser(RETURN_QUIT.ordinal(), this.controller.getUsername(this.idPlayer));
            // SEND BROADCAST MESSAGE
            this.interfaceNetwork.send(-1, messageToClient);

            // I DISCONNECT EVERYONE
            this.interfaceNetwork.RemoveAllNetwork();
            // CLEAN ALL GAME MEMORY
            // TODO TO CONTROL
            cleanGame();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method to clean the game
     */
    private void cleanGame() {
        this.controller.cleanGame();
        this.createdGame = false;
        this.creatorIdGame = -1;
        this.playersLobby = new ArrayList<>();
        this.controller = new Controller();
    }

    /**
     * This method handle the finishing of turn
     */
    private void finishedTurn() {

        // make a message on purpose of the points -> WHICH SENDS THE SUMMARY OF ALL IN THE BODY OBJECT
        MessageByServer messageToClient;
        if (!controller.controlCompletedCommonObjective(this.idPlayer)) {
            System.out.println("KO NO_COMMON_POINTS");
        }

        // setting the point for the first to finish the bookshelf in that case sets true last turn
        if (!controller.controlCompletedBookshelf(this.idPlayer)) {
            System.out.println("KO NO_POINT_FIRST");
        }

        // I check the refill
        // I ask who's next
        nextIdTurn = this.controller.nextTurn();
        System.out.println("NEXT TURN: " + nextIdTurn);

        // save in the store
        controller.setTurnStorage(nextIdTurn);

        // returns -1 when the game is over
        if (nextIdTurn != -1) {

            if(!this.controller.controlBoardRefill()){
                System.out.println("NO REFILL");

            }

            messageToClient = new IsYourTurnMessage(nextIdTurn, "your");
            String username = controller.getUsername(nextIdTurn);

            MessageByServer messageToNotTurn = new IsYourTurnMessage(-1, username);

            this.interfaceNetwork.sendPersonalized(nextIdTurn, messageToNotTurn, messageToClient);
        } else {
            // returns all the scores of players and users
            messageToClient = new EndGameMessage();
            this.interfaceNetwork.send(-1, messageToClient);
            controller.endGame();

            StoreReader.eliminate();
            // here the game ends
        }
    }

    /**
     * Cheack if the username is already used in the game
     *
     * @param username username to check
     * @return false if It's already used
     */
    private boolean userNameCheck(String username) {
        for (User user : playersLobby) {
            if (user.getUsername().equals(username)) return false;
        }

        return true;
    }

    /**
     * Ping message to control if the client is still connected
     */
    public void ping() {
        System.out.println("PING BY USER: "+this.idPlayer);
    }
}

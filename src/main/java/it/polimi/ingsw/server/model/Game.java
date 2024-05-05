package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.PersonalObjectiveCard;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCardFactory;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observable;
import it.polimi.ingsw.utils.store.StoreGame;
import it.polimi.ingsw.utils.store.StoreReader;
import javafx.util.Pair;

import java.util.*;

import static it.polimi.ingsw.Constant.*;
import static it.polimi.ingsw.Constant.MessageNumber.*;

/**
 * Game
 */
public class Game {
    private ArrayList<User> users;
    private Board board;
    private int numberPlayers;
    private ArrayList<CommonObjectiveCard> commonObjectiveCards;
    private int playerTurn;
    private int firstPlayer;
    private boolean lastTurn;
    private AdjacentTiles adjacentTiles;
    private int winner;

    private final Observable notify;

    //add CommonObjectiveCard -> shuffle at the start of the game
    //here add the common card that is the one on the table 6+

    /**
     * Constructor of the game
     * @param numberPlayers number of players
     * @param notify reference to interfaceNetwork
     */
    public Game(int numberPlayers, Observable notify) {
        this.users = new ArrayList<>();
        this.board = null;
        this.numberPlayers = numberPlayers;
        this.commonObjectiveCards = null;
        this.lastTurn = false;
        adjacentTiles = null;
        this.notify = notify;
    }

    /**
     * Method that controls if the game can start based upon the equivalence between the number of players waiting
     * and the number of players selected
     * @return boolean value
     */
    public boolean canStart() {
        return numberPlayers == users.size();
    }

    /**
     * Method that initializes the common objective card and the personal objective card for every user.
     * It initializes even the file of restored game
     */
    public void initialize() {
        StoreGame storeGame = StoreReader.getStoreGame();
        //tutte cose da inviare al client
        board = new Board(numberPlayers, notify);
        //board.setNotify(notify);

        commonObjectiveCards = new ArrayList<>();
        adjacentTiles = new AdjacentTiles();

        List<Integer> personalCardId = new ArrayList<>();
        for (int i = 0; i < PERSONAL_GOAL_CARDS; i++) {
            personalCardId.add(i);
        }
        Collections.shuffle(personalCardId);

        int i = 0;
        for (User user : users) {
            user.setNotify(notify);
            user.initialize(new PersonalObjectiveCard(personalCardId.get(i)));
            storeGame.setUser(user.getUserStore());
            i++;
        }

        List<Integer> commonCardId = new ArrayList<>();
        for (i = 0; i < COMMON_GOAL_CARDS; i++) {
            commonCardId.add(i);
        }
        Collections.shuffle(commonCardId);

        //factoryMethod
        for (i = 0; i < COMMON_GOAL_CARDS_PER_GAME; i++) {
            // updateCommonCards(commonCardId.get(i));
            commonObjectiveCards.add(CommonObjectiveCardFactory.getCommonObjectiveCard(commonCardId.get(i), numberPlayers));
        }

        updateCommonCards(commonObjectiveCards);

    }

    /**
     * Initializes the common objective card and the personal objective card for every user
     * based upon the restoration file
     */
    public void initializeFromStore(StoreGame storeGame) {
        //tutte cose da inviare al client
        board = new Board(storeGame.getPlayers().size(), storeGame.getBoard(), notify, storeGame.getDeck());
        // la mando agli user
        board.updateBoard();

        commonObjectiveCards = new ArrayList<>();
        adjacentTiles = new AdjacentTiles();

        int i = 0;
        for (User user : users) {

            UserStore userStore = storeGame.getPlayers().get(user.getUsername());
            user.setNotify(notify);
            PersonalObjectiveCard personalObjectiveCard = userStore.getPersonalObjectiveCard();
            user.initialize(personalObjectiveCard);

            user.setFinalPoints(userStore.getFinalPoints());
            user.setFirstFinished(userStore.getFirstFinished());
            user.setCommonDoneCards(userStore.getCommonDoneCards());
            user.setCommonPoints(userStore.getCommonPoints());

            // così viene mandato anche ai singoli giocatori
            user.setBookshelf(userStore.getBookshelf());

            i++;
        }

        // factoryMethod
        for (i = 0; i < COMMON_GOAL_CARDS_PER_GAME; i++) {
            int id = storeGame.getCommonObjectiveCards().get(i).getId();
            commonObjectiveCards.add(CommonObjectiveCardFactory.getCommonObjectiveCard(id, numberPlayers));
        }

        updateCommonCards(commonObjectiveCards);

    }

    /**
     * helper method
     * @param idCommonCards common objective card's id
     */
    private void updateCommonCards(ArrayList<CommonObjectiveCard> idCommonCards){
        // posso direttamente mandare List
        CommonObjectiveCard[] id = new CommonObjectiveCard[idCommonCards.size()];
        StoreGame storeGame = StoreReader.getStoreGame();

        id[0] = idCommonCards.get(0);
        id[1] = idCommonCards.get(1);

        storeGame.setCommonObjectiveCards(idCommonCards);

        this.notify.notify(-1, RETURN_ID_COMMON_CARDS.ordinal(), id);
    }

    /**
     * Method that adds the player to the game
     * @param id user's id
     * @param username  user's username
     * @return code
     */
    public int addUserToGame(int id, String username) {
        if (users.size() < numberPlayers) {
            if(canAddUser(username)) {
                users.add(new User(id, username));
                return 1;
            }else {
                return 0;
            }
        }

        return -2;
    }

    /**
     * Helper method
     * @param username user's username
     * @return boolean value
     */
    private boolean canAddUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * getUsername
     * @param id id
     * @return username
     */
    public String getUsername(int id){
        String username = null;

        for(User user : users){
            if(user.getId() == id) username = user.getUsername();
        }

        return username;
    }

    /**
     * getPlayers
     * @return Return the players of the game
     */
    public ArrayList<User> getPlayers(){return this.users;}

    /**
     * setPlayers
     * @param players players
     */
    public void setPlayers(ArrayList<User> players){this.users = players;}

    /**
     * getUsers
     * @return The user of the game
     */
    public List<Pair<String, Integer>> getUsers(){
        ArrayList<Pair<String, Integer>> usersGet = new ArrayList<>();

        for(User user : users){
            usersGet.add(new Pair<>(user.getUsername(), user.getId()));
        }

        return usersGet;
    }

    /**
     * Method that controls if the cards are valid (the tiles exist in the board) and then control if they have
     * at least 1 side free
     * @param selectedCoordinates selected coordinates
     * @return boolean value
     */
    public boolean controlSelectedCoordinates(List<Pair<Integer, Integer>> selectedCoordinates) {
        for (Pair<Integer, Integer> selectedCoordinate : selectedCoordinates) {
            int i = selectedCoordinate.getKey();
            int j = selectedCoordinate.getValue();
            if (i < 0 || j < 0 || i >= BOARD_HEIGHT || j >= BOARD_WIDTH ||
                    !board.isCoordinateValid(selectedCoordinate) ||
                    !board.isAtLeastOneSideFree(selectedCoordinate)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method that takes the tiles from the board
     * @param selectedCoordinates lista of selected coordinates
     * @return tilelist list of taken tiles
     */
    public List<Tile> takeTiles(List<Pair<Integer, Integer>> selectedCoordinates) {
        List<Tile> tileList = new ArrayList<>();
        for (Pair<Integer, Integer> selectedCoordinate : selectedCoordinates) {
            tileList.add(board.removeCard(selectedCoordinate));
        }

        // modifica del modello
        this.board.updateBoard();

        return tileList;
    }

    /**
     * Method that return the next turn player's id
     * @return id
     */
    public int nextTurn() {
        int nextTurn = (playerTurn + 1) % numberPlayers;

        if (lastTurn && nextTurn == firstPlayer) {
            calculateFinalPointsAndWinner();
            return -1;
        }

        playerTurn = nextTurn;

        return users.get(playerTurn).getId();
    }

    /**
     * setPlayerTurn
     * @param pos position
     */
    public void setPlayerTurn(int pos){this.playerTurn = pos;}

    /**
     * Method that return the first player's id
     * @return id
     */
    public int startPlayer() {
        int pos = new Random().nextInt(numberPlayers);
        // memorizzare qui il primo giocatore
        firstPlayer = users.get(pos).getId();
        this.playerTurn = pos;

        return users.get(pos).getId();
    }

    /**
     * Method that controls if the user has enough space for the selection
     * @param player player's number
     * @param column column
     * @param spaceRequired space required
     * @return boolean value
     */
    public boolean controlUserSpace(int player, int column, int spaceRequired) {
        // TODO da controllare
        return getUserFromId(player).controlSpaceBookshelf(column, spaceRequired);
    }

    /**
     * Alternative method
     * @param player player's number
     * @param spaceRequired space required
     * @return boolean value
     */
    public boolean controlUserSpace(int player, int spaceRequired) {
        return getUserFromId(player).controlSpaceBookshelf(spaceRequired);
    }

    /**
     * Method that inserts the player's tiles on his bookshelf in the right column
     * @param player player's id
     * @param column column
     * @param tileList list of selected tiles
     */
    public void putTilesInBookshelf(int player, int column, List<Tile> tileList) {
        User user = getUserFromId(player);
        StoreGame storeGame = StoreReader.getStoreGame();

        for (Tile tile : tileList) {
            user.setTileInBookshelf(column, tile);
        }

        storeGame.setUser(user.getUserStore());

        user.updateUser(-1, RETURN_BOOKSHELF.ordinal(), new Pair<>(user.getUsername(), user.getBookshelf()));
    }

    /**
     * Method that controls if a user has completed the common objective cards
     * @param player player's id
     * @return boolean value
     */
    public boolean controlCompletedCommonObjective(int player) {
        boolean done = false;

        for (int i = 0; i < COMMON_GOAL_CARDS_PER_GAME; i++) {
            CommonObjectiveCard commonObjectiveCard = commonObjectiveCards.get(i);
            if(users.get(player).calculateCommonObjectiveCard(commonObjectiveCard, i, commonObjectiveCard.getId())) done = true;
        }

        return done;
    }

    /**
     * Method that controls if the player's bookshelf is full
     * @param player player's id
     * @return boolean value
     */
    public boolean controlCompletedBookshelf(int player) {
        // TODO CONTROL
        if (users.get(player).controlCompletedBookshelf() && !lastTurn) {
            lastTurn = true;
            return true;
        }
        return false;
    }

    /**
     * Method that controls if the board ha only isolated tiles and in that case it resets the board
     * inserting the tiles in different places
     * @return boolean value
     */
    public boolean controlBoardRefill() {
        if (board.areAllCardsSingle()) {
            board.refill();
            return true;
        }

        return false;
    }

    /**
     * Method that calculates the points of every player and determines the winner
     */
    private void calculateFinalPointsAndWinner() {
        int maxFinalPoints = -1;
        for (int i = 0; i < numberPlayers; i++) {
            User player = users.get(i);
            player.calculateFinalPoints(adjacentTiles);

            if (maxFinalPoints < player.getFinalPoints()) {
                winner = i;
                maxFinalPoints = player.getFinalPoints();
            }
        }
    }

    /**
     * getUserFrom Id
     * @param id id
     * @return User
     */
    private User getUserFromId(int id){
        User userU = null;

        for(User user : users) {
            if(user.getId() == id) userU = user;
        }

        return userU;
    }

    /**
     * Method that is executed at the end of the game and notify the players the winner ant their final points
     */
    public void endGame(){
        ArrayList<Pair<String, Integer>> messagePoint = new ArrayList<>();

        int pointMax = 0;
        String userNameWinner = null;
        int idUserWinner = -1;

        for(User user : users){
            int point;
            String userName = user.getUsername();

            user.calculateFinalPoints(adjacentTiles);
            point = user.getFinalPoints();

            if(point > pointMax){
                pointMax = point;
                userNameWinner = userName;
                idUserWinner = user.getId();
            }

            messagePoint.add(new Pair<>(userName, point));
        }

        this.notify.notify(-1, RETURN_POINT_FINAL.ordinal(), messagePoint);
        this.notify.notify(-1, RETURN_WINNER.ordinal(), new Pair<>(idUserWinner, userNameWinner));
    }
}

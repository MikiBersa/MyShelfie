package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.User;
import it.polimi.ingsw.server.model.UserStore;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observable;
import it.polimi.ingsw.utils.store.StoreGame;
import it.polimi.ingsw.utils.store.StoreReader;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Tasked server-side controller
 * to manage the logic of the game
 */
public class Controller {
    private Game game;

    private List<Tile> selections;

    private StoreGame storeGame;

    /**
     * creation of the controller and get the handler of storage
     */
    public Controller() {
        game = null;
        this.selections = new ArrayList<>();
        StoreReader.controllStore();
        this.storeGame = StoreReader.getStoreGame();
        this.storeGame = StoreReader.restore();
    }

    /***
     * A method that cleans the game parameters
     */
    public void cleanGame(){
        this.game = null;
        this.selections = null;
        this.storeGame = null;
    }

    /***
     * A method that controls the stored game's file and establish if it's restorable
     * @return boolean value
     */
    public boolean understandRestore(){
        StoreGame storeGame = StoreReader.getStoreGame();
        if(!storeGame.isStatus()) return false;

        List<Pair<String, Integer>> users = game.getUsers();
        HashMap<String, UserStore> playerUsers = storeGame.getPlayers();


        System.out.println(storeGame.getPlayers());
        System.err.println(users.size());
        System.err.println(playerUsers.size());


        if(users.size() != playerUsers.size()){
            System.err.println("THEY HAVEN'T THE SAME LENGTH");
            return false;
        }

        for(Pair<String, Integer> user : users){
            if(playerUsers.get(user.getKey()) == null){
                System.err.println("THERE ISN'T A MATCHING");
                return false;
            }
        }

        return true;
    }

    /**
     * set the handler of storage
     * @param storeGame the handler of storage
     */
    public void setStoreGame(StoreGame storeGame){
        this.storeGame = storeGame;
    }

    /**
     * get the handler of storage
     * @return the handler of storage
     */
    public StoreGame getStoreGame(){return storeGame;}

    /***
     * A method that return the turn's id from the stored game's file
     * @return id
     */
    public int getIdTurnFromStorage(){
        StoreGame storeGame = StoreReader.getStoreGame();
        int id = -1;
        List<Pair<String, Integer>> users = game.getUsers();

        for(Pair<String, Boolean> user : storeGame.getTurns()){
            if(user.getValue()){
                // looking for the id of this game
                id = idUserFormStorage(users, user.getKey());
                rearrangementPlayers();
                return id;
            }
        }

        return id;
    }

    /**
     * reordering of the player array for the correct storage of the order of play
     */
    private void rearrangementPlayers(){
        StoreGame storeGame = StoreReader.getStoreGame();
        // the starting one created in the new game
        // ArrayList<User> players = game.getPlayers();
        // sorted return
        ArrayList<User> returnPlayer = new ArrayList<>();

        // the one saved to disk that holds the order before
        ArrayList<Pair<String, Boolean>> pairs = storeGame.getTurns();


        // knows old order
        for(Pair<String, Boolean> pair : pairs){
            returnPlayer.add(getUserPos(pair.getKey()));
        }

        game.setPlayers(returnPlayer);
    }

    /**
     * search the user with this username
     * @param username player's username
     * @return return the User with this username
     */
    public User getUserPos(String username){
        ArrayList<User> players = game.getPlayers();
        for(User user1 : players){
            if(user1.getUsername().equals(username)) return user1;
        }

        return null;
    }

    /**
     * lookup user id from file storage
     * @param users list of player in the game
     * @param username username of the player that you want to search the id
     * @return return the id of the user
     */
    private int idUserFormStorage(List<Pair<String, Integer>> users, String username){
        for(Pair<String, Integer> pair : users){
            if(pair.getKey().equals(username)) return pair.getValue();
        }

        return -1;
    }

    /**
     * method to create a game
     * @param players  number of players
     * @return true if it is created, false if it is already created
     */
    public boolean createGame(int players, Observable notify) {
        if (game == null) {
            game = new Game(players, notify);
            return true;
        }
        return false;
    }

    /**
     * Method that adds a user to the game
     * @param id of the user
     * @param username of the user
     * @return boolean value
     */
    public int addUser(int id, String username) {
        return game.addUserToGame(id, username);
    }

    /**
     * getUsername
     * @param id id of the player that you want to have the username
     * @return username of player
     */
    public String getUsername(int id){
        if(game == null) return null;
        return game.getUsername(id);
    }

    /**
     * Method that return if the game can start
     * @return boolean value
     */
    public boolean full(){
        return game.canStart();
    }

    /**
     * method to start and initialize the game
     * @return the index of the player that has to start the game
     */
    public int startGame() {
        if (game != null && game.canStart()) {
            game.initialize();
            return game.startPlayer();
        }
        return -1;
    }

    /**
     * method to start and initialize the game
     * @return the index of the player that has to start the game
     */
    public int startStoreGame() {
        if (game != null && game.canStart()) {
            game.initializeFromStore(StoreReader.getStoreGame());
            return getIdTurnFromStorage();
        }
        return -1;
    }

    /**
     * method to determine the next player
     * @return the index of the next player
     */

    public int nextTurn() {
        return game.nextTurn();
    }

    /**
     * Method that sets the game's turn reloading it from the storage
     * @param id of the player
     */
    public void setTurnStorage(int id){
        StoreGame storeGame = StoreReader.getStoreGame();
        // AND ID / USERNAME OF WHOM IT WOULD TOUCH
        ArrayList<Pair<String, Boolean>> turns = new ArrayList<>();
        // HERE TO TAKE THE ORDER OF PEOPLE
        List<Pair<String, Integer>> users = getPlayers();
        int pos = 0;

        for(Pair<String, Integer> player : users){
            boolean turnId = player.getValue() == id;

            if(turnId){
                // update where to start in the game
                game.setPlayerTurn(pos);
            }
            turns.add(new Pair<>(player.getKey(), turnId));
            pos ++;
        }

        storeGame.setTurns(turns);
    }

    /**
     * return of players
     * @return return the list of users with for each one the association username and id
     */
    public List<Pair<String, Integer>> getPlayers(){
        return game.getUsers();
    }

    // In one pass the network transfer the coordinates and the column where the player wants to insert the tiles to this function
    // Control also if the tiles are not the same
    // Control if the tiles are not null, control if they can be taken and control if they are on a line
    // If I don't want to sort the tiles, I can pass a clone and then do the controls on it

    /**
     * method to control the coordinates and if the tiles are aligned and then take them
     * @param player that takes the tiles
     * @param selectedCoordinates coordinates selected by the player
     * @return list of the tiles selected, otherwise null
     */
    public List<Tile> takeCards(int player, List<Pair<Integer, Integer>> selectedCoordinates) {
        if (selectedCoordinates.size() == 0 || selectedCoordinates.size() > 3) return null;
        if (!game.controlSelectedCoordinates(selectedCoordinates)) return null;
        if (!game.controlUserSpace(player, selectedCoordinates.size())) return null;
        if (selectedCoordinates.size() == 1) return game.takeTiles(selectedCoordinates);

        boolean horizontal = false, vertical = false;

        if (selectedCoordinates.get(0).getKey().equals(selectedCoordinates.get(1).getKey()) &&
                (selectedCoordinates.size() == 2 || selectedCoordinates.get(0).getKey().equals(selectedCoordinates.get(2).getKey()))) {
            horizontal = true;
        } else if (selectedCoordinates.get(0).getValue().equals(selectedCoordinates.get(1).getValue()) &&
                (selectedCoordinates.size() == 2 || selectedCoordinates.get(0).getValue().equals(selectedCoordinates.get(2).getValue()))) {
            vertical = true;
        }

        if (!horizontal && !vertical) return null;

        if (horizontal) {
            selectedCoordinates.sort(Comparator.comparingInt(Pair::getValue));

            for (int i = 0; i < selectedCoordinates.size() - 1; i++) {
                if (selectedCoordinates.get(i + 1).getValue() - selectedCoordinates.get(i).getValue() != 1) {
                    return null;
                }
            }

        } else {
            selectedCoordinates.sort(Comparator.comparingInt(Pair::getKey));

            for (int i = 0; i < selectedCoordinates.size() - 1; i++) {
                if (selectedCoordinates.get(i + 1).getKey() - selectedCoordinates.get(i).getKey() != 1) {
                    return null;
                }
            }
        }

        this.selections = game.takeTiles(selectedCoordinates);

        return this.selections;
    }

    /**
     * Control if the tiles can be placed in the bookshelf of the player
     * @param player   the player id
     * @param column   column selected
     * @param tileList list of tiles to place, in the order chosen by the player
     * @return 1 if it is possible, 0 or -2 otherwise
     */
    public int putTilesInBookshelf(int player, int column, List<Tile> tileList) {
        // first check that they are the previous ones

        if(this.selections == null) {System.err.println(player+" HASN'T DONE THE PREVIOUS SELECTION"); return -1;}

        if(!checkTile(tileList)) { System.err.println("TILES AREN'T THE SAME"); return 0;}

        if (!game.controlUserSpace(player, column, tileList.size())) return -2;
        game.putTilesInBookshelf(player, column, tileList);

        this.selections = null;

        return 1;
    }

    /**
     * set the tiles' selection
     * @param tiles tiles chosen by the player
     */
    public void setSelections(List<Tile> tiles){this.selections = tiles;}

    /**
     * check if the tiles sent by the client are the same as those sent by the server
     * @param tileList list of tiles
     * @return true if they match
     */
    public boolean checkTile(List<Tile> tileList){
        boolean sup = false;

        if(tileList.size() != this.selections.size()) {
            System.err.println("LENGTH NOT THE SAME: "+tileList.size()+ " "+this.selections.size());
            return false;
        }

        for(Tile tile : tileList){
            sup = false;

            for(Tile tile1 : this.selections){
                if(tile.getId() == tile1.getId() && tile.getColor().ordinal() == tile1.getColor().ordinal()){
                    sup = true;
                    break;
                }
            }

            if(!sup) break;
        }

        return sup;
    }

    /**
     * Control if the player has completed common goal cards
     * @param player user
     * @return true if the player has completed common goal cards
     */
    public boolean controlCompletedCommonObjective(int player) {
        return game.controlCompletedCommonObjective(player);
    }

    /**
     * Control if a bookshelf is completed
     * @param player user
     * @return true if the player has completed his bookshelf
     */
    public boolean controlCompletedBookshelf(int player) {
        return game.controlCompletedBookshelf(player);
    }

    /**
     * Control if the board has to be refilled and if true it will be refilled
     * @return true there is the refill od board
     */
    public boolean controlBoardRefill() {
        return game.controlBoardRefill();
    }

    /**
     * management of the end of the game and then calculation of the points and the winner
     */
    public void endGame(){
        game.endGame();
    }

}

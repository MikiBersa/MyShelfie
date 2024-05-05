package it.polimi.ingsw.utils.store;

import it.polimi.ingsw.server.model.UserStore;
import it.polimi.ingsw.server.model.cards.Deck;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * StoreGame id the game with the updated parameters from the stored file
 */
public class StoreGame implements Serializable {

    static final long serialVersionUID = 6012513956001820703L;

    private HashMap<String, UserStore> players;

    private Tile[][] board;

    private Deck deck;

    private ArrayList<Pair<String, Boolean>> turns;

    private ArrayList<CommonObjectiveCard> commonObjectiveCards;
    private boolean status;

    /**
     * Constructor of StoreGame to handle the store
     */
    public StoreGame() {
        this.players = new HashMap<>();
        this.board = null;
        this.turns = new ArrayList<>();
        this.commonObjectiveCards = new ArrayList<>();
        this.status = false;
    }

    /**
     * A method that stores the game's state
     */
    public void store() {
        this.status = true;
        StoreReader.store(this);
    }


    /**
     * This method has the aim to get the status of the storing game
     *
     * @return true if there is something in the store file
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Restoring of the board
     *
     * @return The board to restore in the game
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Set the Board to store in the store file
     *
     * @param board Board to store
     */
    public void setBoard(Tile[][] board) {
        System.out.println("BOARD STORED");
        this.board = board;
        store();
    }

    /**
     * Restoring of the Deck
     *
     * @return The Deck to restore in the game
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Set the Deck to store in the store file
     *
     * @param deck Deck to store in the file
     */
    public void setDeck(Deck deck) {
        System.out.println("DECK STORED");
        this.deck = deck;
        store();
    }

    /**
     * Restoring of the players
     *
     * @return The players to restore in the game
     */
    public HashMap<String, UserStore> getPlayers() {
        return players;
    }

    /**
     * Restoring of the turn of the game and the order
     *
     * @return It'll return the arrayList of the players in that game
     */
    public ArrayList<Pair<String, Boolean>> getTurns() {
        return turns;
    }

    /**
     * Set the Turn to store in the store file
     *
     * @param turns Turn to store
     */
    public void setTurns(ArrayList<Pair<String, Boolean>> turns) {
        System.out.println("TURN STORED");
        this.turns = turns;
        store();
    }

    /**
     * Restoring of CommonCards of the previous game
     *
     * @return It'll return the arrayList of CommonCards
     */
    public ArrayList<CommonObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    /**
     * Set the CommonCards to store in the store file
     *
     * @param commonObjectiveCards The CommonCards to store
     */
    public void setCommonObjectiveCards(ArrayList<CommonObjectiveCard> commonObjectiveCards) {
        System.out.println("CommonObjectiveCard STORED");
        this.commonObjectiveCards = commonObjectiveCards;
        store();
    }

    /**
     * Set the User to store in the store file
     *
     * @param user User to store
     */
    public void setUser(UserStore user) {
        System.out.println("USER STORED");
        this.players.put(user.getUsername(), user);
        store();
    }

    /*
    WHEN SERVER STARTS:
         1) READ THE FILE:
              -> IF THERE WASN'T ONE THEN IT CREATES IT AND READS FALSE
              -> IF THERE WAS ONE THEN IT READS TRUE
         2) TRUE:
              -> GETTING READY FOR THE RESTORE
              -> ENTER USERS IN THE NORMAL WAY
              NB: Triggers storage when the number of players is the same and all player nicknames are the same
              -> IF THESE USERS ALL HAVE THE SAME NICKNAMES THEN INSTEAD OF SEND THE SETUP
              -> I SEND THE CURRENT STATE OF THE GAME DONE THE STORAGE
         3) FALSE:
              -> START THE GAME NORMALLY
     */
}


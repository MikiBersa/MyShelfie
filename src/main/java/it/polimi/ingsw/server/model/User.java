package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.PersonalObjectiveCard;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observable;
import it.polimi.ingsw.utils.store.StoreGame;
import it.polimi.ingsw.utils.store.StoreReader;
import javafx.util.Pair;

import static it.polimi.ingsw.Constant.COMMON_GOAL_CARDS_PER_GAME;
import static it.polimi.ingsw.Constant.MessageNumber.*;

/**
 * Class User that represents the player in the game
 */
public class User {

    private final String username;
    private final int id;
    private int commonPoints;
    private Bookshelf bookshelf;
    private PersonalObjectiveCard personalObjectiveCard;
    private int finalPoints;

    private Observable notify;

    private int firstFinished;
    private boolean[] commonDoneCards;

    private final UserStore userStore;

    /**
     * Constructor of the User
     * @param id unique player's id in the game
     * @param username unique player's username in the game
     */
    public User(int id, String username) {
        this.username = username;
        this.id = id;
        this.commonPoints = 0;
        this.bookshelf = null;
        personalObjectiveCard = null;
        this.finalPoints = 0;
        this.commonDoneCards = null;
        this.firstFinished = 0;
        this.userStore = new UserStore(id, username);
    }

    /**
     * getUsername
     * @return username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * getId
     * @return id
     */
    public int getId(){
        return this.id;
    }

    /**
     * getBookshelf
     * @return bookshelf
     */
    public Bookshelf getBookshelf(){
        return this.bookshelf;
    }

    /**
     * Method that initializes the player with a personal objective card
     * @param personalObjectiveCard personal objective card's id
     */
    public void initialize(PersonalObjectiveCard personalObjectiveCard) {
        bookshelf = new Bookshelf();
        this.personalObjectiveCard = personalObjectiveCard;
        commonDoneCards = new boolean[COMMON_GOAL_CARDS_PER_GAME];

        this.userStore.setCommonDoneCards(commonDoneCards);
        this.userStore.setPersonalObjectiveCard(this.personalObjectiveCard);
        this.userStore.setBookshelf(this.bookshelf);

        //qui deve fare update della view
        updateUser(id, RETURN_ID_PERSONAL_CARDS.ordinal(), personalObjectiveCard.getId());
    }

    /**
     * Method that notifies tha player with a message
     * @param id user's id
     * @param messageNumber message number
     * @param message message
     */
    public void updateUser(int id, int messageNumber, Object message){
        this.notify.notify(id, messageNumber, message);
    }
    /**
     * Method that controls if a column has enough space for the selection
     * @param column column
     * @param spaceRequired space requires
     * @return boolean value
     */
    public boolean controlSpaceBookshelf(int column, int spaceRequired) {
        return bookshelf.isSpecificColumnValid(column, spaceRequired);
    }

    /**
     * Method that controls if the bookshelf has enough space for the selection
     * @param spaceRequired space required
     * @return boolean value
     */
    public boolean controlSpaceBookshelf(int spaceRequired) {
        return bookshelf.hasEnoughSpace(spaceRequired);
    }

    /**
     * setTileInBookshelf
     * @param column column
     * @param tile tile
     */
    public void setTileInBookshelf(int column, Tile tile) {

        bookshelf.setTile(column, tile);

        this.userStore.setBookshelf(this.bookshelf);
    }

    /**
     * Method that updates the common objective card's points based upon the player and
     * his bookshelf situation
     * @param commonObjectiveCard objective common card
     * @param index common objective card's index
     * @param id player's id
     * @return boolean value
     */
    public boolean calculateCommonObjectiveCard(CommonObjectiveCard commonObjectiveCard, int index, int id) {
        StoreGame storeGame = StoreReader.getStoreGame();

        if (!commonDoneCards[index]) {
            int commonPointsGained = commonObjectiveCard.calculatePoints(bookshelf);
            if (commonPointsGained != 0) {
                commonPoints += commonPointsGained;
                commonDoneCards[index] = true;
                // send the points
                System.err.println("COMMON CARDS: " + id + " user: " + this.username);
                updateUser(-1, RETURN_POINTS_COMMON.ordinal(), new Pair<>(this.id, new Pair<>(id, commonPointsGained)));
                // updateCommonObjective(id);
                // update commonDoneCards
                this.userStore.setCommonDoneCards(commonDoneCards);
                // points
                this.userStore.setCommonPoints(commonPoints);
                // salvo localmente
                storeGame.setUser(getUserStore());

                return true;
            }
        }

        return false;
    }

    /**
     * Method that controls if the user's bookshelf is completed
     * @return boolean value
     */
    public boolean controlCompletedBookshelf() {
        if(bookshelf.complete()){
            this.firstFinished = 1;
            this.userStore.setFirstFinished(this.firstFinished);

            // salvo nel disco
            StoreGame storeGame = StoreReader.getStoreGame();
            storeGame.setUser(getUserStore());

            // mandare messaggio del punteggio fine bookshelf
            updateUser(-1,  RETURN_POINT_FINISHED.ordinal(), new Pair<>(id, firstFinished));
            return true;
        }
        return false;
    }

    /**
     * Method that calculates the final points of the player
     * @param adjacentTiles adjacent tiles of the bookshelf
     */
    public void calculateFinalPoints(AdjacentTiles adjacentTiles) {
        StoreGame storeGame = StoreReader.getStoreGame();

        finalPoints = personalObjectiveCard.calculatePoints(bookshelf) + commonPoints + adjacentTiles.calculatePoints(bookshelf) + firstFinished;
        this.userStore.setFinalPoints(finalPoints);

        storeGame.setUser(this.getUserStore());
    }

    /**
     * getFinalPoints
     * @return finalPoints
     */
    public int getFinalPoints() {
        return finalPoints;
    }

    /**
     * setNotify
     * @param notify notify
     */
    public void setNotify(Observable notify){
        this.notify = notify;
    }

    /**
     * setBookshelf
     * @param bookshelf bookshelf
     */
    public void setBookshelf(Bookshelf bookshelf){
        this.bookshelf = bookshelf;
        updateUser(-1, RETURN_BOOKSHELF.ordinal(), new Pair<>(getUsername(), getBookshelf()));
    }

    /**
     * getUserStore
     * @return userStore
     */
    public UserStore getUserStore() {
        return userStore;
    }

    /**
     * setFinalPoints
     * @param finalPoints finalPoints
     */
    public void setFinalPoints(int finalPoints) {
        this.finalPoints = finalPoints;
    }

    /**
     * setFirstFinished
     * @param firstFinished firstFinished
     */
    public void setFirstFinished(int firstFinished) {
        this.firstFinished = firstFinished;
    }

    /**
     * setCommonDoneCards
     * @param commonDoneCards commonDoneCards
     */
    public void setCommonDoneCards(boolean[] commonDoneCards) {
        this.commonDoneCards = commonDoneCards;
    }

    /**
     * setCommonPoints
     * @param commonPoints commonPoints
     */
    public void setCommonPoints(int commonPoints) {
        this.commonPoints = commonPoints;
    }
}

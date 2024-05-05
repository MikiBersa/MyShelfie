package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.PersonalObjectiveCard;

import java.io.Serializable;

/**
 * Helper Class in order to memorize the player's data
 */
public class UserStore implements Serializable {
    private String username;
    private int id;
    private int commonPoints;
    private Bookshelf bookshelf;
    private PersonalObjectiveCard personalObjectiveCard;
    private int finalPoints;
    private int firstFinished;

    private boolean[] commonDoneCards;

    /**
     * Constructor of UserStore
     * @param id if of the player
     * @param username username of the player
     */
    public UserStore(int id, String username) {
        this.username = username;
        this.id = id;
        this.commonPoints = 0;
        this.bookshelf = null;
        personalObjectiveCard = null;
        this.finalPoints = 0;
        this.commonDoneCards = null;
        this.firstFinished = 0;
    }

    /**
     * getUsername
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getId
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getCommonPoints
     * @return commonPoints
     */
    public int getCommonPoints() {
        return commonPoints;
    }

    /**
     * getBookshelf
     * @return bookshelf
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * getPersonalObjectiveCard
     * @return personalObjectiveCard
     */
    public PersonalObjectiveCard getPersonalObjectiveCard() {
        return personalObjectiveCard;
    }

    /**
     * getFinalPoint finalPoints
     * @return finalPoints
     */
    public int getFinalPoints() {
        return finalPoints;
    }

    /**
     * getFirstFinished
     * @return firstFinished
     */
    public int getFirstFinished() {
        return firstFinished;
    }

    /**
     * getCommonDoneCards
     * @return commonDoneCards
     */
    public boolean[] getCommonDoneCards() {
        return commonDoneCards;
    }

    /**
     * detUsername
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * setId
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setCommonPoints
     * @param commonPoints commonPoints
     */
    public void setCommonPoints(int commonPoints) {
        this.commonPoints = commonPoints;
    }

    /**
     * setBookshelf
     * @param bookshelf bookshelf
     */
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    /**
     * setPersonalObjectiveCard
     * @param personalObjectiveCard personalObjectiveCard
     */
    public void setPersonalObjectiveCard(PersonalObjectiveCard personalObjectiveCard) {
        this.personalObjectiveCard = personalObjectiveCard;
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
}

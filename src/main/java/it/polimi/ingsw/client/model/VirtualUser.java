package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.Bookshelf;

/**
 * The VirtualUser of the VirtualModel in the client
 */
public class VirtualUser {
    private final int id;
    private final String username;
    private Bookshelf bookshelf = new Bookshelf();
    private int commonPoints = 0;
    private boolean setShowFirst;
    private int firstFinishedPoint;
    private int finalPoint;

    /**
     * Create a VirtualUser
     *
     * @param id       the id of the player
     * @param username the username of the player
     */
    public VirtualUser(int id, String username) {
        this.id = id;
        this.username = username;
        firstFinishedPoint = 0;
        setShowFirst = false;
    }

    /**
     * Get the id of the VirtualUser
     *
     * @return the id of the VirtualUser
     */
    public int getId() {
        return id;
    }

    /**
     * Get the username of the VirtualUser
     *
     * @return the username of the VirtualUser
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the bookshelf of the VirtualUser
     *
     * @return the bookshelf of the VirtualUser
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * Set the bookshelf of the VirtualUser
     *
     * @param bookshelf the bookshelf to set
     */
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    /**
     * Add common points to a VirtualUser
     *
     * @param pointsGained the points gained by the VirtualUser
     */
    public void addCommonPoints(int pointsGained) {
        commonPoints += pointsGained;
    }

    /**
     * Get the common points of the VirtualUser
     *
     * @return the common points of the VirtualUser
     */
    public int getCommonPoints() {
        return commonPoints;
    }

    /**
     * Get the firstFinishedPoint of the VirtualUser that has finished as first the bookshelf
     *
     * @return the firstFinishedPoint of the VirtualUser that has finished as first the bookshelf
     */
    public int getFirstFinishedPoint() {
        return firstFinishedPoint;
    }

    /**
     * Set the firstFinishedPoint of the VirtualUser that has finished as first the bookshelf
     *
     * @param firstFinishedPoint the firstFinishedPoint of the VirtualUser that has finished as first the bookshelf
     */
    public void setFirstFinishedPoint(int firstFinishedPoint) {
        this.firstFinishedPoint = firstFinishedPoint;
    }

    /**
     * Get if the player has completed the bookshelf as first
     *
     * @return if the player has completed the bookshelf as first
     */
    public boolean isSetShowFirst() {
        return setShowFirst;
    }

    /**
     * If the player has completed the bookshelf as first
     *
     * @param setShowFirst if the player has completed the bookshelf as first
     */
    public void setSetShowFirst(boolean setShowFirst) {
        this.setShowFirst = setShowFirst;
    }

    /**
     * Get the final points of the VirtualUser
     *
     * @return the final points of the VirtualUser
     */
    public int getFinalPoint() {
        return this.finalPoint;
    }

    /**
     * Set the final points of the VirtualUser
     *
     * @param point the final points to set
     */
    public void setFinalPoint(int point) {
        this.finalPoint = point;
    }
}

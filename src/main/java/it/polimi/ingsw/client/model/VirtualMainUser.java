package it.polimi.ingsw.client.model;

import it.polimi.ingsw.server.model.cards.PersonalObjectiveCard;

/**
 * VirtualMainUser of the VirtualModel in the client
 */
public class VirtualMainUser extends VirtualUser {
    private PersonalObjectiveCard personalObjectiveCard;

    /**
     * Create a VirtualMainUser
     *
     * @param id       the id of the player
     * @param username the username of the player
     */
    public VirtualMainUser(int id, String username) {
        super(id, username);
    }

    /**
     * Get the personal objective card
     *
     * @return the personal objective card
     */
    public PersonalObjectiveCard getPersonalObjectiveCard() {
        return personalObjectiveCard;
    }

    /**
     * Set the personal objective card
     *
     * @param personalCard the personal objective card to set
     */
    public void setPersonalObjectiveCard(int personalCard) {
        personalObjectiveCard = new PersonalObjectiveCard(personalCard);
    }
}

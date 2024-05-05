package it.polimi.ingsw.server.model.cards;

import java.io.Serializable;

/**
 * The general card of the game
 */
public abstract class Card implements Serializable {
    int id;

    /**
     * constructor of the card that is unique in the game
     * @param id the id of the card
     */
    public Card(int id) {
        this.id = id;
    }

    /**
     * get id of the card
     * @return id of the card
     */
    public int getId(){return id;}
}

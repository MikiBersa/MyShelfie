package it.polimi.ingsw.server.model.cards;


import it.polimi.ingsw.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.Constant.*;

/**
 * The deck of the game
 */
public class Deck implements Serializable {
    private final List<Tile> deck = new ArrayList<>();
    private int nextIndexCard = 0;

    /**
     * constructor of the game's deck
     */
    public Deck() {
        int id = 0;
        for (Constant.Color color : Constant.Color.values()) {
            for (int i = 0; i < TILES_OF_TYPE; i++) {
                deck.add(new Tile(id, color, getImageId(color)));
                id++;
            }
        }

        Collections.shuffle(deck);
    }

    /**
     * get the String of the card's image in order to search its image in the images' folder
     * @param color the color of the card
     * @return String of the card's image
     */
    private String getImageId(Constant.Color color){
        Random random = new Random();
        int num = random.nextInt(3);

        String imageId = null;
        int colorId = color.ordinal();

        if(colorId == 0){
            // green -> kitten
            imageId = "G_"+num;
        } else if (colorId == 1) {
            // white -> book
            imageId = "L_"+num;
        }else if (colorId == 2) {
            // yellow -> games
            imageId = "O_"+num;
        }else if (colorId == 3) {
            // DARK_BLUE -> frames
            imageId = "C_"+num;
        } else if (colorId == 4) {
            // LIGHT_BLUE -> trophies
            imageId = "T_"+num;
        }else if (colorId == 5) {
            // PINK -> trophies
            imageId = "F_"+num;
        }

        return imageId;
    }

    /**
     * print the deck
     */
    public void printDeck() {
        while (hasNext()) {
            System.out.println(next());
        }
    }

    /**
     * control if there are other cards in the deck
     * @return true if there are other cards in the deck
     */
    public boolean hasNext() {
        return nextIndexCard < deck.size();
    }

    /**
     * return the next Tile in the deck
     * @return the next Tile
     */
    public Tile next() {
        Tile res = deck.get(nextIndexCard);
        nextIndexCard++;
        return res;
    }
}

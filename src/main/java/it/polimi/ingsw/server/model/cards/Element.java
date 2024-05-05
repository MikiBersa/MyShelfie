package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.Constant;

import java.io.Serializable;

/**
 * Support class to combine the coordinates with the color
 */
public class Element implements Serializable {
    private final int x;
    private final int y;
    private final Constant.Color color;

    /**
     * Constructor of this class
     * @param x coordinate x
     * @param y coordinate x
     * @param color color of the tile
     */
    public Element(int x, int y, Constant.Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * get coordinate x
     * @return coordinate x
     */
    public int getX(){
        return x;
    }

    /**
     * get coordinate y
     * @return coordinate x
     */
    public int getY(){
        return y;
    }

    /**
     * get color
     * @return color
     */
    public Constant.Color getColor(){
        return color;
    }
}

package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.Constant;

/**
 * Tile of the game
 */
public class Tile extends Card {
    private final Constant.Color color;
    private final String imageId;

    /**
     * constructor of the Tile
     * @param id the unique id of the Tile
     * @param color the color of the Tile
     * @param imageId the imageID of the Tile
     */
    public Tile(int id, Constant.Color color, String imageId) {
        super(id);
        //this.imageId = imageId;
        this.color = color;
        this.imageId = imageId;
    }

    /**
     * get ID of the Tile
     * @return ID of the Tile
     */
    public int getId(){return id;}

    /**
     * get ImageId of the Tile
     * @return ImageId of the Tile
     */
    public String getImageId(){return imageId;}

    @Override
    public String toString() {
        return "id = " + id + ", color = " + color;
    }

    /**
     * get the color of the Tile
     * @return the color of the Tile
     */
    public Constant.Color getColor() {
        return color;
    }
}

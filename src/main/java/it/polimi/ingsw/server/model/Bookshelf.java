package it.polimi.ingsw.server.model;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.cards.Tile;

import java.io.Serializable;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * Bookshelf of the game
 */
public class Bookshelf implements Serializable {
    private Tile[][] positionTiles;

    /**
     * Bookshelf constructor
     */
    public Bookshelf(){
        positionTiles = new Tile[Constant.BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
    }

    /**
     * Method that controls if the column is valid for the insertion
     * @param column column
     * @param spaceRequired space required for the number of selected tiles
     * @return boolean value
     */
    public boolean isSpecificColumnValid(int column, int spaceRequired) {
        if (column < 0 || column >= BOOKSHELF_WIDTH) return false;

        int count = 0;
        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            if (positionTiles[i][column] == null) count++;
        }
        return count >= spaceRequired;
    }

    /**
     * Method that controls how many columns have enough space for the selection
     * @param spaceRequired space required
     * @return boolean value
     */
    public boolean hasEnoughSpace(int spaceRequired) {
        for (int i = 0; i < BOOKSHELF_WIDTH; i++) {
            if (isSpecificColumnValid(i, spaceRequired)) return true;
        }
        return false;
    }

    /**
     * setTile
     * @param i row
     * @param j column
     * @param tile tile
     */
    public void setTile(int i, int j, Tile tile) {
        this.positionTiles[i][j] = tile;
    }

    /**
     * setTile
     * @param column column
     * @param tile tile
     */
    public void setTile(int column, Tile tile) {
        for (int i = BOOKSHELF_HEIGHT - 1; i >= 0; i--) {
            if (positionTiles[i][column] == null) {
                positionTiles[i][column] = tile;
                return;
            }
        }
    }

    /**
     * getPositionTiles
     * @return Tile[][]
     */
    public Tile[][] getPositionTiles(){
        return positionTiles;
    }

    //onlyFor test

    /**
     * setPositionTiles
     * @param tiles tiles
     */
    public void setPositionTiles(Tile[][] tiles){
        positionTiles = tiles;
    }
    

    /**
     * Method that controls if the bookshelf is full
     * @return boolean value
     */
    public boolean complete() {
        for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
            if (positionTiles[0][j] == null) return false;
        }
        return true;
    }

    /**
     * toString of bookshelf
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
                stringBuilder.append('|').append(positionTiles[i][j] == null ? " " : Constant.colorToString(positionTiles[i][j].getColor()));
            }
            stringBuilder.append("|\n");
        }
        return stringBuilder.toString();
    }
}

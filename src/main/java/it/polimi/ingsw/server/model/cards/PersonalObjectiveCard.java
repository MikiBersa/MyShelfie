package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;

import static it.polimi.ingsw.server.json.JsonReader.readerPersonalCards;

/**
 * PersonalObjectiveCard of the game
 */
public class PersonalObjectiveCard extends Card{
    private final Element[] elements;

    /**
     * Constructor of the PersonalObjectiveCard
     * @param id card id draws from game start
     */
    public PersonalObjectiveCard(int id){
        super(id);
        elements = readerPersonalCards(id);
    }

    /**
     * A method that calculates the player's personal objective card's points
     * @param b bookshelf
     * @return points
     */
    public int calculatePoints(Bookshelf b) {
        int counter = 0;
        int[] points = {0, 1, 2, 4, 6, 9, 12};
        Tile[][] tiles = b.getPositionTiles();

        for (Element element : elements) {
            Tile tile = tiles[element.getY()][element.getX()];
            if (tile != null && tile.getColor() == element.getColor())
                counter++;
        }

        return points[counter];
    }

    /**
     * Print the image of the PersonalObjectiveCard
     * @return the image of the PersonalObjectiveCard
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexArr = 0;
        stringBuilder.append("Personal objective card\n");
        stringBuilder.append(' ');

        for (int i = 0; i < Constant.BOOKSHELF_WIDTH; i++) {
            stringBuilder.append(i).append(' ');
        }
        stringBuilder.append('\n');

        for (int i = 0; i < Constant.BOOKSHELF_HEIGHT; i++) {
            for (int j = 0; j < Constant.BOOKSHELF_WIDTH; j++) {
                stringBuilder.append('|');
                if (indexArr < Constant.NUMBER_OF_OBJECTIVES && elements[indexArr].getX() == j && elements[indexArr].getY() == i) {
                    stringBuilder.append(Constant.colorToString(elements[indexArr].getColor()));
                    indexArr++;
                } else {
                    stringBuilder.append(' ');
                }
            }
            stringBuilder.append("|\n");
        }

        return stringBuilder.toString();
    }
}

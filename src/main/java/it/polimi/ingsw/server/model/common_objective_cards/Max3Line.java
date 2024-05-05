package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.Constant.*;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import java.util.ArrayList;
import java.util.Hashtable;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * Max3Line
 */
public class Max3Line extends CommonObjectiveCard {

    /**
     * Max3Line constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public Max3Line(int id, int numPlayers) {
        super(id, numPlayers);
    }

    //parametro bookshelf dell'utente

    /**
     * A method that calculates the common objective card's points based on the specific pattern
     * @param bookshelf bookshelf
     * @return points
     */
    @Override
    public int calculatePoints(Bookshelf bookshelf) {
        Tile[][] matrix = bookshelf.getPositionTiles();
        int lineMax4 = 0;

        //here we have the logic of Max3Column
        ArrayList<Hashtable<Color, Boolean>> counterType = new ArrayList<>(BOOKSHELF_HEIGHT);

        for(int i = 0; i< BOOKSHELF_HEIGHT; i++){
            Hashtable<Color, Boolean> hashtable = new Hashtable<>();

            for(int j = 0; j<BOOKSHELF_WIDTH; j++){

                if(matrix[i][j] == null) break;

                hashtable.put(matrix[i][j].getColor(), true);
            }

            counterType.add(i, hashtable);
        }

        for(int i = 0; i< BOOKSHELF_HEIGHT; i++){

            if(counterType.get(i).size() <= 3 && counterType.get(i).size() >=1 && supportControl(bookshelf.getPositionTiles(), i)) lineMax4++;
        }

        if(lineMax4 >= 4){
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }
        return 0;
    }

    /**
     * Helper method
     * @param bookshelf bookshelf
     * @param row row
     * @return boolean value
     */
    private boolean supportControl(Tile[][] bookshelf, int row){

        for(int j = 0; j<BOOKSHELF_WIDTH; j++){
            if(bookshelf[row][j] == null) return false;
        }

        return true;
    }

    /**
     * toString of Max3Line
     * @return String
     */
    @Override
    public String toString() {
        return "Four lines each formed by 5 tiles of " +
                "maximum three different types. One " +
                "line can show the same or a different " +
                "combination of another line. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of Max3Line
     * @return String
     */
    @Override
    public String getDescription(){
        return "Four lines each formed by 5 tiles of \n" +
                "maximum three different types. \nOne " +
                "line can show the same or a different\n " +
                "combination of another line";
    }
}


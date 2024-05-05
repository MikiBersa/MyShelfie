package it.polimi.ingsw.server.model.common_objective_cards;

import static it.polimi.ingsw.Constant.*;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import java.util.ArrayList;
import java.util.Hashtable;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * Max3Column
 */
public class Max3Column extends CommonObjectiveCard {

    /**
     * Max3Column constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public Max3Column(int id, int numPlayers) {
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
        int columnMax3 = 0;

        boolean done = false;

        //here we have the logic of Max3Column
        ArrayList<Hashtable<Color, Boolean>> counterType = new ArrayList<>(BOOKSHELF_WIDTH);

        for(int j = 0; j< BOOKSHELF_WIDTH; j++){
            Hashtable<Color, Boolean> hashtable = new Hashtable<>();

            for(int i = 0; i<BOOKSHELF_HEIGHT; i++){

                if(matrix[i][j] == null) break;

                hashtable.put(matrix[i][j].getColor(), true);
            }
            counterType.add(j, hashtable);
        }

        for(int j = 0; j< BOOKSHELF_WIDTH; j++){

            if(counterType.get(j).size() <= 3 && counterType.get(j).size() >=1 && supportControl(bookshelf.getPositionTiles(), j)){
                // deve essere piena la colonna
                columnMax3++;
            }
        }

        // okok ma deve essere piena la riga per contarla
        if(columnMax3 >= 3){
            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }
        return 0;
    }

    /**
     * Helper method
     * @param bookshelf bookshelf
     * @param column column
     * @return boolean value
     */
    private boolean supportControl(Tile[][] bookshelf, int column){

        for(int i = 0; i<BOOKSHELF_HEIGHT; i++){
            if(bookshelf[i][column] == null) return false;
        }

        return true;
    }

    /**
     * toString of Max3Column
     * @return String
     */
    @Override
    public String toString() {
        return "Three columns each formed by 6 tiles of maximum three different types. " +
                "One column can show the same or a different combination of another column. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of Max3Column
     * @return String
     */
    @Override
    public String getDescription(){
        return "Three columns each formed by 6 tiles of\nmaximum three different types. \n" +
                "One column can show the same or a different\ncombination of another column";
    }
}

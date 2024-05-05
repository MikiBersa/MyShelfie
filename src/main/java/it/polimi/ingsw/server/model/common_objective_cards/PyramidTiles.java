package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;
import static it.polimi.ingsw.server.json.JsonReader.readerPyramidPosition;

/**
 * PyramidTiles
 */
public class PyramidTiles extends CommonObjectiveCard {

    private final boolean[][] pyramidSx;
    private final boolean[][] pyramidDx;

    /**
     * PyramidTiles constructor
     * @param id id
     * @param numPlayers numPlayers
     */
    public PyramidTiles(int id, int numPlayers) {

        super(id, numPlayers);

        this.pyramidDx = readerPyramidPosition("dx");
        this.pyramidSx = readerPyramidPosition("sx");
    }

    /**
     * A method that calculates the common objective card's points based on the specific pattern
     * @param bookshelf bookshelf
     * @return points
     */
    @Override
    public int calculatePoints(Bookshelf bookshelf) {
        Tile[][] matrix = bookshelf.getPositionTiles();
        boolean done;
        //tessere di qualsiasi tipo

        done = controlPyramid(matrix, this.pyramidDx, 1) || controlPyramid(matrix, this.pyramidSx, 1);
        //System.out.println("DONE SHIFT: "+done);
        done = done || controlPyramid(matrix, this.pyramidSx, 0) || controlPyramid(matrix, this.pyramidDx, 0);
        //System.out.println("DONE NORMAL: "+done);

        if(done){

            int ans = points.get(playersThatCompletedObjective);
            playersThatCompletedObjective++;
            return ans;
        }
        return 0;
    }


    /**
     * Helper method that helps to control the pyramid pattern
     * @param matrix tiles matrix
     * @param controllo control matrix
     * @param tag append index
     * @return boolean value
     */
    private boolean controlPyramid(Tile[][] matrix, boolean[][] controllo, int tag){
        boolean done = true;
        int last;

        if(tag == 0){
            //normal
            last = BOOKSHELF_HEIGHT;
        }else {
            //shift up
            last = BOOKSHELF_HEIGHT - 1;
        }

        for(int i = 0; i < last; i++){

            for(int j = 0; j < BOOKSHELF_WIDTH; j++){
                if((matrix[i][j] == null && controllo[i+tag][j]) || (matrix[i][j] != null && !controllo[i+tag][j])) {
                    done = false;
                    break;
                }
            }

        }

        return done;
    }

    /**
     * toString of PyramidTiles
     * @return String
     */
    @Override
    public String toString() {
        return "Five columns of increasing or decreasing " +
                "height. Starting from the first column on " +
                "the left or on the right, each next column " +
                "must be made of exactly one more tile. " +
                "Tiles can be of any type. \n" +
                "Maximum reachable points: " + points.get(playersThatCompletedObjective);
    }

    /**
     * getDescription of PyramidTiles
     * @return String
     */
    @Override
    public String getDescription(){
        return "Five columns of increasing or decreasing\n" +
                "height. Starting from the first column on\n " +
                "the left or on the right, each next column\n" +
                "must be made of exactly one more tile. \n" +
                "Tiles can be of any type";
    }
}

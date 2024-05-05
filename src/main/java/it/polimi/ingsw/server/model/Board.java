package it.polimi.ingsw.server.model;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.json.JsonReader;
import it.polimi.ingsw.server.model.cards.Deck;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observable;
import it.polimi.ingsw.utils.store.StoreGame;
import it.polimi.ingsw.utils.store.StoreReader;
import javafx.util.Pair;

import java.io.Serializable;

import static it.polimi.ingsw.Constant.*;
import static it.polimi.ingsw.Constant.MessageNumber.RETURN_BOARD;

/**
 * Board of the game
 */
public class Board implements Serializable {
    static final long serialVersionUID = 6012513953481820703L; //assign a long value
    private final Deck deck;
    private final Tile[][] board;
    private boolean[][] boardValidator;

    private Observable notify;

    /**
     * Constructor of the game's board
     * @param numPlayers number of game's players
     * @param notify the reference to the networkInterface
     */
    public Board(int numPlayers, Observable notify) {
        deck = new Deck();
        board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

        setValidator(numPlayers);

        this.notify = notify;

        refill();

    }

    /**
     * Constructor of the game's board
     * @param numPlayers number of game's players
     * @param myBoard Tiles for the board to set
     */
    public Board(int numPlayers, Tile[][] myBoard) {
        deck = new Deck();

        setValidator(numPlayers);

        board = myBoard;
    }

    /**
     * Constructor of the game's board for storeGame
     * @param numPlayers number of game's players
     * @param myBoard board of the game stored in the store file
     * @param notify the reference to the networkInterface
     * @param deck the deck of the previous game
     */
    public Board(int numPlayers, Tile[][] myBoard, Observable notify, Deck deck){
        setValidator(numPlayers);

        this.board = myBoard;
        this.deck = deck;
        this.notify = notify;
    }

    /**
     * set the validator as appropriate
     * @param numPlayers number of players of the game
     */
    private void setValidator(int numPlayers){
        if (numPlayers == 2) {
            boardValidator = JsonReader.readerBoard("two");
        } else if (numPlayers == 3) {
            boardValidator = JsonReader.readerBoard("three");
        } else if (numPlayers == 4) {
            boardValidator = JsonReader.readerBoard("four");
        }
    }

    //in case notify the new board to the players to every body

    /**
     * Method that fills the board from left to right and from top to bottom
     * and that prints it
     */
    public void refill() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (boardValidator[row][col] && board[row][col] == null) {
                    board[row][col] = deck.next();
                }
            }
        }
        updateBoard();
    }

    /**
     * Update the Board from the storage file
     */
    public void updateBoard(){
        StoreGame storeGame = StoreReader.getStoreGame();
        // salvo ogni volta la board
        storeGame.setBoard(this.board);
        storeGame.setDeck(this.deck);

        this.notify.notify(-1, RETURN_BOARD.ordinal(), board);
    }

    /**
     * FIXME only for test
     * Method that prints the board's tiles
     */
    public void printBoardTiles() {
        System.out.print("  ");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < board.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(Constant.colorToString(board[i][j].getColor()) + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Helper method
     * @param coordinates coordinates
     * @return boolean value
     */
    public boolean isCoordinateValid(Pair<Integer, Integer> coordinates) {
        return board[coordinates.getKey()][coordinates.getValue()] != null;
    }

    /**
     * Method that establishes if all cards are isolated
     * @return boolean value
     */
    public boolean areAllCardsSingle() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (boardValidator[i][j] && board[i][j] != null) {
                    for (int k = 0; k < 4; k++) {
                        int newJ = j + DX[k];
                        int newI = i + DY[k];

                        if (newI < 0 || newJ < 0 || newI == BOARD_HEIGHT || newJ == BOARD_WIDTH) continue;
                        if (board[newI][newJ] != null) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method that removes a tile from the board
     * @param coordinates tile's coordinates
     * @return res deleted tile
     */
    public Tile removeCard(Pair<Integer, Integer> coordinates) {
        Tile res = board[coordinates.getKey()][coordinates.getValue()];
        board[coordinates.getKey()][coordinates.getValue()] = null;

        return res;
    }

    /**
     * Method that controls if the tile has at least one free side
     * @param selectedCoordinate selected coordinates
     * @return boolean value
     */
    public boolean isAtLeastOneSideFree(Pair<Integer, Integer> selectedCoordinate) {
        for (int k = 0; k < 4; k++) {
            if (selectedCoordinate.getKey() + DY[k] == BOARD_HEIGHT || selectedCoordinate.getValue() + DX[k] == BOARD_WIDTH ||
                    board[selectedCoordinate.getKey() + DY[k]][selectedCoordinate.getValue() + DX[k]] == null) {
                return true;
            }
        }
        return false;
    }
}

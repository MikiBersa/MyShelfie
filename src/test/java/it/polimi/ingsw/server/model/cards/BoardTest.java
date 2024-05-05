package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.network.Observer.oberserverOut.ManagementObserver;
import it.polimi.ingsw.server.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static it.polimi.ingsw.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void areAllCardsSingleInitial() {
        Board board = new Board(2, new ManagementObserver());
        Assertions.assertFalse(board.areAllCardsSingle());
    }

    @Test
    void areAllCardsSingleCustom() {
        Tile[][] myBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        myBoard[3][4] = new Tile(0, Color.DARK_BLUE, "C_0");
        Board board = new Board(2, myBoard);
        board.printBoardTiles();
        assertTrue(board.areAllCardsSingle());
    }

    @Test
    void twoLine() {
        Tile[][] myBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        myBoard[3][4] = new Tile(0, Color.DARK_BLUE, "C_0");
        myBoard[3][5] = new Tile(0, Color.DARK_BLUE, "C_0");
        Board board = new Board(2, myBoard);
        board.printBoardTiles();
        Assertions.assertFalse(board.areAllCardsSingle());
    }

    @Test
    void twoColumn() {
        Tile[][] myBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        myBoard[3][4] = new Tile(0, Color.DARK_BLUE, "C_0");
        myBoard[4][4] = new Tile(0, Color.DARK_BLUE, "C_0");
        Board board = new Board(2, myBoard);
        board.printBoardTiles();
        Assertions.assertFalse(board.areAllCardsSingle());
    }

    @Test
    void twoSingle() {
        Tile[][] myBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        myBoard[3][3] = new Tile(0, Color.DARK_BLUE, "C_0");
        myBoard[3][5] = new Tile(0, Color.DARK_BLUE, "C_0");
        Board board = new Board(2, myBoard);
        board.printBoardTiles();
        assertTrue(board.areAllCardsSingle());
    }
}
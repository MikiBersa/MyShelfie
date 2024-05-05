package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnAllDifferentTest {
    @Test
    void Max3ColumnTestNormall() {
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        ColumnAllDifferent columnAllDifferent = new ColumnAllDifferent(1,2);
        assertEquals(8, columnAllDifferent.calculatePoints(bookshelf));

    }

    @Test
    void Max3ColumnTestNull() {
        Tile[][] tile = new Tile[Constant.BOOKSHELF_HEIGHT][Constant.BOOKSHELF_WIDTH];
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        ColumnAllDifferent columnAllDifferent = new ColumnAllDifferent(1,2);
        assertEquals(0, columnAllDifferent.calculatePoints(bookshelf));

    }

    @Test
    void Max3ColumnTestAllGood() {
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0")},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0")},
                {new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        ColumnAllDifferent columnAllDifferent = new ColumnAllDifferent(1,2);
        assertEquals(8, columnAllDifferent.calculatePoints(bookshelf));
    }

    @Test
    void firstAllNull() {
        Tile[][] tile = new Tile[][]{
                {null,null,null, null, null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"),null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), null}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        ColumnAllDifferent columnAllDifferent = new ColumnAllDifferent(1,2);
        assertEquals(0, columnAllDifferent.calculatePoints(bookshelf));
    }

}
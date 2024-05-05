package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Constant.Color.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class XTilesTest {
    @Test
    void Normal() {
        Tile[][] tile = new Tile[][]{
                {new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), null},
                {new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0")},
                {new Tile(1, GREEN, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, WHITE, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, WHITE, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        CommonObjectiveCard xtiles = new XTiles(1, 2);
        assertEquals(0, xtiles.calculatePoints(bookshelf));

    }

    @Test
    void Null() {
        Tile[][] tile = new Tile[Constant.BOOKSHELF_HEIGHT][Constant.BOOKSHELF_WIDTH];
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        CommonObjectiveCard xtiles = new XTiles(1, 2);
        assertEquals(0, xtiles.calculatePoints(bookshelf));
    }

    @Test
    void valid() {
        Tile[][] tile = new Tile[][]{
                {new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), null},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, GREEN, "C_0")},
                {new Tile(1, PINK, "C_0"), new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, DARK_BLUE, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, WHITE, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, WHITE, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, WHITE, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        CommonObjectiveCard xtiles = new XTiles(1, 2);
        assertEquals(8, xtiles.calculatePoints(bookshelf));
    }

    @Test
    void FirstLineAllNUll() {
        Tile[][] tile = new Tile[][]{
                {null, null, null, null, null},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, GREEN, "C_0")},
                {new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")},
                {new Tile(1, PINK, "C_0"), new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, WHITE, "C_0")},
                {new Tile(1, WHITE, "C_0"), new Tile(1, WHITE, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, PINK, "C_0")},
                {new Tile(1, PINK, "C_0"), new Tile(1, LIGHT_BLUE, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, GREEN, "C_0"), new Tile(1, DARK_BLUE, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        CommonObjectiveCard xtiles = new XTiles(1, 2);
        assertEquals(0, xtiles.calculatePoints(bookshelf));
    }

    @Test
    void AllTheSameLine() {
        Tile[][] tile = new Tile[][]{
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0")},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0")},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0")},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0")},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0")},
                {new Tile(1, GREEN, "C_0"), new Tile(1, YELLOW, "C_0"), new Tile(1, PINK, "C_0"), new Tile(1, DARK_BLUE, "C_0"), new Tile(1, LIGHT_BLUE, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        CommonObjectiveCard xtiles = new XTiles(1, 2);
        assertEquals(0, xtiles.calculatePoints(bookshelf));
    }
}
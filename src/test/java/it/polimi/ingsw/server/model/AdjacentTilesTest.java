package it.polimi.ingsw.server.model;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.cards.CalculatePointsCommon;
import it.polimi.ingsw.server.model.cards.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdjacentTilesTest {

    @Test
    void firstTest() {
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        CalculatePointsCommon adjacentTile = new AdjacentTiles();
        assertEquals(2 + 5 + 2 + 2 + 3, adjacentTile.calculatePoints(bookshelf));
    }

    @Test
    void allNull() {
        Tile[][] tile = new Tile[Constant.BOOKSHELF_HEIGHT][Constant.BOOKSHELF_WIDTH];
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        CalculatePointsCommon adjacentTile = new AdjacentTiles();
        assertEquals(0, adjacentTile.calculatePoints(bookshelf));
    }
}
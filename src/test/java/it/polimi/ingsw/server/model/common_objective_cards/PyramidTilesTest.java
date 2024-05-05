package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PyramidTilesTest {

    @Test
    void StructureDx() {
        System.out.println("StructureDx");
        Tile[][] tile = new Tile[][]{
                {null, null, null, null, null},
                {null, null, null, null, new Tile(1, Constant.Color.GREEN, "C_0")},
                {null, null , null, new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {null, null, new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {null, new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(8, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void StructureSx() {
        System.out.println("StructureSx");
        Tile[][] tile = new Tile[][]{
                {null, null, null, null, null},
                {new Tile(1, Constant.Color.PINK, "C_0"), null,null, null, null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), null, null, null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), null, null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(   8, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void FailSxDx() {
        System.out.println("FailSxDx");
        Tile[][] tile = new Tile[][]{
                {null, new Tile(1, Constant.Color.PINK, "C_0"), null, new Tile(1, Constant.Color.PINK, "C_0"), null},
                {new Tile(1, Constant.Color.PINK, "C_0"), null, new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {null, new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(   0, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void All() {
        System.out.println("All");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(0, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void FailStructureSx() {
        System.out.println("StructureSx");
        Tile[][] tile = new Tile[][]{
                {null, null, null, null, null},
                {new Tile(1, Constant.Color.PINK, "C_0"), null,null, null, new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), null, null, new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), null, new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(   0, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void FailStructureDx() {
        System.out.println("FailStructureDx");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.PINK, "C_0"), null, null, null, new Tile(1, Constant.Color.PINK, "C_0")},
                {null, null,null, null, new Tile(1, Constant.Color.PINK, "C_0")},
                {null, null, null, new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {null, null, new Tile(1, Constant.Color.WHITE, "C_0"), null, new Tile(1, Constant.Color.PINK, "C_0")},
                {null, new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(   0, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void ShiftSx() {
        System.out.println("ShiftSx");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.PINK, "C_0"), null, null, null, null},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"),null, null, null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), null, null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(   8, pyramidTiles.calculatePoints(bookshelf));

    }

    @Test
    void ShiftDx() {
        System.out.println("ShiftDx");
        Tile[][] tile = new Tile[][]{
                {null, null, null, null, new Tile(1, Constant.Color.PINK, "C_0")},
                {null, null,null, new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {null, null, new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {null, new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        PyramidTiles pyramidTiles = new PyramidTiles(1,2);
        Assertions.assertEquals(   8, pyramidTiles.calculatePoints(bookshelf));

    }

}
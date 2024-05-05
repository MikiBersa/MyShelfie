package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

class Max3LineTest {
    @Test
    void Max3LineTestNormall() {
        System.out.println("Max3LineTestNormall");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(0, max3Line.calculatePoints(bookshelf));

    }

    @Test
    void Max3lineTestNull() {
        System.out.println("Max3lineTestNull");
        Tile[][] tile = new Tile[Constant.BOOKSHELF_HEIGHT][Constant.BOOKSHELF_WIDTH];
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);

        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(0, max3Line.calculatePoints(bookshelf));

    }

    @Test
    void TestNormallValid() {
        System.out.println("TestNormallValid");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(0, max3Line.calculatePoints(bookshelf));

    }

    @Test
    void FirstLineAllNUll() {
        System.out.println("FirstLineAllNUll");
        Tile[][] tile = new Tile[][]{
                {null, null, null, null, null},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(0, max3Line.calculatePoints(bookshelf));

    }

    @Test
    void FirstLineSame() {
        System.out.println("TestNormallValid");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0")},
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.GREEN, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")},
                {new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"), new Tile(1, Constant.Color.PINK, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(8, max3Line.calculatePoints(bookshelf));

    }

    @Test
    void FirstLineNull() {
        System.out.println("TestNormallValid");
        Tile[][] tile = new Tile[][]{
                {new Tile(1, Constant.Color.GREEN, "C_0"), null, null,null,null},
                {new Tile(1, Constant.Color.GREEN, "C_0"),null, null,null,null},
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), null, null,null,null},
                {new Tile(1, Constant.Color.YELLOW, "C_0"), null, null,null,null},
                {new Tile(1, Constant.Color.WHITE, "C_0"), null, null,null,null},
                {new Tile(1, Constant.Color.PINK, "C_0"), null, null,null,null}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(0, max3Line.calculatePoints(bookshelf));

    }

    @Test
    void OnyLastTwo() {
        System.out.println("TestNormallValid");
        Tile[][] tile = new Tile[][]{
                {null, null, null,null,null},
                {null,null, null,null,null},
                {null, null, null,null,null},
                {null, null, null,null,null},
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.DARK_BLUE, "C_0"), new Tile(1, Constant.Color.YELLOW, "C_0"),new Tile(1, Constant.Color.YELLOW, "C_0"),new Tile(1, Constant.Color.DARK_BLUE, "C_0")},
                {new Tile(1, Constant.Color.LIGHT_BLUE, "C_0"), new Tile(1, Constant.Color.WHITE, "C_0"), new Tile(1, Constant.Color.PINK, "C_0"),new Tile(1, Constant.Color.WHITE, "C_0"),new Tile(1, Constant.Color.LIGHT_BLUE, "C_0")}
        };

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(tile);
        Max3Line max3Line = new Max3Line(1,2);
        Assert.assertEquals(0, max3Line.calculatePoints(bookshelf));

    }

}
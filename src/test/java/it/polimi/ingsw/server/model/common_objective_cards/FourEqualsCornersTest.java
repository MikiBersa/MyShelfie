package it.polimi.ingsw.server.model.common_objective_cards;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;
import static it.polimi.ingsw.Constant.Color.DARK_BLUE;
import static it.polimi.ingsw.Constant.Color.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FourEqualsCornersTest {
    CommonObjectiveCard fourEqualsCornersTest;

    @BeforeEach
    void creationCommonCard() {
        fourEqualsCornersTest = new FourEqualsCorners(0, 2);
    }

    @Test
    void sameColorCorners() {
        Tile[][] positionTiles = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];

        positionTiles[0][0] = new Tile(0, GREEN, "C_0");
        positionTiles[0][BOOKSHELF_WIDTH - 1] = new Tile(0, GREEN, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][0] = new Tile(0, GREEN, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1] = new Tile(0, GREEN, "C_0");

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(positionTiles);
        assertEquals(8, fourEqualsCornersTest.calculatePoints(bookshelf));
    }

    @Test
    void differentColorCorners() {
        Tile[][] positionTiles = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];

        positionTiles[0][0] = new Tile(0, GREEN, "C_0");
        positionTiles[0][BOOKSHELF_WIDTH - 1] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][0] = new Tile(0, GREEN, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1] = new Tile(0, GREEN, "C_0");

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(positionTiles);
        assertEquals(0, fourEqualsCornersTest.calculatePoints(bookshelf));
    }

    @Test
    void nullCorners() {
        Tile[][] positionTiles = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];

        positionTiles[0][0] = null;
        positionTiles[0][BOOKSHELF_WIDTH - 1] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][0] = new Tile(0, GREEN, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1] = new Tile(0, GREEN, "C_0");

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(positionTiles);
        assertEquals(0, fourEqualsCornersTest.calculatePoints(bookshelf));
    }

    @Test
    void doubleNull() {
        Tile[][] positionTiles = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];

        positionTiles[0][0] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[0][BOOKSHELF_WIDTH - 1] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][0] = null;
        positionTiles[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1] = null;

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(positionTiles);
        assertEquals(0, fourEqualsCornersTest.calculatePoints(bookshelf));
    }

    @Test
    void doubleBookshelfWithSameColorCorners() {
        Tile[][] positionTiles = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];

        positionTiles[0][0] = new Tile(0, GREEN, "C_0");
        positionTiles[0][BOOKSHELF_WIDTH - 1] = new Tile(0, GREEN, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][0] = new Tile(0, GREEN, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1] = new Tile(0, GREEN, "C_0");

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(positionTiles);
        assertEquals(8, fourEqualsCornersTest.calculatePoints(bookshelf));

        positionTiles[0][0] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[0][BOOKSHELF_WIDTH - 1] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][0] = new Tile(0, DARK_BLUE, "C_0");
        positionTiles[BOOKSHELF_HEIGHT - 1][BOOKSHELF_WIDTH - 1] = new Tile(0, DARK_BLUE, "C_0");

        bookshelf = new Bookshelf();
        bookshelf.setPositionTiles(positionTiles);
        assertEquals(4, fourEqualsCornersTest.calculatePoints(bookshelf));
    }
}
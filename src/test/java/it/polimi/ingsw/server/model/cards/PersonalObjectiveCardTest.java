package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Bookshelf;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Constant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalObjectiveCardTest {

    void ConfigurationTiles(Tile[][] tile){
        tile[0][0] = new Tile(0, Color.PINK, "C_0");
        tile[0][1] = new Tile(0, Color.YELLOW, "C_0");
        tile[0][2] = new Tile(0, Color.DARK_BLUE, "C_0");
        tile[0][3] = new Tile(0, Color.LIGHT_BLUE, "C_0");
        tile[0][4] = new Tile(0, Color.GREEN, "C_0");

        tile[1][0] = new Tile(0, Color.GREEN, "C_0");
        tile[1][1] = null;
        tile[1][2] = new Tile(0, Color.PINK, "C_0");
        tile[1][3] = new Tile(0, Color.LIGHT_BLUE, "C_0");
        tile[1][4] = new Tile(0, Color.GREEN, "C_0");

        tile[2][0] = new Tile(0, Color.GREEN, "C_0");
        tile[2][1] = new Tile(0, Color.GREEN, "C_0");
        tile[2][2] = new Tile(0, Color.GREEN, "C_0");
        tile[2][3] = new Tile(0, Color.WHITE, "C_0");
        tile[2][4] = new Tile(0, Color.DARK_BLUE, "C_0");

        tile[3][0] = new Tile(0, Color.LIGHT_BLUE, "C_0");
        tile[3][1] = new Tile(0, Color.YELLOW, "C_0");
        tile[3][2] = new Tile(0, Color.YELLOW, "C_0");
        tile[3][3] = null;
        tile[3][4] = new Tile(0, Color.WHITE, "C_0");

        tile[4][0] = new Tile(0, Color.WHITE, "C_0");
        tile[4][1] = new Tile(0, Color.YELLOW, "C_0");
        tile[4][2] = new Tile(0, Color.GREEN, "C_0");
        tile[4][3] = new Tile(0, Color.WHITE, "C_0");
        tile[4][4] = new Tile(0, Color.WHITE, "C_0");

        tile[5][0] = new Tile(0, Color.GREEN, "C_0");
        tile[5][1] = new Tile(0, Color.GREEN, "C_0");
        tile[5][2] = new Tile(0, Color.LIGHT_BLUE, "C_0");
        tile[5][3] = new Tile(0, Color.DARK_BLUE, "C_0");
        tile[5][4] = new Tile(0, Color.LIGHT_BLUE, "C_0");
    }
    @Test
    void Card0(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(0);
        assertEquals(12, p.calculatePoints(b));
    }
    @Test
    void Card1(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(1);
        assertEquals(2, p.calculatePoints(b));
    }

    @Test
    void Card2(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(2);
        assertEquals(0, p.calculatePoints(b));
    }

    @Test
    void Card3(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(3);
        assertEquals(1, p.calculatePoints(b));
    }
    @Test
    void Card4(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(4);
        assertEquals(0, p.calculatePoints(b));
    }

    @Test
    void Card5(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(5);
        assertEquals(4, p.calculatePoints(b));
    }
    @Test
    void Card6(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(6);
        assertEquals(1, p.calculatePoints(b));
    }

    @Test
    void Card7(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(7);
        assertEquals(1, p.calculatePoints(b));
    }

    @Test
    void Card8(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(8);
        assertEquals(2, p.calculatePoints(b));
    }

    @Test
    void Card9(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(9);
        assertEquals(0, p.calculatePoints(b));
    }

    @Test
    void Card10(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(10);
        assertEquals(0, p.calculatePoints(b));
    }
    @Test
    void Card11(){
        Tile[][] tile = new Tile[BOOKSHELF_HEIGHT][BOOKSHELF_WIDTH];
        ConfigurationTiles(tile);
        Bookshelf b = new Bookshelf();
        b.setPositionTiles(tile);
        PersonalObjectiveCard p = new PersonalObjectiveCard(11);
        assertEquals(1, p.calculatePoints(b));
    }
}

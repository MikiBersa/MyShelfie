package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.model.Bookshelf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookShelfTest {
    @Test
    void isSpecificColumnValid() {
        Bookshelf bookshelf = new Bookshelf();
        assertTrue(bookshelf.isSpecificColumnValid(3, 3));

        int j = 0;

        for (int i = 5; i >= 3; i--) bookshelf.setTile(i, j, new Tile(1, Constant.Color.DARK_BLUE, "C_0"));
        assertTrue(bookshelf.isSpecificColumnValid(j, 3));

        bookshelf.setTile(0, j, new Tile(1, Constant.Color.DARK_BLUE, "C_0"));
        assertFalse(bookshelf.isSpecificColumnValid(j, 3));

        j = 2;

        for (int i = 5; i >= 4; i--) bookshelf.setTile(i, j, new Tile(1, Constant.Color.DARK_BLUE, "C_0"));
        assertTrue(bookshelf.isSpecificColumnValid(j, 3));

        bookshelf.setTile(3, j, new Tile(1, Constant.Color.DARK_BLUE, "C_0"));
        assertTrue(bookshelf.isSpecificColumnValid(j, 3));
    }

    @Test
    void isColumnValid() {
        Bookshelf bookshelf = new Bookshelf();
        Random rand = new Random();
        int[] free = new int[Constant.BOOKSHELF_WIDTH];

        for (int j = 0; j < Constant.BOOKSHELF_WIDTH; j++) {
            int number = rand.nextInt(7);
            System.out.println("number: " + number);
            for (int i = 5; i >= number; i--) {
                free[j] = number;
                System.out.println(i + " " + j + " add ");
                bookshelf.setTile(i, j, new Tile(1, Constant.Color.DARK_BLUE, "C_0"));
            }
        }

        int selection = 2;
        boolean valid = false;

        for (int num : free)
            if (num >= selection) {
                valid = true;
                break;
            }

        System.out.println("valore: " + valid);

        Assertions.assertEquals(valid, bookshelf.hasEnoughSpace(selection));

    }

    @Test
    void notColumnValid() {
        Bookshelf bookshelf = new Bookshelf();

        for (int j = 0; j < Constant.BOOKSHELF_WIDTH; j++) {
            for (int i = 5; i >= 0; i--) {
                bookshelf.setTile(i, j, new Tile(1, Constant.Color.DARK_BLUE, "C_0"));
            }
        }

        assertFalse(bookshelf.hasEnoughSpace(3));
        assertFalse(bookshelf.hasEnoughSpace(2));
        assertFalse(bookshelf.hasEnoughSpace(1));
    }
}

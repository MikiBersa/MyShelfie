package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.network.Observer.oberserverOut.ManagementObserver;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ClientCliControllerTest {
    Controller controller;

    @BeforeEach
    void beforeEach() {
        controller = new Controller();
    }

    /**
     * Creates the game and add users
     */
    @Test
    void gameWithTwoPeople() {
        assertEquals(-1, controller.startGame());

        assertTrue(controller.createGame(2, new ManagementObserver()));
        assertFalse(controller.createGame(2, new ManagementObserver()));
        assertEquals(1, controller.addUser(0, "Tommy"));
        assertEquals(0, controller.addUser(0, "Tommy"));
        assertEquals(1, controller.addUser(1, "Marco"));
        //ritorna -2 perchè ha già riempito il game di giocatori del numero richiesto
        assertEquals(-2, controller.addUser(2, "Marco"));
        assertEquals(-2, controller.addUser(3, "Luca"));
    }

    @Test
    void takeCardsValid() {
        gameWithTwoPeople();
        int res = controller.startGame();
        assertTrue(res <= 1 && res >= 0);

        ArrayList<Pair<Integer, Integer>> cardsToTake = new ArrayList<>();
        cardsToTake.add(new Pair<>(4, 7));
        cardsToTake.add(new Pair<>(3, 7));

        assertNotNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(7, 5));
        cardsToTake.set(1, new Pair<>(7, 4));

        assertNotNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(5, 6));
        cardsToTake.set(1, new Pair<>(3, 6));
        cardsToTake.add(new Pair<>(4, 6));

        assertNotNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(6, 5));
        cardsToTake.set(1, new Pair<>(6, 4));
        cardsToTake.set(2, new Pair<>(6, 3));

        assertNotNull(controller.takeCards(0, cardsToTake));
    }

    @Test
    void takeCardsInvalid() {
        gameWithTwoPeople();
        int res = controller.startGame();
        assertTrue(res <= 1 && res >= 0);

        ArrayList<Pair<Integer, Integer>> cardsToTake = new ArrayList<>();
        cardsToTake.add(new Pair<>(0, 0));
        cardsToTake.add(new Pair<>(-1, -1));

        assertNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(10, 5));
        cardsToTake.set(1, new Pair<>(11, 4));

        assertNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(5, 6));
        cardsToTake.set(1, new Pair<>(3, 6));
        cardsToTake.add(new Pair<>(4, 6));

        assertNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(6, 5));
        cardsToTake.set(1, new Pair<>(6, 4));
        cardsToTake.set(2, new Pair<>(6, 3));

        assertNull(controller.takeCards(0, cardsToTake));

        cardsToTake.set(0, new Pair<>(4, 7));
        cardsToTake.set(1, new Pair<>(4, 7));
        cardsToTake.remove(2);

        assertNull(controller.takeCards(0, cardsToTake));
    }

    @Test
    void putTilesInBookshelf() {
        gameWithTwoPeople();
        int res = controller.startGame();
        assertTrue(res <= 1 && res >= 0);

        ArrayList<Pair<Integer, Integer>> cardsToTake = new ArrayList<>();
        cardsToTake.add(new Pair<>(4, 7));
        cardsToTake.add(new Pair<>(3, 7));

        List<Tile> cardsTaken = controller.takeCards(0, cardsToTake);
        controller.setSelections(cardsTaken);
        assertNotNull(cardsTaken);
        assertEquals(1, controller.putTilesInBookshelf(0, 0, cardsTaken));
        // Now the player with id = 0 has 2 tiles in the first column of the bookshelf

        cardsToTake.set(0, new Pair<>(7, 5));
        cardsToTake.set(1, new Pair<>(7, 4));
        cardsTaken = controller.takeCards(0, cardsToTake);
        controller.setSelections(cardsTaken);
        assertNotNull(cardsTaken);
        assertEquals(1,controller.putTilesInBookshelf(0, 0, cardsTaken));
        // Now the player with id = 0 has 4 tiles in the first column of the bookshelf

        cardsToTake.set(0, new Pair<>(5, 6));
        cardsToTake.set(1, new Pair<>(3, 6));
        cardsToTake.add(new Pair<>(4, 6));
        cardsTaken = controller.takeCards(0, cardsToTake);
        controller.setSelections(cardsTaken);
        assertNotNull(cardsTaken);
        assertEquals(-2,controller.putTilesInBookshelf(0, 0, cardsTaken));
        assertEquals(1,controller.putTilesInBookshelf(0, 1, cardsTaken));
        // Now the player with id = 0 has 4 tiles in the first column of the bookshelf and 3 tiles in the
        // second column

        cardsToTake.set(0, new Pair<>(6, 5));
        cardsToTake.remove(2);
        cardsToTake.remove(1);
        cardsTaken = controller.takeCards(0, cardsToTake);
        controller.setSelections(cardsTaken);
        assertNotNull(cardsTaken);
        assertEquals(1,controller.putTilesInBookshelf(0, 0, cardsTaken));
        // Now the player with id = 0 has 5 tiles in the first column of the bookshelf and 3 tiles in the
        // second column

        cardsToTake.set(0, new Pair<>(6, 4));
        cardsToTake.add(new Pair<>(6, 3));
        cardsTaken = controller.takeCards(0, cardsToTake);
        controller.setSelections(cardsTaken);
        assertNotNull(cardsTaken);
        assertEquals(1,controller.putTilesInBookshelf(0, 1, cardsTaken));
        // Now the player with id = 0 has 5 tiles in the first column of the bookshelf and 5 tiles in the
        // second column

        cardsToTake.set(0, new Pair<>(5, 4));
        cardsToTake.set(1, new Pair<>(5, 3));
        cardsTaken = controller.takeCards(0, cardsToTake);
        controller.setSelections(cardsTaken);
        assertNotNull(cardsTaken);
        assertEquals(-2,controller.putTilesInBookshelf(0, 0, cardsTaken));
        assertEquals(-2,controller.putTilesInBookshelf(0, 1, cardsTaken));
        // Now the player with id = 0 has 5 tiles in the first column of the bookshelf and 5 tiles in the
        // second column => it is impossible to add cards
    }

    @Test
    void simulationOfAGameWithTwoPlayers() {
        gameWithTwoPeople();
        int player = controller.startGame();
        assertTrue(player <= 1 && player >= 0);

        ArrayList<Pair<Integer, Integer>> cardsToTake = new ArrayList<>();

        cardsToTake.add(new Pair<>(4, 7));
        cardsToTake.add(new Pair<>(3, 7));
        List<Tile> cardsTaken = controller.takeCards(player, cardsToTake);
        assertNotNull(cardsTaken);
        assertEquals(1 ,controller.putTilesInBookshelf(player, 0, cardsTaken));
        controller.controlCompletedCommonObjective(player);
        controller.controlCompletedBookshelf(player);
        controller.controlBoardRefill();

        player = controller.nextTurn();
        assertTrue(player <= 1 && player >= 0);

        cardsToTake.set(0, new Pair<>(7, 5));
        cardsToTake.set(1, new Pair<>(7, 4));
        cardsTaken = controller.takeCards(player, cardsToTake);
        assertNotNull(cardsTaken);
        assertEquals(1,controller.putTilesInBookshelf(player, 0, cardsTaken));
        controller.controlCompletedCommonObjective(player);
        controller.controlCompletedBookshelf(player);
        controller.controlBoardRefill();

        player = controller.nextTurn();
        assertTrue(player <= 1 && player >= 0);

        cardsToTake.set(0, new Pair<>(5, 6));
        cardsToTake.set(1, new Pair<>(3, 6));
        cardsToTake.add(new Pair<>(4, 6));
        cardsTaken = controller.takeCards(player, cardsToTake);
        assertNotNull(cardsTaken);
        assertEquals(1,controller.putTilesInBookshelf(player, 0, cardsTaken));
        controller.controlCompletedCommonObjective(player);
        controller.controlCompletedBookshelf(player);
        controller.controlBoardRefill();

        player = controller.nextTurn();
        assertTrue(player <= 1 && player >= 0);

        cardsToTake.set(0, new Pair<>(6, 4));
        cardsToTake.set(1, new Pair<>(6, 3));
        cardsToTake.remove(2);
        cardsTaken = controller.takeCards(player, cardsToTake);
        assertNotNull(cardsTaken);
        assertEquals(1,controller.putTilesInBookshelf(player, 0, cardsTaken));
        controller.controlCompletedCommonObjective(player);
        controller.controlCompletedBookshelf(player);
        controller.controlBoardRefill();
    }


    @Test
    void checkTileTest() {
        gameWithTwoPeople();
        int player = controller.startGame();
        assertTrue(player <= 1 && player >= 0);

        ArrayList<Pair<Integer, Integer>> cardsToTake = new ArrayList<>();

        cardsToTake.add(new Pair<>(4, 7));
        cardsToTake.add(new Pair<>(3, 7));

        List<Tile> cardsTaken = controller.takeCards(player, cardsToTake);
        assertNotNull(cardsTaken);
        controller.setSelections(cardsTaken);
        assertTrue(controller.checkTile(cardsTaken));

        List<Tile> other = new ArrayList<>();
        other.add(cardsTaken.get(1));
        other.add(cardsTaken.get(0));
        controller.setSelections(other);

        assertTrue(controller.checkTile(cardsTaken));

        other = new ArrayList<>();
        other.add(cardsTaken.get(0));
        other.add(cardsTaken.get(0));
        controller.setSelections(other);

        assertFalse(controller.checkTile(cardsTaken));
    }
}
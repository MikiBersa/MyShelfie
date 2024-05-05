package it.polimi.ingsw.utils;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.UserStore;
import it.polimi.ingsw.server.network.Observer.oberserverOut.ManagementObserver;
import it.polimi.ingsw.utils.store.StoreGame;
import it.polimi.ingsw.utils.store.StoreReader;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class StoreGameTest {
    // test the storage
    Controller controller;

    @BeforeEach
    void beforeEach() {
        StoreReader.eliminate();
        controller = new Controller();
    }
    @Test
    void storageGame(){

        controller.createGame(2, new ManagementObserver());

        controller.addUser(0, "Tommy");
        controller.addUser(1, "Marco");

        StoreGame storeGame = controller.getStoreGame();

        storeGame.setUser(new UserStore(controller.getPlayers().get(0).getValue(), controller.getPlayers().get(0).getKey()));
        storeGame.setUser(new UserStore(controller.getPlayers().get(1).getValue(), controller.getPlayers().get(1).getKey()));
        System.out.println(storeGame.getPlayers());
        System.out.println(controller.getPlayers());

        controller.setStoreGame(StoreReader.restore());

        assertTrue(controller.understandRestore());
    }

    @Test
    void storageNotGame(){

        controller.createGame(2, new ManagementObserver());

        controller.addUser(0, "Tommy");
        controller.addUser(1, "Gianni");

        StoreGame storeGame = controller.getStoreGame();

        System.out.println(storeGame.getPlayers());
        System.out.println(controller.getPlayers());

        assertFalse(controller.understandRestore());
    }

    @Test
    void storageNotMatchGame(){

        controller.createGame(3, new ManagementObserver());

        controller.addUser(0, "Tommy");
        controller.addUser(1, "Marco");
        controller.addUser(2, "Bella");

        StoreGame storeGame = controller.getStoreGame();

        System.out.println(storeGame.getPlayers());
        System.out.println(controller.getPlayers());

        assertFalse(controller.understandRestore());
    }

    @Test
    void storageTurn(){

        storageGame();

        StoreGame storeGame = controller.getStoreGame();

        ArrayList<Pair<String, Boolean>> list = new ArrayList<>();
        list.add(new Pair<>("Tommy", true));
        list.add(new Pair<>("Marco", false));

        storeGame.setTurns(list);

        Controller controller1 = new Controller();
        controller1.createGame(2, new ManagementObserver());
        controller1.addUser(0, "Tommy");
        controller1.addUser(1, "Marco");

        assertTrue(controller1.understandRestore());
        assertEquals(0, controller1.getIdTurnFromStorage());


        list = new ArrayList<>();
        list.add(new Pair<>("Tommy", false));
        list.add(new Pair<>("Marco", true));
        storeGame = controller.getStoreGame();
        storeGame.setTurns(list);

        Controller controller2 = new Controller();
        controller2.createGame(2, new ManagementObserver());
        controller2.addUser(0, "Tommy");
        controller2.addUser(1, "Marco");

        assertTrue(controller2.understandRestore());
        assertEquals(1, controller2.getIdTurnFromStorage());
    }

}
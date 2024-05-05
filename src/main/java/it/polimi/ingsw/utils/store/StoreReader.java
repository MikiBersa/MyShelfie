package it.polimi.ingsw.utils.store;

import java.io.*;

import static it.polimi.ingsw.Constant.STORE_GAME;

/**
 * StoreReader is an object to manage the stores file of the game
 */
public class StoreReader {

    private static StoreGame storeGame;

    /**
     * A method that restore the game's state when it interrupted
     * @return StoreGame
     */
    public static StoreGame restore(){
        StoreGame storeGame = null;
        
        File file = new File(STORE_GAME);
        String path = file.getAbsolutePath();

        try(FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn)){


            storeGame = (StoreGame)  objectIn.readObject();

            StoreReader.storeGame = storeGame;

            objectIn.close();
            return StoreReader.storeGame;
        }
        catch(Exception e){
            // messaggio di errore
            System.out.println(e.getMessage());
            System.err.println("RESTORE FAILED");
        }

        return null;

    }

    /***
     * A method that stores the game's state when it interrupts
     * @param storeGame
     */
    public static void store(StoreGame storeGame) {
        ObjectOutputStream oss;
        File file = new File(STORE_GAME);
        String path = file.getAbsolutePath();


        try(FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)){
            objectOut.writeObject(storeGame);
            objectOut.flush();
        }
        catch(Exception e){
            System.out.println(e);
            System.err.println("YOU WON'T BE ABLE TO SAVE THE FILE");
        }

    }

    /***
     * A method that deletes the stored game when the game is finished
     */
    public static void eliminate(){
        StoreReader.storeGame  = new StoreGame();
        store(StoreReader.storeGame);
    }

    /***
     * A method that controls if the stored game's file is there and if it doesn't exist it creates anew
     */
    public static void controllStore() {
        File file = new File(STORE_GAME);
        if (!file.exists()) {
            eliminate();
            System.err.println("Creation of the file");
        }
    }
    // SINGLETON PATTERN

    /**
     * It gets the reference to the StoreGame
     *
     * @return It'll return the StoreGame
     */
    public static StoreGame getStoreGame() {
        if (StoreReader.storeGame == null) {
            StoreReader.storeGame = new StoreGame();
        }
        return StoreReader.storeGame;
    }
}

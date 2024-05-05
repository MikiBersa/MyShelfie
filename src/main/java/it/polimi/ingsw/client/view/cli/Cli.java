package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.client.controller.CliController;
import it.polimi.ingsw.client.model.VirtualModel;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.utils.Log;
import javafx.util.Pair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Cli
 */
public class Cli implements View {
    private final VirtualModel virtualModel;
    private final CliController cliController;
    private final PrintStream out = System.out;
    private final ExecutorService reader = Executors.newSingleThreadExecutor();
    private final ExecutorService asyncInput = Executors.newSingleThreadExecutor();
    private final Object lockPrintWrite = new Object();
    private final Logger log = Log.getInstance();

    /**
     * Constructor of the Cli
     * @param cliController cliController
     * @param virtualModel virtualModel
     */
    public Cli(CliController cliController, VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
        this.cliController = cliController;
    }

    /**
     * Start function of the CLI
     */
    public void start() {
        showMessage("Welcome to MyShelfie");
        askServerInfo();
        askUsername();
    }

    /**
     * Read a line from the stdin
     *
     * @return the line read
     */
    private String readLine() {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        reader.submit(futureTask);

        String input = null;
        try {
            input = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    /**
     * Ask the server info
     */
    @Override
    public void askServerInfo() {
        out.println("Insert the IP address of the server: ");
        String ip = readLine();
        out.println("Insert the port where the game is running: ");
        String port = readLine();
        cliController.openSocket(ip, Integer.parseInt(port));
    }

    /**
     * Ask the username of the player
     */
    @Override
    public void askUsername() {
        out.println("Insert the username: ");
        String username = readLine();
        username = username.substring(0, Math.min(username.length(), Constant.MAX_CHARACTERS_USERNAME));
        cliController.sendUsername(username);
    }

    /**
     * Ask the number of players of the game
     */
    @Override
    public void askNumberOfPlayers() {
        out.println("Select the number of players of the game (from 2 to 4)");
        String players = readLine();
        cliController.sendNumberOfPlayers(Integer.parseInt(players));
    }

    /**
     * Take the asynchronous input
     */
    public void asynchronousInput() {
        asyncInput.submit(() -> {
            while (!asyncInput.isShutdown()) {
                String input = readLine();

                synchronized (lockPrintWrite) {
                    if (input == null) continue;
                    if (!input.equalsIgnoreCase("i")) {
                        out.println("Not valid input: to enter in the command mode press i and enter");
                        continue;
                    }

                    out.println("Input your line (chat, take, ordering, board, personal, common, bookshelves, disconnect, quit) and then what you want");
                    input = readLine();
                    List<String> tokens = Arrays.asList(input.split(" "));
                    if (tokens.size() == 0) continue;
                    if (tokens.get(0).equalsIgnoreCase("chat") && tokens.size() >= 3) {
                        tokens = tokens.subList(1, tokens.size());
                        if (tokens.get(0).equalsIgnoreCase("all") || virtualModel.getListOfUsername().contains(tokens.get(0))) {
                            String destination = tokens.get(0);
                            tokens = tokens.subList(1, tokens.size());
                            cliController.sendChatMessage(tokens.stream().reduce("", (a, b) -> a + " " + b), destination);
                        } else {
                            out.println("Not valid input: the second word must be a username or 'all'");
                        }
                    } else if (tokens.get(0).equalsIgnoreCase("take") && virtualModel.getTakeTiles()) {
                        tokens = tokens.subList(1, tokens.size());
                        parsingCoordinates(tokens);
                    } else if (tokens.get(0).equalsIgnoreCase("ordering") && virtualModel.getOrdering()) {
                        tokens = tokens.subList(1, tokens.size());
                        parsingOrder(tokens, virtualModel.getListOfTiles());
                    } else if (tokens.get(0).equalsIgnoreCase("board") && virtualModel.getReadyGame()) {
                        showBoard();
                    } else if (tokens.get(0).equalsIgnoreCase("personal") && virtualModel.getReadyGame()) {
                        showPersonalCard();
                    } else if (tokens.get(0).equalsIgnoreCase("common") && virtualModel.getReadyGame()) {
                        showCommonCards();
                    } else if (tokens.get(0).equalsIgnoreCase("bookshelves") && virtualModel.getReadyGame()) {
                        showBookshelves();
                    } else if (tokens.get(0).equalsIgnoreCase("disconnect")) {
                        cliController.sendDisconnectMessage();
                    } else if (tokens.get(0).equalsIgnoreCase("quit")) {
                        out.println("Quitting line command");
                    } else {
                        out.println("Invalid input: repeat all the sequence");
                    }
                }
            }
        });
    }

    /**
     * Parsing of the coordinates after the input of the player
     *
     * @param tokens input of the player
     */
    private void parsingCoordinates(List<String> tokens) {
        // odd number or if too many coordinates
        if (tokens.size() % 2 == 1 || tokens.size() > 6) {
            out.println("Not valid selection of coordinates");
        } else {
            List<Pair<Integer, Integer>> listOfTiles = new ArrayList<>();
            try {
                for (int i = 0; i < tokens.size(); i += 2) {
                    int x = Integer.parseInt(tokens.get(i));
                    int y = Integer.parseInt(tokens.get(i + 1));
                    if (x < 0 || y < 0 || x >= Constant.BOARD_HEIGHT || y >= Constant.BOARD_WIDTH) {
                        throw new NumberFormatException();
                    }
                    Pair<Integer, Integer> newPair = new Pair<>(x, y);
                    listOfTiles.add(newPair);
                }
                cliController.sendListOfTiles(listOfTiles);
            } catch (NumberFormatException e) {
                out.println("The input written is not right!");
            }
        }
    }

    /**
     * Parsing the order and the column of the bookshelf
     *
     * @param tokens      input of the player
     * @param listOfTiles tiles selected previously
     */
    private void parsingOrder(List<String> tokens, List<Tile> listOfTiles) {
        if (tokens.size() == listOfTiles.size() + 1) {
            try {
                List<Integer> order = new ArrayList<>();

                for (String token : tokens) {
                    order.add(Integer.parseInt(token));
                }

                List<Tile> listOfTilesToSend = new ArrayList<>();
                for (int i = 0; i < order.size() - 1; i++) {
                    int indexTile = order.get(i) - 1;
                    if (indexTile < 0 || indexTile >= listOfTiles.size()) {
                        throw new NumberFormatException();
                    }
                    listOfTilesToSend.add(listOfTiles.get(order.get(i) - 1));
                }

                int column = order.get(order.size() - 1);
                if (column < 0 || column >= Constant.BOOKSHELF_WIDTH) {
                    throw new NumberFormatException();
                }

                cliController.sendOrderOfTilesAndColumn(listOfTilesToSend, column);
            } catch (NumberFormatException e) {
                out.println("The input written is not right!");
            }
        } else {
            out.println("The input written is not right!");
        }
    }

    /**
     * Print the message to take the tiles
     */
    @Override
    public void askTilesToTake() {
        showMessage("It's your turn: Which tiles do you want to take? " +
                "Press i then write <take> and after in the same line" +
                " write two coordinates the first is the row and the second is the column\nExample: 1 3 => row number 1 and column number 3");
    }

    /**
     * Print the message to ask for the order of the tiles and the column of the bookshelf
     *
     * @param listOfTiles tiles selected previously
     */
    @Override
    public void askOrderOfTilesAndColumn(List<Tile> listOfTiles) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("In which order do you want to take the tiles? Write for each pair a number that represents the order starting by 1\n")
                .append("Then write also the column of the bookshelf where you want to put the tiles starting from 0 to 4")
                .append("Example: B B W    =>    2 1 3 3\n")
                .append("Color of the tiles selected: ");

        for (Tile tile : listOfTiles) {
            stringBuilder.append(Constant.colorToString(tile.getColor())).append(' ');
        }
        stringBuilder.append('\n').append("Press i and enter, then select the order of your tiles and the column");
        showMessage(stringBuilder.toString());
    }

    /**
     * Show the board of the game
     */
    @Override
    public void showBoard() {
        showMessage(virtualModel.boardToString());
    }

    /**
     * Show the personal card of the player
     */
    @Override
    public void showPersonalCard() {
        showMessage(virtualModel.personalCardToString());
    }

    /**
     * Show the common goal cards of the game
     */
    @Override
    public void showCommonCards() {
        showMessage(virtualModel.commonGoalCardsToString());
    }

    /**
     * Show the bookshelves of all the players
     */
    public void showBookshelves() {
        showMessage(virtualModel.bookshelvesToString());
    }

    /**
     * Show the winner of the game
     *
     * @param winner the winner of the game
     */
    @Override
    public void showWinner(String winner) {
        showMessage("The winner is " + winner);
        showMessage("Thank you for playing the game. Disconnecting");
    }

    /**
     * Function to print a string in a synchronous way
     *
     * @param s the string
     */
    public void showMessage(String s) {
        synchronized (lockPrintWrite) {
            out.println(s);
        }
    }

    /**
     * Show the final points of all the players
     *
     * @param points the points of all the players
     */
    public void showFinalPoints(List<Pair<String, Integer>> points) {
        for (Pair<String, Integer> pair : points) {
            showMessage(pair.getKey() + " has scored " + pair.getValue() + " points");
        }
    }

    /**
     * Shutdown of the CLI
     */
    public void shutdown() {
        asyncInput.shutdown();
        reader.shutdown();
        try {
            if (!reader.awaitTermination(3, TimeUnit.SECONDS)) {
                reader.shutdownNow(); // Cancel currently executing tasks
                if (!reader.awaitTermination(3, TimeUnit.SECONDS))
                    log.info("Reader thread did not terminate");

                asyncInput.shutdownNow();
                if (!asyncInput.awaitTermination(3, TimeUnit.SECONDS))
                    log.info("Asynchronous I/O thread did not terminate");
            }
        } catch (InterruptedException e) {
            // (Re-)Cancel if current thread also interrupted
            reader.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
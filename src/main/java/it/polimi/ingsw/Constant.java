package it.polimi.ingsw;

import java.util.Arrays;
import java.util.List;

/**
 * Constant of the game
 */
public final class Constant {

    public static final String STILE_CLICCATO = "-fx-border-color: green; -fx-border-width: 2px;";
    public static final String STILE_OVER = "-fx-border-color: yellow; -fx-border-width: 2px;";
    public static final String STILE_PREDEFINITO = "";

    public static final String STILE_SFONDO = "-fx-background-color: rgba(255, 255, 0, 0.5);";
    public static final int MAX_CHARACTERS_USERNAME = 12;

    public enum Color {
        GREEN, WHITE, YELLOW, DARK_BLUE, LIGHT_BLUE, PINK
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_LIGHT_BLUE = "\u001B[34m";

    public static String colorToString(Color color) {
        if (color == Constant.Color.GREEN) return ANSI_GREEN+"G"+ANSI_RESET;
        if (color == Constant.Color.DARK_BLUE) return ANSI_BLUE+"B"+ANSI_RESET;
        if (color == Constant.Color.LIGHT_BLUE) return ANSI_LIGHT_BLUE+"L"+ANSI_RESET;
        if (color == Constant.Color.PINK) return ANSI_PURPLE+"P"+ANSI_RESET;
        if (color == Constant.Color.WHITE) return ANSI_WHITE+"W"+ANSI_RESET;
        if (color == Constant.Color.YELLOW) return ANSI_YELLOW+"Y"+ANSI_RESET;
        return "";
    }

    //structure of game
    public static int TILES_OF_TYPE = 22;
    public static int BOARD_WIDTH = 9;
    public static int BOARD_HEIGHT = 9;

    public static int BOOKSHELF_WIDTH = 5;

    public static int BOOKSHELF_HEIGHT = 6;


    public enum MessageNumber {
        RETURN_BOARD, RETURN_ID_PERSONAL_CARDS, RETURN_ID_COMMON_CARDS, RETURN_BOOKSHELF, RETURN_POINTS_COMMON, RETURN_POINT_FINISHED, RETURN_POINT_FINAL, RETURN_WINNER,
        RETURN_QUIT
    }

    //about cards
    public static int COMMON_GOAL_CARDS_PER_GAME = 2;
    public static int COMMON_GOAL_CARDS = 12;
    public static int PERSONAL_GOAL_CARDS = 12;
    public static int NUMBER_OBJECTIVES_PERSONAL_CARD = 12;
    public static int NUMBER_OF_OBJECTIVES = 6;
    public static List<Integer> COMMON_POINTS_FOR_TWO_PLAYERS = Arrays.asList(8, 4, 0);
    public static List<Integer> COMMON_POINTS_FOR_THREE_PLAYERS = Arrays.asList(8, 6, 4, 0);
    public static List<Integer> COMMON_POINTS_FOR_FOUR_PLAYERS = Arrays.asList(8, 6, 4, 2, 0);

    //some support constant
    public static int[] DX = {0, 1, 0, -1};
    public static int[] DY = {1, 0, -1, 0};

    //path JSON FILE
    public static String BOARD_FILE_VALIDATOR = "src/main/java/it/polimi/ingsw/server/json/board.json";
    public static String PERSONAL_CARDS_FILE_VALIDATOR = "src/main/java/it/polimi/ingsw/server/json/personalCards.json";
    public static String BOOKSHELF_PYRAMID = "src/main/java/it/polimi/ingsw/server/json/bookShelfPyramid.json";
    public static String STORE_GAME = "src/main/java/it/polimi/ingsw/server/store/saveGame.bin";

    public static int PORT = 1337;

    public static int DELAY_PING = 5000;
    public static int TIME_OUT_SOCKET = 10000;

}

package it.polimi.ingsw.server.json;

import it.polimi.ingsw.Constant.Color;
import it.polimi.ingsw.server.model.cards.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static it.polimi.ingsw.Constant.*;
import static it.polimi.ingsw.Constant.Color.*;

/**
 * the handler of reading form json file
 */
public class JsonReader {

    /**
     * Method that reads from JSON the personal objective card
     * @param id personal objective card's id
     * @return array of elements
     */
    public static Element[] readerPersonalCards(int id){
        Element[] elements = new Element[NUMBER_OF_OBJECTIVES];
        try {
            JSONParser parser = new JSONParser();
            File file = new File(PERSONAL_CARDS_FILE_VALIDATOR);
            String path = file.getAbsolutePath();
            JSONObject obj;
            FileReader reader = new FileReader(path);
            obj = (JSONObject) parser.parse(reader);

            //here is better launch an exception to control the error
            if(obj == null || id >= NUMBER_OBJECTIVES_PERSONAL_CARD || id < 0)
                return null;

            JSONArray arr = (JSONArray) obj.get("data");

            JSONArray card_id = (JSONArray) arr.get(id);

            for (int i = 0; i < card_id.size(); i++) {
                JSONObject objCard = (JSONObject)  card_id.get(i);


                int x = ((Long) objCard.get("column")).intValue();
                int y = ((Long) objCard.get("row")).intValue();
                Color color = traduction((String) objCard.get("color"));


                elements[i] = new Element(x, y, color);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return elements;
    }

    /**
     * Method that reads the board based upon the number of players
     * @param player number of players' string
     * @return boolean matrix
     */
    public static boolean[][] readerBoard(String player){
        JSONParser jsonParser = new JSONParser();
        File file = new File(BOARD_FILE_VALIDATOR);
        String path = file.getAbsolutePath();
        JSONObject obj = null;

        try {
            FileReader reader = new FileReader(path);
            //Read JSON file
            obj = (JSONObject) jsonParser.parse(reader);
            //System.out.println(obj);

        } catch (IOException | ParseException e) {
            System.err.println("Errore di input o di parsing del JSON");
            e.printStackTrace();
        }

        if (obj == null) return null;

        JSONArray arr;
        if(player.equals("four")){
            arr = (JSONArray) obj.get("four");
        } else if (player.equals("three")) {
            arr = (JSONArray) obj.get("three");
        }else{
            arr = (JSONArray) obj.get("two");
        }

        boolean[][] validBoard = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < arr.size(); i++) {
            JSONArray post_id = (JSONArray) arr.get(i);
            //System.out.println(post_id);

            for (Object position : post_id) {
                validBoard[i][((Long) position).intValue()] = true;
            }
        }

        return validBoard;
    }

    /**
     * Method that reads the pyramid pattern from JSON
     * @param position position string (right/left)
     * @return boolean matrix
     */
    public static boolean[][] readerPyramidPosition(String position){
            JSONParser jsonParser = new JSONParser();
            File file = new File(BOOKSHELF_PYRAMID);
            String path = file.getAbsolutePath();
            JSONObject obj = null;

            try {
                FileReader reader = new FileReader(path);
                //Read JSON file
                obj = (JSONObject) jsonParser.parse(reader);
                //System.out.println(obj);

            } catch (IOException | ParseException e) {
                System.err.println("Errore di input o di parsing del JSON");
                e.printStackTrace();
            }

            if (obj == null) return null;

            JSONArray arr = null;
            if(position.equals("sx")){
                arr = (JSONArray) obj.get("sx");
            } else if (position.equals("dx")) {
                arr = (JSONArray) obj.get("dx");
            }

            boolean[][] validPyramid = new boolean[BOARD_HEIGHT][BOOKSHELF_WIDTH];

            for (int i = 0; i < Objects.requireNonNull(arr).size(); i++) {
                JSONArray post_id = (JSONArray) arr.get(i);

                for (int j = 0 ; j < post_id.size(); j++) {
                    int elem = ((Long) post_id.get(j)).intValue();

                    validPyramid[i][j] = elem == 1;
                }
            }

            return validPyramid;
        }

    /**
     * Method that translates strings to enums of colors
     * @param string color's string
     * @return color's enum
     */
    private static Color traduction(String string){
            switch (string) {
                case "PINK":
                    return PINK;
                case "DARK_BLUE":
                    return DARK_BLUE;
                case "GREEN":
                    return GREEN;
                case "WHITE":
                    return WHITE;
                case "YELLOW":
                    return YELLOW;
                case "LIGHT_BLUE":
                    return LIGHT_BLUE;
            }

            return null;
        }

}

package it.polimi.ingsw.client.model;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.client.observer.VirtualModelObservable;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.cards.Tile;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.Constant.BOOKSHELF_HEIGHT;
import static it.polimi.ingsw.Constant.BOOKSHELF_WIDTH;

/**
 * VirtualModel of the client
 */
public class VirtualModel extends VirtualModelObservable {
    private VirtualMainUser virtualMainUser;
    private final List<String> usernames = new ArrayList<>();
    private Tile[][] board;
    private CommonObjectiveCard[] commonGoalCards;
    private List<Tile> tileList;
    private boolean readyGame = false;
    private boolean takeTiles = false;
    private boolean ordering = false;
    private int indexBookshelf = 0;
    private List<VirtualUser> virtualUsers;

    /**
     * Create a VirtualModel
     */
    public VirtualModel() {
    }

    /**
     * Create the VirtualMainUser
     *
     * @param id       the id of the user
     * @param username the username of the user
     */
    public void createVirtualMainUser(int id, String username) {
        virtualMainUser = new VirtualMainUser(id, username);
    }

    /**
     * Initialize the VirtualModel
     *
     * @param playersOfTheGame number of players of the game
     * @param listOfPlayers    list of the players with usernames and ids
     */
    public void setNumberOfPlayersAndInitialize(int playersOfTheGame, List<Pair<String, Integer>> listOfPlayers) {
        virtualUsers = new ArrayList<>(playersOfTheGame - 1);
        for (int indexList = 0; indexList < listOfPlayers.size(); indexList++) {
            int idPlayer = listOfPlayers.get(indexList).getValue();
            String usernamePlayer = listOfPlayers.get(indexList).getKey();

            if (!usernamePlayer.equals(virtualMainUser.getUsername())) {
                usernames.add(usernamePlayer);
                virtualUsers.add(new VirtualUser(idPlayer, usernamePlayer));
            }
        }

        commonGoalCards = new CommonObjectiveCard[Constant.COMMON_GOAL_CARDS_PER_GAME];
    }

    /**
     * Set the common goal cards of the game
     *
     * @param commonGoalCards the common goal cards
     */
    public void setCommonGoalCards(CommonObjectiveCard[] commonGoalCards) {
        for (int i = 0; i < commonGoalCards.length; i++) {
            this.commonGoalCards[i] = commonGoalCards[i];
        }
        notifyUpdateModel(View::showCommonCards);
    }

    /**
     * Update the common goal points of a player
     *
     * @param player           the player that has achieved the common goal
     * @param idCommonGoalCard the id of the common goal card
     * @param pointsGained     the points gained by the player
     */
    public void updateCommonPoints(int player, int idCommonGoalCard, int pointsGained) {
        for (CommonObjectiveCard commonObjectiveCard : commonGoalCards) {
            if (commonObjectiveCard.getId() == idCommonGoalCard) {
                commonObjectiveCard.completedObjective();
            }
        }

        if (player == virtualMainUser.getId()) {
            virtualMainUser.addCommonPoints(pointsGained);
        } else {
            for (VirtualUser virtualUser : virtualUsers) {
                if (virtualUser.getId() == player) {
                    virtualUser.addCommonPoints(pointsGained);
                }
            }
        }
        notifyUpdateModel(View::showCommonCards);
    }

    /**
     * Update the bookshelf of a player
     *
     * @param username  the username of the player that has modified his/her bookshelf
     * @param bookshelf the updated bookshelf
     */
    public void updateBookshelf(String username, Bookshelf bookshelf) {
        if (virtualMainUser.getUsername().equals(username)) {
            virtualMainUser.setBookshelf(bookshelf);
        }

        for (VirtualUser virtualUser : virtualUsers) {
            if (virtualUser.getUsername().equals(username)) {
                virtualUser.setBookshelf(bookshelf);
            }
        }
        notifyUpdateModel(View::showBookshelves);
    }

    /**
     * toString method of the personal card
     *
     * @return the personal card to string
     */
    public String personalCardToString() {
        return virtualMainUser.getPersonalObjectiveCard().toString();
    }

    /**
     * toString method of the common goal cards
     *
     * @return the common goal cards to string and the points of all the players
     */
    public String commonGoalCardsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CommonObjectiveCard commonObjectiveCard : commonGoalCards) {
            stringBuilder.append(commonObjectiveCard.toString()).append('\n');
        }
        stringBuilder.append('\n');
        stringBuilder.append(virtualMainUser.getUsername()).append(" has scored ").append(virtualMainUser.getCommonPoints()).append(" common points\n");

        for (VirtualUser virtualUser : virtualUsers) {
            stringBuilder.append(virtualUser.getUsername()).append(" has scored ").append(virtualUser.getCommonPoints()).append(" common points\n");
        }

        return stringBuilder.toString();
    }

    /**
     * toString method of the bookshelves
     *
     * @return the bookshelves to string
     */
    public String bookshelvesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < BOOKSHELF_HEIGHT; i++) {
            stringBuilder.append(creationOfaBookshelfLine(virtualMainUser.getBookshelf().getPositionTiles(), i));
            for (VirtualUser virtualUser : virtualUsers) {
                stringBuilder.append(creationOfaBookshelfLine(virtualUser.getBookshelf().getPositionTiles(), i));
            }
            stringBuilder.append('\n');
        }

        stringBuilder.append(virtualMainUser.getUsername()).append(" ".repeat(21 - virtualMainUser.getUsername().length()));
        for (VirtualUser virtualUser : virtualUsers) {
            stringBuilder.append(virtualUser.getUsername()).append(" ".repeat(21 - virtualUser.getUsername().length()));
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    /**
     * Helper method of the bookshelf
     *
     * @param positionTiles the tiles of the bookshelf
     * @param indexOfLine   the index of the line
     * @return the line to string
     */
    private String creationOfaBookshelfLine(Tile[][] positionTiles, int indexOfLine) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < BOOKSHELF_WIDTH; j++) {
            stringBuilder.append('|').append(positionTiles[indexOfLine][j] == null ? " " : Constant.colorToString(positionTiles[indexOfLine][j].getColor()));
        }
        stringBuilder.append('|').append(" ".repeat(10));
        return stringBuilder.toString();
    }

    /**
     * toString method of the board
     *
     * @return the board to string
     */
    public String boardToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(i).append(" ");
        }
        stringBuilder.append('\n');

        for (int i = 0; i < board.length; i++) {
            stringBuilder.append(i).append(" ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    stringBuilder.append("  ");
                } else {
                    stringBuilder.append(Constant.colorToString(board[i][j].getColor())).append(" ");
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * Get the board of the VirtualModel
     *
     * @return the board
     */
    public Tile[][] getBoard() {
        return this.board;
    }

    /**
     * Set the board of the VirtualModel
     *
     * @param board the updated board
     */
    public void setBoard(Tile[][] board) {
        this.board = board;
        // qui dovrebbe chiamare la board e settare i valori
        notifyUpdateModel(View::showBoard);
    }

    /**
     * Get if the VirtualMainUser can take the tiles
     *
     * @return if the VirtualMainUser can take the tiles
     */
    public synchronized boolean getTakeTiles() {
        return takeTiles;
    }

    /**
     * Set if the VirtualMainUser can take the cards
     *
     * @param value the value to set
     */
    public synchronized void setTakeTiles(boolean value) {
        takeTiles = value;
    }

    /**
     * Get if the VirtualMainUser can order the tiles and select the column of the bookshelf
     *
     * @return if the VirtualMainUser can order the tiles and select the column of the bookshelf
     */
    public synchronized boolean getOrdering() {
        return ordering;
    }

    //synchronized: The ordering status can be retrieved by the view or can be set by the controller after a message by the server

    /**
     * Set if the VirtualMainUser can order the tiles and select the column of the bookshelf
     *
     * @param value the value to set
     */
    public synchronized void setOrdering(boolean value) {
        ordering = value;
    }

    /**
     * Set the tiles that a player has selected
     *
     * @param tileList the tiles selected by the player
     */
    public void setTileList(List<Tile> tileList) {
        this.tileList = tileList;
    }

    /**
     * Get the tiles that a player has selected
     *
     * @return the list of tiles
     */
    public List<Tile> getListOfTiles() {
        return tileList;
    }

    /**
     * Get if the game is ready
     *
     * @return if the game is ready
     */
    public boolean getReadyGame() {
        return readyGame;
    }

    /**
     * Set if the game is ready
     *
     * @param value the value to set
     */
    public void setReadyGame(boolean value) {
        readyGame = value;
    }

    /**
     * Get the id personal card of the VirtualMainUser
     *
     * @return the id of the personal card of the VirtualMainUser
     */
    public int getPersonalCard() {
        return virtualMainUser.getPersonalObjectiveCard().getId();
    }

    /**
     * Set the personal card of the VirtualMainUser
     *
     * @param personalCard the personal card
     */
    public void setPersonalCard(int personalCard) {
        virtualMainUser.setPersonalObjectiveCard(personalCard);
        notifyUpdateModel(View::showPersonalCard);
    }

    /**
     * Get the common goal cards of the VirtualModel
     *
     * @return the common goal cards
     */
    public CommonObjectiveCard[] getCommonGoalCardsId() {
        CommonObjectiveCard[] idCommon = new CommonObjectiveCard[2];
        idCommon[0] = commonGoalCards[0];
        idCommon[1] = commonGoalCards[1];
        return idCommon;
    }

    /**
     * Get the VirtualMainUser
     *
     * @return the VirtualMainUser
     */
    public VirtualMainUser getVirtualMainUser() {
        return virtualMainUser;
    }

    /**
     * Get the list of the VirtualUser
     *
     * @return the list of the VirtualUser
     */
    public List<VirtualUser> getVirtualUsers() {
        return this.virtualUsers;
    }

    /**
     * Set the point of the first player that has completed the bookshelf
     *
     * @param idPlayer the id of the player that has completed the bookshelf
     */
    public void updateFinishGamePoints(int idPlayer, int pointsGained) {
        for (VirtualUser virtualUser : virtualUsers) {
            if (virtualUser.getId() == idPlayer) {
                // because the player has filled the bookshelf first
                virtualUser.addCommonPoints(pointsGained);
                virtualUser.setFirstFinishedPoint(pointsGained);
                notifyUpdateModel(View::showCommonCards);
            }
        }

        if (virtualMainUser.getId() == idPlayer) {
            virtualMainUser.addCommonPoints(pointsGained);
            virtualMainUser.setFirstFinishedPoint(pointsGained);
            notifyUpdateModel(View::showCommonCards);
        }
    }

    /**
     * Get the list of the usernames of the players
     *
     * @return the list of the usernames
     */
    public List<String> getListOfUsername() {
        return usernames;
    }

    /**
     * Get the first VirtualUser
     *
     * @return the first VirtualUser
     */
    public VirtualUser getFirstVirtualUser() {
        return virtualUsers.get(0);
    }

    /**
     * Get the next VirtualUser
     *
     * @return the next VirtualUser
     */
    public VirtualUser getNextVirtualUser() {
        indexBookshelf++;

        if (indexBookshelf == virtualUsers.size()) {
            indexBookshelf = 0;
        }
        return virtualUsers.get(indexBookshelf);
    }

    /**
     * Get the previous VirtualUser
     *
     * @return the previous VirtualUser
     */
    public VirtualUser getPreviousVirtualUser() {
        indexBookshelf--;

        if (indexBookshelf == -1) {
            indexBookshelf = virtualUsers.size() - 1;
        }

        return virtualUsers.get(indexBookshelf);
    }

    /**
     * Sett the final points of all the players
     *
     * @param points the final points
     */
    public void setFinalPoint(List<Pair<String, Integer>> points) {
        for (Pair<String, Integer> p : points)
            if (virtualMainUser.getUsername().equals(p.getKey()))
                virtualMainUser.setFinalPoint(p.getValue());
            else
                for (VirtualUser virtualUser : virtualUsers)
                    if (virtualUser.getUsername().equals(p.getKey()))
                        virtualUser.setFinalPoint(p.getValue());
    }
}

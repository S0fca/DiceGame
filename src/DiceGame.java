import java.util.ArrayList;
import java.util.List;

public class DiceGame {

    private int numberOfPlayers = 0;
    private final static ArrayList<Player> players = new ArrayList<>();
    private int playerOnTurn = 0;
    private String selectedGame;
    private final Dice dice = new Dice();
    private boolean gameEnd = false;

    private final ArrayList<String> gameNames = new ArrayList<>();

    {
        gameNames.add("ones");
        gameNames.add("twos");
        gameNames.add("trees");
        gameNames.add("fours");
        gameNames.add("fives");
        gameNames.add("sixes");
        gameNames.add("pairs");
        gameNames.add("trios");
        gameNames.add("row");
        gameNames.add("pyramid");
        gameNames.add("smallPoker");
        gameNames.add("bigPoker");
        gameNames.add("general");
    }

    /**
     * gets a list of game names
     *
     * @return a list of game names
     */
    public ArrayList<String> getGameNames() {
        return gameNames;
    }

    /**
     * gets which game the player has selected
     *
     * @return which game the player has selected
     */
    public String getSelectedGame() {
        if (selectedGame == null) {
            return "";
        }
        return selectedGame;
    }

    /**
     * sets which game the player has selected
     *
     * @param selectedGame which game the player has selected
     */
    public void setSelectedGame(String selectedGame) {
        if (isGameSelected()) {
e            return;
        }
        if (isNotPlayed(selectedGame)) {
            this.selectedGame = selectedGame;
        }
    }

    /**
     * sets selected game to null
     */
    public void resetSelectedGame() {
        selectedGame = null;
    }

    /**
     * gets the number of players
     *
     * @return number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * sets how many players will be playing
     *
     * @param numberOfPlayers how many players will be playing
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * checks if the player has selected a game
     *
     * @return if the player has selected a game
     */
    public boolean isGameSelected() {
        return selectedGame != null;
    }

    /**
     * adds players to a list of players
     *
     * @param id   players number
     * @param name players name
     */
    public void addPlayer(int id, String name) {
        Player p = new Player(id, name);
        if (id == 0) {
            p.addOrDeductTries(2);
        }
        players.add(p);
    }

    /**
     * gets an ArrayList of players
     *
     * @return an ArrayList of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * gets a player from a list of players based of the number of the player on turn
     *
     * @return a player on turn
     */
    public Player getPlayerOnTurn() {
        return players.get(playerOnTurn);
    }

    /**
     * gets the index of selected game from list of games names
     *
     * @return index of selected game
     */
    public int getGameIndex(String game) {
        return gameNames.indexOf(game) + 1;
    }

    /**
     * checks if the player has played a game
     *
     * @return if the game has already been played by the current player
     */
    public boolean isNotPlayed(String game) {
        return getPlayerOnTurn().getMap().get(game) == null;
    }

    /**
     * changes player on turn and resets selected game
     */
    public void nextPlayer() {
        if ((playerOnTurn + 1) != numberOfPlayers) {
            ++playerOnTurn;
        } else {
            playerOnTurn = 0;
        }
        addOrDeductTries(2);
        selectedGame = null;
    }

    /**
     * sets points to a selected game for the player  on turn
     */
    public void setGamePoints() {
        int points = dice.checkGameGetPoints(getGameIndex(selectedGame));
        getPlayerOnTurn().setGamePoints(selectedGame, points);
        getPlayerOnTurn().addPoints(points);
    }

    /**
     * adds or deducts tries of the player on turn
     *
     * @param triesNumber number of tries
     */
    public void addOrDeductTries(int triesNumber) {//change map points
        getPlayerOnTurn().addOrDeductTries(triesNumber);
    }

    /**
     * checks if the player has enough tries to throw
     *
     * @return if the player has enough tries
     */
    public boolean hasTries() {
        return getPlayerOnTurn().getTries() > 0;
    }

    /**
     * if the game isn't selected it'll add tries and generate new dice numbers for new player
     */
    public void skipGame() {
        if (!isGameSelected()) {
            dice.generateDiceNumbers();
            nextPlayer();
        }
    }

    /**
     * adds players points on selected game
     */
    public void playGame() {
        if (isGameSelected()) {
            setGamePoints();
            dice.generateDiceNumbers();
        }
    }

    /**
     * will change the number of the selected dice in the list of dice numbers
     *
     * @return if the player has selected a game and has tries
     */
    public boolean selectDice(int  i) {
        if (isGameSelected() && hasTries()) {
            dice.replaceValue(i);
            return true;
        }
        return false;
    }

    /**
     * gets the list of numbers that are supposed to be on the dice from dice class
     *
     * @return list of numbers that are supposed to be on the dice
     */
    public List<Integer> getDiceNumbers() {
        return dice.getDiceNumbers();
    }

    /**
     * checks if the player on turn has played all the games meaning the game is over
     */
    public void gameEnd() {
        if (getPlayerOnTurn().getMap().size() == gameNames.size()) {
            gameEnd = true;
        }
    }

    /**
     * gets boolean that says whether the game is over
     * @return boolean that says whether the game is over
     */
    public boolean isGameEnd() {
        return gameEnd;
    }
}

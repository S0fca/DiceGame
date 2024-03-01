import java.util.LinkedHashMap;
import java.util.Map;

public class Player {

    private final Map<String, Integer> map = new LinkedHashMap<>();
    private int points;
    private final int id;
    private final String name;
    private int tries = 0;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * gets how many points this player has on a game
     *
     * @param gameName name of a game
     * @return how many points this player has on the game
     */
    public int getGamePoints(String gameName) {
        return map.get(gameName);
    }

    /**
     * adds game with points to this players map
     *
     * @param gameName name of a game
     * @param points   number of points this player got
     */
    public void setGamePoints(String gameName, int points) {
        map.put(gameName, points);
    }

    /**
     * adds points to this player
     *
     * @param points number of points this player got
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * gets how many points this player has
     *
     * @return how many points this player has
     */
    public int getPoints() {
        return points;
    }

    /**
     * gets index of this player
     *
     * @return index of this player
     */
    public int getId() {
        return id;
    }

    /**
     * gets the name of this player
     *
     * @return this players name
     */
    public String getName() {
        return name;
    }

    /**
     * gets how many points this player has
     *
     * @return how many points this player has
     */
    public int getTries() {
        return tries;
    }

    /**
     * adds or deducts tries from this player
     *
     * @param tries how many tries to add(+) or deduct(-)
     */
    public void addOrDeductTries(int tries) {
        this.tries += tries;
    }

    /**
     * gets a map of the games this player has played
     *
     * @return a map of games
     */
    public Map<String, Integer> getMap() {
        return map;
    }
}

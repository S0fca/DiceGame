import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class Dice {

    private final Random r = new Random();
    private ArrayList<Integer> diceNumbers;

    public Dice() {
        generateDiceNumbers();
    }

    /**
     * gets an ArrayList with the numbers for dice buttons
     *
     * @return an ArrayList with the numbers for dice buttons
     */
    public ArrayList<Integer> getDiceNumbers() {
        return diceNumbers;
    }

    /**
     * counts how many points the player got on the selected game
     *
     * @param gameNumber number of the selected game
     * @return how many points the player got on the selected game
     */
    public int checkGameGetPoints(int gameNumber) {
        if (gameNumber < 7) {
            return onesToSixes(gameNumber);
        } else if (gameNumber == 11 || gameNumber == 12) {
            return poker(gameNumber);
        }
        return switch (gameNumber) {
            case 7 -> pairs();
            case 8 -> trios();
            case 9 -> row();
            case 10 -> pyramid();
            case 13 -> general();
            default -> 0;
        };
    }

    /**
     * checks how many points the player got on the game pyramid
     *
     * @return how many point the player got on the game pyramid
     */
    private int pyramid() {
        Collections.sort(diceNumbers);
        boolean firstThreeSame = Objects.equals(diceNumbers.get(0), diceNumbers.get(1)) && Objects.equals(diceNumbers.get(1), diceNumbers.get(2));
        boolean lastThreeSame = Objects.equals(diceNumbers.get(3), diceNumbers.get(4)) && Objects.equals(diceNumbers.get(4), diceNumbers.get(5));
        if ((diceNumbers.get(0) + 1 == diceNumbers.get(1) && Objects.equals(diceNumbers.get(1), diceNumbers.get(2)) && diceNumbers.get(2) + 1 == diceNumbers.get(3) && lastThreeSame)
                || (diceNumbers.get(0) - 1 == diceNumbers.get(1) && Objects.equals(diceNumbers.get(1), diceNumbers.get(2)) && diceNumbers.get(2) - 1 == diceNumbers.get(3) && lastThreeSame)
                || (firstThreeSame && diceNumbers.get(2) + 1 == diceNumbers.get(3) && Objects.equals(diceNumbers.get(3), diceNumbers.get(4)) && diceNumbers.get(4) + 1 == diceNumbers.get(5))
                || (firstThreeSame && diceNumbers.get(2) - 1 == diceNumbers.get(3) && Objects.equals(diceNumbers.get(3), diceNumbers.get(4)) && diceNumbers.get(4) - 1 == diceNumbers.get(5))) {
            return diceNumbers.stream().mapToInt(a -> a).sum();
        }
        return 0;
    }

    /**
     * checks how many points the player got on the game row
     *
     * @return how many point the player got on the game row
     */
    private int row() {
        Collections.sort(diceNumbers);
        boolean row = true;
        for (int i = 0; i < 6; i++) {
            if (diceNumbers.get(i) != i + 1) {
                row = false;
                break;
            }
        }
        if (row) {
            return 21;
        }
        return 0;
    }

    /**
     * checks how many points the player got on the game trios
     *
     * @return how many point the player got on the game trios
     */
    private int trios() {
        Collections.sort(diceNumbers);
        if (Objects.equals(diceNumbers.get(0), diceNumbers.get(1)) && Objects.equals(diceNumbers.get(1), diceNumbers.get(2)) && Objects.equals(diceNumbers.get(3), diceNumbers.get(4)) && Objects.equals(diceNumbers.get(4), diceNumbers.get(5))) {
            return diceNumbers.stream().mapToInt(a -> a).sum();
        }
        return 0;
    }

    /**
     * checks how many points the player got on the game pairs
     *
     * @return how many point the player got on the game pairs
     */
    private int pairs() {
        Collections.sort(diceNumbers);
        if (Objects.equals(diceNumbers.get(0), diceNumbers.get(1)) && Objects.equals(diceNumbers.get(2), diceNumbers.get(3)) && Objects.equals(diceNumbers.get(4), diceNumbers.get(5))) {
            return diceNumbers.stream().mapToInt(a -> a).sum();
        }
        return 0;
    }

    /**
     * counts how many points the player got on small/big poker
     *
     * @param gameNumber number of the selected game
     * @return how many points the player got on small/big poker
     */
    private int poker(int gameNumber) {
        int sixes = 0;
        int fives = 0;
        for (int dice : diceNumbers) {
            if (dice == 6) {
                sixes++;
            } else if (dice == 5) {
                fives++;
            }
        }
        if (fives == 2 && sixes == 4 && gameNumber == 12) {
            return 34;
        } else if (fives == 4 && sixes == 2 && gameNumber == 11) {
            return 32;
        }
        return 0;
    }

    /**
     * checks how many points the player got on the game general
     *
     * @return how many point the player got on the game general
     */
    private int general() {
        int points = 0;
        for (int dice : diceNumbers) {
            if (dice == 6) {
                points = points + 6;
            }
        }
        if (points == 36) {
            return points;
        }
        return 0;
    }

    /**
     * counts how many points the player got on games ones to sixes
     *
     * @param gameNumber number of the selected game
     * @return how many points the player got on games ones to sixes
     */
    public int onesToSixes(int gameNumber) {
        int points = 0;
        for (int dice : diceNumbers) {
            if (gameNumber == dice) {
                points += gameNumber;
            }
        }
        if (points < gameNumber * 3) {
            return 0;
        }
        return points;
    }

    /**
     * generates new numbers for dice and puts them in a list
     */
    public void generateDiceNumbers() {
        diceNumbers = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            diceNumbers.add(r.nextInt(6) + 1);
        }
    }

    /**
     * replaces a selected number from the list  with a random number
     *
     * @param number number of dice which the player wants to throw
     */
    public void replaceValue(int number) {
        try {
            diceNumbers.set(number, r.nextInt(6) + 1);
        } catch (Exception ignore) {
        }
    }
}

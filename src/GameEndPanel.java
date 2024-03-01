import javax.swing.*;
import java.awt.*;

public class GameEndPanel extends JPanel {

    private final DiceGame game;

    public GameEndPanel(DiceGame game) {
        this.game = game;
        initialize();
    }

    /**
     * adds components to this panel
     * writes who's the winner
     * writes how many points each player got
     */
    public void initialize() {
        setBackground(Color.WHITE);
        String text = "";
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        int max = Integer.MIN_VALUE;
        String winner = "";
        for (Player p : game.getPlayers()) {
            text += " • " + p.getName() + ": " + p.getPoints() + " \n";
            if (p.getPoints() > max) {
                max = p.getPoints();
                winner = " • " + p.getName() + " " + p.getPoints() + "\n";
            } else if (p.getPoints() == max) {
                winner += " • " + p.getName() + " " + p.getPoints() + "\n";
            }
        }
        textArea.setText("Winner:\t\n" + winner + "\nResults:\n" + text);
        add(textArea);
    }
}

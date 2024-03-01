import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IntroductionPanel extends JPanel {

    private final KeyListener keyAdapterNumberPlayers;
    private final JLabel inputLabelIntroduction = new JLabel();
    private final JTextField inputFieldIntroduction = new JTextField();
    private final DiceGame game;
    private final JFrame frame;
    private int playerNumber = 1;
    final JTextArea rulesText = new JTextArea(10, 20);

    /**
     * creates the introduction panel
     *
     * @param game  the game class
     * @param frame the JFrame
     */
    public IntroductionPanel(DiceGame game, JFrame frame) {
        super(new BorderLayout());
        this.game = game;
        this.frame = frame;
        inputLabelIntroduction.setText("\t\tNumber of players: ");
        rulesText.setText("""
                2-9 Players
                Rules:
                  The first player rolls six dice and based on what they get
                  they'll decide whether they want to save throws (tries),\s
                  or they want to play and pick a game from the left column.

                  Each turn the player gets 6 new random dice and gets 2 tries.
                  If the player picks tries they'll save their tries for later, won't
                  loose a game and the next player will be on turn.\s

                  If the player chooses a game they can roll selected dice until they
                  are happy with the numbers on dice and want to write their points\s
                  (Points button), or until they run out of tries meaning they have to\s
                  click points whether they are happy or not and write their points.

                  If the player doesn't play the game correctly they won't get any\s
                  points and won't be able to play that game anymore.
                  The point are the sum of all dice that are needed for
                  the selected game.

                  Once one player finishes all the games the rest of the
                  round is played and the game is over.
                \s
                Games:
                  Ones to Sixes - at least 3 dice of the selected number
                  Pairs - 3 × two of a kind
                  Trios - 2 × three of a kind
                  Row - numbers 1-6
                  Pyramid - row of 3 consecutive numbers 1 × the biggest/smallest
                                   + 2 × the next number\s
                                   + 3 × next number (the smallest/biggest)
                  Small poker - 4× five + 2× six
                  Big poker - 4× six + 2× five
                  General - 6 × 6
                """);
        rulesText.setEditable(false);
        rulesText.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8));
        add(rulesText, BorderLayout.NORTH);
        add(inputLabelIntroduction, BorderLayout.WEST);
        add(inputFieldIntroduction, BorderLayout.CENTER);
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(e -> {
            if (inputFieldIntroduction.getText().length() > 0 && !inputFieldIntroduction.getText().equals("0") && game.getNumberOfPlayers() == 0) {
                endEnteringNumber();
            } else {
                endEnteringNames();
            }
        });
        add(nextButton, BorderLayout.EAST);
        keyAdapterNumberPlayers = newTextListenerNumber();
        inputFieldIntroduction.addKeyListener(keyAdapterNumberPlayers);
    }

    /**
     * creates a listener for text field to write only numbers
     * sets the number of players
     *
     * @return a listener for writing numbers
     */
    public KeyListener newTextListenerNumber() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isDigit(ch) && ch != KeyEvent.VK_BACK_SPACE || inputFieldIntroduction.getText().length() >= 1 || ch == '0' || ch == '1') {
                    e.consume();
                }
                if (ch == KeyEvent.VK_ENTER && (inputFieldIntroduction.getText().length() > 0)) {
                    endEnteringNumber();
                }
            }
        };
    }

    /**
     * creates and adds a listener to write names
     * adds new players to a list  of players in DiceGame class
     */
    public void newTextListenerNames() {
        inputFieldIntroduction.setText("");
        KeyListener keyAdapterNames = new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                char ch2 = e.getKeyChar();
                if (inputFieldIntroduction.getText().length() > 8) {
                    e.consume();
                }
                if (ch2 == KeyEvent.VK_ENTER) {
                    endEnteringNames();
                }
            }
        };
        inputFieldIntroduction.addKeyListener(keyAdapterNames);
        add(inputFieldIntroduction, BorderLayout.CENTER);
    }

    /**
     * will set the number of players
     * remove number listener
     * add names listener
     */
    public void endEnteringNumber() {
        game.setNumberOfPlayers(Integer.parseInt(inputFieldIntroduction.getText()));
        remove(inputFieldIntroduction);
        inputLabelIntroduction.setText("\t\tName of player " + 1);
        inputFieldIntroduction.removeKeyListener(keyAdapterNumberPlayers);
        remove(inputFieldIntroduction);
        newTextListenerNames();
    }

    /**
     * will set players names and replace introduction panel with the game panel
     */
    public void endEnteringNames() {
        final JPanel thisPanel = this;
        if (inputFieldIntroduction.getText().length() > 0 && game.getNumberOfPlayers() != playerNumber - 1) {
            game.addPlayer(playerNumber - 1, inputFieldIntroduction.getText());
            ++playerNumber;
            inputLabelIntroduction.setText("\t\tName of player " + playerNumber);
            inputFieldIntroduction.setText("");
        }
        if (game.getNumberOfPlayers() == playerNumber - 1) {
            frame.remove(thisPanel);
            JFrame jFrame = new JFrame();
            jFrame.setVisible(true);
            jFrame.add(rulesText);
            jFrame.setTitle("Rules");
            jFrame.pack();
            frame.add((new GamePanel(game, frame)).createGamePanel());
            frame.pack();
        }
    }
}

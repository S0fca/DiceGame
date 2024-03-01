import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel {

    private final JLabel instructions = new JLabel();
    private final JLabel onTurn = new JLabel();
    private final JLabel selectedGameLabel = new JLabel();
    private final List<JButton> diceButtons = new ArrayList<>();
    private final MyCanvas canvas;
    private final DiceGame game;
    private final JFrame frame;
    private final JPanel gamePanel = new JPanel();
    private final JPanel tablePanel = new JPanel(new GridBagLayout());
    private final JPanel panel = new JPanel(new BorderLayout());


    public GamePanel(DiceGame game, JFrame frame) {
        canvas = new MyCanvas(game);
        this.game = game;
        this.frame = frame;
    }

    /**
     * makes the graphics of the game panel
     *
     * @return a JPanel with the game graphics
     */
    public JPanel createGamePanel() {
        gamePanel.setBackground(Color.WHITE);
        canvas.drawTable();
        JPanel gameButtonsPanel = new JPanel(new GridLayout(0, 1)); //buttons
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        createGameButton("", gameButtonsPanel);

        for (String key : game.getGameNames()) {
            JButton games = createGameButton(" " + key + " ", gameButtonsPanel);
            games.addActionListener(e -> {
                game.setSelectedGame(games.getText().trim());
                displayNewValues();
            });
        }

        createGameButton("tries", gameButtonsPanel).addActionListener(e -> {
            int playerId = game.getPlayerOnTurn().getId();
            game.skipGame();
            displayNewValues();
            gameEnd(playerId);
        });
        tablePanel.add(gameButtonsPanel, constraints);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8, false));
        tablePanel.add(canvas, new GridBagConstraints());
        gamePanel.add(tablePanel);
        gamePanel.add(createDiceGamePanel());
        onTurn.setText("On turn: " + game.getPlayerOnTurn().getName());
        return gamePanel;
    }

    /**
     * creates a button with the looks for game button
     *
     * @param text             text on the button
     * @param gameButtonsPanel a panel on which are the game buttons
     * @return the game button
     */
    private static JButton createGameButton(String text, JPanel gameButtonsPanel) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.black));
        gameButtonsPanel.add(button);
        return button;
    }

    /**
     * adds dice buttons with random numbers to a dicePanel
     *
     * @return a JPanel with dice buttons
     */
    public JPanel addDiceButtons() {
        final JPanel dicePanel = new JPanel(new GridLayout(0, 2));
        dicePanel.setBackground(Color.WHITE);
        for (int i = 0; i < 6; i++) {
            JButton diceButton = new JButton(String.valueOf(game.getDiceNumbers().get(i)));
            diceButton.setPreferredSize(new Dimension(30, 30));
            diceButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            int finalI = i;
            diceButton.addActionListener(e -> {
                if (game.selectDice(finalI)) {
                    diceButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1, true));
                }
            });
            diceButtons.add(diceButton);
            dicePanel.add(diceButton);
        }
        return dicePanel;
    }

    /**
     * creates a panel with text and buttons for the game
     *
     * @return a JPanel with text and buttons for the game
     */
    public JPanel createDiceGamePanel() {
        JPanel text = new JPanel(new BorderLayout());
        text.setBackground(Color.WHITE);
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        selectedGameLabel.setText("Selected game: ");
        instructions.setText("Select game.");
        text.add(onTurn, BorderLayout.CENTER);
        text.add(selectedGameLabel, BorderLayout.SOUTH);
        text.add(instructions, BorderLayout.NORTH);
        panel.add(text, BorderLayout.NORTH);
        panel.add(addDiceButtons(), BorderLayout.CENTER);
        JButton throwButton = new JButton();
        throwButton.setText("Throw");
        throwButton.addActionListener(newListenerThrowButton());
        JButton pointsButton = new JButton();
        pointsButton.setText("Points");
        pointsButton.addActionListener(newListenerPointsButton());
        buttons.add(throwButton, BorderLayout.NORTH);
        buttons.add(pointsButton, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * creates an action listener for points button that if the player has selected a game
     * count and set players points
     * display new values
     * change the player on turn
     *
     * @return an action listener for points button
     */
    public ActionListener newListenerPointsButton() {
        return e -> {
            if (game.isGameSelected()) {
                game.playGame();
                game.gameEnd();
                gameEnd(game.getPlayerOnTurn().getId());
                if (!(game.isGameEnd() && (game.getPlayerOnTurn().getId()) == (game.getNumberOfPlayers() - 1))) {
                    game.nextPlayer();
                }
            }
            displayNewValues();
        };
    }

    /**
     * will show the statistics once the game is over
     *
     * @param playerId number of player on turn
     */
    public void gameEnd(int playerId) {
        if (game.isGameEnd() && (playerId == (game.getNumberOfPlayers() - 1))) {
            game.resetSelectedGame();
            canvas.repaint();
            gamePanel.remove(panel);
            gamePanel.add(new GameEndPanel(game));
            frame.pack();
        }
    }

    /**
     * creates an action listener for throw button that if the player has selected a game
     * changes the values on the selected dice button
     * deducts one try from the player on turn
     *
     * @return an action listener for throw button
     */
    public ActionListener newListenerThrowButton() {
        return e -> {
            if (game.isGameSelected() && game.hasTries()) {
                game.addOrDeductTries(-1);
                displayNewValues();
            }
        };
    }

    /**
     * changes text for next player
     * who is on turn
     * selected game
     * instructions
     */
    public void drawNextPlayer() {
        instructions.setText(game.isGameSelected() ? "Select dice to throw." : "Select game.");
        selectedGameLabel.setText(game.isGameSelected() ? "Selected game: " + game.getSelectedGame() : "Selected game: ");
        onTurn.setText("On turn: " + game.getPlayerOnTurn().getName());
    }

    /**
     * displays dice numbers
     * displays how many tries the player has
     */
    public void displayNewValues() {
        drawNextPlayer();
        for (int i = 0; i < 6; i++) {
            diceButtons.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            diceButtons.get(i).setText(String.valueOf(game.getDiceNumbers().get(i)));
        }
        canvas.repaint();
    }
}

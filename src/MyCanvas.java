import java.awt.*;

public class MyCanvas extends Canvas {

    private final DiceGame game;

    public MyCanvas(DiceGame game) {
        this.game = game;
    }

    /**
     * sets canvas size and background color
     */
    public void drawTable() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(game.getNumberOfPlayers() * 80, 525));
    }

    /**
     * draws the table with current points and tries
     *
     * @param g the specified Graphics context
     */
    public void paint(Graphics g) {
        int x = 0;
        int y = 0;
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        for (int i = 0; i < game.getNumberOfPlayers() + 1; i++) {
            graphics.drawLine(x, y, x, 525);
            if (i < game.getNumberOfPlayers()) {
                x += 80;
            }
        }
        for (int i = 0; i < 16; i++) {
            graphics.drawLine(0, y, x, y);
            y += 35;
        }
        x = 12;
        for (Player p : game.getPlayers()) {
            Font f = graphics.getFont();
            if (p.getId() == game.getPlayerOnTurn().getId()) graphics.setFont(f.deriveFont(Font.BOLD));
            graphics.drawString(p.getName(), x, 22);
            graphics.setFont(f);
            x += 80;
        }
        drawTries();
        drawGamePoints();
    }

    /**
     * draws how many tries the players have
     */
    public void drawTries() {
        Graphics g = this.getGraphics();
        int y = getYCoordinate(14);
        for (Player p : game.getPlayers()) {
            int x = getXCoordinate(p.getId());
            String tries = String.valueOf(p.getTries());
            g.setColor(Color.WHITE);
            g.fillRect(x, y - 12, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(tries, x, y);
        }

    }

    /**
     * draws how many points the players have on each game
     */
    public void drawGamePoints() {
        Graphics g = this.getGraphics();
        for (Player p : game.getPlayers()) {
            int x = getXCoordinate(p.getId());
            for (String gameName : p.getMap().keySet()) {
                int y = getYCoordinate(game.getGameIndex(gameName));
                g.drawString(String.valueOf(p.getGamePoints(gameName)), x, y);
            }
        }
        if (game.isGameSelected()) {
            int x = getXCoordinate(game.getPlayerOnTurn().getId());
            int y = getYCoordinate(game.getGameIndex(game.getSelectedGame()));
            g.drawString("__", x, y);
        }
    }

    /**
     * counts y coordinate based on game number
     *
     * @param gameNumber index of the selected game
     * @return y coordinate
     */
    private static int getYCoordinate(int gameNumber) {
        return 58 + (gameNumber - 1) * 35;
    }

    /**
     * counts x coordinate based on players number
     *
     * @param player players number
     * @return x coordinate
     */
    private static int getXCoordinate(int player) {
        return 30 + player * 80;
    }
}

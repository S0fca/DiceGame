import javax.swing.*;

public class MyJFrame {

    /**
     * creates the frame
     */
    public MyJFrame() {
        DiceGame game = new DiceGame();
        JFrame frame = new JFrame();
        frame.setTitle("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(new IntroductionPanel(game, frame));
        frame.pack();
    }
}

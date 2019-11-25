import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final int WIDTH = 200;
    private final int HEIGHT = 500;
    private JButton play;
    private JButton playFrame;
    private JLabel scoreA;
    private JLabel scoreB;

    public ScorePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        play = new JButton("Play");
        add(play, BorderLayout.SOUTH);
        playFrame = new JButton("Frame");
        add(playFrame, BorderLayout.NORTH);

        scoreA = new JLabel();
        add(scoreA, BorderLayout.WEST);
        scoreB = new JLabel();
        add(scoreB, BorderLayout.EAST);
    }

    public JLabel getScoreA() {
        return scoreA;
    }

    public JLabel getScoreB() {
        return scoreB;
    }

    public JButton getPlay() {
        return play;
    }

    public JButton getPlayFrame() {
        return playFrame;
    }
}

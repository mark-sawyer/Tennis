import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ScorePanel extends JPanel {
    private final int WIDTH = 500;
    private final int HEIGHT = 150;
    private JButton play;
    private JLabel scoreA;
    private JLabel scoreB;

    public ScorePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        play = new JButton("Play");
        add(play, BorderLayout.SOUTH);

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
}

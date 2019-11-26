import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final int WIDTH = 200;
    private final int HEIGHT = 500;
    private JButton play;
    private JButton playFrame;
    private JLabel scoreA;
    private JLabel scoreB;
    private JLabel message;

    public ScorePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());

        play = new JButton("Play");
        play.setPreferredSize(new Dimension(150, 100));
        add(play);
        playFrame = new JButton("Frame");
        playFrame.setPreferredSize(new Dimension(150, 50));
        add(playFrame);

        scoreA = new JLabel();
        scoreA.setPreferredSize(new Dimension(80, 50));
        scoreA.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreA);
        scoreB = new JLabel();
        scoreB.setPreferredSize(new Dimension(80, 50));
        scoreB.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreB);
        message = new JLabel();
        message.setPreferredSize(new Dimension(150, 100));
        message.setFont(new Font("Arial", Font.BOLD, 24));
        add(message);
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

    public JLabel getMessage() {
        return message;
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.zip.DeflaterInputStream;

public class ScorePanel extends JPanel {
    private final int WIDTH = 200;
    private final int HEIGHT = 500;
    private JButton play, playFrame, newMatch;
    private JLabel scoreA, scoreB, serverIcon, message;

    public ScorePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());

        play = new JButton("Play");
        play.setPreferredSize(new Dimension(150, 100));
        add(play);
        playFrame = new JButton("Frame");
        playFrame.setPreferredSize(new Dimension(150, 50));
        add(playFrame);
        newMatch = new JButton("New Match");
        newMatch.setPreferredSize(new Dimension(150, 50));
        add(newMatch);

        scoreA = new JLabel("", SwingConstants.CENTER);
        scoreA.setPreferredSize(new Dimension(90, 20));
        scoreA.setFont(new Font("Monospaced", Font.BOLD, 18));
        add(scoreA);
        scoreB = new JLabel("", SwingConstants.CENTER);
        scoreB.setPreferredSize(new Dimension(90, 20));
        scoreB.setFont(new Font("Monospaced", Font.BOLD, 18));
        add(scoreB);
        serverIcon = new JLabel("O", SwingConstants.CENTER);
        serverIcon.setPreferredSize(new Dimension(150, 50));
        serverIcon.setFont(new Font("Arial", Font.BOLD, 20));
        add(serverIcon);
        message = new JLabel("", SwingConstants.CENTER);
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

    public JButton getNewMatch() {
        return newMatch;
    }

    public JLabel getMessage() {
        return message;
    }

    public JLabel getServerIcon() {
        return serverIcon;
    }
}

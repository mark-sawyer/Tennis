import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Match extends JPanel implements ActionListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private JButton play;
    private JLabel scoreA;
    private JLabel scoreB;
    private Player playerA;
    private Player playerB;
    private Random random;


    public Match() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        playerA = new Player(PlayerName.PLAYER_A);
        playerB = new Player(PlayerName.PLAYER_B);
        playerA.setOpponent(playerB);
        playerB.setOpponent(playerA);

        play = new JButton("Play");
        play.addActionListener(this);
        add(play, BorderLayout.SOUTH);

        scoreA = new JLabel(playerA.toString());
        add(scoreA, BorderLayout.WEST);
        scoreB = new JLabel(playerB.toString());
        add(scoreB, BorderLayout.EAST);

        random = new Random();
    }

    public void set() {
        if (playerA.getSetsWonInMatch() == 3 || playerB.getSetsWonInMatch() == 3) {
            System.out.println("game over");
        }
        else {
            if (random.nextBoolean()) {
                playerA.winSet();
                scoreA.setText(playerA.toString());
            } else {
                playerB.winSet();
                scoreB.setText(playerB.toString());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        set();
    }
}

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

    public void match() {
        if (playerA.getSetsWonInMatch() == 3 || playerB.getSetsWonInMatch() == 3) {
            System.out.println("game over");
        }
        else {
            set();
        }
    }

    public void set() {
        if (playerA.getIsTiebreak()) {
            tiebreak();
        }
        else {
            game();
            int playerAGames = playerA.getGamesWonInSet();
            int playerBGames = playerB.getGamesWonInSet();
            if (playerAGames >= 6 && playerBGames <= playerAGames - 2) {
                playerA.winSet();
                playerB.resetGames();
                playerB.resetPoints();
            }
            else if (playerBGames >= 6 && playerAGames <= playerBGames - 2) {
                playerB.winSet();
                playerA.resetGames();
                playerA.resetPoints();
            }
            else if (playerAGames == 6 && playerBGames == 6 &&
                    playerA.getSetsWonInMatch() + playerB.getSetsWonInMatch() < 4) {
                playerA.setIsTiebreak(true);
                playerB.setIsTiebreak(true);
            }
        }
    }

    public void game() {
        if (random.nextBoolean()) {
            playerA.winPoint();
        } else {
            playerB.winPoint();
        }
    }

    public void tiebreak() {
        if (random.nextBoolean()) {
            playerA.winTiebreakPoint();
        } else {
            playerB.winTiebreakPoint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        match();
        scoreA.setText(playerA.toString());
        scoreB.setText(playerB.toString());
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TennisFrame extends JFrame implements ActionListener {
    private Player playerA;
    private Player playerB;
    private Ball ball;
    private ScorePanel scorePanel;
    private CourtPanel courtPanel;
    private Timer timer;

    public TennisFrame(String s) {
        super(s);

        scorePanel = new ScorePanel();
        scorePanel.getPlay().addActionListener(this);

        ball = new Ball();
        Random random = new Random();
        if (random.nextBoolean()) {
            playerA = new Player(true, Color.RED, ball);
            playerB = new Player(false, Color.BLUE, ball);
        } else {
            playerA = new Player(false, Color.RED, ball);
            playerB = new Player(true, Color.BLUE, ball);
        }
        playerA.setOpponent(playerB);
        playerB.setOpponent(playerA);

        courtPanel = new CourtPanel(playerA, playerB, ball);

        timer = new Timer(5, this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        add(scorePanel, BorderLayout.WEST);
        add(courtPanel, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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

    public void game() {
        playerA.setPosition(100, 100);
        playerB.setPosition(400, 500);
        ball.setPosition();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == scorePanel.getPlay()) {
            match();
            scorePanel.getScoreA().setText(playerA.toString());
            scorePanel.getScoreB().setText(playerB.toString());
        }

        else if (e.getSource() == timer) {
            playerA.moveToLocation(ball.getX(), ball.getY());
            playerB.moveToLocation(ball.getX(), ball.getY());

            double playerADist = Math.sqrt(Math.pow(playerA.getX() - ball.getX(), 2) +
                    Math.pow(playerA.getY() - ball.getY(), 2));
            double playerBDist = Math.sqrt(Math.pow(playerB.getX() - ball.getX(), 2) +
                    Math.pow(playerB.getY() - ball.getY(), 2));

            boolean isTiebreak = playerA.getIsTiebreak();

            if (playerADist < 20 && playerADist < playerBDist) {
                timer.stop();
                if (isTiebreak) {
                    playerA.winTiebreakPoint();
                } else {
                    playerA.winPoint();
                }

            }
            if (playerBDist < 20 && playerBDist < playerADist) {
                timer.stop();
                if (isTiebreak) {
                    playerB.winTiebreakPoint();
                } else {
                    playerB.winPoint();
                }
            }
            courtPanel.repaint();
            courtPanel.invalidate();
            courtPanel.validate();
        }
    }
}

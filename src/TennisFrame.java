import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TennisFrame extends JFrame implements ActionListener, PositionConstants {
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
            playerA = new Player(true, Color.RED, ball, Side.NORTH);
            playerB = new Player(false, Color.BLUE, ball, Side.SOUTH);
        } else {
            playerA = new Player(false, Color.RED, ball, Side.NORTH);
            playerB = new Player(true, Color.BLUE, ball, Side.SOUTH);
        }
        playerA.setOpponent(playerB);
        playerB.setOpponent(playerA);

        courtPanel = new CourtPanel(playerA, playerB, ball);

        timer = new Timer(30, this);

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
    }

    public void game() {
        playerA.setPosition();
        playerB.setPosition();
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
            if (playerA.getIsTurnToHit()) {
                if (playerA.getSide() == Side.NORTH) {
                    playerA.moveToLocation(ball.getX(), ball.getY());
                    playerB.moveToLocation(NEUTRAL_X, SOUTH_NEUTRAL_Y);
                } else {
                    playerA.moveToLocation(ball.getX(), ball.getY());
                    playerB.moveToLocation(NEUTRAL_X, NORTH_NEUTRAL_Y);
                }
            } else {
                if (playerA.getSide() == Side.NORTH) {
                    playerA.moveToLocation(NEUTRAL_X, NORTH_NEUTRAL_Y);
                    playerB.moveToLocation(ball.getX(), ball.getY());
                } else {
                    playerA.moveToLocation(NEUTRAL_X, SOUTH_NEUTRAL_Y);
                    playerB.moveToLocation(ball.getX(), ball.getY());
                }
            }
            ball.dropHeight();
            ball.move();

            boolean isTiebreak = playerA.getIsTiebreak();
            boolean playerAServing = playerA.getIsServing();

            int bounceNum = ball.getBounceNum();
            if (bounceNum == 2) {
                timer.stop();
                if (isTiebreak) {
                    if (playerAServing) {
                        playerA.winTiebreakPoint();
                    } else {
                        playerB.winTiebreakPoint();
                    }
                } else {
                    if (playerAServing) {
                        playerA.winPoint();
                    } else {
                        playerB.winPoint();
                    }
                }
            }

            else {
                double playerADist = Math.sqrt(Math.pow(playerA.getX() - ball.getX(), 2) +
                        Math.pow(playerA.getY() - ball.getY(), 2));
                double playerBDist = Math.sqrt(Math.pow(playerB.getX() - ball.getX(), 2) +
                        Math.pow(playerB.getY() - ball.getY(), 2));

                if (playerADist < 20 && playerADist < playerBDist) {
//                    timer.stop();
                    if (isTiebreak) {
                        playerA.winTiebreakPoint();
                    } else {
                        if (ball.getHeight() < 10) {
                            playerA.hitBall();
                        }
                    }

                } else if (playerBDist < 20 && playerBDist < playerADist) {
//                    timer.stop();
                    if (isTiebreak) {
                        playerB.winTiebreakPoint();
                    } else {
                        if (ball.getHeight() < 10) {
                            playerB.hitBall();
                        }
                    }
                }
            }

            courtPanel.repaint();
            courtPanel.invalidate();
            courtPanel.validate();
        }
    }
}

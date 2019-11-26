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
    private Timer timer, pointDelayTimer;
    private final double MAX_HITTABLE_BALL_HEIGHT = 5;
    private final double MIN_HITTABLE_BALL_HEIGHT = 0.5;

    public TennisFrame(String s) {
        super(s);

        scorePanel = new ScorePanel();
        scorePanel.getPlay().addActionListener(this);
        scorePanel.getPlayFrame().addActionListener(this);

        ball = new Ball();
        Random random = new Random();
        if (random.nextBoolean()) {
            playerA = new Player(true, Color.RED, ball, Side.EAST);
            playerB = new Player(false, Color.BLUE, ball, Side.WEST);
        } else {
            playerA = new Player(false, Color.RED, ball, Side.EAST);
            playerB = new Player(true, Color.BLUE, ball, Side.WEST);
        }
        playerA.setOpponent(playerB);
        playerB.setOpponent(playerA);

        scorePanel.getScoreA().setForeground(playerA.getColour());
        scorePanel.getScoreA().setText(playerA.toString());
        scorePanel.getScoreB().setForeground(playerB.getColour());
        scorePanel.getScoreB().setText(playerB.toString());

        courtPanel = new CourtPanel(playerA, playerB, ball);

        timer = new Timer(5, this);
        pointDelayTimer = new Timer(1000, this);

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
            game();
        }
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
            doAFrame();
        }

        else if (e.getSource() == scorePanel.getPlayFrame()) {
            timer.stop();
            doAFrame();
        }

        else if (e.getSource() == pointDelayTimer) {
            pointDelayTimer.stop();
            scorePanel.getMessage().setText("");
            match();
        }
    }

    private void doAFrame() {
        playerA.moveToLocation();
        playerB.moveToLocation();
        ball.dropHeight();
        ball.move();

        boolean isTiebreak = playerA.getIsTiebreak();

        double playerADist = Math.sqrt(Math.pow(playerA.getX() - ball.getX(), 2) +
                Math.pow(playerA.getY() - ball.getY(), 2));
        double playerBDist = Math.sqrt(Math.pow(playerB.getX() - ball.getX(), 2) +
                Math.pow(playerB.getY() - ball.getY(), 2));

        if (playerADist < 20 && playerADist < playerBDist) {
            if (ball.getHeight() < MAX_HITTABLE_BALL_HEIGHT &&
                    ball.getHeight() > MIN_HITTABLE_BALL_HEIGHT && playerA.getTurnToHitBall()) {
                playerA.hitBall();
            }
        }
        else if (playerBDist < 20 && playerBDist < playerADist) {
            if (ball.getHeight() < MAX_HITTABLE_BALL_HEIGHT &&
                    ball.getHeight() > MIN_HITTABLE_BALL_HEIGHT && playerB.getTurnToHitBall()) {
                playerB.hitBall();
            }
        }

        courtPanel.repaint();
        courtPanel.invalidate();
        courtPanel.validate();

        int bounceNum = ball.getBounceNum();
        if (bounceNum == 2) {
            timer.stop();
            if (isTiebreak) {
                if (playerA.getTurnToHitBall()) {
                    playerB.winTiebreakPoint();
                    pointTransition(playerB);
                }
                else {
                    playerA.winTiebreakPoint();
                    pointTransition(playerA);
                }
            }
            else {
                if (playerA.getTurnToHitBall()) {
                    playerB.winPoint();
                    pointTransition(playerB);
                }
                else {
                    playerA.winPoint();
                    pointTransition(playerA);
                }
            }
        }
    }

    private void pointTransition(Player winner) {
        JLabel message = scorePanel.getMessage();
        Color colour = winner.getColour();
        message.setForeground(colour);
        if (colour.equals(Color.RED)) {
            message.setText("RED WINS");
        }
        else if (colour.equals(Color.BLUE)) {
            message.setText("BLUE WINS");
        }
        scorePanel.getScoreA().setText(playerA.toString());
        scorePanel.getScoreB().setText(playerB.toString());
        pointDelayTimer.start();
    }
}

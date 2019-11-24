import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CourtPanel extends JPanel implements ActionListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 450;
    private Player playerA;
    private Player playerB;
    private Ball ball;
    private Timer timer;
    private double playerADist;
    private double playerBDist;

    public CourtPanel(Player playerA, Player playerB, Ball ball) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GRAY);
        this.playerA = playerA;
        this.playerB = playerB;
        this.ball = ball;
        timer = new Timer(30, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        playerA.draw(g);
        playerB.draw(g);
        ball.draw(g);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        playerA.moveToLocation(ball.getX(), ball.getY());
        playerB.moveToLocation(ball.getX(), ball.getY());
        repaint();
        invalidate();
        validate();
    }
}

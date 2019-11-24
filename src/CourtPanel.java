import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourtPanel extends JPanel implements ActionListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 600;
    private ImageIcon background;
    private Player playerA;
    private Player playerB;
    private Ball ball;

    public CourtPanel(Player playerA, Player playerB, Ball ball) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setBackground(Color.GRAY);
        this.playerA = playerA;
        this.playerB = playerB;
        this.ball = ball;

        background = new ImageIcon(this.getClass().getResource("court.png"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
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

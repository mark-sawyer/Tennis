import javax.swing.*;
import java.awt.*;

public class CourtPanel extends JPanel {
    private final int WIDTH = 500;
    private final int HEIGHT = 600;
    private ImageIcon background;
    private Player playerA;
    private Player playerB;
    private Ball ball;

    public CourtPanel(Player playerA, Player playerB, Ball ball) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
}

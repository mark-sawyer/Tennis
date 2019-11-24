import javax.swing.*;
import java.awt.*;

public class CourtPanel extends JPanel {
    private final int WIDTH = 500;
    private final int HEIGHT = 450;
    private Player playerA;
    private Player playerB;

    public CourtPanel(Player playerA, Player playerB) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLUE);
        this.playerA = playerA;
        this.playerB = playerB;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        playerA.draw(g);
    }
}

import javax.swing.*;
import java.awt.*;

public class CourtPanel extends JPanel {
    private final int WIDTH = 500;
    private final int HEIGHT = 450;

    public CourtPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLUE);
    }
}

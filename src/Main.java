import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        ScorePanel scorePanel = new ScorePanel();
        CourtPanel courtPanel = new CourtPanel();
        Score score = new Score(scorePanel, courtPanel);

        JFrame tennisFrame = new JFrame("Tennis");
        tennisFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tennisFrame.setResizable(false);
        tennisFrame.add(scorePanel, BorderLayout.SOUTH);
        tennisFrame.add(courtPanel, BorderLayout.NORTH);
        tennisFrame.pack();
        tennisFrame.setLocationRelativeTo(null);
        tennisFrame.setVisible(true);
    }
}

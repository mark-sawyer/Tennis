import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Match match = new Match();

        JFrame tennisFrame = new JFrame("Tennis");
        tennisFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tennisFrame.setResizable(false);
        tennisFrame.add(match);
        tennisFrame.pack();
        tennisFrame.setLocationRelativeTo(null);
        tennisFrame.setVisible(true);
    }
}

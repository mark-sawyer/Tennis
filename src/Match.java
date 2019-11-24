import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Match extends JPanel implements ActionListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private JButton play;

    private Player[] players;


    public Match() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        players = new Player[] {new Player(PlayerName.PLAYER_A), new Player(PlayerName.PLAYER_B)};
        play = new JButton("Play");
        play.addActionListener(this);
        setLayout(new BorderLayout());
        add(play, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("play");
    }
}

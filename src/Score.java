import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Score implements ActionListener {
    private Player playerA;
    private Player playerB;
    private ScorePanel scorePanel;
    private CourtPanel courtPanel;
    private Random random;

    public Score(ScorePanel scorePanel, CourtPanel courtPanel) {
        this.scorePanel = scorePanel;
        this.courtPanel = courtPanel;
        scorePanel.getPlay().addActionListener(this);
        playerA = new Player();
        playerB = new Player();
        playerA.setOpponent(playerB);
        playerB.setOpponent(playerA);
        random = new Random();
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
        if (playerA.getIsTiebreak()) {
            tiebreak();
        }
        else {
            game();
            int playerAGames = playerA.getGamesWonInSet();
            int playerBGames = playerB.getGamesWonInSet();
            if (playerAGames >= 6 && playerBGames <= playerAGames - 2) {
                playerA.winSet();
                playerB.resetGames();
                playerB.resetPoints();
            }
            else if (playerBGames >= 6 && playerAGames <= playerBGames - 2) {
                playerB.winSet();
                playerA.resetGames();
                playerA.resetPoints();
            }
            else if (playerAGames == 6 && playerBGames == 6 &&
                    playerA.getSetsWonInMatch() + playerB.getSetsWonInMatch() < 4) {
                playerA.setIsTiebreak(true);
                playerB.setIsTiebreak(true);
            }
        }
    }

    public void game() {
        if (random.nextBoolean()) {
            playerA.winPoint();
        } else {
            playerB.winPoint();
        }
    }

    public void tiebreak() {
        if (random.nextBoolean()) {
            playerA.winTiebreakPoint();
        } else {
            playerB.winTiebreakPoint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        match();
        scorePanel.getScoreA().setText(playerA.toString());
        scorePanel.getScoreB().setText(playerB.toString());
    }
}

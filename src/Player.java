public class Player {
    private PlayerName name;
    private GamePoint gamePoint;
    private int gamesWonInSet;
    private int setsWonInMatch;
    private boolean isServing;
    private Player opponent;

    public Player(PlayerName name) {
        this.name = name;
        gamePoint = GamePoint.LOVE;
        gamesWonInSet = 0;
        setsWonInMatch = 0;
    }

    public void setIsServing(boolean b) {
        isServing = b;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public int getGamesWonInSet() {
        return gamesWonInSet;
    }

    public int getSetsWonInMatch() {
        return setsWonInMatch;
    }

    public void winSet() {
        ++setsWonInMatch;
    }

    @Override
    public String toString() {
        return setsWonInMatch + "-" + gamesWonInSet + "-" + gamePoint.toString();
    }
}

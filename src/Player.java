import java.awt.*;

public class Player {
    private GamePoint gamePoint;
    private int gamesWonInSet;
    private int setsWonInMatch;
    private int pointsWonInTiebreak;
    private boolean isServing;
    private boolean isTiebreak;
    private Player opponent;
    private Color colour;
    private int x;
    private int y;

    public Player(boolean servingFirst, Color colour) {
        gamePoint = GamePoint.LOVE;
        isServing = servingFirst;
        this.colour = colour;
        x = 200;
        y = 200;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public int getSetsWonInMatch() {
        return setsWonInMatch;
    }

    public int getGamesWonInSet() {
        return gamesWonInSet;
    }

    public GamePoint getPointsWonInGame() {
        return gamePoint;
    }

    public int getPointsWonInTiebreak() {
        return pointsWonInTiebreak;
    }

    public void winSet() {
        ++setsWonInMatch;
        resetPoints();
        resetGames();
    }

    public void winGame() {
        ++gamesWonInSet;
        resetPoints();
        changeServe();
        opponent.changeServe();
    }

    public void winPoint() {
        switch (gamePoint) {
            case LOVE:
                gamePoint = GamePoint.FIFTEEN;
                break;
            case FIFTEEN:
                gamePoint = GamePoint.THIRTY;
                break;
            case THIRTY:
                gamePoint = GamePoint.FORTY;
                break;
            case FORTY:
                GamePoint opponentPoints = opponent.getPointsWonInGame();
                if (opponentPoints == GamePoint.LOVE ||
                        opponentPoints == GamePoint.FIFTEEN ||
                        opponentPoints == GamePoint.THIRTY) {
                    winGame();
                    opponent.resetPoints();
                }
                else if (opponentPoints == GamePoint.FORTY) {
                    gamePoint = GamePoint.AD;
                }
                else if (opponentPoints == GamePoint.AD) {
                    opponent.backToForty();
                }
                break;
            case AD:
                winGame();
                opponent.resetPoints();
        }
    }

    public void winTiebreakPoint() {
        ++pointsWonInTiebreak;
        int opponentPoints = opponent.getPointsWonInTiebreak();
        if (pointsWonInTiebreak >= 7 && opponentPoints <= pointsWonInTiebreak - 2) {
            winSet();
            pointsWonInTiebreak = 0;
            isTiebreak = false;
            opponent.setIsTiebreak(false);
            opponent.resetPointsWonInTiebreak();
            opponent.resetGames();
        }
    }

    public void resetPoints() {
        gamePoint = GamePoint.LOVE;
    }

    public void resetPointsWonInTiebreak() {
        pointsWonInTiebreak = 0;
    }

    public void backToForty() {
        gamePoint = GamePoint.FORTY;
    }

    public void resetGames() {
        gamesWonInSet = 0;
    }

    public void setIsTiebreak(boolean b) {
        isTiebreak = b;
    }

    public boolean getIsTiebreak() {
        return isTiebreak;
    }

    public void changeServe() {
        isServing = !isServing;
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        g.fillOval(x - 15, y - 15, 30, 30);
    }

    @Override
    public String toString() {
        if (isTiebreak) {
            return setsWonInMatch + "-" + gamesWonInSet + "-" + pointsWonInTiebreak;
        }
        else {
            return setsWonInMatch + "-" + gamesWonInSet + "-" + gamePoint.toString();
        }
    }
}

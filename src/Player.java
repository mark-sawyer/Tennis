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
    private double x;
    private double y;
    private Ball ball;
    private double speed;

    public Player(boolean servingFirst, Color colour, Ball ball) {
        gamePoint = GamePoint.LOVE;
        isServing = servingFirst;
        this.colour = colour;
        this.ball = ball;
        speed = 5;
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        int xPos = (int) Math.rint(x);
        int yPos = (int) Math.rint(y);
        g.fillOval(xPos - 15, yPos - 15, 30, 30);
    }

    public void moveToLocation(double xLoc, double yLoc) {
        double xDist = xLoc - x;
        double yDist = yLoc - y;
        double norm = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        double xSpeed = (xDist / norm) * speed;
        double ySpeed = (yDist / norm) * speed;

        x += xSpeed;
        y += ySpeed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

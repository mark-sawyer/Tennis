import java.awt.*;

public class Player {
    private GamePoint gamePoint;
    private int gamesWonInSet;
    private int setsWonInMatch;
    private int pointsWonInTiebreak;
    private boolean isServing;
    private boolean isTiebreak;
    private boolean servedFirstInTiebreak;
    private Player opponent;
    private Color colour;
    private double x;
    private double y;
    private Ball ball;
    private double speed;
    private Side side;

    public Player(boolean servingFirst, Color colour, Ball ball, Side side) {
        gamePoint = GamePoint.FORTY;
        gamesWonInSet = 5;
        isServing = servingFirst;
        this.colour = colour;
        this.ball = ball;
        this.side = side;
        speed = 5;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public int getSetsWonInMatch() {
        return setsWonInMatch;
    }

    public GamePoint getPointsWonInGame() {
        return gamePoint;
    }

    public void winSet() {
        ++setsWonInMatch;
        resetPoints();
        resetGames();
        swapSides();
    }

    public void winGame() {
        ++gamesWonInSet;
        resetPoints();
        swapServe();

        if (gamesWonInSet >= 6 && opponent.gamesWonInSet <= gamesWonInSet - 2) {
            winSet();
        }
        else if (gamesWonInSet == 6 && opponent.gamesWonInSet == 6 &&
                setsWonInMatch + opponent.setsWonInMatch < 4) {
            isTiebreak = true;
            opponent.isTiebreak = true;
            if (isServing) {
                servedFirstInTiebreak = true;
                opponent.servedFirstInTiebreak = false;
            } else {
                servedFirstInTiebreak = false;
                opponent.servedFirstInTiebreak = true;
            }
        }
        else if ((gamesWonInSet + opponent.gamesWonInSet) % 2 == 1) {
            swapSides();
        }
    }

    public void swapSides() {
        switch (side) {
            case NORTH:
                side = Side.SOUTH;
                opponent.side = Side.NORTH;
                break;
            case SOUTH:
                side = Side.NORTH;
                opponent.side = Side.SOUTH;
                break;
        }
    }

    public void swapServe() {
        isServing = !isServing;
        opponent.isServing = !opponent.isServing;
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
                }
                else if (opponentPoints == GamePoint.FORTY) {
                    gamePoint = GamePoint.AD;
                }
                else if (opponentPoints == GamePoint.AD) {
                    opponent.gamePoint = GamePoint.FORTY;
                }
                break;
            case AD:
                winGame();
        }
    }

    public void winTiebreakPoint() {
        ++pointsWonInTiebreak;
        if (pointsWonInTiebreak >= 7 && opponent.pointsWonInTiebreak <= pointsWonInTiebreak - 2) {
            winSet();
            endTiebreak();
            swapSides();
            if (isServing && servedFirstInTiebreak) {
                swapServe();
            }
        }
        else if ((pointsWonInTiebreak + opponent.pointsWonInTiebreak) % 2 == 1) {
            swapServe();
        }
        else if ((pointsWonInTiebreak + opponent.pointsWonInTiebreak) % 6 == 0) {
            swapSides();
        }
    }

    public void resetPoints() {
        gamePoint = GamePoint.LOVE;
        opponent.gamePoint = GamePoint.LOVE;
    }

    public void resetGames() {
        gamesWonInSet = 0;
        opponent.gamesWonInSet = 0;
    }

    public void endTiebreak() {
        pointsWonInTiebreak = 0;
        opponent.pointsWonInTiebreak = 0;
        isTiebreak = false;
        opponent.isTiebreak = false;
    }

    public boolean getIsTiebreak() {
        return isTiebreak;
    }

    public boolean getIsServing() {
        return isServing;
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

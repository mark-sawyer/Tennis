import java.awt.*;
import java.util.Random;

public class Player implements PositionConstants {
    private GamePoint gamePoint;
    private int gamesWonInSet, setsWonInMatch, pointsWonInTiebreak;
    private boolean isServing, isTiebreak, servedFirstInTiebreak;
    private double x, y, speed, xGoal, yGoal;
    private Player opponent;
    private Color colour;
    private Ball ball;
    private Side side;
    private Random random;

    public Player(boolean servingFirst, Color colour, Ball ball, Side side) {
        gamePoint = GamePoint.LOVE;
        isServing = servingFirst;
        this.colour = colour;
        this.ball = ball;
        this.side = side;
        speed = 2;
        random = new Random();
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

    public void setPosition() {
        boolean isDeuceSide;
        if (isTiebreak) {
            if ((pointsWonInTiebreak + opponent.pointsWonInTiebreak) % 2 == 0) {
                isDeuceSide = true;
            }
            else {
                isDeuceSide = false;
            }
        }
        else {
            if ((GamePoint.toInt(gamePoint) + GamePoint.toInt(opponent.gamePoint)) % 2 == 0) {
                isDeuceSide = true;
            }
            else {
                isDeuceSide = false;
            }
        }

        if (side == Side.NORTH) {
            if (isDeuceSide) {
                if (isServing) {
                    x = NORTH_SERVE_DEUCE_X;
                    y = NORTH_SERVE_DEUCE_Y;
                    ball.setPosition(NORTH_SERVE_DEUCE_X, NORTH_SERVE_DEUCE_Y + 10);
                    xGoal = NORTH_SERVE_DEUCE_X;
                    yGoal = NORTH_SERVE_DEUCE_Y;
                }
                else {
                    x = NORTH_RECEIVE_DEUCE_X;
                    y = NORTH_RECEIVE_DEUCE_Y;
                    ball.setPosition(SOUTH_SERVE_DEUCE_X, SOUTH_SERVE_DEUCE_Y - 10);
                    xGoal = NORTH_RECEIVE_DEUCE_X;
                    yGoal = NORTH_RECEIVE_DEUCE_Y;
                }
            }
            else {
                if (isServing) {
                    x = NORTH_SERVE_AD_X;
                    y = NORTH_SERVE_AD_Y;
                    ball.setPosition(NORTH_SERVE_AD_X, NORTH_SERVE_AD_Y + 10);
                    xGoal = NORTH_SERVE_AD_X;
                    yGoal = NORTH_SERVE_AD_Y;
                }
                else {
                    x = NORTH_RECEIVE_AD_X;
                    y = NORTH_RECEIVE_AD_Y;
                    ball.setPosition(SOUTH_SERVE_AD_X, SOUTH_SERVE_AD_Y - 10);
                    xGoal = NORTH_RECEIVE_AD_X;
                    yGoal = NORTH_RECEIVE_AD_Y;
                }
            }
        }
        else {
            if (isDeuceSide) {
                if (isServing) {
                    x = SOUTH_SERVE_DEUCE_X;
                    y = SOUTH_SERVE_DEUCE_Y;
                    ball.setPosition(SOUTH_SERVE_DEUCE_X, SOUTH_SERVE_DEUCE_Y - 10);
                    xGoal = SOUTH_SERVE_DEUCE_X;
                    yGoal = SOUTH_SERVE_DEUCE_Y;
                }
                else {
                    x = SOUTH_RECEIVE_DEUCE_X;
                    y = SOUTH_RECEIVE_DEUCE_Y;
                    ball.setPosition(NORTH_SERVE_DEUCE_X, NORTH_SERVE_DEUCE_Y + 10);
                    xGoal = SOUTH_RECEIVE_DEUCE_X;
                    yGoal = SOUTH_RECEIVE_DEUCE_Y;
                }
            } else {
                if (isServing) {
                    x = SOUTH_SERVE_AD_X;
                    y = SOUTH_SERVE_AD_Y;
                    ball.setPosition(SOUTH_SERVE_AD_X, SOUTH_SERVE_AD_Y - 10);
                    xGoal = SOUTH_SERVE_AD_X;
                    yGoal = SOUTH_SERVE_AD_Y;
                }
                else {
                    x = SOUTH_RECEIVE_AD_X;
                    y = SOUTH_RECEIVE_AD_Y;
                    ball.setPosition(NORTH_SERVE_AD_X, NORTH_SERVE_AD_Y + 10);
                    xGoal = SOUTH_RECEIVE_AD_X;
                    yGoal = SOUTH_RECEIVE_AD_Y;
                }
            }
        }
    }

    public void hitBall() {
        int xTarget = random.nextInt(221) + 141;
        int yTarget;
        if (side == Side.NORTH) {
            yTarget = random.nextInt(67) + 458;
        } else {
            yTarget = random.nextInt(67) + 74;
        }
        System.out.println(xTarget + " " + yTarget);

        double maxExtraVelocity = -12 * Math.pow(ball.getHeight(), 2) + 120 * ball.getHeight();
        if (maxExtraVelocity < 1) {
            maxExtraVelocity = 1;
        }
//        double heightVelocity = ((double) random.nextInt((int) maxExtraVelocity)) / 1000;
        double heightVelocity = 0.1;
        ball.setHeightVelocity(heightVelocity);

        double xDist = xTarget - ball.getX();
        double yDist = yTarget - ball.getY();
        double norm = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        System.out.println("norm: " + norm);
        double xDir = xDist / norm;
        double yDir = yDist / norm;

        // Get number of steps needed for ball to hit ground
        System.out.println("current height: " + ball.getHeight());
        double stepsToGround = quadratic(-0.00225, heightVelocity, ball.getHeight());
        System.out.println("stg: " + stepsToGround);

        double speed = norm / stepsToGround;
        System.out.println("speed: " + speed);
//        if (speed > 10) {
//            speed = 10;
//        }

        ball.setSpeed(speed);
        ball.setDirection(xDir, yDir);
        ball.resetBounceNum();

        // Change location goals
        xGoal = NEUTRAL_X;
        if (side == Side.NORTH) {
            yGoal = NORTH_NEUTRAL_Y;
        } else {
            yGoal = SOUTH_NEUTRAL_Y;
        }
        double stepsToMaxHeight = (0.8 * heightVelocity) / 0.0045;

        opponent.xGoal = xTarget + xDir * speed * stepsToMaxHeight;
        opponent.yGoal = yTarget + yDir * speed * stepsToMaxHeight;
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        int xPos = (int) Math.rint(x);
        int yPos = (int) Math.rint(y);
        g.fillOval(xPos - 15, yPos - 15, 30, 30);
    }

    public void moveToLocation() {
        double xDist = xGoal - x;
        double yDist = yGoal - y;
        double norm = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        if (norm < 3) {
            norm = 0;
        }
        double xSpeed, ySpeed;
        if (norm != 0) {
            xSpeed = (xDist / norm) * speed;
            ySpeed = (yDist / norm) * speed;
        }
        else {
            xSpeed = 0;
            ySpeed = 0;
        }

        x += xSpeed;
        y += ySpeed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private double quadratic(double a, double b, double c) {
        return ((-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a));
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

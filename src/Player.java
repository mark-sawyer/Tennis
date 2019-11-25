import java.awt.*;
import java.util.Random;

public class Player implements PositionConstants {
    private GamePoint gamePoint;
    private int gamesWonInSet, setsWonInMatch, pointsWonInTiebreak;
    private boolean isServing, isTiebreak, servedFirstInTiebreak, turnToHitBall, hasServed;
    private double x, y, speed, yGoal, xGoal;
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
        speed = 7;
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
            case EAST:
                side = Side.WEST;
                opponent.side = Side.EAST;
                break;
            case WEST:
                side = Side.EAST;
                opponent.side = Side.WEST;
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

    public boolean getTurnToHitBall() {
        return turnToHitBall;
    }

    public void setPosition() {
        boolean isDeuceSide = getIsDeuce();

        if (side == Side.EAST) {
            if (isDeuceSide) {
                if (isServing) {
                    x = EAST_SERVE_DEUCE_X;
                    y = EAST_SERVE_DEUCE_Y;
                    ball.setPosition(EAST_SERVE_DEUCE_X - 10, EAST_SERVE_DEUCE_Y);
                    xGoal = EAST_SERVE_DEUCE_X;
                    yGoal = EAST_SERVE_DEUCE_Y;
                }
                else {
                    x = EAST_RECEIVE_DEUCE_X;
                    y = EAST_RECEIVE_DEUCE_Y;
                    ball.setPosition(WEST_SERVE_DEUCE_X + 10, WEST_SERVE_DEUCE_Y);
                    xGoal = EAST_RECEIVE_DEUCE_X;
                    yGoal = EAST_RECEIVE_DEUCE_Y;
                }
            }
            else {
                if (isServing) {
                    x = EAST_SERVE_AD_X;
                    y = EAST_SERVE_AD_Y;
                    ball.setPosition(EAST_SERVE_AD_X - 10, EAST_SERVE_AD_Y);
                    xGoal = EAST_SERVE_AD_X;
                    yGoal = EAST_SERVE_AD_Y;
                }
                else {
                    x = EAST_RECEIVE_AD_X;
                    y = EAST_RECEIVE_AD_Y;
                    ball.setPosition(WEST_SERVE_AD_X + 10, WEST_SERVE_AD_Y);
                    xGoal = EAST_RECEIVE_AD_X;
                    yGoal = EAST_RECEIVE_AD_Y;
                }
            }
        }
        else {
            if (isDeuceSide) {
                if (isServing) {
                    x = WEST_SERVE_DEUCE_X;
                    y = WEST_SERVE_DEUCE_Y;
                    ball.setPosition(WEST_SERVE_DEUCE_X + 10, WEST_SERVE_DEUCE_Y);
                    xGoal = WEST_SERVE_DEUCE_X;
                    yGoal = WEST_SERVE_DEUCE_Y;
                }
                else {
                    x = WEST_RECEIVE_DEUCE_X;
                    y = WEST_RECEIVE_DEUCE_Y;
                    ball.setPosition(EAST_SERVE_DEUCE_X - 10, EAST_SERVE_DEUCE_Y);
                    xGoal = WEST_RECEIVE_DEUCE_X;
                    yGoal = WEST_RECEIVE_DEUCE_Y;
                }
            } else {
                if (isServing) {
                    x = WEST_SERVE_AD_X;
                    y = WEST_SERVE_AD_Y;
                    ball.setPosition(WEST_SERVE_AD_X + 10, WEST_SERVE_AD_Y);
                    xGoal = WEST_SERVE_AD_X;
                    yGoal = WEST_SERVE_AD_Y;
                }
                else {
                    x = WEST_RECEIVE_AD_X;
                    y = WEST_RECEIVE_AD_Y;
                    ball.setPosition(EAST_SERVE_AD_X - 10, EAST_SERVE_AD_Y);
                    xGoal = WEST_RECEIVE_AD_X;
                    yGoal = WEST_RECEIVE_AD_Y;
                }
            }
        }

        if (isServing) {
            turnToHitBall = true;
            hasServed = false;
        } else {
            turnToHitBall = false;
        }
    }

    private boolean getIsDeuce() {
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
        return isDeuceSide;
    }

    public void hitBall() {
        turnToHitBall = false;
        opponent.turnToHitBall = true;

        int yTarget;
        int xTarget;
        if (isServing && !hasServed) {
            if (side == Side.EAST) {
                if (getIsDeuce()) {
                    yTarget = random.nextInt(Y_TARGET_RANGE_SERVE) + WEST_DEUCE_Y_TARGET_LOWER_BOUND_SERVE;
                    xTarget = random.nextInt(X_TARGET_RANGE_SERVE) + WEST_X_TARGET_LOWER_BOUND_SERVE;
                }
                else {
                    yTarget = random.nextInt(Y_TARGET_RANGE_SERVE) + WEST_AD_Y_TARGET_LOWER_BOUND_SERVE;
                    xTarget = random.nextInt(X_TARGET_RANGE_SERVE) + WEST_X_TARGET_LOWER_BOUND_SERVE;
                }
            }
            else {
                if (getIsDeuce()) {
                    yTarget = random.nextInt(Y_TARGET_RANGE_SERVE) + EAST_DEUCE_Y_TARGET_LOWER_BOUND_SERVE;
                    xTarget = random.nextInt(X_TARGET_RANGE_SERVE) + EAST_X_TARGET_LOWER_BOUND_SERVE;
                }
                else {
                    yTarget = random.nextInt(Y_TARGET_RANGE_SERVE) + EAST_AD_Y_TARGET_LOWER_BOUND_SERVE;
                    xTarget = random.nextInt(X_TARGET_RANGE_SERVE) + EAST_X_TARGET_LOWER_BOUND_SERVE;
                }
            }
            hasServed = true;
        }
        else {
            if (side == Side.EAST) {
                xTarget = random.nextInt(X_TARGET_RANGE) + WEST_X_TARGET_LOWER_BOUND;
            }
            else {
                xTarget = random.nextInt(X_TARGET_RANGE) + EAST_X_TARGET_LOWER_BOUND;
            }

            int absXDist = (int) Math.abs(xTarget - ball.getX());
            yTarget = (int) ball.getY() + (random.nextInt(absXDist) - (absXDist / 2));
            if (yTarget < Y_TARGET_LOWER_BOUND) {
                yTarget = Y_TARGET_LOWER_BOUND;
            }
            else if (yTarget > Y_TARGET_UPPER_BOUND) {
                yTarget = Y_TARGET_UPPER_BOUND;
            }
        }


        System.out.println("xTarget: " + xTarget + " yTarget: " + yTarget);

        double extraVelocity = -44.4444 * ball.getHeight() + 222.2222;
        if (extraVelocity < 1) {
            extraVelocity = 1;
        }

        double heightVelocity = (extraVelocity + (((double) random.nextInt((int) extraVelocity)) / 2)) / 1000;
        ball.setHeightVelocity(heightVelocity);

        double xDist = xTarget - ball.getX();
        double yDist = yTarget - ball.getY();
        double norm = Math.sqrt(Math.pow(yDist, 2) + Math.pow(xDist, 2));
        double xDir = xDist / norm;
        double yDir = yDist / norm;

        // Get number of steps needed for ball to hit ground
        double stepsToGround = quadratic(-0.00225, heightVelocity, ball.getHeight());

        double ballSpeed = norm / stepsToGround;
        System.out.println("ballSpeed: " + ballSpeed);

        ball.setSpeed(ballSpeed);
        ball.setDirection(xDir, yDir);
        ball.resetBounceNum();

        // Change location goals
        yGoal = NEUTRAL_Y;
        if (side == Side.EAST) {
            xGoal = EAST_NEUTRAL_X;
        } else {
            xGoal = WEST_NEUTRAL_X;
        }

        // What will the maximum height be after the bounce?
        // Get the velocity when the ball hits the ground.
        double bounceVelocity = -0.8 * (-0.0045 * stepsToGround + heightVelocity);
        double stepsToMaxHeight = bounceVelocity / 0.0045;
        System.out.println("Steps to max height: " + stepsToMaxHeight);
        double maxBounceHeight = -0.00225 * Math.pow(stepsToMaxHeight, 2) +
                bounceVelocity * stepsToMaxHeight;
        System.out.println("predicted max bounce height: " + maxBounceHeight);
        if (maxBounceHeight < 5) {
            System.out.println("xDir: " + xDir);
            System.out.println("yDir: " + yDir);
            opponent.xGoal = xTarget + xDir * ballSpeed * stepsToMaxHeight;
            opponent.yGoal = yTarget + yDir * ballSpeed * stepsToMaxHeight;
        }
        else {
            double stepsToHeightFive = quadratic(-0.00225, bounceVelocity, 5);
            opponent.xGoal = xTarget + xDir * ballSpeed * stepsToHeightFive;
            opponent.yGoal = yTarget + yDir * ballSpeed * stepsToHeightFive;
        }
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

import java.awt.*;
import java.util.Random;

public class Ball {
    private double x, y, height, heightVelocity, speed, xDir, yDir;
    private int bounceNum;
    private final double velocityLostPerStep = 0.0045;
    private Random random;
    private Color colour, bounceColour;

    public Ball() {
        random = new Random();
        colour = Color.GREEN;
        bounceColour = new Color(225, 128, 0);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        height = 10;
        heightVelocity = 0;
        speed = 0;
        bounceNum = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void dropHeight() {
        height += heightVelocity;
        System.out.println(height);
        if (height < 0) {
            ++bounceNum;
            height = Math.abs(height);
            heightVelocity = Math.abs(heightVelocity) * 0.8;
        }
        heightVelocity -= velocityLostPerStep;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHeightVelocity(double heightVelocity) {
        this.heightVelocity = heightVelocity;
    }

    public double getHeight() {
        return height;
    }

    public void setDirection(double xDir, double yDir) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

    public void move() {
        x += xDir * speed;
        y += yDir * speed;
    }

    public int getBounceNum() {
        return bounceNum;
    }

    public void resetBounceNum() {
        bounceNum = 0;
    }

    public void draw(Graphics g) {
        if (height < 0.5) {
            g.setColor(bounceColour);
        } else {
            g.setColor(colour);
        }
        int xPos = (int) Math.rint(x);
        int yPos = (int) Math.rint(y);
        double radius = height + 5.0;
        int intRadius = (int) Math.rint(radius);

        g.fillOval(xPos - intRadius, yPos - intRadius, 2 * intRadius, 2 * intRadius);
    }
}

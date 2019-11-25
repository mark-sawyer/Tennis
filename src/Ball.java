import java.awt.*;
import java.util.Random;

public class Ball {
    private double x;
    private double y;
    private double height;
    private double heightVelocity;
    private final double velocityLostPerStep = 0.0045;
    private Random random;
    private Color colour;

    public Ball() {
        random = new Random();
        setPosition();
        colour = Color.GREEN;
    }

    public void setPosition() {
        x = random.nextInt(501);
        y = random.nextInt(601);
        height = 10;
        heightVelocity = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public void dropHeight() {
        height += heightVelocity;
        if (height < 0) {
            height = Math.abs(height);
            heightVelocity = Math.abs(heightVelocity) * 0.8;
        }
        heightVelocity -= velocityLostPerStep;
        System.out.println(height);
    }

    public double getHeightVelocity() {
        return heightVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        int xPos = (int) Math.rint(x);
        int yPos = (int) Math.rint(y);
        double radius = (2.0 / 3.0) * height + 5.0;
        int intRadius = (int) Math.rint(radius);

        g.fillOval(xPos - intRadius, yPos - intRadius, 2 * intRadius, 2 * intRadius);
    }
}

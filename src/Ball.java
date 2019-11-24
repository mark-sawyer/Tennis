import java.awt.*;
import java.util.Random;

public class Ball {
    private double x;
    private double y;
    private Random random;
    private Color colour;

    public Ball() {
        random = new Random();
        setPosition();
        colour = Color.GREEN;
    }

    public void setPosition() {
        x = random.nextInt(501);
        y = random.nextInt(451);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        int xPos = (int) Math.rint(x);
        int yPos = (int) Math.rint(y);
        g.fillOval(xPos - 5, yPos - 5, 10, 10);
    }
}

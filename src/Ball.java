import java.awt.*;

public class Ball {
    private double x, y, height, heightVelocity, speed, xDir, yDir;
    private int bounceNum;
    private final double VELOCITY_LOST_PER_STEP = 0.0045, BOUNCE_REDUCTION = 0.7;
    private Color colour, bounceColour;

    public Ball() {
        x = -100;  // So not seen at the start
        y = -100;
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
        if (height < 0.5) {
            colour = bounceColour;
        }
        else {
            float brightness = 0.06f * (float) height + 0.7f;
            if (brightness > 1) {
                brightness = 1;
            }
            float sat = -0.05f * (float) height + 1.25f;
            if (sat > 1) {
                sat = 1;
            }
            colour = Color.getHSBColor(0.33f, sat, brightness);
        }
        if (height < 0) {
            ++bounceNum;
            height = Math.abs(height);
            heightVelocity = Math.abs(heightVelocity) * BOUNCE_REDUCTION;
        }
        heightVelocity -= VELOCITY_LOST_PER_STEP;
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
        g.setColor(colour);
        int xPos = (int) Math.rint(x);
        int yPos = (int) Math.rint(y);
        int intRadius;
        double radius;
        if (height < 0.5) {
            intRadius = 8;
        }
        else {
            radius = height + 5.0;
            intRadius = (int) Math.rint(radius);
        }

        g.fillOval(xPos - intRadius, yPos - intRadius, 2 * intRadius, 2 * intRadius);
    }
}

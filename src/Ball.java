import java.awt.*;

class Ball {
    private double x, y, height, heightVelocity, speed, xDir, yDir;
    private int bounceNum;
    private Color colour, bounceColour;

    Ball() {
        x = -100;  // So not seen at the start
        y = -100;
        bounceColour = new Color(225, 128, 0);
    }

    void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        height = 10;
        heightVelocity = 0;
        speed = 0;
        bounceNum = 0;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    void dropHeight() {
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
            double BOUNCE_REDUCTION = 0.7;
            heightVelocity = Math.abs(heightVelocity) * BOUNCE_REDUCTION;
        }
        double VELOCITY_LOST_PER_STEP = 0.0045;
        heightVelocity -= VELOCITY_LOST_PER_STEP;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }

    void setHeightVelocity(double heightVelocity) {
        this.heightVelocity = heightVelocity;
    }

    double getHeight() {
        return height;
    }

    void setDirection(double xDir, double yDir) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

    void move() {
        x += xDir * speed;
        y += yDir * speed;
    }

    int getBounceNum() {
        return bounceNum;
    }

    void resetBounceNum() {
        bounceNum = 0;
    }

    void draw(Graphics g) {
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

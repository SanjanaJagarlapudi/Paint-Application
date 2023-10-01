package pa;

import java.awt.*;
import java.io.Serializable;

/**
 * This class models a Point on the Canvas, and thus signifies a turtle position, color and pen position
 * at a specific point.
 */
class Position implements Serializable {

    private final int x;
    private final int y;
    private final Color color;
    private boolean penWasUp;

    public Position(int x, int y, Color color, boolean end) {
        this.x = x;
        this.y = y;
        this.color = color;
        penWasUp = end;
    }

    public Position(int x, int y, Color color) {
        this(x, y, color, false);
    }

    public Position(int x, int y) {
        this(x, y, Color.BLACK, false);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Color getColor() {
        return color;
    }


    public boolean getPenWasUp() {
        return penWasUp;
    }

    public void setPenWasUp(boolean flag) {
        penWasUp = flag; // needed when pen is toggled


    }
}
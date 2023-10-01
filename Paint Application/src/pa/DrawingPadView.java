package pa;
import paTools.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;

/**
 * This class establishes the canvas on which the turtle and its drawings can be seen.
 */
    public class DrawingPadView extends JPanel implements Subscriber { //This is the place where the turtle actually moves around and paints
        private Turtle t;

        public DrawingPadView(Turtle turt) {
            this.t = turt;
            t.subscribe(this);
            setSize(World.SIZE, World.SIZE);
            Border blackline = BorderFactory.createLineBorder(Color.black);
            setBorder(blackline);
            setBackground(Color.WHITE);
        }

        /*
         * Called by t.notifySubscribers in t.change.
         * repaint triggers a call to paintComponent
         */
        public void update()
        { repaint(); }

        /*
         * This must be called when the current turtle
         * is replaced by a new one or one loaded from
         * a fall. This happens when the user selects
         * New or Open from the File menu.
         */
        public void setTurtle(Turtle tu) {
            t.unSubscribe(this);
            t = tu;
            t.subscribe(this);
            repaint();
        }

        /*
         * Java VM will schedule a call to this. We can't
         * call it because we don't have gc
         */
        public void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            Color oldColor = gc.getColor();
            TurtleShape shape = new TurtleShape(t);
            shape.draw((Graphics2D) gc);
            gc.setColor(oldColor);
            t.getPath();
            Position p1 = null;
            Position p2 = null;
            Iterator<Position> it = t.iterator();
            boolean done = false;
            while (!done) {
                if (it.hasNext()) {
                    p1 = it.next();
                    p2 = it.next(); // next point after p1.
                    if (!p1.getPenWasUp()) {
                        gc.setColor(p2.getColor());
                        ((Graphics2D) gc).setStroke(new BasicStroke(3));
                        gc.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    }
                }
                else {
                    done = true;
                }
            }
        }
    }

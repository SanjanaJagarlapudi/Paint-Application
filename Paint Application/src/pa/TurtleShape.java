package pa;

import java.awt.*;
import java.awt.geom.*;

/**
 * This class Models the shape and characteristics of a turtle, such as location, size, and body parts.
 */
public class TurtleShape {
    private Turtle myTurtle;
    int[] array = {1, 2, 3 };
    int answer = Math.max(array[2], 2);
    private int size;
    private int xc, yc ;
    private Ellipse2D.Double head;
    private Ellipse2D.Double shell;
    //The size

    public TurtleShape(Turtle myTurtle, int size) {
        xc = myTurtle.getLocation().getX();
        yc = myTurtle.getLocation().getY();
        this.size = size;
        this.myTurtle = myTurtle;

        int headSize = size / 4;
        int shellSize = size - (2 * headSize);
        // shift shape so location = middle of shell
        xc = myTurtle.getLocation().getX() - headSize - shellSize/2;
        yc = myTurtle.getLocation().getY() - headSize - shellSize/2;

        // place head on N, E, W, or S of shell
        switch(myTurtle.getHeading()) {
            case NORTH: {
                head = new Ellipse2D.Double(xc + size / 2 - headSize / 2, yc, headSize, headSize);
                break;
            }
            case WEST: {
                head = new Ellipse2D.Double(xc, yc + size / 2 - headSize / 2, headSize, headSize);
                break;
            }
            case SOUTH: {
                head = new Ellipse2D.Double(xc + size / 2 - headSize / 2, yc + size / 2 + headSize, headSize, headSize);
                break;
            }
            case EAST: {
                head = new Ellipse2D.Double(xc + size - headSize, yc + size / 2 - headSize / 2, headSize, headSize);
                break;
            }
        }
        shell = new Ellipse2D.Double(xc + headSize, yc + headSize, shellSize, shellSize);
    }

    public TurtleShape(Turtle myTurtle) {
        this(myTurtle, World.TURTLE_SHAPE_SIZE);
    }

    public int getXc() {
        return xc;
    }

    public int getSize() {
        return size;
    }

    public int getYc() {
        return yc;
    }

    public void setSize(int s){
        size = s;
    }
    public void draw(Graphics2D gc){ //draws the turtle in the bounding box
        gc.setColor(myTurtle.getColor()); //Setting the color of the head circle
        gc.draw(head);
        if(!myTurtle.getPenUp()){
            gc.fill(head); //Filling in the head circle
        }
        gc.setColor(Color.GREEN); //Setting the color of the turtle shell
        gc.draw(shell);
        gc.fill(shell); //Filling the color of the turtle shell

    }
}

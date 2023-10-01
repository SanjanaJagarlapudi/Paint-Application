package pa;
import java.awt.Color;
import java.io.Serializable;
import paTools.*;
import java.util.*;

import static pa.Direction.*;

/**
 * This class models a Turtle and its Actions + Characteristics. The turtle is essentially the cursor by which
 * the user can paint on the canvas with.
 */
public class Turtle extends Publisher implements Serializable { //Allows us to save the turtle to a file
    private Position location;
    private Boolean penUp;
    private final List<Position> path; // turtle's rest stops, all  of the points where the turtle came to a stop
    private Direction heading;
    private Color color;

    public Turtle() {
        location = new Position(World.TURTLE_INITIAL_POSITION, World.TURTLE_INITIAL_POSITION, this.getColor());
        penUp = false;
        path = new LinkedList<Position>();
        heading = NORTH;
        color = Color.red;
    }

    public Direction getHeading() {
        return heading;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color){
        this.color = color;
        notifySubscribers();
    }

    public List<Position> getPath() {
        return path;
    }

    public Position getLocation() {
        return location;
    }

    public void setHeading(Direction heading) {
        this.heading = heading;
        notifySubscribers();
    }

    public Boolean getPenUp(){
        return penUp;
    }
    public void setPenUp(Boolean penUp){
        this.penUp = penUp;
        this.location.setPenWasUp(penUp);
        notifySubscribers();
    }
    public Iterator<Position> iterator(){
        return path.iterator();
    }

    public void Clear(){
        path.clear();
        notifySubscribers();
    }

    /**
     * Moves the Turtle in the desired direction for the specified units.
     *
     * @param n - the desired units the turtle has to move
     */
    public void move(int n){
        path.add(location); //Add the initial location of the turtle
        if(heading == NORTH) {
            northMovement(n);
        }
        if(heading == SOUTH){
            southMovement(n);
        }
        if(heading == WEST){
            westMovement(n);
        }
        if(heading == EAST){
            eastMovement(n);
        }
    }

    /**
     * This method moves the turtle in the North direction for the specified amount of units
     * @param userInput - specified number of units the turtle is to travel
     */
    private void northMovement(int userInput){
        int movement = -1; //Just a sentinel value to enter the while loop
        int change = 0; //Initialize the change variable to an arbitrary value, it will later be initialized properly
        Position loc;

        //If statement to make sure that if the input makes the turtle go too North and out of bounds,
        // the turtle would wrap around and emerge from the south.
        if (location.getY() - userInput < 0) {
            while (movement < 0) {
                change = userInput - location.getY(); //This is the amount that the turtle would be off the top of the screen
                Position top = new Position(location.getX(), 0, this.getColor(), true); //Point where turtle goes out of bounds
                location = top;
                path.add(top);
                notifySubscribers();

                movement = World.SIZE - change; //the amount that the turtle would travel from the bottom
                Position bottom = new Position(location.getX(), World.SIZE, this.getColor(), this.getPenUp());
                location = bottom;
                path.add(bottom);
                notifySubscribers();

                userInput = change;
            }
            loc = new Position(location.getX(), World.SIZE - change, this.getColor(), this.getPenUp());

        } else { //General Case for when turtle is not out of bounds/
            loc = new Position(location.getX(), location.getY() - userInput, this.getColor(), this.getPenUp());
        }
        location = loc;
        path.add(loc);
        notifySubscribers();

    }
    /**
     * This method moves the turtle in the South direction for the specified amount of units
     * @param userInput - specified number of units the turtle is to travel
     */
    private void southMovement(int userInput){
        int movement = World.SIZE + 1; //Just a sentinel value to enter the while loop
        int change = 0; //Initialize the change variable to an arbitrary value, it will later be initialized properly
        Position loc;

        //If statement to make sure that if the input makes the turtle go too South and out of bounds,
        // the turtle would wrap around and emerge from the north.
        if (location.getY() + userInput > World.SIZE) {
            while (movement > World.SIZE) {
                change = userInput - (World.SIZE - location.getY()); //This is the amount that the turtle would be off the bottom of the screen
                Position bottom = new Position(location.getX(), World.SIZE, this.getColor(), true); //Point where turtle goes out of bounds
                location = bottom;
                path.add(bottom);
                notifySubscribers();

                movement = change; //the amount that the turtle would travel from the top
                Position top = new Position(location.getX(), 0, this.getColor(), this.getPenUp());
                location = top;
                path.add(top);
                notifySubscribers();

                userInput = change;
            }
            loc = new Position(location.getX(), change, this.getColor(), this.getPenUp());

        } else { //General Case for when turtle is not out of bounds/
            loc = new Position(location.getX(), location.getY() + userInput, this.getColor(), this.getPenUp());
        }
        location = loc;
        path.add(loc);
        notifySubscribers();

    }
    /**
     * This method moves the turtle in the West direction for the specified amount of units
     * @param userInput - specified number of units the turtle is to travel
     */
    private void westMovement(int userInput){
        int movement = -1; //Just a sentinel value to enter the while loop
        int change = 0; //Initialize the change variable to an arbitrary value, it will later be initialized properly
        Position loc;

        //If statement to make sure that if the input makes the turtle go too West and out of bounds,
        // the turtle would wrap around and emerge from the East.
        if (location.getX() - userInput < 0) {
            while (movement < 0) {
                change = userInput - location.getX(); //This is the amount that the turtle would be off the left of the screen
                Position left = new Position(0, location.getY(), this.getColor(), true); //Point where turtle goes out of bounds
                location = left;
                path.add(left);
                notifySubscribers();

                movement = World.SIZE - change; //the amount that the turtle would travel from the right
                Position right = new Position(World.SIZE, location.getY(), this.getColor(), this.getPenUp());
                location = right;
                path.add(right);
                notifySubscribers();

                userInput = change;
            }
            loc = new Position(World.SIZE - change,location.getY() , this.getColor(), this.getPenUp());

        } else { //General Case for when turtle is not out of bounds/
            loc = new Position(location.getX() - userInput,location.getY(), this.getColor(), this.getPenUp());
        }
        location = loc;
        path.add(loc);
        notifySubscribers();

    }
    /**
     * This method moves the turtle in the East direction for the specified amount of units
     * @param userInput - specified number of units the turtle is to travel
     */
    private void eastMovement(int userInput){
        int movement = World.SIZE + 1; //Just a sentinel value to enter the while loop
        int change = 0; //Initialize the change variable to an arbitrary value, it will later be initialized properly
        Position loc;

        //If statement to make sure that if the input makes the turtle go too East and out of bounds,
        // the turtle would wrap around and emerge from the West.
        if (location.getX() + userInput > World.SIZE) {
            while (movement > World.SIZE) {
                change = userInput - (World.SIZE - location.getX()); //This is the amount that the turtle would be off the right of the screen
                Position right = new Position(World.SIZE, location.getY(), this.getColor(), true); //Point where turtle goes out of bounds
                location = right;
                path.add(right);
                notifySubscribers();

                movement = change; //the amount that the turtle would travel from the left
                Position left = new Position(0, location.getY(), this.getColor(), this.getPenUp());
                location = left;
                path.add(left);
                notifySubscribers();

                userInput = change;
            }
            loc = new Position(change ,location.getY() , this.getColor(), this.getPenUp());

        } else { //General Case for when turtle is not out of bounds/
            loc = new Position(location.getX() + userInput, location.getY(), this.getColor(), this.getPenUp());
        }
        location = loc;
        path.add(loc);
        notifySubscribers();
    }
}

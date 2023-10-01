package pa;

/*
 This is the MVC controller.
 */

import paTools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This class establishes the App Panel, on which both the Control Panel and the Canvas on which the
 * turtle is displayed can be seen. The
 */
public class UserView  extends JPanel implements ActionListener {
    private Turtle t;
    private ControlPanel controls;
    private DrawingPadView view; //this is canvas view

    private String fileName;

    public UserView() {
        // create model, install controls & view
        t = new Turtle();
        view = new DrawingPadView(t);
        controls = new ControlPanel();
        this.setLayout((new GridLayout(World.APP_PANEL_ROWS, World.APP_PANEL_COLS)));
        this.add(controls);
        this.add(view);
        // create my frame with menus and display it
        SafeFrame frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(this.createMenuBar());
        frame.setTitle("Turtle Graphics");
        frame.setSize(World.APP_PANEL_WIDTH, World.APP_PANEL_HEIGHT);
        frame.setVisible(true);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "Save As", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", new String[]{ "North", "East", "West", "South", "Clear",
                "Pen", "Color", "# of Steps" }, this);
        result.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[]{"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    /**
     * This method processes events performed by the users when they interact with the control panel.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();
        //2.0: New, Save, SaveAs, Open, Quit, Help, and About.
        //Original: North, East, West, South, Clear, Pen, Color;
        try {
            switch (cmmd) {
                case "North": {
                    t.setHeading(Direction.NORTH);
                    break;
                }
                case "South": {
                    t.setHeading(Direction.SOUTH);
                    break;
                }
                case "East": {
                    t.setHeading(Direction.EAST);
                    break;
                }
                case "West": {
                    t.setHeading(Direction.WEST);
                    break;
                }
                case "Color": {
                    Color newColor = JColorChooser.showDialog(null, "Choose a color", t.getColor());
                    t.setColor(newColor);
                    break;
                }
                case "Pen": {
                    if(t.getPenUp()){
                        t.setPenUp(false);
                    }
                    else{
                        t.setPenUp(true);
                    }
                    break;
                }
                case "Clear" : {
                    t.Clear();
                    break;
                }
                case "StepInput" : {
                    JTextField field = (JTextField) e.getSource();
                    int val = 0;
                    try{
                        val = Integer.parseInt(field.getText());
                    }
                    catch(Exception x){//If the input is invalid, set val to negative value so it enters the if statement
                        val = -1;
                    }
                    if (val <= 0){
                        throw new IllegalArgumentException("# of Steps Must Be A Non-Negative Number");
                    }
                    t.move(val);
                    break;
                }
                case "# of Steps": { //Case for when the number of steps is inputted through the edit window
                    String input = Utilities.ask("Please enter the # of Steps: ");
                    int val = 0;
                    try{
                        val = Integer.parseInt(input);
                    }
                    catch(Exception x){//If the input is invalid, set val to negative value so it enters the if statement
                        val = -1;
                    }
                    if (val <= 0){
                        throw new IllegalArgumentException("# of Steps Must Be A Non-Negative Number");
                    }
                    t.move(val);
                    break;

                }
                case "Save": {
                    if(fileName == null){
                        fileName = Utilities.getFileName((String) null, false);
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
                        os.writeObject(this.t);
                        os.close();
                    }
                    break;
                }
                case "Save As": {
                    String fName = Utilities.getFileName((String) null, false);
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                    os.writeObject(this.t);
                    os.close();
                    break;
                }
                case "Open": {
                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!")) {
                        String fName = Utilities.getFileName((String) null, true);
                        ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
                        t = (Turtle) is.readObject();
                        view.setTurtle(t);
                        is.close();
                    }
                    break;
                }
                case "New": {
                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!")) {
                        t = new Turtle();
                        view.setTurtle(t);
                    }
                    break;
                }
                case "Quit": {
                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!"))
                        System.exit(0);
                    break;
                }
                case "About": {
                    Utilities.inform("Cyberdellic Designs Turtle Graphics, 2021. All rights reserved.");
                    break;
                }
                case "Help": {
                    String[] cmmds = new String[]{
                            "North: Changes the Direction of the Turtle to North" + "\n" +
                                    "East: Changes the Direction of the Turtle to East " + "\n" +
                                    "West: Changes the Direction of the Turtle to West" + "\n" +
                                    "South: Changes the Direction of the Turtle to South" + "\n" +
                                    "Clear: Clears the current drawings" + "\n" +
                                    "Pen: Toggles the pen to be either up or down" + "\n" +
                                    "Color: Allows user to choose the color of the line to be drawn by the turtle" + " \n" +
                            "# of Steps: Move the turtle the specified number of steps in the specified direction",
                    };
                    Utilities.inform(cmmds);
                    break;
                }
                default: {
                    throw new Exception("Unrecognized command: " + cmmd);
                }
            }
        } catch (Exception ex) {
            Utilities.error(ex); // all error handling done here!
        }
    }

    /**
     * This is an Inner class that establishes the control panel on which many buttons
     * and selections are displayed for the user to manipulate appearance of the Canvas.
     */
    class ControlPanel extends JPanel {
        public ControlPanel() {
            setBackground(Color.PINK);
            JPanel a = new JPanel();
            JButton North = new JButton("North");
            North.addActionListener(UserView.this);
            a.add(North); //adding the button to the panel a
            add(a); //adding panel a to this control Panel

            JPanel b = new JPanel();
            JButton South = new JButton("South");
            South.addActionListener(UserView.this);
            b.add(South);
            add(b);

            JPanel c = new JPanel();
            JButton East = new JButton("East");
            East.addActionListener(UserView.this);
            c.add(East);
            add(c);

            JPanel d = new JPanel();
            JButton West = new JButton("West");
            West.addActionListener(UserView.this);
            d.add(West);
            add(d);

            JPanel e = new JPanel();
            JButton Color = new JButton("Color");
            Color.addActionListener(UserView.this);
            e.add(Color);
            add(e);

            JPanel f = new JPanel();
            JButton Pen = new JButton("Pen");
            Pen.addActionListener(UserView.this);
            f.add(Pen);
            add(f);

            JPanel g = new JPanel();
            JButton Clear = new JButton("Clear");
            Clear.addActionListener(UserView.this);
            g.add(Clear);
            add(g);

            JPanel h = new JPanel();
            JTextArea Steps = new JTextArea("# of Steps");
            h.add(Steps);
            add(h);

            JTextField text = new JTextField("", 15);
            text.setActionCommand("StepInput");
            text.addActionListener(UserView.this);
            h.add(text);
            add(h);

        }
    }
    // and away we go ...
    public static void main(String[] args) {
        UserView app = new UserView();
    }
}

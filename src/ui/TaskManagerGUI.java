package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 * TaskManagerGUI is used to create a GUI for TaskManager.
 * 
 * @author A0113966Y
 *
 */

public class TaskManagerGUI extends JPanel{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 9047466908965386773L;
	
	private static final String TITLE_FRAME = "Task's Manager";
    private static final String TITLE_SLOGAN = "Managing Tasks Like A Boss";
    private static final Font FONT_KOKONOR = new Font("Kokonor", Font.ITALIC, 12);
      
    private InputBox inputBox;
    private FeedbackFrame feedbackFrame;
    private DeadlinesAndTimeTasksTable deadlinesAndTimeTasksTable;
    private FloatingTasksTable floatingTasksTable;
    private JLabel slogan;

    private JFrame frame;
    
    public TaskManagerGUI() {
        super(new GridBagLayout());

        inputBox = InputBox.getInstance();
        feedbackFrame = FeedbackFrame.getInstance();
        deadlinesAndTimeTasksTable = DeadlinesAndTimeTasksTable.getInstance();
        floatingTasksTable = FloatingTasksTable.getInstance();
        
        addKeyboardCommand();
        
        GridBagConstraints c = new GridBagConstraints();
       
        //add and position inputBox
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        add(inputBox, c);
        
        //add and position feedbackFrame
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        add(feedbackFrame, c);

        //add and position deadlinesAndTimeTasksTable
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        add(deadlinesAndTimeTasksTable, c);
 
        //add and position floatingTasksTable
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 10;
        add(floatingTasksTable, c);      
     
        updateTable();
    }
    
    public void updateTable() {
    	int taskNumber = deadlinesAndTimeTasksTable.updateTable();
    	floatingTasksTable.updateTable(taskNumber - 1);
    }

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
    	frame = new JFrame(TITLE_FRAME);
    	initializeFrame();
    	initializeSlogan();

    	Clock clock = new Clock();
    	TaskManagerGUI gui = new TaskManagerGUI();
    	
    	frame.add(clock, BorderLayout.NORTH);
    	frame.add(gui, BorderLayout.CENTER);
    	frame.add(slogan, BorderLayout.SOUTH);

    	displayFrame();
    }

    private void initializeFrame() {
    	frame.setResizable(false);
    	frame.setMinimumSize(new Dimension(700, 500));
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new BorderLayout());
    }

    private void displayFrame() {
    	frame.setLocation(200, 200);
    	frame.pack();
    	frame.setVisible(true);
    }

    private void initializeSlogan() {
    	slogan = new JLabel(TITLE_SLOGAN, SwingConstants.CENTER);
    	slogan.setFont(FONT_KOKONOR);
    }

    public void run() {
    	createAndShowGUI();
    }

    public void printMessageToUser(String message){
    	System.out.println(message);
    }

    private void addKeyboardCommand() {
    	 KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

             public boolean dispatchKeyEvent(KeyEvent e) {
            	 
            	 //Allows the use of "escape" key to close the program
                 if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                     System.exit(0);
                 }
                 
                 //Allows the use of "alt" key to minimize the program
                 if (e.getKeyCode() == KeyEvent.VK_ALT) {
                 	frame.setState(Frame.ICONIFIED);
                 }
  
                 return false;
             }
         });
    }
    
}

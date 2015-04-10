package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

import logic.LogicController;

public class TaskManagerGUI extends JPanel{
    
	private static final String TITLE_FRAME = "Task's Manager";
    private static final String TITLE_SLOGAN = "Managing Tasks Like A Boss";
    private static final Font FONT_KOKONOR = new Font("Kokonor", Font.ITALIC, 12);
  
//    final static boolean shouldFill = true;
    
    private InputBox inputBox;
    private FeedbackBox feedbackBox;
    private static TaskManagerGUI taskManagerGUI;
    private DeadlinesAndTimeTasksTable deadlinesAndTimeTasksTable;
    private FloatingTasksTable floatingTasksTable;
    private static JLabel slogan;

    private static LogicController logicController;

    private static JFrame frame;
    private static JScrollPane scrollPane1;
    private static JScrollPane scrollPane2;
    

    
    public TaskManagerGUI() {
        super(new GridBagLayout());

        logicController = LogicController.getInstance();
        inputBox = InputBox.getInstance();
        feedbackBox = FeedbackBox.getInstance();
        JScrollPane scrollPaneFeedbackBox = new JScrollPane(feedbackBox);
        deadlinesAndTimeTasksTable = DeadlinesAndTimeTasksTable.getInstance();
        floatingTasksTable = FloatingTasksTable.getInstance();
        
        addKeyboardCommand();
        
        GridBagConstraints c = new GridBagConstraints();
       
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        add(inputBox, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        add(scrollPaneFeedbackBox, c);


        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        add(deadlinesAndTimeTasksTable, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 10;
        add(floatingTasksTable, c);

//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 11;
//        add(scrollPane2, c);
        
     
        updateTable();
    }
    
    public void updateTable() {
    	int taskNumber = deadlinesAndTimeTasksTable.updateTable();
    	floatingTasksTable.updateTable(taskNumber);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame(TITLE_FRAME);
        initializeFrame();
        initializeSlogan();
  
        Clock clock = new Clock();
        
        //Create and set up the content pane.
        TaskManagerGUI gui = new TaskManagerGUI();
        
        
        frame.add(clock, BorderLayout.NORTH);
        frame.add(gui, BorderLayout.CENTER);
        frame.add(slogan, BorderLayout.SOUTH);


        //Display the window.
        displayFrame();
    }

    private static void initializeFrame() {
    	frame.setResizable(false);
    	frame.setMinimumSize(new Dimension(700, 500));
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new BorderLayout());
    }

    private static void displayFrame() {
    	frame.setLocation(200, 200);
    	frame.pack();
    	frame.setVisible(true);
    }

    private static void initializeSlogan() {
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
                 if (e.getKeyCode() == e.VK_ESCAPE) {
                     System.exit(0);
                 }
                 
                 //Allows the use of "alt" key to minimize the program
                 if (e.getKeyCode() == e.VK_ALT) {
                 	frame.setState(Frame.ICONIFIED);
                 }
  
                 return false;

             }
         });
    }
    
    public static String processUserInput(String userInput){
	    String message;
	    LogicController commandHandler = LogicController.getInstance();
	    message = commandHandler.executeCommand(userInput);
	    return message;
	}
    
    public static JScrollPane getScrollPane1() {
    	return scrollPane1;
    }
    
    public static JScrollPane getScrollPane2() {
    	return scrollPane2;
    }
    public static TaskManagerGUI getInstance() {
    	return taskManagerGUI;
    }

}

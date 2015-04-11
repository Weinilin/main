//@author A0113966Y

package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import logic.LogicController;

public class InputBox extends JTextField implements ActionListener{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 5019197807784262340L;
	
	private static final Font FONT_ARIAL = new Font("Arial", Font.PLAIN, 12);
	private static final String TITLE_HELP_POP_UP = "Help Message";
	private static final String COMMAND_HELP = "help";

	private static FeedbackFrame feedbackFrame;
	private static InputBox inputBox;
	private static DeadlinesAndTimeTasksTable deadlinesAndTimeTasksTable;
	private static FloatingTasksTable floatingTasksTable;
	
	private static LogicController logicController;
	

	private InputBox() {
		super();
		feedbackFrame = FeedbackFrame.getInstance();
		feedbackFrame = FeedbackFrame.getInstance();
		deadlinesAndTimeTasksTable = DeadlinesAndTimeTasksTable.getInstance();
		floatingTasksTable = FloatingTasksTable.getInstance();
		logicController = LogicController.getInstance();

		initializeInputFrame();
	}

	private void initializeInputFrame() {
		setFont(FONT_ARIAL);
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent evt) {
		String input = getUserInput();
		highlightUserInput();
		String feedback = processUserInput(input);

		if (isHelpCommand(input)) {
			popUpHelpWindow(feedback);
		} else {
			if (isExitCommand(feedback)) {
				System.exit(0);
			} else {
				displayFeedback(feedback);
			}
		}

		ensureProperDisplayOfFeedback();    
		updateTable();
	}

	/*
	 * Make sure new text is visible, even if there was a selection in the feedbackBox
	 */
	private void ensureProperDisplayOfFeedback() {
		feedbackFrame.ensureProperDisplayOfFeedback();
    }
    
    private void popUpHelpWindow(String message) {
    	PopUp popUp = new PopUp(message);
    	JOptionPane.showMessageDialog(null, popUp, TITLE_HELP_POP_UP, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean isHelpCommand(String input) {
    	return input.toLowerCase().equals(COMMAND_HELP);
    }
    
    private void displayFeedback(String feedback) {
    	feedbackFrame.displayText(feedback);
    }
    
    private String getUserInput() {
    	return getText();
    }
    
    private void highlightUserInput() {
    	selectAll();
    }
    
    private void updateTable() {
    	int taskNumber = deadlinesAndTimeTasksTable.updateTable();
    	floatingTasksTable.updateTable(taskNumber);
    }
    
    public static InputBox getInstance() {
    	if (inputBox == null) {
			inputBox = new InputBox();
		}
    	
    	return inputBox;
    }
    
    private boolean isExitCommand(String feedback) {
    	return feedback == null;
    }
    
    /**
	 * Scan user input and execute the command.
	 */
    public String processUserInput(String userInput){
	    String message;
	    message = logicController.executeCommand(userInput);
	    return message;
	}
}

package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InputBox extends JTextField implements ActionListener{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 5019197807784262340L;
	
	private static final Font FONT_ARIAL = new Font("Arial", Font.PLAIN, 12);
	private static final String TITLE_HELP_POP_UP = "Help Message";
	private static final String COMMAND_HELP = "HELP";
	
	
	private static TaskManagerGUI taskManagerGUI;
	private static FeedbackBox feedbackBox;
	private static InputBox inputBox;
	
	private InputBox() {
		super();
		taskManagerGUI = TaskManagerGUI.getInstance();
		feedbackBox = FeedbackBox.getInstance();
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
			displayFeedback(feedback);
		}

		ensureProperDisplayOfFeedback();    
		updateTable();
	}

	/*
	 * Make sure new text is visible, even if there was a selection in the feedbackBox
	 */
	private void ensureProperDisplayOfFeedback() {
		feedbackBox.setCaretPosition(feedbackBox.getDocument().getLength());
    }
    
    private void popUpHelpWindow(String message) {
    	PopUp popUp = new PopUp(message);
    	JOptionPane.showMessageDialog(null, popUp, TITLE_HELP_POP_UP, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean isHelpCommand(String input) {
    	return input.equals(COMMAND_HELP);
    }
    
    private void displayFeedback(String feedback) {
    	feedbackBox.setText(feedback);
    }
    
    private String getUserInput() {
    	return getText();
    }
    
    private void highlightUserInput() {
    	selectAll();
    }
    
    private String processUserInput(String input) {
    	return TaskManagerGUI.processUserInput(input);
    }
    
    private void updateTable() {
    	taskManagerGUI.updateTable();
    }
    
    public static InputBox getInstance() {
    	if (inputBox == null) {
			inputBox = new InputBox();
		}
    	
    	return inputBox;
    }
}

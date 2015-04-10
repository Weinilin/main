package ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FeedbackFrame extends JPanel{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -2842115507042961347L;
	
	private static FeedbackFrame feedbackFrame;
	private static JTextArea feedbackBox;
	
    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");

	private FeedbackFrame() {
		super();
		feedbackBox = new JTextArea();
		add(feedbackBox);
		initializeFeedbackBox();
	}
	
	private void initializeFeedbackBox() {
		feedbackBox.setPreferredSize(new Dimension(700, 4));
		feedbackBox.setRows(4);
		feedbackBox.setColumns(20);
		feedbackBox.setFont(new Font("Arial", Font.PLAIN, 12));
		feedbackBox.setLineWrap(true);
		feedbackBox.setEditable(false);
		feedbackBox.setText(WELCOME_MESSAGE);
	}
	
	public void displayText(String text) {
		feedbackBox.setText(text);
	}

	public static FeedbackFrame getInstance() {
		if (feedbackFrame == null) {
			feedbackFrame = new FeedbackFrame();
		}
		return feedbackFrame;
	}
	
	void ensureProperDisplayOfFeedback() {
		feedbackBox.setCaretPosition(feedbackBox.getDocument().getLength());
    }
}

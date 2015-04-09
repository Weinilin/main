package ui;

import java.awt.Font;

import javax.swing.JTextArea;

public class FeedbackBox extends JTextArea{
	private static FeedbackBox feedbackBox;
	
    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");

			
	private FeedbackBox() {
		super();
		initializeFeedbackBox();
	}
	
	private void initializeFeedbackBox() {
		setRows(4);
		setColumns(20);
		setFont(new Font("Arial", Font.PLAIN, 12));
        setLineWrap(true);
        setEditable(false);
        setText(WELCOME_MESSAGE);
	}

	public static FeedbackBox getInstance() {
		if (feedbackBox != null) {
			feedbackBox = new FeedbackBox();
		}
		return feedbackBox;
	}
}

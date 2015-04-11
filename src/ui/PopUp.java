package ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

/**
 * PopUp is used to create a pop up window.
 * In TaskManager, it is used to create a pop-up window for help message
 * 
 * @author A0113966Y
 *
 */

public class PopUp extends JTextArea {
	/**
	 * generated
	 */
	private static final long serialVersionUID = 3251601284689941756L;
	
	public PopUp(String message) {
		super(message);
		initializePopUp();

	}
	
	private void initializePopUp() {
		setPreferredSize(new Dimension(450, 400));
		setEditable(false);
    	setLineWrap(true); 
        setWrapStyleWord(true); 
    	setFont(new Font("Arial", Font.PLAIN, 12 ));
    	setSize(450, 1);
	}
}

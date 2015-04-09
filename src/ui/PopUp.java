package ui;

import java.awt.Font;

import javax.swing.JTextArea;

public class PopUp extends JTextArea {
	/**
	 * generated
	 */
	private static final long serialVersionUID = 3251601284689941756L;
	
	private static JTextArea popUp;
	
	public PopUp(String message) {
		popUp = new JTextArea(message);
		initializePopUp();
	}
	
	private void initializePopUp() {
		popUp.setEditable(false);
    	popUp.setLineWrap(true); 
        popUp.setWrapStyleWord(true); 
    	popUp.setFont(new Font("Arial", Font.PLAIN, 12 ));
    	popUp.setSize(450, 1);
	}
}

package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Clock class is used to create a clock for Task Manager's GUI
 * 
 * @author @A0113966Y
 *
 */

class Clock extends JLabel implements ActionListener {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 1971896250789963175L;
	
	private static final Font FONT_IOWAN_OLD_STYLE = new Font("Iowan Old Style", Font.PLAIN, 12);

	public Clock() {
		super("" + new Date());
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(FONT_IOWAN_OLD_STYLE);
		Timer t = new Timer(0, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		setText((new Date()).toString().substring(0, 10) + (new Date()).toString().substring(23) + " " + (new Date()).toString().substring(11, 20));
	}
}

package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

class Clock extends JLabel implements ActionListener {

	public Clock() {
		super("" + new Date());
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(new Font("Iowan Old Style", Font.PLAIN, 12 ));
		Timer t = new Timer(0, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		setText((new Date()).toString().substring(0, 10) + (new Date()).toString().substring(23) + " " + (new Date()).toString().substring(11, 20));
	}
}

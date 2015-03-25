package application;

import logic.LogicController;
import ui.CommandLineInterface;

class TaskManager	{
	
	public static void main (String[] args)	{		
		CommandLineInterface cli = new CommandLineInterface();
		cli.processUserInput();
	}
	

}
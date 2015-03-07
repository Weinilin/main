package application;

import logic.LogicController;
import logic.TaskList;
import ui.CommandLineInterface;

class TaskManager	{
	
	public static void main (String[] args)	{		
		CommandLineInterface cli = new CommandLineInterface();
		cli.userInput();
	}
	

}
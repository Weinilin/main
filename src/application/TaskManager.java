package application;

import handler.CommandHandler;
import handler.TaskList;
import ui.CommandLineInterface;

class TaskManager	{
	
	public static void main (String[] args)	{		
		CommandLineInterface cli = new CommandLineInterface();
		cli.userInput();
	}
	

}
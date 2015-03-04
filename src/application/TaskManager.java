package application;

import application.TaskList;
import handler.CommandHandler;
import ui.CommandLineInterface;

class TaskManager	{
	
	public static void main (String[] args)	{		
		CommandLineInterface cli = new CommandLineInterface();
		cli.userInput();
	}
	

}
package application;

import java.util.Scanner;

import ui.CommandLineInterface;
import ui.TaskManagerGUI;
import ui.TaskListUI;

class TaskManager	{
    
    private static final String COMMAND_MESSAGE = new String("Command: ");
    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
	
	public static void main (String[] args)	{	
		CommandLineInterface cli = new CommandLineInterface();
		Scanner scanner = new Scanner(System.in);

		TaskManagerGUI std = new TaskManagerGUI();
        std.run();
        
        TaskListUI taskListUI = new TaskListUI();
        taskListUI.showTask();

	    printMessageToUser(String.format(WELCOME_MESSAGE));
        printMessageToUser(String.format(COMMAND_MESSAGE));

		while (true) {
		    String userInput = scanner.nextLine();
		    String feedback = cli.processUserInput(userInput);
	        printMessageToUser(feedback);
	        taskListUI.showTask();
	        printMessageToUser(String.format(COMMAND_MESSAGE));
		}
	}
	
	public static void printMessageToUser(String message){
        System.out.println(message);
    }

}

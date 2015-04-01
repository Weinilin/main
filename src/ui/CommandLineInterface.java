package ui;

import java.util.Scanner;

import logic.LogicController;

public class CommandLineInterface {

	private static final String COMMAND_MESSAGE = new String("Command: ");
	private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
	private static final String GOODBYE_MESSAGE = new String("GoodBye!\n");
	


	public CommandLineInterface(){
	} 	

	/**
	 * Scan the user input and execute the command.
	 */
	public String processUserInput(String userInput){
		String message;
		LogicController commandHandler = LogicController.getInstance();
		
		TaskListUI taskListUI = new TaskListUI(commandHandler.getTaskList());
		taskListUI.showTask();
       

		

	    message = commandHandler.executeCommand(userInput);
						
		std.updateTable();
		taskListUI.showTask();
		
		return message;
	}


	public String processUserInputFromGUI(String userInput){
	    String message;
	    LogicController commandHandler = LogicController.getInstance();


	    printMessageToUser(String.format(WELCOME_MESSAGE));

	    printMessageToUser(String.format(COMMAND_MESSAGE));

	    message = commandHandler.executeCommand(userInput);
	            
	    
	    TaskListUI taskListUI = new TaskListUI(commandHandler.getTaskList());
	    taskListUI.showTask();

	    return message;

	}

	/**
	 * Print all of the message to the user
	 * @param message
	 */
	
	public static void printMessageToUser(String message){
        System.out.println(message);
    }

	
	
}

package ui;

import java.util.Scanner;

import logic.LogicController;

public class CommandLineInterface {

	private static final String COMMAND_MESSAGE = new String("Command: ");
	private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
	private static final String GOODBYE_MESSAGE = new String("GoodBye!\n");
	private Scanner scanner;
	


	public CommandLineInterface(){
	} 	

	/**
	 * Scan the user input and execute the command.
	 */
	public void processUserInput(){
		String userCommand, message;
		scanner = new Scanner(System.in);
		LogicController commandHandler = LogicController.getInstance();
		
		TaskListUI taskListUI = new TaskListUI(commandHandler.getTaskList());
		taskListUI.showTask();
       

		GUI std = new GUI();
	    std.run();
		printMessageToUser(String.format(WELCOME_MESSAGE));

		while (true) {
			printMessageToUser(String.format(COMMAND_MESSAGE));
			userCommand = scanner.nextLine();
			message = commandHandler.executeCommand(userCommand);
			if (message == null) {
				printMessageToUser(GOODBYE_MESSAGE);
				System.exit(0);
			}			
			printMessageToUser(message);
			std.updateTable();
			taskListUI.showTask();
		}
	}


	public String processUserInputFromGUI(String userInput){
	    String message;
	    LogicController commandHandler = LogicController.getInstance();


	    printMessageToUser(String.format(WELCOME_MESSAGE));

	    printMessageToUser(String.format(COMMAND_MESSAGE));

	    message = commandHandler.executeCommand(userInput);
	    if (message == null) {
	        printMessageToUser(GOODBYE_MESSAGE);
	        System.exit(0);
	    }         
	    
	    TaskListUI taskListUI = new TaskListUI(commandHandler.getTaskList());
	    taskListUI.showTask();

	    return message;

	}

	/**
	 * Print all of the message to the user
	 * @param message
	 */
	public void printMessageToUser(String message){
		System.out.println(message);
	}

	
	
}

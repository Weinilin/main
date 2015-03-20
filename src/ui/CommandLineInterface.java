package ui;

import java.util.Scanner;

import logic.LogicController;


public class CommandLineInterface {

	private static final String COMMAND_MESSAGE = new String("Command: ");
	private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
	private Scanner scanner;

	public CommandLineInterface(){
	} 	

	/**
	 * Scan the user input and execute the command.
	 */
	public void processUserInput(){
		String userCommand, message, display;
		scanner = new Scanner(System.in);
		LogicController commandHandler = LogicController.getInstance();
		display = commandHandler.executeCommand("show");
		printMessageToUser(display);
	
		printMessageToUser(String.format(WELCOME_MESSAGE));

		while (true) {
			printMessageToUser(String.format(COMMAND_MESSAGE));
			userCommand = scanner.nextLine();
			message = commandHandler.executeCommand(userCommand);
			display = commandHandler.executeCommand("show");
			printMessageToUser(message);
			printMessageToUser(display);
		}
	}

	/**
	 * Print all of the message to the user
	 * @param message
	 */
	public void printMessageToUser(String message){
		System.out.printf(message);
	}

	
}

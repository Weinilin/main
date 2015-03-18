package ui;

import java.util.Scanner;

import logic.LogicController;


public class CommandLineInterface {

	private static final String COMMAND_MESSAGE = new String("Command: ");
	private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");

	public CommandLineInterface(){
	} 	

	public void processUserInput(){
		Scanner scanner = new Scanner(System.in);
		LogicController commandHandler = new LogicController();
		commandHandler.processCommand("display");
		String userCommand, message;
	
		printMessageToUser(String.format(WELCOME_MESSAGE));

		while (true) {
			printMessageToUser(String.format(COMMAND_MESSAGE));
			userCommand = scanner.nextLine();
			message = commandHandler.processCommand(userCommand);
			commandHandler.processCommand("display");
			printMessageToUser(message);
		}
	}

	//print all of the different type of message using this method
	public void printMessageToUser(String message){
		System.out.printf(message);
	}

	
}

package ui;

import logic.LogicController;

/**
 * CommandLineInterface is used to create a Command Line Interface for TaskManager
 * 
 * @author A0113966Y
 *
 */

public class CommandLineInterface {

	public CommandLineInterface(){
	} 	

	/**
	 * Scan user input and execute the command.
	 */
	public String processUserInput(String userInput){
		String message;
		LogicController commandHandler = LogicController.getInstance();
	    message = commandHandler.executeCommand(userInput);
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

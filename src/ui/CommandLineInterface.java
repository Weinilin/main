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
		
       

		

	    message = commandHandler.executeCommand(userInput);
						
		
		return message;
	}


	public String processUserInputFromGUI(String userInput){
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

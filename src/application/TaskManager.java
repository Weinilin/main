
package application;

import java.util.Scanner;

import ui.CommandLineInterface;
import ui.GUI;

class TaskManager	{
    
    private static final String COMMAND_MESSAGE = new String("Command: ");
    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
	
	public static void main (String[] args)	{	
		CommandLineInterface cli = new CommandLineInterface();
		Scanner scanner = new Scanner(System.in);
		
		GUI std = new GUI();
        std.run();
		
	    printMessageToUser(String.format(WELCOME_MESSAGE));

		while (true) {
		    printMessageToUser(String.format(COMMAND_MESSAGE));
		    String userInput = scanner.nextLine();
		    String feedback = cli.processUserInput(userInput);
	        printMessageToUser(feedback);
		}
	}
	
	public static void printMessageToUser(String message){
        System.out.println(message);
    }

}
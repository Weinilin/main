import java.util.Scanner;



public class CommandLineInterface {

	public static final String COMMAND_MESSAGE = new String("Command: \n");
	public static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");

	public CommandLineInterface(){
	} 	

	public void userInput(){
		Scanner scanner = new Scanner(System.in);
		String userCommand, commandType, message;
		//get to display all file.
				DisplayHandler....
		printMessageToUser(String.format(WELCOME_MESSAGE));
	
		while(true){
			printMessageToUser(String.format(COMMAND_MESSAGE));
			userCommand = scanner.next();
			commandType = CommandParser.determineCommandType(userCommand);
			message = CommandHandler.executeCommand(userCommand, commandType);
			printMessageToUser(message);
		}
	}

	//print all of the different type of message using this method
	public void printMessageToUser(String message){
		System.out.printf(message);
	}

}

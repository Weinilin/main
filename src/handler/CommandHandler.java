package handler;

import java.util.logging.Logger;

import parser.CommandParser;

/**
 * Construct by passing a TaskList to the constructor 
 * 
 * process each line of input given by user and calls different
 * handlers by different command given by user
 * and returns a feedback to ui
 */
public class CommandHandler {

	TaskList taskList = new TaskList();
	
	public CommandHandler() {
		
	}
	
	public String processCommand(String userInput) {
		String userCommand = CommandParser.determineCommandType(userInput);
		return executeCommand(userCommand, userInput);
	}
	
	// dummy for now
	private String executeCommand(String command, String userInput) {
		switch (command) {
			case "add":
				break;
			default:
				break;
		}
		return "";
	}
}

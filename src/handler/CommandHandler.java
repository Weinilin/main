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
	
	public CommandHandler(TaskList taskList) {
		this.taskList = taskList;
	}
	
	public String processCommand(String userInput) {
		CommandParser cp = new CommandParser();
		String userCommand = cp.determineCommandType(userInput);
		executeCommand(userCommand, userInput);
		
		return "";
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

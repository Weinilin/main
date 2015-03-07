package handler;

import java.util.logging.Logger;

import parser.CommandParser;
import application.TaskData;

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
		String feedback = new String();
		switch (command) {
			case "add":
				AddHandler ah = new AddHandler(taskList);
				if (ah.addTask(userInput)) {
					feedback = "Successfully added " + userInput;
				}
				break;
			case "delete":
				DeleteHandler dh = new DeleteHandler(taskList);
				TaskData removedTask= dh.deleteTask(userInput);
				if (removedTask != null) {
					feedback = "Successfully deleted " + removedTask.toString();
				}
				else {
					feedback = "Please check your input";
				}
				break;
			case "edit":
				EditHandler eh = new EditHandler(taskList);
				if (eh.editTask(userInput)) {
					feedback = "Updated to " + userInput;
				}
				else {
					feedback = "Please check your input";
				}
				break;
			case "show":
				ShowHandler sh = new ShowHandler(taskList);
				feedback = sh.showTask(userInput);
				break;
			default:
				break;
		}
		return feedback;
	}
}

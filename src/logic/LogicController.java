package logic;

import java.util.logging.Logger;

import database.Memory;
import database.Database;
import parser.CommandParser;
import application.TaskData;

/**
 * Construct by passing a TaskList to the constructor 
 * 
 * process each line of input given by user and calls different
 * handlers by different command given by user
 * and returns a feedback to ui
 */
public class LogicController {

	private Memory memory;
	private Database database;
	
	public LogicController() {
		Database database = new Database();
		memory = new Memory(database);
	}
	
	public String processCommand(String userInput) {
		String userCommand = CommandParser.determineCommandType(userInput);
		return executeCommand(userCommand, userInput);
	}
	
	/**
	 * execute command by creating different handler object
	 * associating to the memory object so that it deals
	 * with the list of tasks
	 * 
	 * @param command - command extracted from user
	 * @param userInput - parameters of command
	 * @return - feedback string to be displayed on console
	 */
	private String executeCommand(String command, String userInput) {
		String feedback = new String();
		userInput = userInput.replaceFirst(command, "").trim();
		switch (command) {
			case "add":
				AddHandler ah = new AddHandler(memory);
				if (ah.addTask(userInput)) {
					feedback = "Successfully added " + userInput + "\n";
				}
				break;
			case "delete":
				DeleteHandler dh = new DeleteHandler(memory);
				TaskData removedTask= dh.deleteTask(userInput);
				if (removedTask != null) {
					feedback = "Successfully deleted: \n" + removedTask.toString();
				}
				else {
					feedback = "Please check your input";
				}
				break;
			case "edit":
				EditHandler eh = new EditHandler(memory);
				if (eh.editTask(userInput)) {
					feedback = "Updated to " + userInput;
				}
				else {
					feedback = "Please check your input";
				}
				break;
			case "show":
				ShowHandler sh = new ShowHandler(memory);
				feedback = sh.showTask(userInput);
				break;
			case "exit":
				feedback = "Goodbye!";
				System.exit(0);
				break;
			case "mark":
				MarkHandler mh = new MarkHandler(memory);
				if (mh.markTaskDone(userInput)) {
					feedback = "Successfully marked. ";
				}
				else {
					feedback = "Please check your input";
				}
				break;
			default:
				break;
		}
		return feedback;
	}
}

package logic;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import database.Memory;
import parser.CommandParser;
import application.Task;

/**
 * Construct by passing a TaskList to the constructor 
 * 
 * process each line of input given by user and calls different
 * handlers by different command given by user
 * and returns a feedback to ui
 */
public class LogicController {

	private List<Task> taskList = new ArrayList<Task>();
	
	public LogicController() {
		taskList = Memory.getInstance().getTaskList();
	}
	
	public String executeCommand(String userCommand) {
		String command = CommandParser.getCommandType(userCommand);
		
		String feedback = null;
		return feedback;
	}
}

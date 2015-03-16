
package logic;

import java.util.ArrayList;

import parser.DateParser;
import parser.DescriptionParser;
import parser.TaskTypeParser;
import parser.TimeParser;
import application.TaskData;
import database.Memory;

/**
 * All handlers in Logic (exept undo) shall extend this class so that all
 * handlers have functions of execute() and getHelp()
 */
abstract class CommandHandler {
	/**
	 * memory object that all handlers will interact with
	 */
	private Memory memory;
	
	/*
	 * execute the command based on the input from user (such as "add",
	 * "delete", etc) and the parameter for executing the command. Return
	 * the feedback as a String object for now.
	 *@param command - command extracted from user input
	 *@param parameter - parameter for executing the command based on user input
	 *@return feedback String to UI after each execution of the command
	 */
	abstract String execute(String command, String parameter);
	
	/*
	 * get help String for each of the commands when user types "[command] help"
	 */
	abstract public String getHelp();
	
	/**
	 * creates a new task by a string containing the information of the task.
	 * It will call the respective parsers to get information about the new
	 * task
	 *@param taskInformation - the input from user that specifies the task
	 *@return new task obejct created based on the input from user.
	 */
	static TaskData createNewTask(String taskInformation) {
		String description = DescriptionParser.getDescription(taskInformation);
		
		DateParser dp = new DateParser();
		ArrayList<String> date = dp.extractDate(taskInformation);
		
		ArrayList<String> time = TimeParser.extractTime(taskInformation);
		
		String taskType = TaskTypeParser.getTaskType();
		
		String deadline = new String("-");
		String startDateTime = new String("-");
		String endDateTime = new String("-");
		switch (taskType) {
			case "deadline":
				deadline = date.get(0) + " " + time.get(0);
				break;
			case "time task":
				startDateTime = date.get(0)+ " " + time.get(1);
				endDateTime = date.get(1) + " " + time.get(0);
				break;
			case "floating task":
				break;
			default:
				break;
		}

		TaskData newTask = new TaskData(taskType, description, startDateTime, endDateTime, deadline, "undone");
		return newTask;
	}
}

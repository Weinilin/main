
package logic;

import java.util.ArrayList;
import java.io.File;

import database.Memory;
import parser.DateParser;
import parser.DescriptionParser;
import parser.TaskTypeParser;
import parser.TimeParser;
import application.Task;

/**
 * All handlers in Logic (except undo) shall extend this class so that all
 * handlers have functions of execute() and getHelp()
 */
abstract class CommandHandler {

	File configure;
	Memory memory = Memory.getInstance();
	/**
	 * get all the aliases acceptable to the command such that
	 * they can invoke the handler
	 * @return string that contains the command 
	 */
	abstract protected ArrayList<String> getAliases();
	
	/**
	 * execute the command based on the input from user (such as "add",
	 * "delete", etc) and the parameter for executing the command. Return
	 * the feedback as a String object for now.
	 *@param command - command extracted from user input
	 *@param parameter - parameter for executing the command based on user input
	 *@return feedback String to UI after each execution of the command
	 */
	abstract String execute(String command, String parameter, ArrayList<Task> taskList);
	
	/**
	 * get help String for each of the commands when user types "[command] help"
	 */
	abstract public String getHelp();
	
	/**
	 * creates a new task by a string containing the information of the task.
	 * It will call the respective parsers to get information about the new
	 * task
	 *@param taskInformation - the input from user that specifies the task
	 *@return new task object created based on the input from user.
	 */
	static Task createNewTask(String taskInformation) {
		String description = DescriptionParser.getDescription(taskInformation);
		assert (description.trim() != ""); // ensure that the task has some description for it
		
		ArrayList<String> date = DateParser.extractDate(taskInformation);
		
		ArrayList<String> time = TimeParser.extractTime(taskInformation);
		
		String taskType = TaskTypeParser.getTaskType(taskInformation);
		
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

		Task newTask = new Task(taskType, description, startDateTime, endDateTime, deadline, "undone");
		return newTask;
	}
}

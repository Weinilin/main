
package logic;

import java.util.ArrayList;
import java.io.File;

import database.Memory;
import parser.DateParser;
import parser.DescriptionParser;
import parser.TaskTypeParser;
import parser.TimeParser;
import parser.DateTimeParser;
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
		DescriptionParser descriptionParser = new DescriptionParser(taskInformation);
		String description = descriptionParser.getDescription();
		assert (description.trim() != ""); // ensure that the task has some description for it
				
		TaskTypeParser ttp = new TaskTypeParser(taskInformation);
		String taskType = ttp.getTaskType();

		DateTimeParser dtp = new DateTimeParser(taskInformation);
		String deadline = dtp.getDeadlineDate() + " " + dtp.getDeadlineTime();
		String startDateTime = dtp.getStartDate() + " " + dtp.getStartTime();
		String endDateTime = dtp.getEndDate() + " " + dtp.getEndTime();

		Task newTask = new Task(taskType, description, startDateTime, endDateTime, deadline, "undone");
		return newTask;
	}
}

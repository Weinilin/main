/* 
 * @author A0114463M
 */
package logic;

import java.util.ArrayList;

import parser.DateParser;
import parser.DescriptionParser;
import parser.TaskTypeParser;
import parser.TimeParser;
import application.TaskData;
import database.Memory;

/**
 *  handle adding a task to the task list
 *  and write the changes to database if adding is
 *  success
 */
public class AddHandler {

	private Memory memory;
	
	protected AddHandler(Memory memory) {
		this.memory = memory;
	}
	/**
	 * add a new task to TaskList by input from user
	 * 
	 * @param taskInformation - the parameters user given
	 * @param taskList - list of tasks
	 * @return - true if successfully added
	 */
	protected boolean addTask(String taskInformation) {
		TaskData newTask = createNewTask(taskInformation);
		if (memory.addTask(newTask)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * add a new task to tasklist by a given TaskData object
	 * 
	 * @param newTask
	 * @param taskList
	 * @return true if success
	 */
	protected boolean addTask(TaskData newTask) {
		return memory.addTask(newTask);
	}
	
	/**
	 * parsing and create a new task
	 * @param taskInformation
	 * @return new taskdata
	 */
	protected static TaskData createNewTask(String taskInformation) {
		String description = DescriptionParser.getDescription(taskInformation);
		
		DateParser dp = new DateParser();
		ArrayList<String> date = dp.extractDate(taskInformation);
		
		TimeParser tp = new TimeParser();
		ArrayList<String> time = tp.extractTime(taskInformation);
		
		String taskType = TaskTypeParser.extractTaskType(time);
		
		String deadline = new String("-");
		String startDateTime = new String("-");
		String endDateTime = new String("-");
		switch (taskType) {
			case "deadline":
				deadline = date.get(0) + " " + time.get(0);
				break;
			case "time task":
				startDateTime = date.get(0)+ " " + time.get(0);
				endDateTime = date.get(1) + " " + time.get(1);
				break;
			case "floating task":
				break;
			default:
				break;
		}

		TaskData newTask = new TaskData(taskType, description, startDateTime, endDateTime, deadline, "undone");
		return newTask;
	}
	
	
	
	protected String getHelp() {
		return "add <task informatino>\n to TaskManager";
	}
}

/* 
 * @author A0114463M
 */
package logic;

import java.util.ArrayList;

import parser.DateParser;
import parser.DescriptionParser;
import parser.TaskTypeParser;
import parser.TimeParser;
import application.Task;
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
	protected boolean addTask(String taskInformation) {//shouldn't this be userinput
		Task newTask = createNewTask(taskInformation);
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
	protected boolean addTask(Task newTask) {
		return memory.addTask(newTask);
	}
	
	/**
	 * parse and create a new task
	 * 
	 * @param taskInformation - information to be included in the task
	 * @return new taskdata
	 */
	protected static Task createNewTask(String taskInformation) {//shouldn't this be userinput?
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

		Task newTask = new Task(taskType, description, startDateTime, endDateTime, deadline, "undone");
		return newTask;
	}
	
	
	
	protected String getHelp() {
		return "add <task informatino>\n to TaskManager";
	}
}

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
	 * @return - String of new task
	 */
	protected String addTask(String taskInformation) {
		TaskData newTask = CommandHandler.createNewTask(taskInformation);
		assert (newTask != null);	// a non empty task is created
		if (memory.addTask(newTask)) {
			return newTask.toString();
		} 
		else {
			// It shall never come here
			return null;
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
	

	
	protected String getHelp() {
		return "add <task informatino>\n to TaskManager";
	}
}

/* 
 * @author A0114463M
 */
package handler;

import application.TaskData;

/**
 *  handle adding a task to the task list
 *  and write the changes to database if adding is
 *  success
 */
public class AddHandler {

	public AddHandler() {
		
	}
	/**
	 * add a new task to TaskList by input from user
	 * 
	 * @param taskInformation - the parameters user given
	 * @param taskList - list of tasks
	 * @return - true if successfully added
	 */
	public boolean addTask(String taskInformation, TaskList taskList) {
		// parsing the parameters of the taskData
		
		//
		TaskData newTask = createNewTask(taskInformation);
		if (taskList.addTask(newTask)) {
			// write changes to file
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
	protected boolean addTask(TaskData newTask, TaskList taskList) {
		return taskList.addTask(newTask);
	}
	
	/**
	 * parsing and create a new task
	 * @param taskInformation
	 * @return new taskdata
	 */
	protected TaskData createNewTask(String taskInformation) {
		
		return null;
	}
	public String getHelp() {
		return "add <task informatino>\n to TaskManager";
	}
}

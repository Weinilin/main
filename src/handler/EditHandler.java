/* 
 * @author A0114463M
 */
package handler;

import application.TaskData;

/**
 * Modify a task by invoking remove the task intended to remove and 
 * add the new task
 */
public class EditHandler {

	protected EditHandler() {
		
	}
	/**
	 * update a task by removing the task at index and 
	 * add the new task by the new task information
	 * 
	 * @param taskInformation
	 * @param taskList
	 * @return
	 */
	protected boolean editTask(String taskInformation, TaskList taskList) {
		DeleteHandler dh = new DeleteHandler();
		AddHandler ah = new AddHandler();
		TaskData oldTask = new TaskData();
		TaskData newTask = ah.createNewTask(taskInformation);	
		try  {
			oldTask = dh.deleteTask(taskInformation, taskList);
			ah.addTask(newTask, taskList);
		} catch (IndexOutOfBoundsException iob) {
			return false;
		}
		
		return true;
	}
}

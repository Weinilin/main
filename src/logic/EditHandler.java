/* 
 * @author A0114463M
 */
package logic;

import database.Memory;
import application.TaskData;

/**
 * Modify a task by invoking remove the task intended to remove and 
 * add the new task
 */
public class EditHandler {

	private Memory memory;
	
	protected EditHandler(Memory memory) {
		this.memory = memory;
	}
	/**
	 * update a task by removing the task at index and 
	 * add the new task by the new task information
	 * 
	 * @param taskInformation
	 * @param taskList
	 * @return true if success deletion
	 * @throws IndexOutOfBoundsException if the number entered is out of range or invalid
	 */
	protected boolean editTask(String taskInformation) throws IndexOutOfBoundsException {
		DeleteHandler dh = new DeleteHandler(memory);
		AddHandler ah = new AddHandler(memory);
		TaskData oldTask = new TaskData();
		TaskData newTask = CommandHandler.createNewTask(taskInformation.substring(taskInformation.indexOf(" ")));	
		try  {
			oldTask = dh.deleteTask(taskInformation);
			ah.addTask(newTask);
		} catch (IndexOutOfBoundsException iob) {
			return false;
		}
		
		return true;
	}
}

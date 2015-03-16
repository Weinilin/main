/*
 *  @author A0114463Ms
 */
package logic;

import database.Memory;
import application.TaskData;
import parser.IndexParser;

/**
 * remove a task from TaskList 
 *
 */
public class DeleteHandler {

	private Memory memory;
	
	protected DeleteHandler(Memory memory) {
		this.memory = memory;
	}
	
	/**
	 * remove a task from taskList
	 * 
	 * @param taskInformation - parameter user given
	 * @param taskList
	 * @return removed taskdata if success, null if no legal index entered
	 * @throws IndexOutOfBoundsException if the index entered is larger then the size
	 */
	protected TaskData deleteTask(String taskInformation) throws IndexOutOfBoundsException  {
		IndexParser ip = new IndexParser();
		TaskData removedTask = new TaskData();
		int index = ip.getIndex(taskInformation);
		try {
			removedTask = memory.deleteTask(index);
		} catch (IndexOutOfBoundsException iob) {
			return removedTask;
		} 
	
		return removedTask;
	}
}

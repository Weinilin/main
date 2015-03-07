
package logic;

import java.util.ArrayList;
import java.util.List;

import application.TaskData;

/**
 * collects TaskData and manage the TaskDatas
 */

public class TaskList {

	private List<TaskData> taskList = new ArrayList<TaskData>();
	private int size = 0;
	
	protected TaskList() {
		
	}
	
	/**
	 * insert a new task to the list, returns true if successfully
	 * added
	 * 
	 * @param newTask - new task created
	 * @return true - if the operation is success.
	 */
	protected boolean addTask(TaskData newTask) {
		size++;
		return taskList.add(newTask);	
	}
	
	/**
	 * try to remove a task by index from the list
	 * 
	 * @param index - index of task want to be deleted
	 * @return removedTask - deleted Task
	 * @throws IndexOutOfBoundsException - if index if out of range of the list
	 */
	protected TaskData deleteTask(int index) throws IndexOutOfBoundsException {
		TaskData removedTask = new TaskData();
		try {
			removedTask = taskList.remove(index);
			size--;
		} catch (IndexOutOfBoundsException iob) {
			return null;
		}
		return removedTask;
	}
	
	/**
	 * search the list by a keyword
	 * 
	 * @param keyword
	 * @return result arraylist containing the index that contains the keyword
	 */
	protected String searchTask(String keyword) {
		String result = new String();
		for (TaskData task: taskList) {
			if (task.getDescription().contains(keyword)) {
				result += task.toString();
			}
		}
		return result;
	}
	
	/**
	 * display all the tasks in the list
	 * @return formatted taskData
	 */
	protected String displayTask() {
		String taskListString = new String(); 
		for (TaskData td: taskList) {
			taskListString += td.toString();
		}
			
		return taskListString;
	}
	
	/**
	 * display the selected taskData by the an arraylist of index given
	 * 
	 * @param indexOfDisplay - ArrayList that stores the indexes wish to display
	 * @return formatted taskData
	 */
	protected String displayTask(ArrayList<Integer> indexOfDisplay) {
		String taskListString = new String(); 
		for (int i: indexOfDisplay) {
			taskListString += taskList.get(i).toString();
		}
		
		return taskListString;
	}
	
	protected int getSize() {
		return size;
	}
}

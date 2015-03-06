
package handler;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

import application.TaskData;

/**
 * collects TaskData and make changes to the collection
 */
public class TaskList {

	private List<TaskData> taskList = new ArrayList<TaskData>();
	private int size = 0;
	
	public TaskList() {
		
	}
	
	/**
	 * insert a new task to the list, returns true if successfully
	 * added
	 * 
	 * @param newTask - new task created
	 * @return true - if the operation is success.
	 */
	public boolean addTask(TaskData newTask) {
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
	public TaskData deleteTask(int index) throws IndexOutOfBoundsException {
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
	public ArrayList<Integer> searchTask(String keyword) {
		ArrayList<Integer> foundTaskIndex = new ArrayList<Integer>();
		int indexCounter = 1;
		for (TaskData task: taskList) {
			if (task.getDescription().contains(keyword)) {
				foundTaskIndex.add(indexCounter);
			}
			indexCounter++;
		}
		return foundTaskIndex;
	}
	
	/**
	 * display all the tasks in the list
	 * @return formatted taskData
	 */
	public String displayTask() {
		String taskListString = new String(); 
		for (TaskData td: taskList) {
			taskListString += td.toString();
		}
			
		return taskListString;
	}
	
	/**
	 * displaythe selected taskData by the an arraylist of index given
	 * @param indexOfDisplay - ArrayList that stores the indexes wish to display
	 * @return formatted taskData
	 */
	public String displayTask(ArrayList<Integer> indexOfDisplay) {
		String taskListString = new String(); 
		for (int i: indexOfDisplay) {
			taskListString += taskList.get(i).toString();
		}
		
		return taskListString;
	}
}

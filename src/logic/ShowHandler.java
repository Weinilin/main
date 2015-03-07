package logic;

import java.util.ArrayList;

import database.Memory;
import application.TaskData;

/**
 * showing all tasks in the taskList by passing no
 * parameters OR search for tasks containing the 
 * keyword
 */
public class ShowHandler {

	private Memory memory;
	
	protected ShowHandler(Memory memory) {
		this.memory = memory;
	}
	
	/**
	 * show all contents in taskList
	 * 
	 * @param taskList - list to be shown
	 * @return String of all tasks, no task message will be shown if 
	 * 		   the list is empty
	 */
	protected String showTask() {
		String result = new String();
		if (memory.getTaskList().size() == 0) {
			result = "There is no task.";
		}
		else {
			ArrayList<TaskData> task = memory.getTaskList();
			for (TaskData td: task) {
				result += td.toString() + "\n";
			}
		}
		
		return result;
	}
	
	/**
	 * show tasks containing the keyword
	 * 
	 * @param index - arraylist storing tasks to be shown
	 * @param taskList - taskList to be shown
	 * @return formatted string of tasks, message if no task if found
	 */
	protected String showTask(String keyword) {
		int i = 1;
		String result = new String();
		ArrayList<TaskData> searchList = memory.searchTask(keyword);
		if (searchList.isEmpty()) {
			return "No task containing " + keyword +"\n";
		}
		else {
			for (TaskData td: searchList) {
				result += i + ". \n" + td.toString() + "\n";
				i++;
			}
			return result;
		}
	}
}

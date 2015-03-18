/*
 * @author A0113966Y
 */

package database;

import application.Task;
import application.TaskDataComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Memory acts as a facade between LogicController and Database.
 * LogicController makes changes to the taskList stored in Memory.
 * Memory writes these changes to the Database.
 * 
 * @author A0113966
 *
 */
public class Memory {

	private final String DONE = "done";
	
	private ArrayList<Task> taskList = new ArrayList<Task>();
	private static Memory memory;
	
	private Memory() {
		Database database = Database.getInstance();
		initMemory(database);
	}
	
	public static Memory getInstance() {
		if (memory == null) {
			memory = new Memory();
		}
		return memory;
	}
	
	/**
	 * insert a new task to the list, returns true if successfully
	 * added
	 * 
	 * @param newTask - new task created
	 * @return 
	 */
	public boolean addTask(Task newTask) {
		taskList.add(newTask);
		assert isTaskAdded(newTask);
		sortTaskList();
		writeToDatabase();
		return true;
	}
	
	private void writeToDatabase() {
		Database database = Database.getInstance();
		database.writeToDatabase(taskList);
	}
	
	/**
	 * try to remove a task by index from the list
	 * 
	 * @param index - index of task want to be deleted
	 * @return removedTask - deleted Task
	 * @throws IndexOutOfBoundsException - if index if out of range of the list
	 */
//	public Task deleteTask(int index) throws IndexOutOfBoundsException {
//		Task removedTask;
//		try {
//			removedTask = taskList.remove(index - 1);
//		} catch (IndexOutOfBoundsException iob) {
//			return null;
//		}
//		sortTaskList();
//		writeToDatabase();
//		return removedTask;
//	}
	
	public Task removeTask(Task removedTask) {
		for (int i = 0; i < taskList.size(); i++) {
			Task currentTask = taskList.get(i);
			if (currentTask == removedTask) {
				taskList.remove(i);
			}
		}
		assert isTaskRemoved(removedTask);
		sortTaskList();
		writeToDatabase();
		return removedTask;
	}
	
	public ArrayList<Task> deleteAll() {
		ArrayList<Task> deletedTaskList = taskList;
		taskList = new ArrayList<Task>();
		assert isEmptyTaskList();
		writeToDatabase();
		return deletedTaskList;
	}
	
	/**
	 * search the list by a keyword
	 * 
	 * @param keyword
	 * @return result arraylist containing the index that contains the keyword
	 */
	public ArrayList<Task> searchDescription(String keyword) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getDescription().contains(keyword)) {
				searchList.add(task);
			}
		}
		assert isCorrectKeywordSearchList(searchList, keyword);
		return searchList;
	}
	
	public ArrayList<Task> searchStatus(String status) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getStatus().contains(status)) {
				searchList.add(task);
			}
		}
		assert isCorrectStatusSearchList(searchList, status);
		return searchList;
	}
	
	public void editDeadline(int index, String newDeadline) {
		Task task = taskList.get(index - 1);
		task.setDeadline(newDeadline);
		sortTaskList();
		writeToDatabase();
	}
	
	public void editTime(int index, String newStartDateTime, String newEndDateTime) {
		Task task = taskList.get(index - 1);
		task.setStartDateTime(newStartDateTime);
		task.setEndDateTime(newEndDateTime);
		sortTaskList();
		writeToDatabase();
	}
	
	public void editDescription(int index, String newDescription) {
		Task task = taskList.get(index - 1);
		task.setDescription(newDescription);
		sortTaskList();
		writeToDatabase();
	}
	
	private void sortTaskList() {
		Collections.sort(taskList, new TaskDataComparator());
	}
	
	public void markDone(int index) {
		Task task = taskList.get(index - 1);
		task.setStatus(DONE);
		sortTaskList();
		writeToDatabase();
	}
	
	public void editTaskType(int index, String newTaskType) {
		Task task = taskList.get(index - 1);
		task.setTaskType(newTaskType);
		sortTaskList();
		writeToDatabase();
	}
	
	private void initMemory(Database database) {
		taskList = database.readDatabase();
	}
	
	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	public void display() {
		for (int i = 0; i < taskList.size(); i++) {
			System.out.print(taskList.get(i));
			System.out.println();
		}
	}
	
	private boolean isCorrectKeywordSearchList(ArrayList<Task> searchList, String keyword) {
		for (int i = 0; i < searchList.size(); i++) {
			Task currentTask = searchList.get(i);
			if (!currentTask.getDescription().contains(keyword)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isCorrectStatusSearchList(ArrayList<Task> searchList, String status) {
		for (int i = 0; i < searchList.size(); i++) {
			Task currentTask = searchList.get(i);
			if (!currentTask.getStatus().equals(status)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isTaskRemoved(Task removedTask) {
		for (int i = 0; i < taskList.size(); i++) {
			Task currentTask = taskList.get(i);
			if (currentTask == removedTask) {
				return false;
			}
		}
		return true;
	}

	private boolean isEmptyTaskList() {
		if (taskList.size() == 0) {
			return true;
		}
		return false;
	}
	
	private boolean isTaskAdded(Task newTask) {
		for (int i = 0; i < taskList.size(); i++) {
			Task currentTask = taskList.get(i);
			if (currentTask == newTask) {
				return true;
			}
		}
		return false;
	}
	
	
	
//	public static void main(String[] args) {
//		Database db = new Database();
//		Memory memory = new Memory(db);
//
//		
//		ArrayList<String> taskData1 = new ArrayList<String>();
//		String taskType = "time task";
//		taskData1.add(taskType);
//		String description = "task 1";
//		taskData1.add(description);
//		String startDateTime = "04/04/2015 17:00";
//		taskData1.add(startDateTime);
//		String endDateTime = "04/04/2015 18:00";
//		taskData1.add(endDateTime);
//		String deadline = "-";
//		taskData1.add(deadline);
//		String status = "not done";
//		taskData1.add(status);
//		TaskData newTaskData1 = new TaskData(taskData1);
//		memory.addTask(newTaskData1);
//		
//		ArrayList<String> taskData2 = new ArrayList<String>();
//		taskType = "time task";
//		taskData2.add(taskType);
//		description = "task 2";
//		taskData2.add(description);
//		startDateTime = "04/04/2015 14:00";
//		taskData2.add(startDateTime);
//		endDateTime = "04/04/2015 15:00";
//		taskData2.add(endDateTime);
//		deadline = "-";
//		taskData2.add(deadline);
//		status = "not done";
//		taskData2.add(status);
//		TaskData newTaskData2 = new TaskData(taskData2);
//		memory.addTask(newTaskData2);
//		
//		ArrayList<String> taskData3 = new ArrayList<String>();
//		taskType = "time task";
//		taskData3.add(taskType);
//		description = "task 3";
//		taskData3.add(description);
//		startDateTime = "26/07/2015 19:00";
//		taskData3.add(startDateTime);
//		endDateTime = "26/07/2015 20:00";
//		taskData3.add(endDateTime);
//		deadline = "-";
//		taskData3.add(deadline);
//		status = "not done";
//		taskData3.add(status);
//		TaskData newTaskData3 = new TaskData(taskData3);
//		memory.addTask(newTaskData3);
//		
//		ArrayList<String> taskData4 = new ArrayList<String>();
//		taskType = "deadline";
//		taskData4.add(taskType);
//		description = "task 4";
//		taskData4.add(description);
//		startDateTime = "-";
//		taskData4.add(startDateTime);
//		endDateTime = "-";
//		taskData4.add(endDateTime);
//		deadline = "05/05/2015 04:00";
//		taskData4.add(deadline);
//		status = "not done";
//		taskData4.add(status);
//		TaskData newTaskData4 = new TaskData(taskData4);
//		memory.addTask(newTaskData4);
//		
//		ArrayList<String> taskData5 = new ArrayList<String>();
//		taskType = "deadline";
//		taskData5.add(taskType);
//		description = "task 5";
//		taskData5.add(description);
//		startDateTime = "-";
//		taskData5.add(startDateTime);
//		endDateTime = "-";
//		taskData5.add(endDateTime);
//		deadline = "01/01/2015 09:00";
//		taskData5.add(deadline);
//		status = "not done";
//		taskData5.add(status);
//		TaskData newTaskData5 = new TaskData(taskData5);
//		memory.addTask(newTaskData5);
//		
//		ArrayList<String> taskData6 = new ArrayList<String>();
//		taskType = "floating task";
//		taskData6.add(taskType);
//		description = "task 6";
//		taskData6.add(description);
//		startDateTime = "-";
//		taskData6.add(startDateTime);
//		endDateTime = "-";
//		taskData6.add(endDateTime);
//		deadline = "-";
//		taskData6.add(deadline);
//		status = "not done";
//		taskData6.add(status);
//		TaskData newTaskData6 = new TaskData(taskData6);
//		memory.addTask(newTaskData6);
//		
//		ArrayList<String> taskData7 = new ArrayList<String>();
//		taskType = "floating task";
//		taskData7.add(taskType);
//		description = "abukhari";
//		taskData7.add(description);
//		startDateTime = "-";
//		taskData7.add(startDateTime);
//		endDateTime = "-";
//		taskData7.add(endDateTime);
//		deadline = "-";
//		taskData7.add(deadline);
//		status = "not done";
//		taskData7.add(status);
//		TaskData newTaskData7 = new TaskData(taskData7);
//		memory.addTask(newTaskData7);
//		
//		memory.display();
//		
//
//		
//	}
}

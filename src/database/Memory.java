/*
 * @author A0113966Y
 */

package database;

import application.Task;
import application.TaskComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.logging.Level;
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
	
	private static final Logger memoryLogger = Logger.getLogger(Memory.class.getName());
	
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
		memoryLogger.entering(getClass().getName(), "adding a new task to taskList");
		assert isValidTask(newTask);
		if (taskList.add(newTask)) {
			memoryLogger.log(Level.FINE, "add success");
		} else {
			memoryLogger.log(Level.SEVERE, "Error adding new task!");
			throw new Error("Fatal error! Unable to add Task");
		}
		sortTaskList();
		writeToDatabase();
		memoryLogger.exiting(getClass().getName(), "adding a new task to taskList");
		return true;
	}
	
	private void writeToDatabase() {
		memoryLogger.entering(getClass().getName(), "writing new task to database");
		Database database = Database.getInstance();
		if (database.writeToDatabase(taskList)) {
			memoryLogger.log(Level.FINE, "write success");
		} else {
			memoryLogger.log(Level.SEVERE, "error writing task to database");
		}
		memoryLogger.exiting(getClass().getName(), "writing new task to database");
	}
	
	
	public Task removeTask(Task removedTask) {
		memoryLogger.entering(getClass().getName(), "removing a task from database");
		assert isValidTask(removedTask);
		for (int i = 0; i < taskList.size(); i++) {
			Task currentTask = taskList.get(i);
			if (currentTask == removedTask) {
				if (taskList.remove(i) != null) {
					memoryLogger.log(Level.FINE, "remove success");
				} else {
					memoryLogger.log(Level.SEVERE, "task cannot be found in taskList");
				}
			}
		}
		sortTaskList();
		writeToDatabase();
		memoryLogger.exiting(getClass().getName(), "removing a task from database");
		return removedTask;
	}
	
	public ArrayList<Task> removeAll() {
		memoryLogger.entering(getClass().getName(), "removing all tasks from taskList");
		ArrayList<Task> deletedTaskList = taskList;
		taskList = new ArrayList<Task>();
		writeToDatabase();
		memoryLogger.log(Level.FINE, "remove success");
		memoryLogger.exiting(getClass().getName(), "removing all tasks from taskList");

		return deletedTaskList;
	}
	
	/**
	 * search the list by a keyword
	 * 
	 * @param keyword
	 * @return result arraylist containing the index that contains the keyword
	 */
	public ArrayList<Task> searchDescription(String keyword) {
		memoryLogger.entering(getClass().getName(), "searching task containing keyword");
		assert isValidKeyword(keyword);
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getDescription().contains(keyword)) {
				searchList.add(task);
			}
		}
		memoryLogger.log(Level.FINE, "search success");
		memoryLogger.exiting(getClass().getName(), "searching task containing keyword");

		return searchList;
	}
	
	public ArrayList<Task> searchStatus(String status) {
		memoryLogger.entering(getClass().getName(), "searching task of the specified status");
		assert isValidStatus(status);
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getStatus().contains(status)) {
				searchList.add(task);
			}
		}
		memoryLogger.log(Level.FINE, "search success");
		memoryLogger.entering(getClass().getName(), "searching task of the specified status");
		return searchList;
	}
	
	public void editDeadline(int index, String newDeadline) {
		memoryLogger.entering(getClass().getName(), "editing deadline");
		assert isValidIndex(index);
		Task task = taskList.get(index - 1);
		task.setDeadline(newDeadline);
		sortTaskList();
		writeToDatabase();
		memoryLogger.log(Level.FINE, "edit success");
		memoryLogger.exiting(getClass().getName(), "editing deadline");

	}
	
	public void editTime(int index, String newStartDateTime, String newEndDateTime) {
		memoryLogger.entering(getClass().getName(), "editing time");
		assert isValidIndex(index);
		Task task = taskList.get(index - 1);
		task.setStartDateTime(newStartDateTime);
		task.setEndDateTime(newEndDateTime);
		sortTaskList();
		writeToDatabase();
		memoryLogger.log(Level.FINE, "edit success");
		memoryLogger.exiting(getClass().getName(), "editing time");

	}
	
	public void editDescription(int index, String newDescription) {
		memoryLogger.entering(getClass().getName(), "editing description");
		assert isValidIndex(index);
		Task task = taskList.get(index - 1);
		task.setDescription(newDescription);
		sortTaskList();
		writeToDatabase();
		memoryLogger.log(Level.FINE, "edit success");
		memoryLogger.exiting(getClass().getName(), "editing description");

	}
	
	private void sortTaskList() {
		Collections.sort(taskList, new TaskComparator());
	}
	
	public void markDone(int index) {
		memoryLogger.entering(getClass().getName(), "marking task");
		assert isValidIndex(index);
		Task task = taskList.get(index - 1);
		task.setStatus(DONE);
		sortTaskList();
		writeToDatabase();
		memoryLogger.log(Level.FINE, "mark success");
		memoryLogger.exiting(getClass().getName(), "marking task");
	}
	
	public void editTaskType(int index, String newTaskType) {
		memoryLogger.entering(getClass().getName(), "editing taskType");
		assert isValidIndex(index);
		Task task = taskList.get(index - 1);
		task.setTaskType(newTaskType);
		sortTaskList();
		writeToDatabase();
		memoryLogger.log(Level.FINE, "edit success");
		memoryLogger.exiting(getClass().getName(), "marking task");

	}
	
	private void initMemory(Database database) {
		memoryLogger.entering(getClass().getName(), "initializing memory");
		taskList = database.readDatabase();
		memoryLogger.log(Level.FINE, "successfully initializing memory");
		memoryLogger.exiting(getClass().getName(), "initializing memory");

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
	
	public boolean contains(Task task) {
		return taskList.contains(task);
	}
	
	public boolean isValidTask(Task newData) {
		return newData != null;
	}
	
	public boolean isValidKeyword(String keyword) {
		return keyword != null;
	}
	
	public boolean isValidStatus(String status) {
		return status != null;
	}
	
	public boolean isValidIndex(int index) {
		return (index > 0 && index <= taskList.size());
	}

}

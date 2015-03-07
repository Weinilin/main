package database;

import application.TaskData;
import application.TaskDataComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Memory {

	public final String DONE = "done";
	
	private ArrayList<TaskData> taskList = new ArrayList<TaskData>();
	
	public Memory() {
		initMemory();
	}
	
	/**
	 * insert a new task to the list, returns true if successfully
	 * added
	 * 
	 * @param newTask - new task created
	 * @return 
	 */
	public boolean addTask(TaskData newTask) {
		taskList.add(newTask);	
		Collections.sort(taskList, new TaskDataComparator());
		Database.writeToDatabase(taskList);
		return true;
	}
	
	/**
	 * try to remove a task by index from the list
	 * 
	 * @param index - index of task want to be deleted
	 * @return removedTask - deleted Task
	 * @throws IndexOutOfBoundsException - if index if out of range of the list
	 */
	public TaskData deleteTask(int index) throws IndexOutOfBoundsException {
		TaskData removedTask;
		try {
			removedTask = taskList.remove(index - 1);
		} catch (IndexOutOfBoundsException iob) {
			return null;
		}
		Collections.sort(taskList, new TaskDataComparator());
		Database.writeToDatabase(taskList);
		return removedTask;
	}
	
	public ArrayList<TaskData> deleteAll() {
		ArrayList<TaskData> deletedTaskList = new ArrayList<TaskData>();
		taskList = new ArrayList<TaskData>();
		Database.writeToDatabase(taskList);
		return deletedTaskList;
	}
	
	/**
	 * search the list by a keyword
	 * 
	 * @param keyword
	 * @return result arraylist containing the index that contains the keyword
	 */
	public ArrayList<TaskData> searchTask(String keyword) {
		ArrayList<TaskData> searchList = new ArrayList<TaskData>();
		for (int i = 0; i < taskList.size(); i++) {
			TaskData task = taskList.get(i);
			if (task.getDescription().contains(keyword)) {
				searchList.add(task);
			}
		}
		return searchList;
	}
	
	public void editDeadline(int index, String newDeadline) {
		TaskData task = taskList.get(index - 1);
		task.setDeadline(newDeadline);
		Collections.sort(taskList, new TaskDataComparator());
		Database.writeToDatabase(taskList);
	}
	
	public void editTime(int index, String newStartDateTime, String newEndDateTime) {
		TaskData task = taskList.get(index - 1);
		task.setStartDateTime(newStartDateTime);
		task.setEndDateTime(newEndDateTime);
		Collections.sort(taskList, new TaskDataComparator());
		Database.writeToDatabase(taskList);
	}
	
	public void editDescription(int index, String newDescription) {
		TaskData task = taskList.get(index - 1);
		task.setDescription(newDescription);
		Collections.sort(taskList, new TaskDataComparator());
		Database.writeToDatabase(taskList);
	}
	
	public void sortTaskList() {
		Collections.sort(taskList, new TaskDataComparator());
	}
	
	public void markDone(int index) {
		TaskData task = taskList.get(index - 1);
		task.setStatus(DONE);
	}
	
	public void editTaskType(int index, String newTaskType) {
		TaskData task = taskList.get(index - 1);
		task.setTaskType(newTaskType);
		Collections.sort(taskList, new TaskDataComparator());
		Database.writeToDatabase(taskList);
	}
	
	private void initMemory() {
		taskList = Database.readDatabase();
	}
	
	public ArrayList<TaskData> getTaskList() {
		return taskList;
	}
	
	public void display() {
		for (int i = 0; i < taskList.size(); i++) {
			taskList.get(i).displayTask();
		}
	}
	
	public static void main(String[] args) {
		Memory memory = new Memory();
		Scanner sc = new Scanner(System.in);

		while (!sc.nextLine().equals("exit")) {
			ArrayList<String> taskData = new ArrayList<String>();
			System.out.println("Enter Task Type: ");
			String taskType = sc.nextLine();
			taskData.add(taskType);
			System.out.println("Enter Task Description: ");
			String description = sc.nextLine();
			taskData.add(description);
			System.out.println("Enter Start Date Time: ");
			String startDateTime = sc.nextLine();
			taskData.add(startDateTime);
			System.out.println("Enter End Date Time: ");
			String endDateTime = sc.nextLine();
			taskData.add(endDateTime);
			System.out.println("Enter Deadline");
			String deadline = sc.nextLine();
			taskData.add(endDateTime);
			System.out.println("Enter status: ");
			String status = sc.nextLine();
			taskData.add(status);
			TaskData newTaskData = new TaskData(taskData);
			memory.addTask(newTaskData);
		}
		memory.display();
		System.out.println("---");
		memory.deleteTask(2);
		memory.display();

		
	}
}

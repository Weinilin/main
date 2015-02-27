package storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import application.TaskData;

public class Database {
	//should this be static?
	private ArrayList<TaskData> deadlines;
	private ArrayList<TaskData> timeTasks;
	private ArrayList<TaskData> floatingTasks;
	
	public Database() {
		setEnvironment();
	}
	
	public void addTimeTask(TaskData taskData) {
		timeTasks.add(taskData);
		serializeTimeTasksDatabase();
	}
	
	public void addDeadlines(TaskData taskData) {
		deadlines.add(taskData);
		serializeDeadlinesDatabase();
	}
	
	public void addFloatingTask(TaskData taskData) {
		deadlines.add(taskData);
		serializeFloatingTasksDatabase();
	}
	
	//Assume no error for the moment
	public void deleteTimeTask(int taskDataIndex) {
		if (isValidTimeTaskIndex(taskDataIndex)) {
			timeTasks.remove(taskDataIndex);
		} 
	}
	
	//Assume no error for the moment
	public void deleteDeadlines(int taskDataIndex) {
		if (isValidDeadlinesIndex(taskDataIndex)) {
			deadlines.remove(taskDataIndex);
		}
	}
	
	//Assume no error for the moment
	public void deleteFloatingTasks(int taskDataIndex) {
		if (isValidFloatingTasksIndex(taskDataIndex)) {
			deadlines.remove(taskDataIndex);
		}
	}
		
	private boolean isValidTimeTaskIndex(int taskDataIndex) {
		if (taskDataIndex < timeTasks.size() && taskDataIndex >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isValidDeadlinesIndex(int taskDataIndex) {
		if (taskDataIndex < deadlines.size() && taskDataIndex >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isValidFloatingTasksIndex(int taskDataIndex) {
		if (taskDataIndex < deadlines.size() && taskDataIndex >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<TaskData> getDeadlines() {
		return deadlines;
	}
	
	public ArrayList<TaskData> getTimeTasks() {
		return timeTasks;
	}
	
	public ArrayList<TaskData> getFloatingTasks() {
		return deadlines;
	}
	
	public void setEnvironment() {
		initializeMemory();
		readFromExistingDatabase();
	}
	
	public void initializeMemory() {
		deadlines = new ArrayList<TaskData>();
		deadlines = new ArrayList<TaskData>();
		timeTasks = new ArrayList<TaskData>();
	}
	
	public void serializeDatabase() {
		serializeFloatingTasksDatabase();
		serializeDeadlinesDatabase();
		serializeTimeTasksDatabase();
	}

	
	private void serializeFloatingTasksDatabase() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Floating tasks.ser", true);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(deadlines);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("1");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("2");
		}
	}
	
	private void serializeDeadlinesDatabase() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Deadlines.ser");
			ObjectOutputStream out = new AppendingObjectOutputStream(fileOut) {
	            protected void writeStreamHeader() throws IOException {
	                reset();
	            }
	        };
			out.writeObject(deadlines);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("3");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("4");
		}
	}
	
	private void serializeTimeTasksDatabase() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Time tasks.ser", true);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(timeTasks);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("5");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("6");
		}
	}
	
	private void readFromExistingDatabase() {
		readFloatingTasksDatabase();
		readDeadlinesDatabase();
		readTimeTasksDatabase();
	}
	
	private void readFloatingTasksDatabase() {
		
		try {
			FileInputStream fileIn = new FileInputStream("FloatingTasks.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			floatingTasks = (ArrayList<TaskData>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("IOException1 is encoutered!");
			//System.exit(0);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("FloatingTasks.ser does not exist!");
			//System.exit(0);
		}
	}
	
	public void readDeadlinesDatabase() {
		try {
			FileInputStream fileIn = new FileInputStream("Deadlines.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			deadlines = (ArrayList<TaskData>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException2 is encoutered!");
			//System.exit(0);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Deadlines.ser does not exist!");
			//System.exit(0);
		}
	}
	
	private void readTimeTasksDatabase() {
		try {
			FileInputStream fileIn = new FileInputStream("TimeTasks.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			timeTasks = (ArrayList<TaskData>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("IOException3 is encoutered!");
			//System.exit(0);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("TimeTasks.ser does not exist!");
			//System.exit(0);
		}
	}

	public ArrayList<TaskData> statusSearch(String status) {
		ArrayList<TaskData> searchResults = new ArrayList<TaskData>();

		if (isValidStatus(status)) {
			searchResults.addAll(statusSearch(timeTasks, status));
			searchResults.addAll(statusSearch(deadlines, status));
			searchResults.addAll(statusSearch(floatingTasks, status));
		}
		
		return searchResults;
	}
	
	private boolean isValidStatus(String status) {
		if (status.equals("completed")) {
			return true;
		}
		
		if (status.equals("uncompleted")) {
			return true;
		}
		
		return false;
	}


	private ArrayList<TaskData> statusSearch(ArrayList<TaskData> taskList, String statusEnquired) {
		ArrayList<TaskData> searchResults = new ArrayList<TaskData>();
		
		for (int i = 0; i < deadlines.size(); i++) {
			TaskData currentTask = deadlines.get(i);
			String status = currentTask.getStatus();
			
			if (status.equals(statusEnquired)) {
				searchResults.add(currentTask);
			}
		}
		
		return searchResults;
	}
	

	public ArrayList<TaskData> descriptionSearch(String searchedItem) {
		ArrayList<TaskData> searchResults = new ArrayList<TaskData>();
		
		searchResults.addAll(descriptionSearch(timeTasks, searchedItem));
		searchResults.addAll(descriptionSearch(deadlines, searchedItem));
		searchResults.addAll(descriptionSearch(floatingTasks, searchedItem));

		return searchResults;
	}

	private ArrayList<TaskData> descriptionSearch(ArrayList<TaskData> taskList, String searchedItem) {
		ArrayList<TaskData> searchResults = new ArrayList<TaskData>();

		for (int i = 0; i < taskList.size(); i++) {
			TaskData currentTask = taskList.get(i);
			String description = currentTask.getDescription();
			
			if (description.contains(searchedItem)) {
				searchResults.add(currentTask);
			}
		}

		return searchResults;
	}

	

	public ArrayList<TaskData> timeSearch(long startDateTimeInMilliseconds, long endDateTimeInMilliseconds) {
		ArrayList<TaskData> searchResults = new ArrayList<TaskData>();
		
		searchResults.addAll(timeSearch(timeTasks, startDateTimeInMilliseconds, endDateTimeInMilliseconds));
		searchResults.addAll(timeSearch(deadlines, startDateTimeInMilliseconds, endDateTimeInMilliseconds));
		searchResults.addAll(timeSearch(floatingTasks, startDateTimeInMilliseconds, endDateTimeInMilliseconds));

		return searchResults;
		
	}
	
	private ArrayList<TaskData> timeSearch(ArrayList<TaskData> taskList, long lowerBound, long upperBound) {
		ArrayList<TaskData> searchResults = new ArrayList<TaskData>();
		
		for (int i = 0; i < taskList.size(); i++) {
			TaskData currentTask = taskList.get(i);
			long startDateTimeInMilliseconds = currentTask.getStartDateTime().getDateTimeInMilliseconds();

			if (startDateTimeInMilliseconds >= lowerBound || startDateTimeInMilliseconds <= upperBound) {
				searchResults.add(currentTask);
			}
		}

		return searchResults;
	}

}

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
	private ArrayList<TaskData> completedTasks;
	private ArrayList<TaskData> uncompletedTasks;
	
	public Database() {
		setEnvironment();
	}
	
	public void addTask(TaskData taskdata) {
		uncompletedTasks.add(taskdata);
	}
	
	//Assume no error for the moment
	public void deleteCompletedTask(int taskDataIndex) {
		if (completedTasks.contains(taskDataIndex)) {
			completedTasks.remove(taskDataIndex);
		} 
	}
	
	//Assume no error for the moment
	public void deleteUncompletedTask(int taskDataIndex) {
		if (uncompletedTasks.contains(taskDataIndex)) {
			uncompletedTasks.remove(taskDataIndex);
		}
	}
	
	public ArrayList<TaskData> getCompletedTasks() {
		return completedTasks;
	}
	
	public ArrayList<TaskData> getUncompletedTasks() {
		return uncompletedTasks;
	}
	
	public void setEnvironment() {
		initializeMemory();
		readFromExistingDatabase();
	}
	
	public void initializeMemory() {
		completedTasks = new ArrayList<TaskData>();
		uncompletedTasks = new ArrayList<TaskData>();
	}
	
	public void serializeDatabase() {
		serializeCompletedTasksDatabase();
		serializeUncompletedTasksDatabase();
	}

	
	private void serializeCompletedTasksDatabase() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Completed tasks.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(completedTasks);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void serializeUncompletedTasksDatabase() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Uncompleted tasks.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(completedTasks);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFromExistingDatabase() {
		readCompletedTasksDatabase();
		readUncompletedTasksDatabase();
	}
	
	private void readCompletedTasksDatabase() {
		try {
			FileInputStream fileIn = new FileInputStream("Completed tasks.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			completedTasks = (ArrayList<TaskData>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void readUncompletedTasksDatabase() {
		try {
			FileInputStream fileIn = new FileInputStream("Uncompleted tasks.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			uncompletedTasks = (ArrayList<TaskData>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

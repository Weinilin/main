package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.Task;

public class Database {
	private static String databaseLocation = "TaskManagerDatabase.txt" ;
	private static Database database;
	
	private Database() {
		createDatabase();
	}
	
	public static Database getInstance() {
		return database;
	}

	public static boolean setDatabaseLocation(String newDatabaseLocation) {		
		File database = new File(databaseLocation);
		if (database.renameTo(new File(newDatabaseLocation))) {
			databaseLocation = newDatabaseLocation;
			return true;
		} else {
			return false;
		}
	}
	
	
	//this should only be called by logic at the start of the program
	private static void createDatabase() {
    	File database = new File(databaseLocation);
    	if (!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public ArrayList<Task> readDatabase() {
		BufferedReader reader = null;
		ArrayList<Task> taskList = new ArrayList<Task> ();
		
		try{
			reader = new BufferedReader(new FileReader(databaseLocation));
			String currentLine;
			while (reader.ready()) {
				ArrayList<String> unprocessedTask = new ArrayList<String> ();

				for ( int i = 0; i < 6; i++ ) {
					currentLine = reader.readLine();
					String processedLine = currentLine.substring(22).trim();
					unprocessedTask.add(processedLine);
				}
				currentLine = reader.readLine();

				taskList.add(new Task(unprocessedTask));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader(reader);
		}
		
		return taskList;
	}
	
	public void writeToDatabase(ArrayList<Task> taskList) {		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(databaseLocation));

			for (int i = 0; i < taskList.size(); i++) {
				Task task = taskList.get(i);
				writer.write(task.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assert isWrittenToDatabase(taskList);
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void closeReader(BufferedReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isWrittenToDatabase(ArrayList<Task> taskList) {
		ArrayList<Task> databaseTaskList = readDatabase();
		if (databaseTaskList == taskList ) {
			return true;
		}
		return false;
	}
}

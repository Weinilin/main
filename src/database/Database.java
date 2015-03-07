package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.TaskData;

public class Database {
	private static File database;
	private static String databaseName = "TaskManagerDatabase.txt" ;
	
	public Database() {
		createDatabase();
	}

	public static void setDatabaseLocation() {
		
	}
	
	//this should only be called by logic at the start of the program
	private static void createDatabase() {
    	database = new File(databaseName);
    	if (!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public ArrayList<TaskData> readDatabase() {
		BufferedReader reader = null;
		ArrayList<TaskData> taskList = new ArrayList<TaskData> ();
		
		try{
			reader = new BufferedReader(new FileReader(database));
			String currentLine;
			while (reader.ready()) {
				ArrayList<String> unprocessedTask = new ArrayList<String> ();

				for ( int i = 0; i < 6; i++ ) {
					currentLine = reader.readLine();
					String processedLine = currentLine.substring(22).trim();
					unprocessedTask.add(processedLine);
				}
				currentLine = reader.readLine();

				taskList.add(new TaskData(unprocessedTask));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader(reader);
		}
		
		return taskList;
	}
	
	public void writeToDatabase(ArrayList<TaskData> taskList) {		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(database));

			for (int i = 0; i < taskList.size(); i++) {
				TaskData task = taskList.get(i);
				writer.write(task.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
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
	
}

/*
 * @author A0113966Y
 */

package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import application.Task;

/**
 * Database handles the writing and adding of tasks to a text file stored in the disk
 *
 * @author A0113966Y
 *
 */

public class Database {
	private static String databaseLocation;
	private static String configFile = "Configuration.txt";
	private static final ArrayList<Task> EMPTY_TASKLIST = new ArrayList<Task>();
	
	
	private static Database database;
	
	/**
	 * Creates a Database 
	 */
	private Database() {
		createConfigFile();
		readConfigFile();
		createDatabase();
	}
	
	private void createConfigFile() {
		File database = new File(configFile);
    	if (!database.exists()) {
            try {
                database.createNewFile();
                updateConfigFile("Database/TaskManagerDatabase.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	private void readConfigFile() {
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(configFile));
			databaseLocation = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader(reader);
		}
		
	}
	
	
	
	/**
	 * returns the instance of database in this Database
	 * @return database
	 */
	
	public static Database getInstance() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}
	
	/**
	 * Change the location of "TaskManagerDatabase.txt" to a newDatabaseLocation
	 * @param newDatabaseLocation
	 * @return true if location of "TaskManagerDatabase.txt" has been successfully changed or false if 
	 * location of "TaskManager.txt" cannot be changed
	 */

	public boolean relocateDatabase(String newDatabaseFolder) {
	    assert isValidDatabaseFolder(newDatabaseFolder);
		String newDatabaseLocation = appendDatabaseName(newDatabaseFolder);
		File database = new File(databaseLocation);
		if (database.renameTo(new File(newDatabaseLocation))) {
			databaseLocation = newDatabaseLocation;
			updateConfigFile(newDatabaseLocation);
			return true;
		} else {
			return false;
		}
	}
	
	public void changeDatabaseLocation(String newDatabaseLocation) {
	    databaseLocation = newDatabaseLocation;
	    
	    if (readDatabase() == null) {
	        writeToDatabase(null);
	    }
	}

    private boolean isValidDatabaseFolder(String newDatabaseFolder) {
        return newDatabaseFolder != null;
    }
	
	private String appendDatabaseName(String newDatabaseFolder) {
		return newDatabaseFolder + "/TextManagerDatabase.txt";
	}
	
	private boolean updateConfigFile(String newDatabaseLocation) {
		assert newDatabaseLocation != null;

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(configFile));
			writer.write(newDatabaseLocation);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Creates a new Database if database does not exist
	 */
	
	private void createDatabase() {
    	File database = new File(databaseLocation);
    	if (!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * Reads from "TaskManagerDatabase.txt" and converts all the tasks stored in it into an ArrayList
	 * @return taskList containing list of tasks stored in the program from previous use
	 */
	
	public ArrayList<Task> readDatabase() {
		
		return readDatabase(databaseLocation);
	}
	
	private ArrayList<Task> readDatabase(String databaseLocation) {
        BufferedReader reader = null;
        ArrayList<Task> taskList = new ArrayList<Task> ();
        
        try{
            reader = new BufferedReader(new FileReader(databaseLocation));
            String currentLine;
            while (reader.ready()) {
                ArrayList<String> unprocessedTask = new ArrayList<String> ();

                for ( int i = 0; i < 5; i++ ) {
                    currentLine = reader.readLine();
                    String processedLine = currentLine.substring(22).trim();
                    unprocessedTask.add(processedLine);
                }
                currentLine = reader.readLine();

                taskList.add(new Task(unprocessedTask));
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return EMPTY_TASKLIST;
        } catch (IndexOutOfBoundsException iob) {
            //e.printStackTrace();
            return EMPTY_TASKLIST;
        } finally {
            closeReader(reader);
        }
        
        return taskList;
    }
	

	
	/** 
	 * updates "TaskMangerDatabase.txt" with new data from taskList
	 * @param taskList
	 */
	public boolean writeToDatabase(ArrayList<Task> taskList) {		
		assert isValidTaskList(taskList);
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
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
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
	
	private boolean isValidTaskList(ArrayList<Task> taskList) {
		return (taskList != null);
	}
	
	
}

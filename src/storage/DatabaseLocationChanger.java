package storage;

import java.util.ArrayList;

import application.Task;

public class DatabaseLocationChanger {
	private static Database database;
	
	public DatabaseLocationChanger() {
		database = Database.getInstance();
	}
	
	public boolean setDatabaseLocation(String newDatabaseFolder) {
		return database.relocateDatabase(newDatabaseFolder);
	}
	
}

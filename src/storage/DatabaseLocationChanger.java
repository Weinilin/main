package storage;

import java.util.ArrayList;

import application.Task;

public class DatabaseLocationChanger {
	private static Database database;
	
	public DatabaseLocationChanger() {
		database = Database.getInstance();
	}
	
	public void setDatabaseLocation(String newDatabaseFolder) {
		database.setDatabaseLocation(newDatabaseFolder);
	}
	
}

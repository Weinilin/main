package storage;

public class DatabaseLocationChanger {
	private static Database database;
	
	public DatabaseLocationChanger() {
		database = Database.getInstance();
	}
	
	public void setDatabaseLocation(String newDatabaseFolder) {
		database.setDatabaseLocation(newDatabaseFolder);
	}
	
}

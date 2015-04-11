//@author A0113966Y

package storage;

/**
 * DatabaseLocationChanger is used to change the location of a database. It acts as a facade between
 * LogicController and Database
 * 
 * @author A0113966Y
 *
 */
public class DatabaseLocationChanger {
	private static Database database;
	
	public DatabaseLocationChanger() {
		database = Database.getInstance();
	}
	
	public boolean setDatabaseLocation(String newDatabaseFolder) {
		return database.relocateDatabase(newDatabaseFolder);
	}
	
}

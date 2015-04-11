//@author A0113966Y

package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import application.Task;
import application.TaskComparator;

/**
 * Unit Testing for Database
 * 
 * @author A0113966Y
 *
 */

public class DatabaseTest {
    private static final String PATH_EMPTY_STRING = "";
    private static final String PATH_INVALID_LOCATION = "InvalidLocation";
    private static final String PATH_VALID_LOCATION = "TestSuite/SetLocationTest";
    
    private static final ArrayList<Task> EMPTY_TASK_LIST = new ArrayList<Task>();

    
	@Test
	public void test() {
		Database database = Database.getInstance();
		
		/*
		 * Testing for setDatabaseLocation() method using Equivalence Partition
		 * The partition are empty string, valid location and invalid location
		 */
		assertEquals(database.relocateDatabase(PATH_EMPTY_STRING), false);
	    assertEquals(database.relocateDatabase(PATH_INVALID_LOCATION), false);
		assertEquals(database.relocateDatabase(PATH_VALID_LOCATION), true);
		
		 /*
		 * Testing for readDatabase() method using Equivalence Partition
		 * The partition are a valid database file, an corrupted database file, and an empty database file
		 */
		Task task1 = new Task("deadline", "task 1", "- -", "Sat 28/03/2015 21:00", "undone");
		Task task2 = new Task("floating task", "task 2", "- -", "- -", "done");
		Task task3 = new Task("time task", "task 3", "Sun 01/02/2015 01:00", "Mon 02/02/2015 02:00", "undone");
		ArrayList<Task> testTaskList = new ArrayList<Task>();
		testTaskList.add(task1);
	    testTaskList.add(task2);
	    testTaskList.add(task3);
	    Collections.sort(testTaskList, new TaskComparator());
	    
		database.changeDatabaseLocation("TestSuite/DatabaseTest/TestRead.txt");
	    assertEquals(database.readDatabase().toString(), testTaskList.toString());
	
		database.changeDatabaseLocation("TestSuite/DatabaseTest/TestReadCorrupted.txt");
		assertEquals(database.readDatabase(), EMPTY_TASK_LIST);
		
		database.changeDatabaseLocation("TestSuite/DatabaseTest/TestReadEmpty.txt");
		assertEquals(database.readDatabase(), EMPTY_TASK_LIST);

		/*
		 * Testing for writeToDatabase() method
		 */
		database.changeDatabaseLocation("TestSuite/DatabaseTest/TestWrite.txt");
		database.writeToDatabase(testTaskList);
		assertEquals(database.readDatabase().toString(), testTaskList.toString());


	



	}

}

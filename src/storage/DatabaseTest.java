package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import application.Task;

public class DatabaseTest {

	@Test
	public void test() {
		Database database = Database.getInstance();
		ArrayList<Task> taskList = new ArrayList<Task>();
		Task task1 = new Task("timetask", "task 1", "01/02/2015 17:00", "01/02/2015 18:00", "-", "not done");
		taskList.add(task1);
		database.writeToDatabase(taskList);
	}

}

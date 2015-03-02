package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import application.DateTime;
import application.TaskData;

public class DatabaseTest {

	@Test
	public void serializeTest() {
		Database database = new Database();
		
		ArrayList<TaskData> deadlines = database.getDeadlines();
		
		for (int i = 0; i < deadlines.size(); i++) {
			System.out.println(deadlines.get(i).getTaskType());
		}
		
		DateTime deadline1 = new DateTime(2015, 2, 28, 23, 59);
		TaskData data1 = new TaskData("Deadline", "data1", null, null, deadline1, "uncompleted" );
		
		DateTime deadline2 = new DateTime(2015, 3, 1, 23, 59);
		TaskData data2 = new TaskData("Deadline", "data2", null, null, deadline2, "uncompleted" );
		
		DateTime deadline3 = new DateTime(2015, 4, 2, 23, 59);
		TaskData data3 = new TaskData("Deadline", "data3", null, null, deadline3, "uncompleted" );
		
		database.addDeadlines(data1);
		database.addDeadlines(data2);
		database.addDeadlines(data3);
				
		for (int i = 0; i < deadlines.size(); i++) {
			System.out.println(deadlines.get(i).getTaskType());
		}
		
		

	}

}

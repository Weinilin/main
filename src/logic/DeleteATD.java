package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

import application.Task;
public class DeleteATD {

	DeleteHandler dh = new DeleteHandler();
	ArrayList<Task> taskTest = new ArrayList<Task>();
	ArrayList<Task> expected = new ArrayList<Task>();
	
	
	public void  setUp() {
		Task task1 = CommandHandler.createNewTask("dinner 6 to 7pm"),
			 task2 = CommandHandler.createNewTask("read Harry Porter"),
			 task3 = CommandHandler.createNewTask("Reflectoin by tomorrow 10pm");
		
		taskTest.add(task1);
		taskTest.add(task2);
		taskTest.add(task3);
		expected.add(task3);
	}

	@Test
	public void testExecute() {
		setUp();
		dh.execute("d", "2 4 a -1 1 @#$@");
		assertEquals(taskTest, expected);
	}

}

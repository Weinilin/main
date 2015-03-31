package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.Task;

public class AddHandlerTest {

	AddHandler ah = new AddHandler();
	ArrayList<Task> taskTest = new ArrayList<Task>();
	ArrayList<Task> expected = new ArrayList<Task>();
	
	@Before
	public void setUp() throws Exception {
		Task task1 = CommandHandler.createNewTask("dinner 6 to 7pm"),
		     task2 = CommandHandler.createNewTask("read Harry Porter"),
			 task3 = CommandHandler.createNewTask("Reflectoin by tomorrow 10pm");
		
		expected.clear();
		taskTest.clear();
		expected.add(task1);
		expected.add(task2);
		expected.add(task3);		
	}

	@Test
	public void testExecute() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ah.execute("+", " read Harry Porter ", taskTest);
		ah.execute("add", "dinner 6-7", taskTest);
		ah.execute("a", "Reflection by 22:00 tomorrow", taskTest);

		assertEquals(ah.execute("+", "help", taskTest), ah.getHelp(), taskTest);
		assertEquals(taskTest, expected);
	}

}

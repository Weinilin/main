
//@author A0114463M
package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.Task;
import application.TaskCreator;
public class AddHandlerTest {

	AddHandler ah = new AddHandler();
	ArrayList<Task> taskTest = new ArrayList<Task>();
	ArrayList<Task> expected = new ArrayList<Task>();
	Task task1, task2, task3 = null;
	@Before
	public void setUp() throws Exception {
	    TaskCreator tc = new TaskCreator("help mom for dinner 6 to 7pm");
	    task1 = tc.createNewTask();
	    tc.setNewString("read Harry Porter");
	    task2 = tc.createNewTask();
	    tc.setNewString("Reflectoin by tomorrow 10pm");
	    task3 = tc.createNewTask();
		
		expected.clear();
		taskTest.clear();
		expected.add(task1);
		expected.add(task2);
		expected.add(task3);		
	}

	@Test
	public void testExecute1() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
		ah.execute("+", " read Harry Porter ", taskTest);
		ah.execute("add", "dinner 6-7", taskTest);
		ah.execute("a", "Reflection by 22:00 tomorrow", taskTest);
		} catch (Exception e) {
		    fail("Unable to access the file for storage");
		}
		assertEquals(taskTest, expected);
	}

	@Test
	public void testExecute2() {
	    // test for help function
        assertEquals(ah.getHelp(), taskTest);
	}
	
	@Test
	public void testExecute3() {
	    taskTest.clear();
	    expected.add(task1);
	    // test for adding task starting with keyword help
	    try {
	        ah.execute("add", "help mom for dinner 6pm to 7pm", taskTest);
	    } catch (Exception e) {
	        
	    }
        assertEquals(taskTest, expected);
	    
	}
}


//@author A0114463M

package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

import application.Task;
public class DeleteHandlerTest {

	DeleteHandler dh = new DeleteHandler();
	ArrayList<Task> taskTest = new ArrayList<Task>();
	ArrayList<Task> expected = new ArrayList<Task>();
	Task task1 = CommandHandler.createNewTask("dinner 6 to 7pm"),
			 task2 = CommandHandler.createNewTask("read Harry Porter"),
			 task3 = CommandHandler.createNewTask("Reflectoin by tomorrow 10pm");
		
	
	public void  setUp1() {		
		expected.clear();
		taskTest.clear();
		taskTest.add(task1);
		taskTest.add(task2);
		taskTest.add(task3);
	}

	
	/*Test1
	 * This is a boundary case where user deletes multiple tasks in 
	 * random order
	 */
	@Test
	public void testExecute1() {
		setUp1();
		dh.execute("d", "2 1 3", taskTest);
		assertEquals(taskTest, expected);
	}
	
	public void setUp2() {
		expected.clear();
		taskTest.clear();
		taskTest.add(task1);
		taskTest.add(task2);
		taskTest.add(task3);
		expected.add(task1);
		expected.add(task2);
		expected.add(task3);
	}
	
	/*Test2
     *
	 * This is a boundary case when user types a non-positive number(negative partition?) 
	 */
	@Test
	public void testExecute2_1() {
		setUp2();
		dh.execute("d", "-1", taskTest);
		assertEquals(taskTest, expected);
	}
	
	@Test
	public void testExecute2_2() {
        setUp2();
        dh.execute("d", "0", taskTest);
        assertEquals(taskTest, expected);
    }
	
	public void setUp3() {
		expected.clear();
		taskTest.clear();
		taskTest.add(task1);
		taskTest.add(task2);
		taskTest.add(task3);
		expected.add(task2);
		expected.add(task3);
	}

	/*Test 3
	 * This is a boundary case when user types the command mixing cases
	 */
	@Test
	public void testExecute3() {
		setUp3();
		dh.execute("DeLEte", "1", taskTest);
		assertEquals(taskTest, expected);
	}
	
	public void setUp4() {
		expected.clear();
		taskTest.clear();
		taskTest.add(task2);
		taskTest.add(task3);
		expected.add(task2);
		expected.add(task3);
	}
	/*Test 4
	 * This is a boundary case when user types an index which is larger than
	 * the size (size boundary partition?)
	 */
	@Test
	public void testExecute4() {
		setUp4();
		dh.execute("D", "4", taskTest);
		assertEquals(taskTest, expected);
	}

}

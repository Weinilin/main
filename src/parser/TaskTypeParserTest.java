package parser;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * 
 * @author A0112823R
 *
 */
public class TaskTypeParserTest {

	@Test
	/**
	 * test time task, floating task, deadlines task
	 */
	public void test() throws Exception {
		
		// floating task
		TaskTypeParser t2 = new TaskTypeParser(0);
		assertEquals("floating task", t2.getTaskType());

		// deadlines task
		TaskTypeParser t3 = new TaskTypeParser(1);
		assertEquals("deadline", t3.getTaskType());
		
		// time task
        TaskTypeParser t1 = new TaskTypeParser(2);
        assertEquals("time task", t1.getTaskType());

	}
}

package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTypeParserTest {

	@Test
	/**
	 * test time task, floating task, deadlines task
	 */
	public void test() throws Exception {
		
		// floating task
		TaskTypeParser t2 = new TaskTypeParser("krej");
		assertEquals("floating task", t2.getTaskType());

		// deadlines task
		TaskTypeParser t3 = new TaskTypeParser("add start reading ST2334 notes by this sat at 0930");
		assertEquals("deadline", t3.getTaskType());
		
		// time task
        TaskTypeParser t1 = new TaskTypeParser("mds sale 12pm-12:20pm");
        assertEquals("time task", t1.getTaskType());

	}
}

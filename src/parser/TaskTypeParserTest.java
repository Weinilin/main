package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTypeParserTest {

	@Test
	/**
	 * equivalence testing: test all aspects
	 */
	public void test() {
		// time task
		TaskTypeParser t1 = new TaskTypeParser("mds sale 12pm-12:20pm");
		assertEquals("time task", t1.getTaskType());

		// floating task
		TaskTypeParser t2 = new TaskTypeParser("krej");
		assertEquals("floating task", t2.getTaskType());

		// deadlines task
		TaskTypeParser t3 = new TaskTypeParser("krej at 2pm 25/06/2015");
		assertEquals("deadline", t3.getTaskType());

		// error
		TaskTypeParser t4 = new TaskTypeParser(
				"krej at 2pm 2/03/2015 3/06 4/05");
		assertEquals(null, t4.getTaskType());
		TaskTypeParser t5 = new TaskTypeParser("jay 2pm 3pm 4pm 24/3");
		assertEquals(null, t5.getTaskType());
	}
}

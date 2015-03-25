package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTypeParserTest {

	@Test
	public void test() {
		TaskTypeParser t1 = new TaskTypeParser("mds sale 12-12:20pm");
		assertEquals("time task", t1.getTaskType());
	}

}

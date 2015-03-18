package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTypeParserTest {

	@Test
	public void test() {
		assertEquals("time task", TaskTypeParser.getTaskType("mds sale 12-12:20pm"));
	}

}

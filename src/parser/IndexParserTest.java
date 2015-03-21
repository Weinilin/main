package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class IndexParserTest {

	@Test
	public void test() {
	  IndexParser i1 = new IndexParser("delete 1 2");
	  assertEquals(1, i1.getIndex());
	  
	  IndexParser i2 = new IndexParser("delete 12");
	  assertEquals(12, i2.getIndex());
	}

}

package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class IndexParserTest {

	@Test
	/**
	 * test the boundary values when only 1 digit entered.
	 */
	public void testWhenDigit() {
		IndexParser i2 = new IndexParser("delete 12");
		assertEquals(12, i2.getIndex());
	}

	@Test
	/**
	 * test the boundary values when more than 1 digit is enter
	 */
	public void testTwoOrMoreInput() {
		IndexParser i1 = new IndexParser("delete 1 2");
		assertEquals(1, i1.getIndex());
	}

	@Test
	/**
	 * test the boundary values when no digit is entered.
	 * this will fail and send an exception message and exit the program
	 */
	public void testWhenNoDigit() {
		IndexParser i1 = new IndexParser("delete");
		assertEquals(1, i1.getIndex());
	}

}

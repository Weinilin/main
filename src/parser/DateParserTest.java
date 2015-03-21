package parser;

import static org.junit.Assert.*;
import org.junit.Test;

public class DateParserTest {

	@Test
	public void test() {
		assertEquals("01/03/2015", DateParser.extractDate("mds sale due 1/3"));
		
		assertEquals("12/01/2015, 13/03/2015", DateParser.extractDate("mds sale from 12 Jan to 13 March"));
		
		assertEquals("22/03/2015, 21/03/2015", DateParser.extractDate("mds sale from the following day to next year"));
		
		assertEquals("28/03/2015", DateParser.extractDate("mds sale start 1 weeks later"));
		
		assertEquals("24/03/2015", DateParser.extractDate("mds sale start 1 month later"));
		
		assertEquals("24/02/2015", DateParser.extractDate("mds sale start in after 3 days"));
		
		assertEquals("21/03/2015", DateParser.extractDate("mds sale after today")); //set the date to today
		
		assertEquals("01/03/2013", DateParser.extractDate("mds sale due 1/3/2013"));
		
		assertEquals("", DateParser.extractDate("mds sale"));
		
		assertEquals("02/02/2015", DateParser.extractDate("mds sale at 2 feb"));
		
	}

}

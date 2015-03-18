package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateParserTest {

	@Test
	public void test() {
		DateParser.extractDate("mds sale due 12/3");
		
		DateParser.extractDate("mds sale from 12 Jan 2012 to 13 March");
		
		DateParser.extractDate("mds sale from the following day to next year");
		
		DateParser.extractDate("mds sale start 1 weeks later");
		
		DateParser.extractDate("mds sale start 1 month later");
		
		DateParser.extractDate("mds sale start in after 3 days");
		
		DateParser.extractDate("mds sale after today");
		
		DateParser.extractDate("mds sale due 12/3/2013");
		
		DateParser.extractDate("mds sale");
		
	}

}

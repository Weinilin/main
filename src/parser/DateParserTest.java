package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateParserTest {

	@Test
	public void test() {
		ArrayList<String> dates = new ArrayList<String> ();
		
		dates.add("01/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale due 1/3"));

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale due ~1/3~"));

		dates.clear();
		dates.add("12/01/2015");
		dates.add("13/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale from 12 Jan to 13 March"));

		dates.clear();
		dates.add("13/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale from ~12 Jan~ to 13 March"));

		dates.clear();
		dates.add("23/03/2015");
		dates.add("22/03/2016");
		assertEquals(dates, DateParser.extractDate("mds sale from tmr to next year"));

		dates.clear();
		dates.add("23/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale from tmr to ~next year~"));
		// does not work if ~tmr to next year~

		dates.clear();
		dates.add("29/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale start 1 weeks later"));

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale start ~1 weeks~ later"));

		dates.clear();
		dates.add("22/04/2015");
		assertEquals(dates, DateParser.extractDate("mds sale start 1 month later"));

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale start ~1 month~ later"));

		dates.clear();
		dates.add("25/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale start in after 3 days"));

		dates.clear();
		dates.add("23/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale after today")); //set the date to today

		dates.clear();
		dates.add("01/03/2013");
		assertEquals(dates, DateParser.extractDate("mds sale due 1/3/2013"));

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale"));

		dates.clear();
		dates.add("03/10/2015");
		assertEquals(dates, DateParser.extractDate("mds sale at 3rd of oct"));


		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale start in ~after 3 days~"));

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale ~after today~")); //set the date to today

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale due ~1/3/2015~"));
		//does not work if it is 1/2/2015

		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale at ~3rd of oct~"));
		 
		dates.clear();
		dates.add("23/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale next monday"));
		
		dates.clear();
		dates.add("28/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale next sat"));
		
		dates.clear();
		dates.add("29/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale next sun"));

		

	}

}

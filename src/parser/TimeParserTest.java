package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTest {

	@Test
	public void test() {
		ArrayList<String> times = new ArrayList<String> ();

		times.add("11:00");
		times.add("13:30");
		assertEquals(times, TimeParser.extractTime("mds sale 11 to 1:30 pm."));
		
		times.clear();
		assertEquals(times, TimeParser.extractTime("mds sale ~11 to 1:30 pm~."));

		times.clear();
		times.add("11:30");
		times.add("12:30");
		assertEquals(times, TimeParser.extractTime("mds sale 11:30-12:30 pm"));

		times.clear();
		times.add("00:30");
		assertEquals(times, TimeParser.extractTime("mds sale 12:30 am")); //add of one more time

		times.clear();
		times.add("18:00");
		assertEquals(times, TimeParser.extractTime("mds sale 6pm")); // add of one more time

		times.clear();
		times.add("12:00");
		times.add("00:00");
		assertEquals(times, TimeParser.extractTime("mds sale from noon to at midnight"));

		times.clear();
		times.add("00:00");
		assertEquals(times, TimeParser.extractTime("mds sale from ~noon~ to at midnight"));

		times.clear();
		times.add("00:00");
		times.add("23:59");
		assertEquals(times, TimeParser.extractTime("mds sale from midnight to before midnight")); //organization 

		times.clear();
		times.add("6:30");
		times.add("19:45");
		assertEquals(times, TimeParser.extractTime("mds sale from 6:30 in morn to 7:45 in night"));

		times.clear();
		times.add("19:45");
		times.add("00:00");
		assertEquals(times, TimeParser.extractTime("mds sale from 7:45pm to midnight")); 

		times.clear();
		times.add("23:30");
		times.add("00:30");
		assertEquals(times, TimeParser.extractTime("mds sale start at 11:30 pm for 1 hour")); 

		//arrangement of the time need more thoughts

	}

}

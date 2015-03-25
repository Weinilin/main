package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTest {

	@Test
	public void timeTestForHashTag() {
		ArrayList<String> times = new ArrayList<String>();

		// test when all of the time is ~~
		times.clear();
		assertEquals(times,
				TimeParser.extractTime("mds sale ~11pm to 1:30 pm~."));

		// test when 1 of the time is ~~ and the other do not
		times.clear();
		times.add("00:00");
		assertEquals(times,
				TimeParser.extractTime("mds sale from ~noon~ to at midnight"));
	}

	@Test
	/**
	 * Equivalence test: test for all possible aspects. 
	 */
	public void timeTestWithoutHashTag() {
		ArrayList<String> times = new ArrayList<String>();

		// test the time keyword 6 with hour format of am and floating task
		times.clear();
		times.add("00:30");
		assertEquals(times, TimeParser.extractTime("mds sale 12:30 am"));

		// test for time keyword 2 with o'clock
		times.clear();
		times.add("11:30");
		assertEquals(times,
				TimeParser.extractTime("mds sale start at 11:30 o'clock"));

		// test for time format 6 of pm and floating task
		times.clear();
		times.add("18:00");
		assertEquals(times, TimeParser.extractTime("mds sale 6pm"));

		// test for time keyword 2
		times.clear();
		times.add("23:30");
		times.add("00:30");
		assertEquals(times,
				TimeParser.extractTime("mds sale start at 11:30 pm for 1 hour"));

		// test for time keyword 3
		times.clear();
		times.add("11:59");
		times.add("23:59");
		assertEquals(times,
				TimeParser
						.extractTime("mds sale before noon to before midnight"));

		// test for timed test and time keyword 4
		times.clear();
		times.add("12:00");
		times.add("00:00");
		assertEquals(times,
				TimeParser.extractTime("mds sale from noon to at midnight"));

		// test for time keyword 5
		times.clear();
		times.add("6:30");
		times.add("19:45");
		assertEquals(
				times,
				TimeParser
						.extractTime("mds sale from 6:30 in morn to 7:45 in night"));

		// test when nothing is detected
		times.clear();
		assertEquals(times, TimeParser.extractTime("mds sale!"));

		// test for the position stored in arraylist and the difference
		// detection of
		// keyword
		times.clear();
		times.add("00:00");
		times.add("23:59");
		assertEquals(
				times,
				TimeParser
						.extractTime("mds sale from midnight to before midnight"));

		times.clear();
		times.add("19:45");
		times.add("00:00");
		assertEquals(times,
				TimeParser.extractTime("mds sale from 7:45pm to midnight"));
	}
}

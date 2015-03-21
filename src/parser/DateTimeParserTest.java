package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeParserTest {

	@Test
	public void test() {
		//deadlines test
		//	DateTimeParser d = new DateTimeParser("CS2103T assignments due 2pm"); 

		DateTimeParser d1 = new DateTimeParser("CS2103T assignments due 23/2"); 
		assertEquals(d1.getDeadlineDate(), "23/02/2015");
		assertEquals(d1.getDeadlineTime(), "23:59");

		//floating test
		DateTimeParser("Shopping!!!!"); 

		//timed test
		DateTimeParser("CS2103T exam on 24/5 from 2 to 4:30pm.");
		DateTimeParser("CS2103T exam on 24/5 start at 1:30pm and end at 3:30pm.");

		DateTimeParser d2 = new DateTimeParser("Mds sale start from tomorrow to 1 week later at 2pm");
		assertEquals(d2.getStartTime(), "14:00");
		assertEquals(d2.getEndTime(), "15:00");
		assertEquals(d2.getEndDate(), "28/03/2015");
		assertEquals(d2.getStartDate(), "22/03/2015");

		DateTimeParser d3 = new DateTimeParser("superman at 7pm");
		assertEquals(d3.getDeadlineTime(), "19:00");

		DateTimeParser("Mds sale start at 6 in the night to midnight");
	}

	private void DateTimeParser(String string) {
		// TODO Auto-generated method stub

	}

}

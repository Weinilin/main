package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeParserTest {
 
	@Test
	public void testWhenTimeNotKeyed() {
		// deadline task without time added
		DateTimeParser d1 = new DateTimeParser("CS2103T assignments due 23/2");
		assertEquals(d1.getDeadlineDate(), "23/02/2015");
		assertEquals(d1.getDeadlineTime(), "23:59");

		// timed task without one time added
		DateTimeParser d2 = new DateTimeParser(
				"CS2103T assignments 23/2/2016 24/3/2016 4pm");
		assertEquals(d2.getStartDate(), "23/02/2016");
		assertEquals(d2.getEndDate(), "24/03/2016");
		assertEquals(d2.getStartTime(), "16:00");
		assertEquals(d2.getEndTime(), "17:00");

		// timed task with no time added
		DateTimeParser d3 = new DateTimeParser(
				"CS2103T assignments 23/2/2016 24/3/2016 4pm 6:30pm");
		assertEquals(d3.getStartDate(), "23/02/2016");
		assertEquals(d3.getEndDate(), "24/03/2016");
		assertEquals(d3.getStartTime(), "16:00");
		assertEquals(d3.getEndTime(), "18:30");
	}

	@Test
	//take note that if date is not added and there is time, I set the default to current date
	//When testing, do change the expected result's date to current date. 
	public void testWhenDateNotKeyed() {
		// deadline task without date added
		DateTimeParser d1 = new DateTimeParser("CS2103T assignments due 3pm");
		assertEquals(d1.getDeadlineDate(), "28/03/2015");
		assertEquals(d1.getDeadlineTime(), "15:00");

		// timed task without one date added
		DateTimeParser d2 = new DateTimeParser(
				"CS2103T assignments 4pm to 5pm 23/02/2016");
		assertEquals(d2.getStartDate(), "23/02/2016");
		assertEquals(d2.getEndDate(), "23/02/2016");
		assertEquals(d2.getStartTime(), "16:00");
		assertEquals(d2.getEndTime(), "17:00");

		// timed task with no date added
		DateTimeParser d3 = new DateTimeParser(
				"CS2103T assignments 4pm 6:30pm");
		assertEquals(d3.getStartDate(), "28/03/2015");
		assertEquals(d3.getEndDate(), "28/03/2015");
		assertEquals(d3.getStartTime(), "16:00");
		assertEquals(d3.getEndTime(), "18:30");
	}

	@Test 
	public void testWhenSameTime(){
	    //test when same time is detected with no date detected
	    DateTimeParser d3 = new DateTimeParser(
                "CS2103T assignments 6:30pm 6:30pm");
        assertEquals(d3.getStartDate(), "28/03/2015");
        assertEquals(d3.getEndDate(), "29/03/2015");
        assertEquals(d3.getStartTime(), "18:30");
        assertEquals(d3.getEndTime(), "18:30");
        
        //test when same time is detected with 1 date detected
        DateTimeParser d4 = new DateTimeParser(
                "25/4 6:30pm 6:30pm assignments");
        assertEquals(d4.getStartDate(), "25/04/2015");
        assertEquals(d4.getEndDate(), "26/04/2015");
        assertEquals(d4.getStartTime(), "18:30");
        assertEquals(d4.getEndTime(), "18:30");
	    
	}

	@Test
	public void test() {
		// deadlines test
		DateTimeParser d1 = new DateTimeParser("CS2103T assignments due 23/2/2015 2pm");
		assertEquals(d1.getDeadlineDate(), "23/02/2015");
		assertEquals(d1.getDeadlineTime(), "14:00");

		// timed test
		DateTimeParser d2 = new DateTimeParser(
				"CS2103T exam on 24/5 27/09/2015 from 2pm to 4:30pm.");
		assertEquals(d2.getStartTime(), "14:00");
		assertEquals(d2.getEndTime(), "16:30");
		assertEquals(d2.getEndDate(), "27/09/2015");
		assertEquals(d2.getStartDate(), "24/05/2015");

		DateTimeParser d3 = new DateTimeParser("mds sale from 2pm 24/3 to 4pm 25/3");
		assertEquals(d3.getStartTime(), "14:00");
		assertEquals(d3.getEndTime(), "16:00");
		assertEquals(d3.getEndDate(), "25/03/2015");
		assertEquals(d3.getStartDate(), "24/03/2015");
	}
	
	
}

package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeParserTest {

	@Test
	public void test() {
		//deadlines test
		DateTimeParser.getDateTime("CS2103T assignments due 2pm"); 
		DateTimeParser.getDateTime("CS2103T assignments due 23/2"); 
		assertEquals(DateTimeParser.getDeadlineDate(), "23/02/2015");
		assertEquals(DateTimeParser.getDeadlineTime(), "23:59");
		
		//floating test
		DateTimeParser.getDateTime("Shopping!!!!"); 
		
		//timed test
		DateTimeParser.getDateTime("CS2103T exam on 24/5 from 2 to 4:30pm."); 
		
		DateTimeParser.getDateTime("CS2103T exam on 24/5 start at 1:30pm and end at 3:30pm.");
		
		DateTimeParser.getDateTime("Mds sale start from tomorrow to 1 week later at 2pm");
		assertEquals(DateTimeParser.getStartTime(), "14:00");
		assertEquals(DateTimeParser.getEndTime(), "15:00");
		assertEquals(DateTimeParser.getEndDate(), "28/03/2015");
		assertEquals(DateTimeParser.getStartDate(), "22/03/2015");
			
		DateTimeParser.getDateTime("Mds sale start at 6 in the night to midnight");
	}

}

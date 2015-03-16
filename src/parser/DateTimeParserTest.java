package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeParserTest {

	@Test
	public void test() {
		//deadlines test
		DateTimeParser.getDateTime("CS2103T assignments due 2pm"); 
		DateTimeParser.getDateTime("CS2103T assignments due 23/2"); 
		
		//floating test
		DateTimeParser.getDateTime("Shopping!!!!"); 
		
		//timed test
		DateTimeParser.getDateTime("CS2103T exam on 24/5 from 2pm to 4:30pm."); 
	}

}

package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeParserTest {

	@Test
	public void test() {
		TimeParser.extractTime("mds sale 11 to 1:30 pm");
		
		TimeParser.extractTime("mds sale 11:30-12:30 pm");
		
		TimeParser.extractTime("mds sale 12:30 am"); //add of one more time
		
		TimeParser.extractTime("mds sale 6pm"); // add of one more time
		
		TimeParser.extractTime("mds sale from noon to at midnight");
		
		TimeParser.extractTime("mds sale from midnight to before midnight"); //organization 
		
		TimeParser.extractTime("mds sale from 6:30 in morning to 7:45 in night");

		TimeParser.extractTime("mds sale from 7:45pm to midnight"); 
		
		TimeParser.extractTime("mds sale start at 11:30 pm for 1 hour"); 

		//arrangement of the time need more thoughts
		
	}

}

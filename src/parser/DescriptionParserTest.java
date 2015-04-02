package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DescriptionParserTest {

	@Test
	public void withHashTagTest() {
		//To test for deadlines task and with escaped character
		DescriptionParser d1 = new DescriptionParser("~6am~ cafe at 2pm");
		assertEquals(d1.getDescription(), "6am cafe");

		DescriptionParser d3 = new DescriptionParser("~CS2103T assignment~ at 2pm 23/3");
		assertEquals(d3.getDescription(), "cs2103t assignment");
		
		//To test for timed task with escaped character
		DescriptionParser d2 = new DescriptionParser("2pm to 7pm in ~6 in the morn~ 27/3");
		assertEquals(d2.getDescription(), "in 6 in the morn");

		DescriptionParser d5 = new DescriptionParser("CS2103T assignment at ~2 jan~");
		assertEquals(d5.getDescription(), "cs2103t assignment at 2 jan"); 


		DescriptionParser d7 = new DescriptionParser("CS2103T assignment on ~tomorrow~");
		assertEquals(d7.getDescription(), "cs2103t assignment on tomorrow"); 
		
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment on 2nd march at 2pm");
		assertEquals(d8.getDescription(), "cs2103t assignment"); 
		
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment on ~2nd december~");
		assertEquals(d9.getDescription(), "cs2103t assignment on 2nd december"); 
		 
	}
	
	@Test
	public void testWithoutHashTag(){
		//test for timed task
		DescriptionParser d10 = new DescriptionParser("Superman from 2pm to 4pm");
		assertEquals(d10.getDescription(), "superman");	
		
		//test for deadlines
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment at 2nd march at 2pm");
		assertEquals(d8.getDescription(), "cs2103t assignment");
		
		DescriptionParser d7 = new DescriptionParser("On this   mon for CS2103T assignment");
		assertEquals(d7.getDescription(), "for cs2103t assignment");
		
		//test for floating
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment!");
		assertEquals(d9.getDescription(), "cs2103t assignment!");
		
		//extra testing
		DescriptionParser d11 = new DescriptionParser("add complete developer guide on 13/04 ");
        assertEquals(d11.getDescription(), "add complete developer guide");
	}

}

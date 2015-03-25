package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DescriptionParserTest {

	@Test
	public void withHashTagTest() {
		//DescriptionParser d4 = new DescriptionParser("CS2103T assignment ~on noon~");
		//assertEquals(d4.getDescription(), "CS2103T assignment ~on noon~");

		//To test for deadlines task and with hashTag
		DescriptionParser d1 = new DescriptionParser("~6am~ cafe at 2pm");
		assertEquals(d1.getDescription(), "~6am~ cafe");

		DescriptionParser d3 = new DescriptionParser("~CS2103T assignment~ at 2pm 23/3");
		assertEquals(d3.getDescription(), "~cs2103t assignment~");
		
		//To test for timed task with hashtag
		DescriptionParser d2 = new DescriptionParser("2pm to 7pm in ~6 in the morn~ 27/3");
		assertEquals(d2.getDescription(), "in ~6 in the morn~");

		DescriptionParser d5 = new DescriptionParser("CS2103T assignment at ~2 jan~");
		assertEquals(d5.getDescription(), "cs2103t assignment at ~2 jan~"); 

		DescriptionParser d6 = new DescriptionParser("CS2103T assignment on tomorrow");
		assertEquals(d6.getDescription(), "cs2103t assignment"); 

		DescriptionParser d7 = new DescriptionParser("CS2103T assignment on ~tomorrow~");
		assertEquals(d7.getDescription(), "cs2103t assignment on ~tomorrow~"); 
		
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment on 2nd march at 2pm");
		assertEquals(d8.getDescription(), "cs2103t assignment"); 
		
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment on ~2nd december~");
		assertEquals(d9.getDescription(), "cs2103t assignment on ~2nd december~"); 
		 
	}
	
	@Test
	public void testWithoutHashTag(){
		//test for timed task
		DescriptionParser d10 = new DescriptionParser("Superman from 2pm to 4pm");
		assertEquals(d10.getDescription(), "superman");	
		
		//test for deadlines
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment on 2nd march at 2pm");
		assertEquals(d8.getDescription(), "cs2103t assignment");
		
		DescriptionParser d7 = new DescriptionParser("CS2103T assignment on 2nd march");
		assertEquals(d7.getDescription(), "cs2103t assignment");
		
		//test for floating
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment!");
		assertEquals(d9.getDescription(), "cs2103t assignment!"); 
	}

}

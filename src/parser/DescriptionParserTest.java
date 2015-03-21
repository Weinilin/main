package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DescriptionParserTest {

	@Test
	public void test() {
		//DescriptionParser d4 = new DescriptionParser("CS2103T assignment ~on noon~");
		//assertEquals(d4.getDescription(), "CS2103T assignment ~on noon~");

		DescriptionParser d1 = new DescriptionParser("~6am~ cafe at 2pm");
		assertEquals(d1.getDescription(), "~6am~ cafe");

		DescriptionParser d2 = new DescriptionParser("2pm in ~6 in the morn~");
		assertEquals(d2.getDescription(), "in ~6 in the morn~");

		DescriptionParser d3 = new DescriptionParser("CS2103T assignment at 2pm 23/3");
		assertEquals(d3.getDescription(), "CS2103T assignment");

		DescriptionParser d5 = new DescriptionParser("CS2103T assignment at ~2 jan~");
		assertEquals(d5.getDescription(), "CS2103T assignment at ~2 jan~"); 

		DescriptionParser d6 = new DescriptionParser("CS2103T assignment on tomorrow");
		assertEquals(d6.getDescription(), "CS2103T assignment"); 

		DescriptionParser d7 = new DescriptionParser("CS2103T assignment on ~tomorrow~");
		assertEquals(d7.getDescription(), "CS2103T assignment on ~tomorrow~"); 
		
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment on 2nd march");
		assertEquals(d8.getDescription(), "CS2103T assignment"); 
		
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment on ~2nd december~");
		assertEquals(d9.getDescription(), "CS2103T assignment on ~2nd december~"); 


	}

}

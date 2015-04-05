package parser;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * take note that this test case is current date and current time and weekday sensitive
 * eg: today and this weekday that pass the current weekday we are in (today: fri, key in this mon will have 
 * error)
 * @author WeiLin
 *
 */
public class DescriptionParserTest {

	@Test
	public void withHashTagTest() throws Exception {
	    
	    DescriptionParser d3 = new DescriptionParser("~CS2103T assignment~ at 2pm 23/4");

        
		//To test for deadlines task and with escaped character
	    //~~ start of the statement
		DescriptionParser d1 = new DescriptionParser("~6am~ cafe at 2pm");
		assertEquals(d1.getDescription(), "6am cafe");

		
		//To test for timed task with escaped character
		//~~middle of statement
		DescriptionParser d2 = new DescriptionParser("2pm to 7pm in ~6 in the morn~ 27/6");
		assertEquals(d2.getDescription(), "in 6 in the morn");

		//~~ end of the statement
		DescriptionParser d5 = new DescriptionParser("CS2103T assignment at ~2 jan~");
		assertEquals(d5.getDescription(), "CS2103T assignment at 2 jan");  


		//~~ more than 2
		DescriptionParser d7 = new DescriptionParser("CS2103T ~tomorrow~ la la la ~ooooo~ 3pm 23/9 ");
		assertEquals(d7.getDescription(), "CS2103T tomorrow la la la ooooo"); 
		
		//no ~~
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment on 2nd May at 2pm");
		assertEquals(d8.getDescription(), "CS2103T assignment"); 
		
		//1 ~  not 2
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment on ~2nd december");
		assertEquals(d9.getDescription(), "CS2103T assignment"); 
		 
	}
	

	@Test
	public void testWithoutHashTag() throws Exception{
	    
	  
        DescriptionParser d11 = new DescriptionParser("add complete developer guide on 13/04 ");
      
		//test for timed task
		DescriptionParser d10 = new DescriptionParser("Superman from 2pm to 4pm");
		assertEquals(d10.getDescription(), "Superman");	
	
		//test for deadlines
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment at 20th April at 2pm");
		assertEquals(d8.getDescription(), "CS2103T assignment at");
		
		DescriptionParser d7 = new DescriptionParser("On   sun for CS2103T assignment");
		assertEquals(d7.getDescription(), "for CS2103T assignment");
		
		//test for floating
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment!");
		assertEquals(d9.getDescription(), "CS2103T assignment!");
		
	
		//test for conjunction
        DescriptionParser d14 = new DescriptionParser("gigi by assignment on next sun at 3pm at ivle from !");
        assertEquals(d14.getDescription(), "gigi by assignment at ivle from !");
		
	}

	}



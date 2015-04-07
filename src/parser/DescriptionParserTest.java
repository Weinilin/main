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

	    MainParser m1 = new MainParser("~CS2103T assignment~ at 2pm 23/4");
	    String userInputLeftOver = m1.getUserLeft();
	    DescriptionParser d3 = new DescriptionParser("~CS2103T assignment~ at 2pm 23/4", userInputLeftOver);
	    assertEquals(d3.getDescription(), "CS2103T assignment");
	
	    
		//To test for deadlines task and with escaped character
	    //~~ start of the statement
	    MainParser m2 = new MainParser("~6am~ cafe at 2pm");
        userInputLeftOver = m2.getUserLeft();
		DescriptionParser d1 = new DescriptionParser("~6am~ cafe at 2pm", userInputLeftOver);
		assertEquals(d1.getDescription(), "6am cafe");

		
		//To test for timed task with escaped character
		//~~middle of statement
		 MainParser m3 = new MainParser("2pm to 7pm in ~6 in the morn~ 27/6");
	     userInputLeftOver = m3.getUserLeft();
		DescriptionParser d2 = new DescriptionParser("2pm to 7pm in ~6 in the morn~ 27/6", userInputLeftOver);
		assertEquals(d2.getDescription(), "in 6 in the morn");

		//~~ end of the statement
		MainParser m4 = new MainParser("CS2103T assignment at ~2 jan~");
        String userInputLeftOver1 = m4.getUserLeft();
		DescriptionParser d5 = new DescriptionParser("CS2103T assignment at ~2 jan~", userInputLeftOver1);
		assertEquals(d5.getDescription(), "CS2103T assignment at 2 jan");  

		//~~ more than 2
		MainParser m5 = new MainParser("CS2103T ~tomorrow~ la la la ~ooooo~ 3pm 23/9 ");
        userInputLeftOver = m5.getUserLeft();
		DescriptionParser d7 = new DescriptionParser("CS2103T ~tomorrow~ la la la ~ooooo~ 3pm 23/9 ", userInputLeftOver);
		assertEquals(d7.getDescription(), "CS2103T tomorrow la la la ooooo"); 
		
		//no ~~
		MainParser m6 = new MainParser("CS2103T assignment on 2nd May at 2pm");
        userInputLeftOver = m6.getUserLeft();
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment on 2nd May at 2pm", userInputLeftOver);
		assertEquals(d8.getDescription(), "CS2103T assignment"); 
		
		//1 ~  not 2
		MainParser m7 = new MainParser("CS2103T assignment on ~2nd december");
        userInputLeftOver = m7.getUserLeft();
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment on ~2nd december", userInputLeftOver);
		assertEquals(d9.getDescription(), "CS2103T assignment"); 
		
		//test ~with space hchhd~
		MainParser m8 = new MainParser("CS2103T assignment on ~ 2nd december ~");
        userInputLeftOver = m8.getUserLeft();
        DescriptionParser d10 = new DescriptionParser("CS2103T assignment on ~ 2nd december ~", userInputLeftOver);
        assertEquals(d10.getDescription(), "CS2103T assignment on 2nd december"); 

		//test with !~ ~ as one word
        MainParser m9 = new MainParser("CS2103T assignment on !!!~2nd december~");
         userInputLeftOver = m9.getUserLeft();
        DescriptionParser d11 = new DescriptionParser("CS2103T assignment on !!!~2nd december~", userInputLeftOver);
        assertEquals(d11.getDescription(), "CS2103T assignment on !!!2nd december"); 

	}
	
	@Test
	public void testWithoutHashTag() throws Exception{
	    
	    MainParser m2 = new MainParser("add complete developer guide on 13/04 ");
        String userInputLeftOver = m2.getUserLeft();
        DescriptionParser d11 = new DescriptionParser("add complete developer guide on 13/04 ", userInputLeftOver);
        assertEquals(d11.getDescription(), "add complete developer guide"); 
	
         
		//test for timed task
        MainParser m1 = new MainParser("Superman from 2pm to 4pm");
        userInputLeftOver = m1.getUserLeft();
		DescriptionParser d10 = new DescriptionParser("Superman from 2pm to 4pm", userInputLeftOver);
		assertEquals(d10.getDescription(), "Superman");	
	
		//test for deadlines
		MainParser m3 = new MainParser("CS2103T assignment at 20th April at 2pm");
        userInputLeftOver = m3.getUserLeft();
		DescriptionParser d8 = new DescriptionParser("CS2103T assignment at 20th April at 2pm", userInputLeftOver);
		assertEquals(d8.getDescription(), "CS2103T assignment at");
		
		MainParser m4 = new MainParser("On   sun for CS2103T assignment");
        userInputLeftOver = m4.getUserLeft();
		DescriptionParser d7 = new DescriptionParser("On   sun for CS2103T assignment", userInputLeftOver);
		assertEquals(d7.getDescription(), "for CS2103T assignment");
		
		//test for floating
		MainParser m5 = new MainParser("CS2103T assignment!");
        userInputLeftOver = m5.getUserLeft();
		DescriptionParser d9 = new DescriptionParser("CS2103T assignment!", userInputLeftOver);
		assertEquals(d9.getDescription(), "CS2103T assignment!");
		
	
		//test for conjunction
		  MainParser m6 = new MainParser("gigi by assignment on next sun at 3pm at ivle from !");
	        userInputLeftOver = m6.getUserLeft();
        DescriptionParser d14 = new DescriptionParser("gigi by assignment on next sun at 3pm at ivle from !", userInputLeftOver);
        assertEquals(d14.getDescription(), "gigi by assignment at ivle from !");
		
	}

	}



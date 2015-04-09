package parser;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * 
 * @author A0112823R
 *
 */
public class DayParserTest {

    @Test
    public void test() {
        //test dd/mm/yyyy
        assertEquals(24, DayParser.getNumberOfDay("24/05/2015"));
        
        //test dd/mm
        assertEquals(25, DayParser.getNumberOfDay("25/07"));
        
      //test dd/mm/yyyy when dd is word
        assertEquals(23, DayParser.getNumberOfDay("twenty-three aug 2015"));
        
        //test dd/mm when dd is word
        assertEquals(31, DayParser.getNumberOfDay("thirty-onejan"));
        
        //test dd/mm 
        assertEquals(1, DayParser.getNumberOfDay("1 feb"));
        
        //test dd/mm 
        assertEquals(20, DayParser.getNumberOfDay("20april"));
             
        //test mm/dd 
        assertEquals(12, DayParser.getNumberOfDay("mar12"));
        
        //test mm/dd 
        assertEquals(4, DayParser.getNumberOfDay("may4 2015"));
        
        //test mm/dd 
        assertEquals(24, DayParser.getNumberOfDay("november twenty-four 2015"));
        
      //test number of days
        assertEquals(24, DayParser.getNumberOfDay("twenty-four day later"));
    }

}

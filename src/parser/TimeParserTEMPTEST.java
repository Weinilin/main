package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTEMPTEST {

    @Test
    public void test() throws Exception {
        ArrayList<String> times = new ArrayList<String>();
     // test with both pm
DateTimeParser dt1 = new DateTimeParser("la ala la 1200h to 13:00 24/4 to Apr 25");
        
        assertEquals("12:00", dt1.getStartTime());
       
        assertEquals("13:00", dt1.getEndTime());
        assertEquals("Fri 24/04/2015", dt1.getStartDate());
        
        assertEquals("Sat 25/04/2015", dt1.getEndDate());
    }     
    
}



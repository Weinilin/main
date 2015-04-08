package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeParserTestTEMP {

    @Test
    public void test() throws Exception {
        ArrayList<String> dates = new ArrayList<String>();
        
     // the tues before
        
        DateTimeParser dateTime5 = new DateTimeParser(
                "two tuesday before 24/5 ");
        assertEquals("Tues 12/05/2015", dateTime5.getEndDate());
        
        // the tues after
  
        DateTimeParser dateTime6 = new DateTimeParser(
                "two tuesday after 24/5 ");
        assertEquals("Tues 02/06/2015", dateTime6.getEndDate());
        
        // the tues after mix with those detected in dateParser
        
        DateTimeParser dateTime7 = new DateTimeParser(
                "two tuesday after 24/5  24/6");
        assertEquals("Tues 02/06/2015", dateTime7.getStartDate());
        assertEquals("Wed 24/06/2015", dateTime7.getEndDate());
        
// next sat or sun
        
        DateTimeParser dateTime8 = new DateTimeParser(
                "sat and sun");
        assertEquals("Sat 11/04/2015", dateTime8.getStartDate());
        assertEquals("Sun 12/04/2015", dateTime8.getEndDate());

    }

}

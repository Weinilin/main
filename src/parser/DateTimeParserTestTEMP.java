package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeParserTestTEMP {

    @Test
    public void test() {
        DateTimeParser d12 = new DateTimeParser(
                "CS2103T assignments 10pm to 9pm today");
        assertEquals(d12.getStartDate(), "Fri 03/04/2015");
        assertEquals(d12.getEndDate(), "Sat 04/04/2015");
        assertEquals(d12.getStartTime(), "22:00");
        assertEquals(d12.getEndTime(), "21:00");

        
    
        
    }

}

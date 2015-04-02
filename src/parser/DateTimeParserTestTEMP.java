package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateTimeParserTestTEMP {

    @Test
    public void test() {
        DateTimeParser d1 = new DateTimeParser("add go run one round on this sun from 10 o’clock to 11 am ");
        //assertEquals(d1.getDeadlineDate(), "15/04/2015");
        assertEquals(d1.getStartTime(), "10:00");
        assertEquals(d1.getEndTime(), "11:00");
        
        
    }

}

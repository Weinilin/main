package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeParserTestTEMP {

    @Test
    public void test() throws Exception {
        DateTimeParser d1 = new DateTimeParser(
                "sgsg 12am - 12");
     //   assertEquals(d1.getEndDate(), "-");
        assertEquals(d1.getEndTime(), "06:30");
     //   assertEquals(d1.getStartDate(), "-");
        assertEquals(d1.getStartTime(), "1:30");

    }

}

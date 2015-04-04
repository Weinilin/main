package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WeekDayParserTest {

    @Test
    public void test() {
       
        assertEquals(  WeekDayParser.getWeekDay("03/04/2015"), "Fri");
        assertEquals(  WeekDayParser.getWeekDay("04/04/2015"), "Sat");
        assertEquals(  WeekDayParser.getWeekDay("05/04/2015"), "Sun");
        assertEquals(  WeekDayParser.getWeekDay("06/04/2015"), "Mon");
        assertEquals(  WeekDayParser.getWeekDay("07/04/2015"), "Tues");
        assertEquals(  WeekDayParser.getWeekDay("08/04/2015"), "Wed");
        assertEquals(  WeekDayParser.getWeekDay("09/04/2015"), "Thur");

      
    }

}

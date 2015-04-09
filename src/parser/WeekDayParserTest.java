package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
/**
 * 
 * @author A0112823R
 *
 */
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
        
        assertEquals(  WeekDayParser.detectDayOfWeek("hdhdhd"), 0);
        assertEquals(  WeekDayParser.detectDayOfWeek("friday"), 5);
        assertEquals(  WeekDayParser.detectDayOfWeek("satuarday"), 6);
        assertEquals(  WeekDayParser.detectDayOfWeek("sunday"), 7);
        assertEquals(  WeekDayParser.detectDayOfWeek("monday"), 1);
        assertEquals(  WeekDayParser.detectDayOfWeek("tuesday"), 2);
        assertEquals(  WeekDayParser.detectDayOfWeek("wednesday"), 3);
        assertEquals(  WeekDayParser.detectDayOfWeek("thursday"), 4);

      
    }

}

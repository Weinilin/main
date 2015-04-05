package parser;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Test;

public class DateTimeNattyTestTemp {
    @Test
    public void test() throws Exception {
       // ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
        DateTimeParser dt = new DateTimeParser("mds sale noon past midnight");
        dates.clear();
        dates.add("00:01");
        assertEquals("12:00", dt.getEndTime());
        dates.clear();
        dates.add("12:00");
        assertEquals("00:01", dt.getStartTime());
    }
}

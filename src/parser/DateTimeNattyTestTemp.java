package parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeNattyTestTemp {
    @Test
    public void test() throws Exception {
       // ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
        dates.clear();
        dates.add("07/04/2015");
        Date1Parser date = new Date1Parser("mds sale 1 tues after 4/4");
        date.getDateList();
        String input = date.getInputLeft();
        DateTimeNattyParser dateTime4 = new DateTimeNattyParser(input );
        assertEquals(dates, dateTime4.getDateList());
    }
}

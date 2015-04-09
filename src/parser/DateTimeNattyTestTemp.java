package parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeNattyTestTemp {
    @Test
    public void test() throws Exception {
       // ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
       
     // test keyword 4: after _ days
        dates.clear();
        dates.add("10/04/2015");
        DateTimeNattyParser dateTime = new DateTimeNattyParser(
                "after 3 day zz");
        assertEquals(dates, dateTime.getDateList());

    }
}

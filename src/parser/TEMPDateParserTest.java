package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TEMPDateParserTest {

    @Test
    public void test() throws Exception {
        ArrayList<String> dates = new ArrayList<String>();
        // date and date
        dates.clear();
        dates.add("24/01/2015");
        dates.add("25/01/2015");

        DateTimeNattyParser dateTime7 = new DateTimeNattyParser(
                "mds sale 24 jan and 25 jan");
        assertEquals(dates, dateTime7.getDateList());
        
    }
}

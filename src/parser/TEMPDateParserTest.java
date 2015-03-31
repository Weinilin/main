package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TEMPDateParserTest {

    @Test
    public void test() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 6: next __month
        dates.clear();
        dates.add("28/04/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale start next 1 months "));
    }
}

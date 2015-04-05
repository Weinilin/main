package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeParserTestTEMP {

    @Test
    public void test() {
        ArrayList<String> dates = new ArrayList<String>();
        dates.add("29/03/2015");
        Date1Parser d1 = new Date1Parser("mds sale this sun");
        assertEquals(dates, d1.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d2 = new Date1Parser("mds sale this sunday");
        assertEquals(dates, d2.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d3 = new Date1Parser("mds sale this mon");
        assertEquals(dates, d3.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d5 = new Date1Parser("mds sale this monday");
        assertEquals(dates, d5.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d4 = new Date1Parser("mds sale this tues");
        assertEquals(dates, d4.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d6 = new Date1Parser("mds sale this tuesday");
        assertEquals(dates, d6.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d7 = new Date1Parser("mds sale this wed");
        assertEquals(dates, d7.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d9 = new Date1Parser("mds sale this wednesday");
        assertEquals(dates, d9.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d8 = new Date1Parser("mds sale this thrus");
        assertEquals(dates, d8.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d12 = new Date1Parser("mds sale this thursday");
        assertEquals(dates, d12.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d10 = new Date1Parser("mds sale this fri");
        assertEquals(dates, d10.getDateList());
        
        dates.add("29/03/2015");
        Date1Parser d11 = new Date1Parser("mds sale this friday");
        assertEquals(dates, d11.getDateList());
    }

}

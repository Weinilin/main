package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeParserTestTEMP {

    @Test
    public void test() throws Exception {
        ArrayList<String> dates = new ArrayList<String>();
     
        // test keyword 5: after _ week 
        dates.clear();
        dates.add("16/04/2015");
        Date1Parser d16 = new Date1Parser(("mds sale after 1 wk"));
        assertEquals(dates, d16.getDateList());
        
     // test keyword 5: after _ week 
        dates.clear();
        dates.add("02/07/2015");
        Date1Parser d17 = new Date1Parser(("mds sale after twelve wk"));
        assertEquals(dates, d17.getDateList());
        
     // test keyword 5: after _ week 
        dates.clear();
        dates.add("10/09/2015");
        Date1Parser d18 = new Date1Parser(("mds sale after twenty-two week"));
        assertEquals(dates, d18.getDateList());

        // test keyword 5: __ week after
        dates.clear();
        dates.add("03/09/2015");
        Date1Parser d21 = new Date1Parser(("mds sale twenty-one week after"));
        assertEquals(dates, d21.getDateList());
        
     // test keyword 5:  _ week after
        dates.clear();
        dates.add("16/04/2015");
        Date1Parser d22 = new Date1Parser(("mds sale one week after"));
        assertEquals(dates, d22.getDateList());
        
     // test keyword 5:  _ week after 
        dates.clear();
        dates.add("18/06/2015");
        Date1Parser d23 = new Date1Parser(("mds sale 10 week after"));
        assertEquals(dates, d23.getDateList());
        
        
        // test keyword 5: after _ month 
        dates.clear();
        dates.add("09/05/2015");
        Date1Parser d24 = new Date1Parser(("mds sale after 1 mth"));
        assertEquals(dates, d24.getDateList());
        
     // test keyword 5: after _ month 
        dates.clear();
        dates.add("09/04/2016");
        Date1Parser d25 = new Date1Parser(("mds sale after twelve mth"));
        assertEquals(dates, d25.getDateList());
        
     // test keyword 5: after _ month 
        dates.clear();
        dates.add("09/02/2017");
        Date1Parser d26 = new Date1Parser(("mds sale after twenty-two month"));
        assertEquals(dates, d26.getDateList());

        // test keyword 5: __ month after
        dates.clear();
        dates.add("09/01/2017");
        Date1Parser d27 = new Date1Parser(("mds sale twenty-one month after"));
        assertEquals(dates, d27.getDateList());
        
     // test keyword 5:  _ month after
        dates.clear();
        dates.add("09/05/2015");
        Date1Parser d28 = new Date1Parser(("mds sale one month after"));
        assertEquals(dates, d28.getDateList());
        
     // test keyword 5:  _ month after 
        dates.clear();
        dates.add("09/02/2016");
        Date1Parser d29 = new Date1Parser(("mds sale 10 month after"));
        assertEquals(dates, d29.getDateList());
    }
    
    }


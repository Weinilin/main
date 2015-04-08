package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class YearParserTest {

    @Test
    public void test() {
        //test in dd/mm/yyyy
       assertEquals(2014, YearParser.getYear("24/05/2014"));
       
       //test in dd/mm
       assertEquals(0, YearParser.getYear("24/05"));
       
       //test in mm/dd/yyyy
       assertEquals(2016, YearParser.getYear("Apr 05 2016"));
       
       //test in mm/dd
       assertEquals(0, YearParser.getYear("Apr 05 "));
       
     //test in dd/mm/yyyy
       assertEquals(2013, YearParser.getYear("05 Feb 2013"));
       
       //test in dd/mm
       assertEquals(0, YearParser.getYear("19 Oct"));
       
       //year not in length YYYY
       assertEquals(0, YearParser.getYear("19 Oct 19"));
    }
    }



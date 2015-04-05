package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * take note of the current date 
 * test case like today and tomorrow based on the current date
 * @author WeiLin
 *
 */
public class Date1ParserTest {

    @Test
    /*
     * test DD/MM/YYYY format
     */
    public void testDDMMYYY() {
        ArrayList<String> dates = new ArrayList<String>();
        // test without year
        dates.add("01/03/2015");
        Date1Parser d1 = new Date1Parser(("mds sale due 1/3 2:30am"));
        assertEquals(dates, d1.getDateList());

        // test date with year
        dates.clear();
        dates.add("01/03/2016");
        Date1Parser d2 = new Date1Parser(("1/3/2016."));
        assertEquals(dates, d2.getDateList());

        // test without year with -
        dates.clear();
        dates.add("01/03/2015");
        Date1Parser d4 = new Date1Parser(("mds sale due 1-3 2:30am"));
        assertEquals(dates, d4.getDateList());

        // test date with year with -
        dates.clear();
        dates.add("01/03/2016");
        Date1Parser d5 = new Date1Parser(("1-3-2016"));
        assertEquals(dates, d5.getDateList());

        // test without year with .
        dates.clear();
        dates.add("01/03/2015");
        Date1Parser d7 = new Date1Parser(("mds sale due 1.3 2:30am"));
        assertEquals(dates, d7.getDateList());

        // test date with year with -
        dates.clear();
        dates.add("01/03/2016");
        Date1Parser d8 = new Date1Parser(("1.3.2016."));
        assertEquals(dates, d8.getDateList());

    }

    @Test
    /*
     * test Day month in word Year date format 1) the format itself with year 2)
     * the format itself without year 3) case sensitively 4)space sensitively
     */
    public void testDDMonthInWordYYYY() {
        ArrayList<String> dates = new ArrayList<String>();
        // test date without year
        dates.clear();
        dates.add("12/09/2015");
        Date1Parser d1 = new Date1Parser(("krej lpp at 12 september."));
        assertEquals(dates, d1.getDateList());

        // test date with year
        dates.clear();
        dates.add("20/12/2016");

        Date1Parser d2 = new Date1Parser(("krej lpp at 20 december 2016"));
        assertEquals(dates, d2.getDateList());

        // test the case sensitivity
        dates.clear();
        dates.add("12/10/2015");

        Date1Parser d3 = new Date1Parser(("krej lpp at 12 OcToBor 3pm"));
        assertEquals(dates, d3.getDateList());

        // test with th
        dates.clear();
        dates.add("12/10/2015");

        Date1Parser d4 = new Date1Parser(("krej lpp at 12th OcToBor 3pm"));
        assertEquals(dates, d4.getDateList());

        // test with rd
        dates.clear();
        dates.add("03/09/2015");

        Date1Parser d5 = new Date1Parser(("krej lpp at 3rd september 3pm"));
        assertEquals(dates, d5.getDateList());

        // test with nd
        dates.clear();
        dates.add("02/04/2015");
        Date1Parser d6 = new Date1Parser(("krej lpp at 2nd april 3pm"));
        assertEquals(dates, d6.getDateList());

        // test with st
        dates.clear();
        dates.add("21/04/2015");
        Date1Parser d7 = new Date1Parser(("krej lpp at 21st april 3pm"));
        assertEquals(dates, d7.getDateList());
        
        
     // test with word 1-9
        dates.clear();
        dates.add("05/05/2015");
        Date1Parser d8 = new Date1Parser(("krej lpp at five may 3pm"));
        assertEquals(dates, d8.getDateList());
        
        //test with word 10-19
        dates.clear();
        dates.add("11/05/2015");
        Date1Parser d9 = new Date1Parser(("krej lpp at eleven may 3pm"));
        assertEquals(dates, d9.getDateList());
        
      //test with word 20-29
        dates.clear();
        dates.add("23/05/2015");
        Date1Parser d10 = new Date1Parser(("krej lpp at twenty-three may 3pm"));
        assertEquals(dates, d10.getDateList());
        
        
        //test with word 30-32
          dates.clear();
          dates.add("30/05/2015");
          Date1Parser d11 = new Date1Parser(("krej lpp at thirty may 3pm"));
          assertEquals(dates, d11.getDateList());

    }

    @Test
    /*
     * test Day shortcut month in word Year date format 1) the format itself
     * with year 2) the format itself without year 3) case sensitively 4)space
     * sensitively
     */
    public void testDDShortFormMonthInWordYYYY() {
        ArrayList<String> dates = new ArrayList<String>();

        // test without year
        dates.clear();
        dates.add("12/01/2015");
        Date1Parser d1 = new Date1Parser(("mds sale at 12 jan."));
        assertEquals(dates, d1.getDateList());

        // test date with year
        dates.clear();
        dates.add("13/03/2014");
        Date1Parser d2 = new Date1Parser(("mds sale 13 mar 2014"));
        assertEquals(dates, d2.getDateList());

        // test case sensitivity
        dates.clear();
        dates.add("13/07/2014");
        Date1Parser d3 = new Date1Parser(("mds sale 13 JuL 2014"));
        assertEquals(dates, d3.getDateList());

        // test space sensitivity
        dates.clear();
        dates.add("18/12/2017");
        Date1Parser d4 = new Date1Parser(("mds sale 18dec 2017"));
        assertEquals(dates, d4.getDateList());

        // test date with th
        dates.clear();
        dates.add("13/04/2014");
        Date1Parser d5 = new Date1Parser(("mds sale 13th apr 2014"));
        assertEquals(dates, d5.getDateList());

        // test date with rd
        dates.clear();
        dates.add("03/06/2014");
        Date1Parser d6 = new Date1Parser(("mds sale 3rd jun 2014"));
        assertEquals(dates, d6.getDateList());

        // test date with nd
        dates.clear();
        dates.add("02/02/2014");
        Date1Parser d7 = new Date1Parser(("mds sale 2nd feb 2014"));
        assertEquals(dates, d7.getDateList());

        // test date with st
        dates.clear();
        dates.add("21/02/2014");
        Date1Parser d8 = new Date1Parser(("mds sale 21st feb 2014"));
        assertEquals(dates, d8.getDateList());
        
     // test with word 1-9
        dates.clear();
        dates.add("05/06/2015");
        Date1Parser d12 = new Date1Parser(("krej lpp at five jun 3pm"));
        assertEquals(dates, d12.getDateList());
        
        //test with word 10-19
        dates.clear();
        dates.add("11/06/2015");
        Date1Parser d9 = new Date1Parser(("krej lpp at eleven jun 3pm"));
        assertEquals(dates, d9.getDateList());
        
      //test with word 20-29
        dates.clear();
        dates.add("23/06/2015");
        Date1Parser d10 = new Date1Parser(("krej lpp at twenty-three jun 3pm"));
        assertEquals(dates, d10.getDateList());
        
        
        //test with word 30-32
          dates.clear();
          dates.add("30/06/2015");
          Date1Parser d11 = new Date1Parser(("krej lpp at thirty jun 3pm"));
          assertEquals(dates, d11.getDateList());
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of days Interpret by
     * the vocab
     */
    public void testDaysApartVocabKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 5: tmr
        dates.clear();
        dates.add("05/04/2015");
        Date1Parser d1 = new Date1Parser(("mds sale from tmr"));
        assertEquals(dates, d1.getDateList());

        // test keyword 5: the following day
        dates.clear();
        dates.add("05/04/2015");

        Date1Parser d2 = new Date1Parser(("mds sale the following day"));
        assertEquals(dates, d2.getDateList());

        // test keyword 5: after tomorrow
        dates.clear();
        dates.add("06/04/2015");
        Date1Parser d4 = new Date1Parser(("mds sale after tomorrow"));
        assertEquals(dates, d4.getDateList());

        // test keyword 5: after tmr
        dates.clear();
        dates.add("06/04/2015");

        Date1Parser d5 = new Date1Parser(("mds sale after tmr"));
        assertEquals(dates, d5.getDateList());

        // test keyword 5: after today
        dates.clear();
        dates.add("05/04/2015");
        Date1Parser d6 = new Date1Parser(("mds sale after today"));
        assertEquals(dates, d6.getDateList());
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of week/month/year
     * Interpret by the vocab
     */
    public void testWeekMonthYearApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 6 : weeks later
        dates.clear();
        dates.add("11/04/2015");

        Date1Parser d1 = new Date1Parser("mds sale start 1 week later");
        assertEquals(dates, d1.getDateList());

        // test keyword 6 : wk later
        dates.clear();
        dates.add("11/04/2015");

        Date1Parser d3 = new Date1Parser("mds sale start 1 wk later");
        assertEquals(dates, d3.getDateList());

        // test keyword 6 : month later
        dates.clear();
        dates.add("04/05/2015");

        Date1Parser d2 = new Date1Parser("mds sale start 1 month later");
        assertEquals(dates, d2.getDateList());

        // test keyword 6 : month later
        dates.clear();
        dates.add("04/05/2015");

        Date1Parser d4 = new Date1Parser("mds sale start 1 mth later");
        assertEquals(dates, d4.getDateList());

        // test keyword 6 : year later
        dates.clear();
        dates.add("04/04/2016");

        Date1Parser d5 = new Date1Parser("mds sale start 1 year later");
        assertEquals(dates, d5.getDateList());

        // test keyword 6 : yr later
        dates.clear();
        dates.add("04/04/2016");

        Date1Parser d6 = new Date1Parser("mds sale start 1 yr later");
        assertEquals(dates, d6.getDateList());
    }
    
    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date to this mon/tues.../fri
     * And if today weekday pass the this weekday you keyed --> error
     */
    public void testThisWeekdayKeyword() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("12/04/2015");
        Date1Parser d1 = new Date1Parser("mds sale this sun");
        assertEquals(dates, d1.getDateList());
        
        // test full form of weekday and change to on
        dates.add("12/04/2015");
        Date1Parser d2 = new Date1Parser("mds sale on sunday");
        assertEquals(dates, d2.getDateList());
        
        dates.add("13/04/2015");
        Date1Parser d3 = new Date1Parser("mds sale this mon");
        assertEquals(dates, d3.getDateList());
        
        // test full form of weekday and change to by
        dates.add("13/04/2015");
        Date1Parser d5 = new Date1Parser("mds sale by monday");
        assertEquals(dates, d5.getDateList());
        
        dates.add("14/04/2015");
        Date1Parser d4 = new Date1Parser("mds sale this tues");
        assertEquals(dates, d4.getDateList());
        
        // test full form of weekday and change to due on
        dates.add("14/04/2015");
        Date1Parser d6 = new Date1Parser("mds sale due on tuesday");
        assertEquals(dates, d6.getDateList());
        
        dates.add("15/04/2015");
        Date1Parser d7 = new Date1Parser("mds sale this wed");
        assertEquals(dates, d7.getDateList());
        
        // test full form of weekday and change to on
        dates.add("15/04/2015");
        Date1Parser d9 = new Date1Parser("mds sale due wednesday");
        assertEquals(dates, d9.getDateList());
        
        dates.add("16/04/2015");
        Date1Parser d8 = new Date1Parser("mds sale this thrus");
        assertEquals(dates, d8.getDateList());
        
        // test full form of weekday and change to at
        dates.add("16/04/2015");
        Date1Parser d12 = new Date1Parser("mds sale at thursday");
        assertEquals(dates, d12.getDateList());
        
        //change to from
        dates.add("17/04/2015");
        Date1Parser d10 = new Date1Parser("mds sale from fri");
        assertEquals(dates, d10.getDateList());
        
        // test full form of weekday and change to 
        dates.add("17/04/2015");
        Date1Parser d11 = new Date1Parser("mds sale to friday");
        assertEquals(dates, d11.getDateList());

    }
    
    @Test
    /*
    * do take note that I use current date extract from laptop Thus, the date
    * result from date parser is the current date to next mon/tues.../fri
    * 
    */
    public void testWeekDayApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("19/04/2015");
        Date1Parser d1 = new Date1Parser("mds sale next sun");
        assertEquals(dates, d1.getDateList());
        
        // test full form of weekday and change to on
        dates.add("19/04/2015");
        Date1Parser d2 = new Date1Parser("mds sale on next sunday");
        assertEquals(dates, d2.getDateList());
        
        dates.add("20/04/2015");
        Date1Parser d3 = new Date1Parser("mds sale next mon");
        assertEquals(dates, d3.getDateList());
        
        // test full form of weekday and change to by
        dates.add("20/04/2015");
        Date1Parser d5 = new Date1Parser("mds sale by next monday");
        assertEquals(dates, d5.getDateList());
        
        dates.add("21/04/2015");
        Date1Parser d4 = new Date1Parser("mds sale next tues");
        assertEquals(dates, d4.getDateList());
        
        // test full form of weekday and change to due on
        dates.add("21/04/2015");
        Date1Parser d6 = new Date1Parser("mds sale due on next tuesday");
        assertEquals(dates, d6.getDateList());
        
        dates.add("22/04/2015");
        Date1Parser d7 = new Date1Parser("mds sale next wed");
        assertEquals(dates, d7.getDateList());
        
        // test full form of weekday and change to on
        dates.add("22/04/2015");
        Date1Parser d9 = new Date1Parser("mds sale due next wednesday");
        assertEquals(dates, d9.getDateList());
        
        dates.add("23/04/2015");
        Date1Parser d8 = new Date1Parser("mds sale next thrus");
        assertEquals(dates, d8.getDateList());
        
        // test full form of weekday and change to at
        dates.add("23/04/2015");
        Date1Parser d12 = new Date1Parser("mds sale at next thursday");
        assertEquals(dates, d12.getDateList());
        
        //change to from
        dates.add("24/04/2015");
        Date1Parser d10 = new Date1Parser("mds sale next fri");
        assertEquals(dates, d10.getDateList());
        
        // test full form of weekday and change to 
        dates.add("24/04/2015");
        Date1Parser d11 = new Date1Parser("mds sale to next friday");
        assertEquals(dates, d11.getDateList());
    }
    
    @Test
    /*
     * for day keyed exceeded the max day in that month, program auto set it by
     * plus the number of day exceeded to the current date eg: 30/02/2015 -->
     * 02/03/2015 max: 28 days in feb 2015
     * will appear a window
     */
    public void testDateError() {
        ArrayList<String> dates = new ArrayList<String>();

       
        Date1Parser d1 = new Date1Parser(("mds sale 30 feb 2015"));
   

    }

    @Test
    /*
     * 1st 20 Dec 2014 will be detect and then 20/3  will be detected check if
     * the position of the date stored is right position 20/3 -->20 Dec 2014
     */
    public void testPosition() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("20/03/2015");
        dates.add("20/12/2014");
        Date1Parser d1 = new Date1Parser(("mds sale 20/3 to 20 Dec 2014"));
        assertEquals(dates,
               d1.getDateList());
    }


}

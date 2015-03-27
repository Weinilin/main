package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateParserTest {

    @Test
    public void testNull() {
        ArrayList<String> dates = new ArrayList<String>();

        // test if nothing detected
        assertEquals(dates, DateParser.extractDate(""));
    }

    @Test
    /*
     * test DD/MM/YYYY format
     */
    public void testDDMMYYY() {
        ArrayList<String> dates = new ArrayList<String>();
        // test without year
        dates.add("01/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale due 1/3 2:30am"));

        // test date with year
        dates.clear();
        dates.add("01/03/2016");
        assertEquals(dates, DateParser.extractDate("1/3/2016."));

        // test date format (YYYY/MM/DD)
        dates.clear();
        dates.add("03/04/2016");
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
        assertEquals(dates, DateParser.extractDate("krej lpp at 12 september."));

        // test date with year
        dates.clear();
        dates.add("20/12/2016");
        assertEquals(dates,
                DateParser.extractDate("krej lpp at 20 december 2016"));

        // test the case sensitivity
        dates.clear();
        dates.add("12/10/2015");
        assertEquals(dates,
                DateParser.extractDate("krej lpp at 12 OcToBor 3pm"));

        // test with th
        dates.clear();
        dates.add("12/10/2015");
        assertEquals(dates,
                DateParser.extractDate("krej lpp at 12th OcToBor 3pm"));

        // test with rd
        dates.clear();
        dates.add("03/09/2015");
        assertEquals(dates,
                DateParser.extractDate("krej lpp at 3rd september 3pm"));

        // test with nd
        dates.clear();
        dates.add("02/04/2015");
        assertEquals(dates, DateParser.extractDate("krej lpp at 2nd april 3pm"));

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
        assertEquals(dates, DateParser.extractDate("mds sale 12 jan."));

        // test date with year
        dates.clear();
        dates.add("13/03/2014");
        assertEquals(dates, DateParser.extractDate("mds sale 13 mar 2014"));

        // test case sensitivity
        dates.clear();
        dates.add("13/07/2014");
        assertEquals(dates, DateParser.extractDate("mds sale 13 JuL 2014"));

        // test space sensitivity
        dates.clear();
        dates.add("18/12/2017");
        assertEquals(dates, DateParser.extractDate("mds sale 18dec 2017"));

        // test date with th
        dates.clear();
        dates.add("13/04/2014");
        assertEquals(dates, DateParser.extractDate("mds sale 13th apr 2014"));

        // test date with rd
        dates.clear();
        dates.add("03/06/2014");
        assertEquals(dates, DateParser.extractDate("mds sale 3rd jun 2014"));

        // test date with nd
        dates.clear();
        dates.add("02/02/2014");
        assertEquals(dates, DateParser.extractDate("mds sale 2nd feb 2014"));
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of days
     */
    public void testDayApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 4: after _ days
        dates.clear();
        dates.add("30/03/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale start in after 3 days"));

        // test keyword 4: next _ days
        dates.clear();
        dates.add("31/03/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale start in next 4 days"));

        // test keyword 4: _ days from now
        dates.clear();
        dates.add("01/04/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale start 5 days from now"));

        // test day without s
        assertEquals(dates,
                DateParser.extractDate("mds sale start 5 day from now"));
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of days Interpret by
     * the vocab
     */
    public void testDaysApartVocabKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 5: tomorrow
        dates.clear();
        dates.add("28/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale from tomorrow"));

        // test keyword 5: tmr
        dates.clear();
        dates.add("28/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale from tmr"));

        // test keyword 5: the following day
        dates.clear();
        dates.add("28/03/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale the following day"));

        // test keyword 5: the next day
        dates.clear();
        dates.add("28/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale the next day"));

        // test keyword 5: after today
        dates.clear();
        dates.add("28/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale the next day"));

        // test keyword 5: after tomorrow
        dates.clear();
        dates.add("29/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale after tomorrow"));

        // test keyword 5: after today
        dates.clear();
        dates.add("28/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale after today"));
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of week/month/year Interpret by
     * the vocab
     */
    public void testWeekMonthYearApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 6 : weeks later
        dates.clear();
        dates.add("03/04/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale start 1 week later"));

        // test s sensitivity(with s behind week or no s)
        assertEquals(dates,
                DateParser.extractDate("mds sale start 1 weeks later"));

        // test keyword 6: month later
        dates.clear();
        dates.add("27/04/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale start 1 month later"));

        // test s sensitivity(with s behind month or no s)
        assertEquals(dates,
                DateParser.extractDate("mds sale start 1 months later"));

        // test keyword 6: year later
        dates.clear();
        dates.add("27/03/2018");
        assertEquals(dates,
                DateParser.extractDate("mds sale start 3 year later"));

        // test s sensitivity(with s behind year or no s)
        assertEquals(dates,
                DateParser.extractDate("mds sale start 3 years later"));
    }

    @Test
    /*
    * do take note that I use current date extract from laptop Thus, the date
    * result from date parser is the current date to next mon/tues.../fri
    * 
    */
    public void testWeekDayApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test date keyword 7
        dates.clear();
        dates.add("30/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale next monday"));

        dates.clear();
        dates.add("05/04/2015");
        assertEquals(dates, DateParser.extractDate("next sun"));
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date to this mon/tues.../fri
     * 
     */
    public void testThisWeekdayKeyword() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("28/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale this saturday"));

    }

    @Test
    // test the date contain the escaped char
    public void testDateWithEscapedChar() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.clear();
        assertEquals(dates, DateParser.extractDate("mds sale due ~1/3~ at 2pm"));
    }

    @Test
    /*
     * for day keyed exceeded the max day in that month, program auto set it by
     * plus the number of day exceeded to the current date eg: 30/02/2015 -->
     * 02/03/2015 max: 28 days in feb 2015
     */
    public void testDateError() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("02/03/2015");
        assertEquals(dates, DateParser.extractDate("mds sale 30 feb 2015"));

    }

    @Test
    /*
     * 1st 20/3 will be detect and then 20 Dec 2014 will be detected check if
     * the position of the date stored is right position 20 Dec 2014 --> 20/3
     */
    public void testPosition() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("20/12/2014");
        dates.add("20/03/2015");
        assertEquals(dates,
                DateParser.extractDate("mds sale 20 Dec 2014 to 20/3"));
    }

}

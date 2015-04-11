package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * take note of the current date test case like today and tomorrow based on the
 * current date
 * 
 * @author A0112823R
 *
 */
public class DateParserTest {

    @Test
    /*
     * test DD/MM/YYYY format
     */
    public void testDDMMYYY() {
        ArrayList<String> dates = new ArrayList<String>();
        // test without year
        dates.add("01/03/2015");
        DateParser d1 = new DateParser(("mds sale due 1/3 2:30am"));
        assertEquals(dates, d1.getDateList());

        // test date with year
        dates.clear();
        dates.add("01/03/2016");
        DateParser d2 = new DateParser(("1/3/2016."));
        assertEquals(dates, d2.getDateList());

        // test without year with -
        dates.clear();
        dates.add("01/03/2015");
        DateParser d4 = new DateParser(("mds sale due 1-3 2:30am"));
        assertEquals(dates, d4.getDateList());

        // test date with year with -
        dates.clear();
        dates.add("01/03/2016");
        DateParser d5 = new DateParser(("1-3-2016"));
        assertEquals(dates, d5.getDateList());

        // test without year with .
        dates.clear();
        dates.add("01/03/2015");
        DateParser d7 = new DateParser(("mds sale due 1.3 2:30am"));
        assertEquals(dates, d7.getDateList());

        // test date with year with -
        dates.clear();
        dates.add("01/03/2016");
        DateParser d8 = new DateParser(("1.3.2016."));
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
        DateParser d1 = new DateParser(("krej lpp at 12 september."));
        assertEquals(dates, d1.getDateList());

        // test date with year
        dates.clear();
        dates.add("20/12/2016");

        DateParser d2 = new DateParser(("krej lpp at 20 december 2016"));
        assertEquals(dates, d2.getDateList());

        // test the case sensitivity
        dates.clear();
        dates.add("12/10/2015");

        DateParser d3 = new DateParser(("krej lpp at 12 OcToBor 3pm"));
        assertEquals(dates, d3.getDateList());

        // test with th
        dates.clear();
        dates.add("12/10/2015");

        DateParser d4 = new DateParser(("krej lpp at 12th OcToBor 3pm"));
        assertEquals(dates, d4.getDateList());

        // test with rd
        dates.clear();
        dates.add("03/09/2015");

        DateParser d5 = new DateParser(("krej lpp at 3rd september 3pm"));
        assertEquals(dates, d5.getDateList());

        // test with nd
        dates.clear();
        dates.add("02/04/2015");
        DateParser d6 = new DateParser(("krej lpp at 2nd april 3pm"));
        assertEquals(dates, d6.getDateList());

        // test with st
        dates.clear();
        dates.add("21/04/2015");
        DateParser d7 = new DateParser(("krej lpp at 21st april 3pm"));
        assertEquals(dates, d7.getDateList());

        // test with word 1-9
        dates.clear();
        dates.add("05/05/2015");
        DateParser d8 = new DateParser(("krej lpp at five may 3pm"));
        assertEquals(dates, d8.getDateList());

        // test with word 10-19
        dates.clear();
        dates.add("11/05/2015");
        DateParser d9 = new DateParser(("krej lpp at eleven may 3pm"));
        assertEquals(dates, d9.getDateList());

        // test with word 20-29
        dates.clear();
        dates.add("23/05/2015");
        DateParser d10 = new DateParser(("krej lpp at twenty-three may 3pm"));
        assertEquals(dates, d10.getDateList());

        // test with word 30-32
        dates.clear();
        dates.add("30/05/2015");
        DateParser d11 = new DateParser(("krej lpp at thirty may 3pm"));
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

        // test 4th of April in the year of 2015 in sentence
        dates.clear();
        dates.add("04/04/2015");
        DateParser d = new DateParser(
                "mummy is nagging 4th of April in the year of 2015");
        assertEquals(dates, d.getDateList());

        // test without year
        dates.clear();
        dates.add("12/01/2015");
        DateParser d1 = new DateParser(("mds sale at 12 jan."));
        assertEquals(dates, d1.getDateList());

        // test date with year
        dates.clear();
        dates.add("13/03/2014");
        DateParser d2 = new DateParser(("mds sale 13 mar 2014"));
        assertEquals(dates, d2.getDateList());

        // test case sensitivity
        dates.clear();
        dates.add("13/07/2014");
        DateParser d3 = new DateParser(("mds sale 13 JuL 2014"));
        assertEquals(dates, d3.getDateList());

        // test space sensitivity
        dates.clear();
        dates.add("18/12/2017");
        DateParser d4 = new DateParser(("mds sale 18dec 2017"));
        assertEquals(dates, d4.getDateList());

        // test date with th
        dates.clear();
        dates.add("13/04/2014");
        DateParser d5 = new DateParser(("mds sale 13th apr 2014"));
        assertEquals(dates, d5.getDateList());

        // test date with rd
        dates.clear();
        dates.add("03/06/2014");
        DateParser d6 = new DateParser(("mds sale 3rd jun 2014"));
        assertEquals(dates, d6.getDateList());

        // test date with nd
        dates.clear();
        dates.add("02/02/2014");
        DateParser d7 = new DateParser(("mds sale 2nd feb 2014"));
        assertEquals(dates, d7.getDateList());

        // test date with st
        dates.clear();
        dates.add("21/02/2014");
        DateParser d8 = new DateParser(("mds sale 21st feb 2014"));
        assertEquals(dates, d8.getDateList());

        // test with word 1-9
        dates.clear();
        dates.add("05/06/2015");
        DateParser d12 = new DateParser(("krej lpp at five jun 3pm"));
        assertEquals(dates, d12.getDateList());

        // test with word 10-19
        dates.clear();
        dates.add("11/06/2015");
        DateParser d9 = new DateParser(("krej lpp at eleven jun 3pm"));
        assertEquals(dates, d9.getDateList());

        // test with word 20-29
        dates.clear();
        dates.add("23/06/2015");
        DateParser d10 = new DateParser(("krej lpp at twenty-three jun 3pm"));
        assertEquals(dates, d10.getDateList());

        // test with word 30-32
        dates.clear();
        dates.add("30/06/2015");
        DateParser d11 = new DateParser(("krej lpp at thirty jun 3pm"));
        assertEquals(dates, d11.getDateList());
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of days Interpret by
     * the vocab current date of mine : 11/4/2015
     */
    public void testDaysApartVocabKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 5: tmr
        dates.clear();
        dates.add("12/04/2015");
        DateParser d1 = new DateParser(("mds sale from tmr"));
        assertEquals(dates, d1.getDateList());

        // test keyword 5: the following day
        dates.clear();
        dates.add("12/04/2015");

        DateParser d2 = new DateParser(("mds sale the following day"));
        assertEquals(dates, d2.getDateList());

        // test keyword 5: after tomorrow
        dates.clear();
        dates.add("13/04/2015");
        DateParser d4 = new DateParser(("mds sale after tomorrow"));
        assertEquals(dates, d4.getDateList());

        // test keyword 5: after tmr
        dates.clear();
        dates.add("13/04/2015");

        DateParser d5 = new DateParser(("mds sale after tmr"));
        assertEquals(dates, d5.getDateList());

        // test keyword 5: after today
        dates.clear();
        dates.add("12/04/2015");
        DateParser d6 = new DateParser(("mds sale after today"));
        assertEquals(dates, d6.getDateList());

        // test keyword 5: _ day later
        dates.clear();
        dates.add("12/04/2015");
        DateParser d7 = new DateParser(("mds sale one day later"));
        assertEquals(dates, d7.getDateList());

        // test keyword 5: _ day later in 4
        dates.clear();
        dates.add("15/04/2015");
        DateParser d8 = new DateParser(("mds sale 4 day later"));
        assertEquals(dates, d8.getDateList());

        // test keyword 5: _ day later in 4
        dates.clear();
        dates.add("03/05/2015");
        DateParser d9 = new DateParser(("mds sale twenty-two day later"));
        assertEquals(dates, d9.getDateList());

        // test keyword 5: after _ day
        dates.clear();
        dates.add("02/05/2015");
        DateParser d10 = new DateParser(("mds sale after twenty-one day"));
        assertEquals(dates, d10.getDateList());

        // test keyword 5: after _ day
        dates.clear();
        dates.add("12/04/2015");
        DateParser d11 = new DateParser(("mds sale after one day"));
        assertEquals(dates, d11.getDateList());

        // test keyword 5: after _ day
        dates.clear();
        dates.add("21/04/2015");
        DateParser d12 = new DateParser(("mds sale after 10 day"));
        assertEquals(dates, d12.getDateList());

        // test keyword 5: _ day after
        dates.clear();
        dates.add("02/05/2015");
        DateParser d13 = new DateParser(("mds sale twenty-one day after"));
        assertEquals(dates, d13.getDateList());

        // test keyword 5: _ day after
        dates.clear();
        dates.add("12/04/2015");
        DateParser d14 = new DateParser(("mds sale one day after"));
        assertEquals(dates, d14.getDateList());

        // test keyword 5: _ day after
        dates.clear();
        dates.add("21/04/2015");
        DateParser d15 = new DateParser(("mds sale 10 day after"));
        assertEquals(dates, d15.getDateList());
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of week/month/year
     * Interpret by the vocab Current date : 11/04/2015
     */
    public void testWeekMonthYearApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 6 : weeks later
        dates.clear();
        dates.add("18/04/2015");

        DateParser d1 = new DateParser("mds sale start 1 week later");
        assertEquals(dates, d1.getDateList());

        // test keyword 6 : wk later
        dates.clear();
        dates.add("18/04/2015");

        DateParser d3 = new DateParser("mds sale start 1 wk later");
        assertEquals(dates, d3.getDateList());

        // test keyword 6 : wk later
        dates.clear();
        dates.add("02/05/2015");

        DateParser d9 = new DateParser("mds sale start three wk later");
        assertEquals(dates, d9.getDateList());

        // test keyword 6 : month later
        dates.clear();
        dates.add("11/05/2015");

        DateParser d2 = new DateParser("mds sale start 1 month later");
        assertEquals(dates, d2.getDateList());

        // test keyword 6 : month later
        dates.clear();
        dates.add("11/05/2015");

        DateParser d4 = new DateParser("mds sale start 1 mth later");
        assertEquals(dates, d4.getDateList());

        // test keyword 6 : month later
        dates.clear();
        dates.add("11/06/2015");

        DateParser d8 = new DateParser("mds sale start two mth later");
        assertEquals(dates, d8.getDateList());

        // test keyword 6 : year later
        dates.clear();
        dates.add("11/04/2016");

        DateParser d5 = new DateParser("mds sale start 1 year later");
        assertEquals(dates, d5.getDateList());

        // test keyword 6 : yr later
        dates.clear();
        dates.add("11/04/2016");

        DateParser d6 = new DateParser("mds sale start 1 yr later");
        assertEquals(dates, d6.getDateList());

        // test keyword 6 : yr later in word
        dates.clear();
        dates.add("11/04/2027");

        DateParser d7 = new DateParser("mds sale start twelve yr later");
        assertEquals(dates, d7.getDateList());

        // test keyword 5: after _ year
        dates.clear();
        dates.add("11/04/2016");
        DateParser d12 = new DateParser(("mds sale after 1 yr"));
        assertEquals(dates, d12.getDateList());

        // test keyword 5: after _ year
        dates.clear();
        dates.add("11/04/2027");
        DateParser d19 = new DateParser(("mds sale after twelve yr"));
        assertEquals(dates, d19.getDateList());

        // test keyword 5: after _ year
        dates.clear();
        dates.add("11/04/2037");
        DateParser d20 = new DateParser(("mds sale after twenty-two year"));
        assertEquals(dates, d20.getDateList());

        // test keyword 5: __ year after
        dates.clear();
        dates.add("11/04/2036");
        DateParser d13 = new DateParser(("mds sale twenty-one year after"));
        assertEquals(dates, d13.getDateList());

        // test keyword 5: _ year after
        dates.clear();
        dates.add("11/04/2016");
        DateParser d14 = new DateParser(("mds sale one year after"));
        assertEquals(dates, d14.getDateList());

        // test keyword 5: _ year after
        dates.clear();
        dates.add("11/04/2025");
        DateParser d15 = new DateParser(("mds sale 10 year after"));
        assertEquals(dates, d15.getDateList());

        // test keyword 5: after _ week
        dates.clear();
        dates.add("18/04/2015");
        DateParser d16 = new DateParser(("mds sale after 1 wk"));
        assertEquals(dates, d16.getDateList());

        // test keyword 5: after _ week
        dates.clear();
        dates.add("04/07/2015");
        DateParser d17 = new DateParser(("mds sale after twelve wk"));
        assertEquals(dates, d17.getDateList());

        // test keyword 5: after _ week
        dates.clear();
        dates.add("12/09/2015");
        DateParser d18 = new DateParser(("mds sale after twenty-two week"));
        assertEquals(dates, d18.getDateList());

        // test keyword 5: __ week after
        dates.clear();
        dates.add("05/09/2015");
        DateParser d21 = new DateParser(("mds sale twenty-one week after"));
        assertEquals(dates, d21.getDateList());

        // test keyword 5: _ week after
        dates.clear();
        dates.add("18/04/2015");
        DateParser d22 = new DateParser(("mds sale one week after"));
        assertEquals(dates, d22.getDateList());

        // test keyword 5: _ week after
        dates.clear();
        dates.add("20/06/2015");
        DateParser d23 = new DateParser(("mds sale 10 week after"));
        assertEquals(dates, d23.getDateList());

        // test keyword 5: after _ month
        dates.clear();
        dates.add("11/05/2015");
        DateParser d24 = new DateParser(("mds sale after 1 mth"));
        assertEquals(dates, d24.getDateList());

        // test keyword 5: after _ month
        dates.clear();
        dates.add("11/04/2016");
        DateParser d25 = new DateParser(("mds sale after twelve mth"));
        assertEquals(dates, d25.getDateList());

        // test keyword 5: after _ month
        dates.clear();
        dates.add("11/02/2017");
        DateParser d26 = new DateParser(("mds sale after twenty-two month"));
        assertEquals(dates, d26.getDateList());

        // test keyword 5: __ month after
        dates.clear();
        dates.add("11/01/2017");
        DateParser d27 = new DateParser(("mds sale twenty-one month after"));
        assertEquals(dates, d27.getDateList());

        // test keyword 5: _ month after
        dates.clear();
        dates.add("11/05/2015");
        DateParser d28 = new DateParser(("mds sale one month after"));
        assertEquals(dates, d28.getDateList());

        // test keyword 5: _ month after
        dates.clear();
        dates.add("11/02/2016");
        DateParser d29 = new DateParser(("mds sale 10 month after"));
        assertEquals(dates, d29.getDateList());
    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date to this mon/tues.../fri And
     * if today weekday pass the this weekday you keyed --> error Current date :
     * 06/04/2015 MON
     */
    public void testThisWeekdayKeyword() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("12/04/2015");
        DateParser d1 = new DateParser("mds sale this sun");
        assertEquals(dates, d1.getDateList());

        // test full form of weekday and change to on
        dates.clear();
        dates.add("12/04/2015");
        DateParser d2 = new DateParser("mds sale on sunday");
        assertEquals(dates, d2.getDateList());

        dates.clear();
        dates.add("06/04/2015");
        DateParser d3 = new DateParser("mds sale this mon");
        assertEquals(dates, d3.getDateList());

        // test full form of weekday and change to by
        dates.clear();
        dates.add("06/04/2015");
        DateParser d5 = new DateParser("mds sale by monday");
        assertEquals(dates, d5.getDateList());

        dates.clear();
        dates.add("07/04/2015");
        DateParser d4 = new DateParser("mds sale this tues");
        assertEquals(dates, d4.getDateList());

        // test full form of weekday and change to due on
        dates.clear();
        dates.add("07/04/2015");
        DateParser d6 = new DateParser("mds sale due on tuesday");
        assertEquals(dates, d6.getDateList());

        dates.clear();
        dates.add("08/04/2015");
        DateParser d7 = new DateParser("mds sale this wed");
        assertEquals(dates, d7.getDateList());

        // test full form of weekday and change to on
        dates.clear();
        dates.add("08/04/2015");
        DateParser d9 = new DateParser("mds sale due wednesday");
        assertEquals(dates, d9.getDateList());

        dates.clear();
        dates.add("09/04/2015");
        DateParser d8 = new DateParser("mds sale this thurs");
        assertEquals(dates, d8.getDateList());

        // test full form of weekday and change to at
        dates.clear();
        dates.add("09/04/2015");
        DateParser d12 = new DateParser("mds sale at thursday");
        assertEquals(dates, d12.getDateList());

        // change to from
        dates.clear();
        dates.add("10/04/2015");
        DateParser d10 = new DateParser("mds sale from fri");
        assertEquals(dates, d10.getDateList());

        // test full form of weekday and change to
        dates.clear();
        dates.add("10/04/2015");
        DateParser d11 = new DateParser("mds sale to fri");
        assertEquals(dates, d11.getDateList());

    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date to next mon/tues.../fri
     * current date : 10/4/2015
     */
    public void testWeekDayApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("19/04/2015");
        DateParser d1 = new DateParser("mds sale next sun");
        assertEquals(dates, d1.getDateList());

        // test full form of weekday and change to on
        dates.clear();
        dates.add("19/04/2015");
        DateParser d2 = new DateParser("mds sale on next sunday");
        assertEquals(dates, d2.getDateList());

        dates.clear();
        dates.add("13/04/2015");
        DateParser d3 = new DateParser("mds sale next mon");
        assertEquals(dates, d3.getDateList());

        // test full form of weekday and change to by
        dates.clear();
        dates.add("13/04/2015");
        DateParser d5 = new DateParser("mds sale by next monday");
        assertEquals(dates, d5.getDateList());

        dates.clear();
        dates.add("14/04/2015");
        DateParser d4 = new DateParser("mds sale next tues");
        assertEquals(dates, d4.getDateList());

        // test full form of weekday and change to due on
        dates.clear();
        dates.add("14/04/2015");
        DateParser d6 = new DateParser("mds sale due on next tuesday");
        assertEquals(dates, d6.getDateList());

        dates.clear();
        dates.add("15/04/2015");
        DateParser d7 = new DateParser("mds sale next wed");
        assertEquals(dates, d7.getDateList());

        // test full form of weekday and change to on
        dates.clear();
        dates.add("15/04/2015");
        DateParser d9 = new DateParser("mds sale due next wednesday");
        assertEquals(dates, d9.getDateList());

        dates.clear();
        dates.add("16/04/2015");
        DateParser d8 = new DateParser("mds sale next thurs");
        assertEquals(dates, d8.getDateList());

        // test full form of weekday and change to at
        dates.clear();
        dates.add("16/04/2015");
        DateParser d12 = new DateParser("mds sale at next thursday");
        assertEquals(dates, d12.getDateList());

        // change to from
        dates.clear();
        dates.add("17/04/2015");
        DateParser d10 = new DateParser("mds sale next fri");
        assertEquals(dates, d10.getDateList());

        // test full form of weekday and change to
        dates.clear();
        dates.add("17/04/2015");
        DateParser d11 = new DateParser("mds sale to next friday");
        assertEquals(dates, d11.getDateList());
    }

    @Test
    /*
     * for day keyed exceeded the max day in that month, program auto set it by
     * plus the number of day exceeded to the current date eg: 30/02/2015 -->
     * 02/03/2015 max: 28 days in feb 2015 will appear a window
     */
    public void testDateError() {
        ArrayList<String> dates = new ArrayList<String>();

        DateParser d1 = new DateParser(("mds sale 30 feb 2015"));

    }

    @Test
    /*
     * 1st 20 Dec 2014 will be detect and then 20/3 will be detected check if
     * the position of the date stored is right position 20/3 -->20 Dec 2014
     */
    public void testPosition() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("20/03/2015");
        dates.add("20/12/2014");
        DateParser d1 = new DateParser(("mds sale 20/3 to 20 Dec 2014"));
        assertEquals(dates, d1.getDateList());
    }

    @Test
    // PS current date : 10/4/2015 make changes based on ur current date
    public void testThisWeekMonthYear() {
        ArrayList<String> dates = new ArrayList<String>();

        dates.add("31/12/2015");
        DateParser d1 = new DateParser(("mds sale this year"));
        assertEquals(dates, d1.getDateList());

        // change to yr
        dates.clear();
        dates.add("31/12/2015");
        DateParser d2 = new DateParser(("mds sale this yr"));
        assertEquals(dates, d2.getDateList());

        // change to yr with s
        dates.clear();
        dates.add("31/12/2015");
        DateParser d3 = new DateParser(("mds sale this yrs"));
        assertEquals(dates, d3.getDateList());

        // change to month
        dates.clear();
        dates.add("30/04/2015");
        DateParser d4 = new DateParser(("mds sale this month"));
        assertEquals(dates, d4.getDateList());

        // change to mth
        dates.clear();
        dates.add("30/04/2015");
        DateParser d5 = new DateParser(("mds sale this mth"));
        assertEquals(dates, d5.getDateList());

        // change to mth with s
        dates.clear();
        dates.add("30/04/2015");
        DateParser d6 = new DateParser(("mds sale this mths"));
        assertEquals(dates, d6.getDateList());

        // change to week with s
        dates.clear();
        dates.add("12/04/2015");
        DateParser d7 = new DateParser(("mds sale this weeks"));
        assertEquals(dates, d7.getDateList());

        // change to week
        dates.clear();
        dates.add("12/04/2015");
        DateParser d8 = new DateParser(("mds sale this week"));
        assertEquals(dates, d8.getDateList());

        // change to wk
        dates.clear();
        dates.add("12/04/2015");
        DateParser d9 = new DateParser(("mds sale this wk"));
        assertEquals(dates, d9.getDateList());

        // when there is both wk and month
        dates.clear();
        dates.add("12/04/2015");
        dates.add("30/04/2015");
        DateParser d10 = new DateParser(("mds sale this wk and this month"));
        assertEquals(dates, d10.getDateList());
    }

}

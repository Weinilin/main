package parser;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Test;

public class Time1ParserTest {

    @Test
    /*
     * test the escaped char work
     */
    public void timeTestForHashTag() throws Exception {
        ArrayList<String> times = new ArrayList<String>();

        // test when all of the time is ~ ~
        times.clear();

        Time1Parser t1 = new Time1Parser("mds sale ~11pm to 1:30 pm~.");
        assertEquals(times, t1.getTimeList());
    }

    @Test
    public void testTimeToTime() throws Exception {

        // test without symbol of pm
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("06:00");
        times.add("18:00");
        Time1Parser dateTime = new Time1Parser("mds sale from 6 - 6pm");
        assertEquals(times, dateTime.getTimeList());

        // test without symbol of am
        times.clear();
        times.add("00:00");
        times.add("06:00");
        Time1Parser dateTime1 = new Time1Parser("mds sale from 12 - 6am");
        // assertEquals(times, dateTime1.getTimeList());

        // test with symbol of am and start time < end time
        times.clear();
        times.add("01:30");
        times.add("06:30");
        Time1Parser dateTime2 = new Time1Parser("mds sale from 1:30 - 6:30am");
        assertEquals(times, dateTime2.getTimeList());

        // test with symbol of pm and start time< end time
        times.clear();
        times.add("15:20");
        times.add("18:10");
        Time1Parser dateTime3 = new Time1Parser(
                "3:20 - 6:10pm nothing better to do");
        assertEquals(times, dateTime3.getTimeList());

        // test with symbol of pm with . and start time > end time
        times.clear();
        times.add("07:20");
        times.add("18:10");
        Time1Parser dateTime8 = new Time1Parser(
                "7.20 - 6.10pm nothing better to do");
        assertEquals(times, dateTime8.getTimeList());

        // test with symbol of pm with ,
        times.clear();
        times.add("06:20");
        times.add("18:10");
        Time1Parser dateTime14 = new Time1Parser(
                "6,20 - 6,10pm nothing better to do");
        assertEquals(times, dateTime14.getTimeList());

        // test with symbol of with both same number and without space
        times.clear();
        times.add("06:20");
        times.add("18:20");
        Time1Parser dateTime9 = new Time1Parser(
                "6,20-6,20pm nothing better to do");
        assertEquals(times, dateTime9.getTimeList());

        // test with both am
        times.clear();
        times.add("01:00");
        times.add("06:00");
        Time1Parser dateTime4 = new Time1Parser("mds sale from 1am - 6am");
        assertEquals(times, dateTime4.getTimeList());

        // test with both pm
        times.clear();
        times.add("13:30");
        times.add("18:30");
        Time1Parser dateTime5 = new Time1Parser("mds sale from 1:30pm - 6:30pm");
        assertEquals(times, dateTime5.getTimeList());

        // test with pm on start time and the start number > end number
        times.clear();
        times.add("21:30");
        times.add("06:30");
        Time1Parser dateTime6 = new Time1Parser("mds sale from 9:30pm - 6:30");
        assertEquals(times, dateTime6.getTimeList());

        // test with pm on start time, the start number < end number and without
        // no :
        times.clear();
        times.add("13:00");
        times.add("18:00");
        Time1Parser dateTime11 = new Time1Parser("mds sale from 1pm - 6");
        assertEquals(times, dateTime11.getTimeList());

        // test with am on start time and both end time set to am/pm is after
        // start time
        times.clear();
        times.add("01:30");
        times.add("06:30");
        Time1Parser dateTime10 = new Time1Parser("mds sale from 1:30am - 6:30");
        assertEquals(times, dateTime10.getTimeList());

        // test with am on start time and both end time set to am/pm is before
        // start time
        // timed test
        times.clear();
        times.add("19:30");
        times.add("05:10");
        Time1Parser dateTime12 = new Time1Parser("mds sale from 7:30pm - 5:10");
        assertEquals(times, dateTime12.getTimeList());

        // test both with the same time
        times.clear();
        times.add("06:30");
        times.add("18:30");
        Time1Parser dateTime13 = new Time1Parser("mds sale from 6:30am - 6:30");
        assertEquals(times, dateTime13.getTimeList());

        // test one end time set to am is before and the other set to pm to
        // after
        // start time
        times.clear();
        times.add("04:30");
        times.add("06:30");
        Time1Parser dateTime21 = new Time1Parser("mds sale from 4:30am-6:30");
        assertEquals(times, dateTime21.getTimeList());

        // test without space
        times.clear();
        times.add("06:30");
        times.add("18:30");
        Time1Parser dateTime15 = new Time1Parser("mds sale from 6:30am-6:30");
        assertEquals(times, dateTime15.getTimeList());

        // test am behind with both start time after end
        times.clear();
        times.add("20:30");
        times.add("06:30");
        Time1Parser dateTime16 = new Time1Parser("mds sale from 8:30-6:30am");
        assertEquals(times, dateTime16.getTimeList());

        // test pm/am behind with both start time before end
        times.clear();
        times.add("15:30");
        times.add("20:30");
        Time1Parser dateTime17 = new Time1Parser("mds sale from 3:30-8:30pm");
        assertEquals(times, dateTime17.getTimeList());

        // test am/pm behind and one am start time before and one pm start time
        // after
        // end time.
        times.clear();
        times.add("07:30");
        times.add("18:30");
        Time1Parser dateTime18 = new Time1Parser("mds sale from 7:30-6:30pm");
        assertEquals(times, dateTime18.getTimeList());

        // test both same
        times.clear();
        times.add("07:30");
        times.add("19:30");
        Time1Parser dateTime19 = new Time1Parser("mds sale from 7:30-7:30pm");
        assertEquals(times, dateTime19.getTimeList());

        // test with space
        times.clear();
        times.add("07:30");
        times.add("19:30");
        Time1Parser dateTime20 = new Time1Parser("mds sale from 7:30 - 7:30pm");
        assertEquals(times, dateTime20.getTimeList());

        // test to instead of - : with semicolon
        // take note "white space"to"white space" then it will work.
        times.clear();
        times.add("01:00");
        times.add("06:30");
        Time1Parser dateTime7 = new Time1Parser("mds sale from 1:00 to 6:30am");
        assertEquals(times, dateTime7.getTimeList());

        // test to instead of - : with semicolon
        // take note "white space"to"white space" then it will work.
        times.clear();
        times.add("01:00");
        times.add("06:30");
        Time1Parser dateTime22 = new Time1Parser("mds sale from 1:00am to 6:30");
        assertEquals(times, dateTime22.getTimeList());

    }

    @Test
    public void testPastMidnightOrNoonKeyword() throws Exception {
        // test past midnight
        ArrayList<String> times1 = new ArrayList<String>();
        times1.clear();
        times1.add("00:01");
        Time1Parser time = new Time1Parser("mds sale start past midnight");
        assertEquals(times1, time.getTimeList());

        // test past noon
        times1.clear();
        times1.add("12:01");
        Time1Parser t1 = new Time1Parser("past noon run");
        assertEquals(times1, t1.getTimeList());

    }

    @Test
    public void testHourApartKeyword() throws Exception {

        // test boundary of when adding the hour will exceed 23 of pm
        ArrayList<String> times1 = new ArrayList<String>();
        times1.clear();
        times1.add("23:30");
        times1.add("01:30");
        Time1Parser t1 = new Time1Parser("mds sale start at 11:30pm for 2 hour");
        assertEquals(times1, t1.getTimeList());

        // test pm without semicolon
        times1.clear();
        times1.add("23:00");
        times1.add("01:00");
        Time1Parser t2 = new Time1Parser("start at 11pm for 2 hours rot");
        assertEquals(times1, t2.getTimeList());

        // test hour apart keyword of am without semicolon
        times1.clear();
        times1.add("03:00");
        times1.add("05:00");
        Time1Parser t3 = new Time1Parser("mds sale start at 3am for 2 hour");
        assertEquals(times1, t3.getTimeList());

        // test hour apart keyword of am with semicolon
        times1.clear();
        times1.add("03:45");
        times1.add("05:45");

        Time1Parser t4 = new Time1Parser(
                ("mds sale start at 3:45am for 2 hour"));
        assertEquals(times1, t4.getTimeList());

        // test space sensitively of am
        times1.clear();
        times1.add("03:00");
        times1.add("05:00");

        Time1Parser t5 = new Time1Parser(("mds sale start at 3 am for 2 hour"));
        assertEquals(times1, t5.getTimeList());

        // test space sensitively of pm
        times1.clear();
        times1.add("15:00");
        times1.add("17:00");

        Time1Parser t6 = new Time1Parser(("mds sale start at 3 pm for 2 hour"));
        assertEquals(times1, t6.getTimeList());

        // test "," connecting HH and MM
        times1.clear();
        times1.add("02:30");
        times1.add("05:30");

        Time1Parser t7 = new Time1Parser(
                ("mds sale start at 2,30 am for 3 hour"));
        assertEquals(times1, t7.getTimeList());

        // test "." connecting HH and MM
        times1.clear();
        times1.add("15:30");
        times1.add("17:30");

        Time1Parser t8 = new Time1Parser(
                ("mds sale start at 3.30 pm for 2 hour"));
        assertEquals(times1, t8.getTimeList());

        // test with am or pm
        times1.clear();
        times1.add("03:30");
        times1.add("05:30");

        Time1Parser t9 = new Time1Parser(("mds sale start at 3.30 for 2 hour"));
        assertEquals(times1, t9.getTimeList());
    }

    @Test
    public void testBeforeNoonOrMidnight() throws Exception {
        // test for time keyword 3: before noon
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("11:59");

        Time1Parser t1 = new Time1Parser("mds sale before noon");
        assertEquals(times, t1.getTimeList());

        // test before midnight
        times.clear();
        times.add("23:59");

        Time1Parser t2 = new Time1Parser("before midnight sleep!!!!");
        assertEquals(times, t2.getTimeList());

    }

    @Test
    public void testMorningNightAfternoon() throws Exception {
        // test in night with semicolon
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("19:45");

        Time1Parser t3 = new Time1Parser("mds sale 7:45 in night.");
        assertEquals(times, t3.getTimeList());
        
     // test in night with at
      
        times.clear();
        times.add("19:45");

        Time1Parser t17 = new Time1Parser("mds sale 7:45 at night.");
        assertEquals(times, t17.getTimeList());

        // test in night without in <conjunction>

        times.clear();
        times.add("19:45");

        Time1Parser t5 = new Time1Parser("mds sale 7:45 night.");
        assertEquals(times, t5.getTimeList());

        // test in morn with semicolon
        times.clear();
        times.add("06:30");

        Time1Parser t4 = new Time1Parser("6:30 in the morn sunshine");
        assertEquals(times, t4.getTimeList());

        // test in morn without in <conjunction>

        times.clear();
        times.add("07:45");

        Time1Parser t14 = new Time1Parser("mds sale 7:45 morn.");
        assertEquals(times, t14.getTimeList());

        // test in morning with semicolon
        times.clear();
        times.add("06:30");

        Time1Parser t13 = new Time1Parser("mds sale at 6:30 in morning");
        assertEquals(times, t13.getTimeList());

        // test in morning without in <conjunction>

        times.clear();
        times.add("07:45");

        Time1Parser t15 = new Time1Parser("mds sale 7:45 morn.");
        assertEquals(times, t15.getTimeList());

        // test in afternoon with semicolon
        times.clear();
        times.add("14:30");

        Time1Parser t6 = new Time1Parser("mds sale from 2:30 in afternoon");
        assertEquals(times, t6.getTimeList());

        // test in afternoon without in <conjunction>

        times.clear();
        times.add("14:45");

        Time1Parser t16 = new Time1Parser("mds sale 2:45 afternoon.");
        assertEquals(times, t16.getTimeList());
        // test in night without semicolon
        times.clear();
        times.add("19:00");
        
        times.clear();
        times.add("15:00");

        Time1Parser t19 = new Time1Parser("mds sale 3 at afternoon.");
        assertEquals(times, t19.getTimeList());
        // test in night without semicolon
        times.clear();
        times.add("19:00");

        Time1Parser t7 = new Time1Parser("mds sale 7 in night");
        assertEquals(times, t7.getTimeList());

        // test in morn without semicolon
        times.clear();
        times.add("06:00");

        Time1Parser t8 = new Time1Parser("mds sale 6 at morn.");
        assertEquals(times, t8.getTimeList());

        // test in morning without semicolon
        times.clear();
        times.add("03:00");

        Time1Parser t9 = new Time1Parser("mds sale at 3 in morning.");
        assertEquals(times, t9.getTimeList());
        
        // test in morning without semicolon
        times.clear();
        times.add("06:00");

        Time1Parser t21 = new Time1Parser("mds sale 6 at morning.");
        assertEquals(times, t21.getTimeList());


        // test in afternoon without semicolon
        times.clear();
        times.add("14:00");

        Time1Parser t10 = new Time1Parser("mds sale from 2 in afternoon");
        assertEquals(times, t10.getTimeList());

        // test , connecting mm and hh in time
        times.clear();
        times.add("13:30");

        Time1Parser t11 = new Time1Parser("mds sale from 1,30 in afternoon");
        assertEquals(times, t11.getTimeList());

        // test . connecting mm and hh in time
        times.clear();
        times.add("23:30");

        Time1Parser t12 = new Time1Parser("mds sale from 11.30 in night");
        assertEquals(times, t12.getTimeList());

    }

    @Test
    /*
     * test time of o'clock 1) HH:MM o'clock 2) HH,MM o'clock 3) HH.MM o'clock
     * 4) HH o'clock 5) space sensitivity
     */
    public void testTimeWithOclock() throws Exception {
        ArrayList<String> times = new ArrayList<String>();
        // test HH o'clock
        times.clear();
        times.add("10:00");

        Time1Parser t1 = new Time1Parser(
                ("add go run one round on this sun from 10 o’clock"));
        assertEquals(times, t1.getTimeList());

        // test HH:MM o'clock
        times.clear();
        times.add("11:45");

        Time1Parser t2 = new Time1Parser(("mds sale start at 11:45 o'clock"));
        assertEquals(times, t2.getTimeList());

        // test HH.MM o'clock
        times.clear();
        times.add("11:45");

        Time1Parser t3 = new Time1Parser(("mds sale start at 11.45 o'clock"));
        assertEquals(times, t3.getTimeList());

        // test HH,MM o'clock
        times.clear();
        times.add("11:45");

        Time1Parser t4 = new Time1Parser(("11,45 o'clock go go go"));
        assertEquals(times, t4.getTimeList());

        // test space sensitively
        times.clear();
        times.add("11:45");

        Time1Parser t5 = new Time1Parser(("mds sale start at 11.45o'clock"));
        assertEquals(times, t5.getTimeList());
    }

    @Test
    public void testHourFormat() throws Exception {
        ArrayList<String> times = new ArrayList<String>();

        // test with symbol "."
        times.clear();
        times.add("23:20");

        Time1Parser t1 = new Time1Parser(("mds sale 23.20"));
        assertEquals(times, t1.getTimeList());

        // test with symbol ","
        times.clear();
        times.add("00:20");

        Time1Parser t2 = new Time1Parser(("mds sale 00,20"));
        assertEquals(times, t2.getTimeList());
    }

    @Test
    /*
     * test for the position of the time
     */
    public void testPosition() throws Exception {
        /*
         * test the position of time is right since program will 1st detect
         * start at 11am for 1 hours then detect noonbut the arrangement of the
         * timing should be noon --> before midnight
         */
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("11:00");
        times.add("12:00");
        times.add("23:59");
        Time1Parser t1 = new Time1Parser(
                ("mds sale from before midnight start at 11am for 1 hours"));

        assertEquals(times, t1.getTimeList());

    }

}

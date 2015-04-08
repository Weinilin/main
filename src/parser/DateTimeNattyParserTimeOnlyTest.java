package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeNattyParserTimeOnlyTest {

    @Test
    public void test() {

    }

    @Test
    /**
     * test without escaped char
     */
    public void testTimeNULL() {
        ArrayList<String> times = new ArrayList<String>();

        // test when nothing is detected
        times.clear();
        DateTimeNattyParser dateTime = new DateTimeNattyParser("");
        assertEquals(times, dateTime.getTimeList());

    }

    @Test
    public void testMidnightOrNoonKeyword() {
        // test midnight
        ArrayList<String> times = new ArrayList<String>();

        DateTimeNattyParser dateTime = new DateTimeNattyParser(
                "mds sale start midnight");

        times.add("00:00");
        assertEquals(times, dateTime.getTimeList());

        // test noon
        times.clear();
        times.add("12:00");
        DateTimeNattyParser dateTime1 = new DateTimeNattyParser("noon run");
        assertEquals(times, dateTime1.getTimeList());
        
        //test afternoon 
        times.clear();
        times.add("12:00");
        DateTimeNattyParser dateTime2 = new DateTimeNattyParser("afternoon run");
        assertEquals(times, dateTime2.getTimeList());
        
        //test morning
        times.clear();
        times.add("08:00");
        DateTimeNattyParser dateTime3 = new DateTimeNattyParser("morning run");
        assertEquals(times, dateTime3.getTimeList());
       
    }

    @Test
    // take note of current time, change the result according
    public void testHoursApart() {
        // test current time + 2 hours
        ArrayList<String> times = new ArrayList<String>();

        DateTimeNattyParser dateTime = new DateTimeNattyParser(
                "mds sale for 2 hours");
        times.add("22:17");
        times.add("00:17");
        // assertEquals(times, dateTime.getTimeList());

        // test current time + 2 hours

        DateTimeNattyParser dateTime1 = new DateTimeNattyParser(
                "mds sale for 2 hrs");
        times.clear();
        times.add("22:17");
        times.add("00:17");
        // assertEquals(times, dateTime1.getTimeList());
        
        // test with hr
        DateTimeNattyParser dateTime2 = new DateTimeNattyParser(
                "mds sale for 1 hr");
        times.clear();
        times.add("22:17");
        times.add("23:17");
       //  assertEquals(times, dateTime2.getTimeList());
        
        //test with hour
        DateTimeNattyParser dateTime3 = new DateTimeNattyParser(
                "mds sale for 1 hour");
        times.clear();
        times.add("22:17");
        times.add("23:17");
       //  assertEquals(times, dateTime3.getTimeList());

    }
    
    @Test
    /**
     * test twenty hour format(HH:MM) without the punctuation between
     * hour and minute and with or without hour or hr or hrs or hours behind it. 
     */
    public void test24HourWithoutPunc() {
        ArrayList<String> times1 = new ArrayList<String>();

        // test with hrs
        times1.clear();
        times1.add("11:00");
        DateTimeNattyParser dateTime = new DateTimeNattyParser(
                "mds sale start at 1100 hrs");
        assertEquals(times1, dateTime.getTimeList());

        // test with hr
        times1.clear();
        times1.add("12:00");
        DateTimeNattyParser dateTime1 = new DateTimeNattyParser(
                "mds sale start at 1200 hr");
        assertEquals(times1, dateTime1.getTimeList());

        // test with hour
        times1.clear();
        times1.add("15:00");
        DateTimeNattyParser dateTime2 = new DateTimeNattyParser(
                "mds sale start at 1500 hour");
        assertEquals(times1, dateTime2.getTimeList());

        // test with hours
        times1.clear();
        times1.add("16:00");
        DateTimeNattyParser dateTime3 = new DateTimeNattyParser(
                "mds sale start at 1600 hours");
        assertEquals(times1, dateTime3.getTimeList());
    }

    @Test
    /**
     * take note testing this follow the current time of their laptop
     * change the test result according.
     * When i test it, current time: 9:44pm
     */
    public void testRelativeTime() {
        ArrayList<String> times = new ArrayList<String>();
        // test _ seconds ago
        times.clear();
        times.add("21:43");
        DateTimeNattyParser dateTime = new DateTimeNattyParser(
                "10 seconds ago, gigi bark!");
        // assertEquals(times, dateTime.getTimeList());

        // test _ minutes ago
        times.clear();
        times.add("21:13");
        DateTimeNattyParser dateTime1 = new DateTimeNattyParser(
                "30 min ago, gigi bark!");
        // assertEquals(times, dateTime1.getTimeList());

        // test _ hours ago
        times.clear();
        times.add("20:44");
        DateTimeNattyParser dateTime2 = new DateTimeNattyParser(
                "One hour ago, gigi bark!");
        // assertEquals(times, dateTime2.getTimeList());

        // test in 5 minutes
        times.clear();
        times.add("21:49");
        DateTimeNattyParser dateTime3 = new DateTimeNattyParser(
                "gigi sleep in 5 minutes");
        // assertEquals(times, dateTime3.getTimeList());

        // test in 5 hours
        times.clear();
        times.add("00:44");
        DateTimeNattyParser dateTime4 = new DateTimeNattyParser(
                "gigi sleep in 3 hours");
        // assertEquals(times, dateTime4.getTimeList());

        // test in 5 second
        times.clear();
        times.add("21:44");
        DateTimeNattyParser dateTime5 = new DateTimeNattyParser(
                "gigi sleep in 1 second");
        // assertEquals(times, dateTime5.getTimeList());

        // test in 4 minutes from now
        times.clear();
        times.add("21:50");
        DateTimeNattyParser dateTime6 = new DateTimeNattyParser(
                "runrun 4 minutes from now");
        // assertEquals(times, dateTime6.getTimeList());

        // test in 4 hour from now
        times.clear();
        times.add("01:45");
        DateTimeNattyParser dateTime7 = new DateTimeNattyParser(
                "runrun 4 hour from now");
        // assertEquals(times, dateTime7.getTimeList());
        
        // test in four hour from now
        times.clear();
        times.add("01:45");
        DateTimeNattyParser dateTime8 = new DateTimeNattyParser(
                "runrun four hour from now");
        // assertEquals(times, dateTime8.getTimeList());

    }
}
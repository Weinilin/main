package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTest {

    @Test
    public void timeTestForHashTag() {
        ArrayList<String> times = new ArrayList<String>();

        // test when all of the time is ~ ~
        times.clear();
        assertEquals(times,
                TimeParser.extractTime("mds sale ~11pm to 1:30 pm~."));

        // test when 1 of the time is ~ ~ and the other do not
        times.clear();
        times.add("00:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from ~noon~ to at midnight"));
    }

    @Test
    /**
     * Equivalence test: test for all possible aspects.
     * test without ~ ~ 
     */
    public void testTimeNULL() {
        ArrayList<String> times = new ArrayList<String>();

        // test when nothing is detected
        times.clear();
        assertEquals(times, TimeParser.extractTime(""));

    }

    @Test
    public void testTimeKeyword2() {
        // test for time keyword 2
        ArrayList<String> times1 = new ArrayList<String>();
        times1.clear();
        times1.add("23:30");
        times1.add("00:30");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 11:30 pm for 1 hour"));
    }

    @Test
    public void testTimeKeyword3() {
        // test for time keyword 3: before noon and before midnight
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("11:59");
        times.add("23:59");
        assertEquals(times,
                TimeParser
                        .extractTime("mds sale before noon to before midnight"));
    }

    @Test
    public void testTimeKeyword4() {
        // test for timed test and time keyword 4: noon and midnight
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("12:00");
        times.add("00:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from noon to at midnight"));
    }

    @Test
    public void testTimeKeyword5() {
        // test for time keyword 5 : in morning, in night, in afternoon
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("6:30");
        times.add("19:45");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale from 6:30 in morn to 7:45 in night"));
    }

    @Test
    /*
     * test digit pm/am hour format am/pm
     */
    public void testTimeKeyword6() {
        // test the time keyword 6 with hour format of am and floating task
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("00:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale 12:30 am 24/3/2014"));

        // test for time format 6 of pm and floating task
        times.clear();
        times.add("18:00");
        assertEquals(times, TimeParser.extractTime("mds sale 6pm"));
    }

    @Test
    /*
     * test time of o'clock
     */
    public void testTimeKeyword7() {
        ArrayList<String> times = new ArrayList<String>();
        // test for time keyword 7 with o'clock
        times.clear();
        times.add("11:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale start at 11:30 o'clock"));
    }

    @Test
    /*
     * test for dectection more than 1 and position stored in arraylist
     */
    public void testDetectionMoreThan1() {
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("12:00");
        times.add("23:59");
        assertEquals(times,
                TimeParser.extractTime("mds sale from noon to before midnight"));

        times.clear();
        times.add("19:45");
        times.add("00:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 7:45pm to midnight"));
    }
}

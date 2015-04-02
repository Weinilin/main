package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTest {

    @Test
    /*
     * test the escaped char work
     */
    public void timeTestForHashTag() {
        ArrayList<String> times = new ArrayList<String>();

       // test when all of the time is ~ ~
        times.clear();
        assertEquals(times,
                TimeParser.extractTime("mds sale ~11pm to 1:30 pm~."));
    }

    @Test
    /**
     * test without escaped char
     */
    public void testTimeNULL() {
        ArrayList<String> times = new ArrayList<String>();

        // test when nothing is detected
        times.clear();
        assertEquals(times, TimeParser.extractTime(""));

    }

    @Test
    public void testPastMidnightOrNoonKeyword(){
        //test past midnight
        ArrayList<String> times1 = new ArrayList<String>();
        times1.clear();
        times1.add("00:01");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start past midnight"));
        
        //test past noon
        times1.clear();
        times1.add("12:01");
        assertEquals(times1,
                TimeParser.extractTime("past noon run"));
    }
    
    @Test
    public void testHourApartKeyword() {
      
        // test boundary of when adding the hour will exceed 23 of pm
        ArrayList<String> times1 = new ArrayList<String>();
        times1.clear();
        times1.add("23:30");
        times1.add("01:30");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 11:30pm for 2 hour"));
        
        // test pm without semicolon
        times1.clear();
        times1.add("23:00");
        times1.add("01:00");
        assertEquals(times1,
                TimeParser.extractTime("start at 11pm for 2 hours rot"));
        
        
        // test  hour apart keyword of am without semicolon
        times1.clear();
        times1.add("03:00");
        times1.add("05:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 3am for 2 hour"));
        
        // test  hour apart keyword of am with semicolon
        times1.clear();
        times1.add("03:45");
        times1.add("05:45");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 3:45am for 2 hour"));
        
        // test space sensitively of am
        times1.clear();
        times1.add("03:00");
        times1.add("05:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 3 am for 2 hour"));
        
     // test space sensitively of pm
        times1.clear();
        times1.add("15:00");
        times1.add("17:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 3 pm for 2 hour"));
        
        
     // test "," connecting HH and MM
        times1.clear();
        times1.add("02:30");
        times1.add("05:30");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 2,30 am for 3 hour"));
        
     // test "." connecting HH and MM
        times1.clear();
        times1.add("15:30");
        times1.add("17:30");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 3.30 pm for 2 hour"));
        
     // test with am or pm
        times1.clear();
        times1.add("03:30");
        times1.add("05:30");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 3.30 for 2 hour"));
    }

    @Test
    public void testBeforeNoonOrMidnight() {
        // test for time keyword 3: before noon
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("11:59");
        assertEquals(times,
                TimeParser
                        .extractTime("mds sale before noon"));
        
        //test before midnight
        times.clear();
        times.add("23:59");
        assertEquals(times,
                TimeParser
                        .extractTime("before midnight sleep!!!!"));
    }

    @Test
    public void testNoonOrMidnight() {
        // test noon 
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("12:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from noon"));
        
        //test midnight
        times.clear();
        times.add("00:00");
        assertEquals(times,
                TimeParser.extractTime("at midnight Twilight"));
    }

    @Test
    public void testMorningNightAfternoon() {
        // test in night with semicolon
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("19:45");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale 7:45 in night."));
        
        //test in morn with semicolon
        times.clear();
        times.add("06:30");
        assertEquals(
                times,
                TimeParser
                        .extractTime("6:30 in the morn sunshine"));
        
        //test in morning with semicolon
        times.clear();
        times.add("06:30");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale at 6:30 in morning"));
        
        //test in afternoon with semicolon
        times.clear();
        times.add("14:30");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale from 2:30 in afternoon"));
        
        // test in night without semicolon
        times.clear();
        times.add("19:00");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale 7 in night"));
        
        //test in morn without semicolon
        times.clear();
        times.add("06:00");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale 6 at morn."));
        
        //test in morning without semicolon
        times.clear();
        times.add("03:00");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale at 3 in morning."));
        
      //test in afternoon without semicolon
        times.clear();
        times.add("14:00");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale from 2 in afternoon"));
        
        //test , connecting mm and hh in time
        times.clear();
        times.add("13:30");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale from 1,30 in afternoon"));
        
        //test . connecting mm and hh in time
        times.clear();
        times.add("23:30");
        assertEquals(
                times,
                TimeParser
                        .extractTime("mds sale from 11.30 in night"));
    }

    @Test
    /*
     * test digit pm/am hour format am/pm
     */
    public void testTwelveHourTime() {
        // test with semicolon format of am 
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("00:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale 12:30 am 24/3/2014"));

        // test with no semicolon of am 
        times.clear();
        times.add("00:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale 12 am 24/3/2014"));
        
        // test for of pm without semicolon
        times.clear();
        times.add("18:00");
        assertEquals(times, TimeParser.extractTime("6pm"));
        
        // test for  pm with semicolon
        times.clear();
        times.add("18:30");
        assertEquals(times, TimeParser.extractTime("mds sale 6:30pm"));
        
        //test for time with "." connecting HH and MM
        times.clear();
        times.add("18:30");
        assertEquals(times, TimeParser.extractTime("mds sale 6.30pm"));
        
        //test for time with "," connecting HH and MM
        times.clear();
        times.add("03:30");
        assertEquals(times, TimeParser.extractTime("mds sale 3,30am"));
        
     // test with <conjunction> 12 hour format
        times.clear();
        times.add("11:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale start at 11"));
    }

    @Test
    /*
     * test time of o'clock 
     * 1) HH:MM o'clock 2) HH,MM o'clock 3) HH.MM o'clock 4) HH o'clock 5) space sensitivity
     */
    public void testTimeWithOclock() {
        ArrayList<String> times = new ArrayList<String>();
     // test HH o'clock
        times.clear();
        times.add("10:00");
        assertEquals(times,
                TimeParser.extractTime("add go run one round on this sun from 10 o’clock"));

     // test HH:MM o'clock
        times.clear();
        times.add("11:45");
        assertEquals(times,
                TimeParser.extractTime("mds sale start at 11:45 o'clock"));
        
     // test HH.MM o'clock
        times.clear();
        times.add("11:45");
        assertEquals(times,
                TimeParser.extractTime("mds sale start at 11.45 o'clock"));
        
     // test HH,MM o'clock
        times.clear();
        times.add("11:45");
        assertEquals(times,
                TimeParser.extractTime("11,45 o'clock go go go"));
        
     // test space sensitively
        times.clear();
        times.add("11:45");
        assertEquals(times,
                TimeParser.extractTime("mds sale start at 11.45o'clock"));
    }
    
    @Test
    public void testTimeToTime() {
        //test without symbol of pm
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("06:00");
        times.add("18:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 6 - 6pm"));
        
        //test without symbol of am
        times.clear();
        times.add("01:00");
        times.add("06:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1 - 6am"));
        
        //test with symbol of am
        times.clear();
        times.add("01:30");
        times.add("06:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1:30 - 6:30am"));
        
      //test with symbol of pm
        times.clear();
        times.add("06:20");
        times.add("18:10");
        assertEquals(times,
                TimeParser.extractTime("6:20 - 6:10pm nothing better to do"));

        //test with both am
        times.clear();
        times.add("01:00");
        times.add("06:00");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1am - 6am"));
        
        //test with both pm
        times.clear();
        times.add("13:30");
        times.add("18:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1:30pm - 6:30pm"));
        
      //test with "." connecting min and hour
        times.clear();
        times.add("13:30");
        times.add("18:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1.30 - 6.30pm"));
        
      //test with "," connecting min and hour
        times.clear();
        times.add("01:30");
        times.add("06:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1,30 - 6,30 am"));
        
      //test space sensitivity is only for -/am/pm
        times.clear();
        times.add("01:30");
        times.add("06:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1,30-6,30am"));
        
        
      //test to instead of - : with symbol "."
       //take note "white space"to"white space" then it will work.
        times.clear();
        times.add("01:30");
        times.add("06:30");
        assertEquals(times,
                TimeParser.extractTime("mds sale from 1.30 to 6.30am"));
        
      //test to instead of - : with semicolon
        //take note "white space"to"white space" then it will work.
         times.clear();
         times.add("01:00");
         times.add("06:30");
         assertEquals(times,
                 TimeParser.extractTime("mds sale from 1:00 to 6:30am"));
         
         //test to instead of - : with ","
         //take note "white space"to"white space" then it will work.
          times.clear();
          times.add("01:00");
          times.add("06:30");
          assertEquals(times,
                  TimeParser.extractTime("mds sale from 1,00 to 6,30am"));
          
          //test to instead of - : without symbol
          //take note "white space"to"white space" then it will work.
           times.clear();
           times.add("13:00");
           times.add("18:00");
           assertEquals(times,
                   TimeParser.extractTime("mds sale from 1 to 6pm"));
           
         //test without am or pm
           times.clear();
           times.add("01:30");
           times.add("06:30");
           assertEquals(times,
                   TimeParser.extractTime("mds sale from 1,30-6,30"));
    }
    
    @Test
    public void testHourFormat(){
        ArrayList<String> times = new ArrayList<String>();
        //test with symbol ":"
        times.clear();
        times.add("11:20");
        assertEquals(times,
                TimeParser.extractTime("mds sale 11:20"));
        
      //test with symbol "."
        times.clear();
        times.add("23:20");
        assertEquals(times,
                TimeParser.extractTime("mds sale 23.20"));
        
      //test with symbol ","
        times.clear();
        times.add("00:20");
        assertEquals(times,
                TimeParser.extractTime("mds sale 00,20"));
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
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 1100 hrs"));
        
     // test with hr
        times1.clear();
        times1.add("12:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 1200 hr"));
        
     // test with hour
        times1.clear();
        times1.add("15:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 1500 hour"));
        
     // test with hours
        times1.clear();
        times1.add("16:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 1600 hours"));
        
        //test without hrs/hours/hour/hr
        times1.clear();
        times1.add("13:00");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 1300"));
    }
        

    @Test
    /*
     * test for the position of the time
     */
    public void testPosition() {
        /*test the position of time is right since program will 1st detect before midnight then detect noon
        *but the arrangement of the timing should be noon --> before midnight
        */
        ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("12:00");
        times.add("23:59");
        assertEquals(times,
                TimeParser.extractTime("mds sale from noon to before midnight"));

    }
}

package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeNattyTimeOnlyTest {

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
        DateTimeNatty dateTime = new DateTimeNatty("");
        assertEquals(times, dateTime.getTimeList());


    }

    @Test
    public void testMidnightOrNoonKeyword() {
        // test midnight
        ArrayList<String> times = new ArrayList<String>();
       
                DateTimeNatty dateTime = new DateTimeNatty("mds sale start midnight");
                
                times.add("00:00"); 
                assertEquals(times, dateTime.getTimeList());

        // test  noon
        times.clear();
        times.add("12:00");  
        DateTimeNatty dateTime1 = new DateTimeNatty("noon run"); 
        assertEquals(times, dateTime1.getTimeList());
      
    }
    
    @Test
    //take note of current time, change the result according
    public void testHoursApart() {
        // test current time + 2 hours 
        ArrayList<String> times = new ArrayList<String>();
       
                DateTimeNatty dateTime = new DateTimeNatty("mds sale for 2 hours");
                
                times.add("22:17"); 
                times.add("00:17"); 
               // assertEquals(times, dateTime.getTimeList());
                
             // test current time + 2 hours 
               
               
                        DateTimeNatty dateTime1 = new DateTimeNatty("mds sale for 2 hrs");
                        
                        times.add("22:17"); 
                        times.add("00:17"); 
                   //     assertEquals(times, dateTime1.getTimeList());

       
      
    }

    @Test
    /*
     * test digit pm/am hour format am/pm
     */
    public void testTwelveHourTime() {
        // test with semicolon format of am
        ArrayList<String> times = new ArrayList<String>();
        times.clear(); 
        DateTimeNatty dateTime = new DateTimeNatty("mds sale 12:30 am ");
        times.add("00:30");
        assertEquals(times, dateTime.getTimeList() );
        
        // test with no semicolon of am
        times.clear();
        times.add("00:00");
        DateTimeNatty dateTime1 = new DateTimeNatty("mds sale 12 am ");
        assertEquals(times, dateTime1.getTimeList());

        // test for of pm without semicolon
        times.clear();
        times.add("18:00");
        DateTimeNatty dateTime2 = new DateTimeNatty("6pm");
        assertEquals(times, dateTime2.getTimeList());

        // test for pm with semicolon
        times.clear();
        times.add("18:30"); 
        DateTimeNatty dateTime3 = new DateTimeNatty("mds sale 6:30pm");
        assertEquals(times,dateTime3.getTimeList());
        
        // test pm with only p
        times.clear();
        times.add("19:30"); 
        DateTimeNatty dateTime4 = new DateTimeNatty("mds sale 7:30p");
        assertEquals(times,dateTime4.getTimeList());
        
        // test pm with only p
        times.clear();
        times.add("07:30"); 
        DateTimeNatty dateTime5 = new DateTimeNatty("mds sale 7:30a");
        assertEquals(times,dateTime5.getTimeList());
        
     // test am with . --> a.m.
        times.clear();
        times.add("05:30"); 
        DateTimeNatty dateTime6 = new DateTimeNatty("mds sale 5:30a.m.");
        assertEquals(times,dateTime6.getTimeList());
        
    }
    
    @Test
    public void testHourFormat() {
        ArrayList<String> times = new ArrayList<String>();
        // test with symbol ":"
        times.clear();
        times.add("11:20");
        DateTimeNatty dateTime = new DateTimeNatty("mds sale 11:20");
        assertEquals(times, dateTime.getTimeList());

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
        DateTimeNatty dateTime = new DateTimeNatty("mds sale start at 1100 hrs");
        assertEquals(times1,dateTime.getTimeList()
                );

        // test with hr
        times1.clear();
        times1.add("12:00");
        DateTimeNatty dateTime1 = new DateTimeNatty("mds sale start at 1200 hr");
        assertEquals(times1,dateTime1.getTimeList()
               );

        // test with hour
        times1.clear();
        times1.add("15:00"); 
        DateTimeNatty dateTime2 = new DateTimeNatty("mds sale start at 1500 hour");
        assertEquals(times1, dateTime2.getTimeList()
               ); 

        // test with hours
        times1.clear();
        times1.add("16:00");
        DateTimeNatty dateTime3 = new DateTimeNatty("mds sale start at 1600 hours");
        assertEquals(times1,
                dateTime3.getTimeList() );

        // test without hrs/hours/hour/hr
        times1.clear();
        times1.add("13:00");
        DateTimeNatty dateTime4 = new DateTimeNatty("mds sale start at 1300");
        assertEquals(times1, dateTime4.getTimeList());
    }

   @Test
   /**
    * take note testing this follow the current time of their laptop
    * change the test result according.
    * When i test it, current time: 9:44pm
    */
   public void testRelativeTime(){
       ArrayList<String> times = new ArrayList<String>();
       //test _ seconds ago
       times.clear();
       times.add("21:43");
       DateTimeNatty dateTime = new DateTimeNatty("10 seconds ago, gigi bark!");
       //assertEquals(times, dateTime.getTimeList());
       
       //test _ minutes ago
       times.clear();
       times.add("21:13");
       DateTimeNatty dateTime1 = new DateTimeNatty("30 min ago, gigi bark!");
      // assertEquals(times, dateTime1.getTimeList());
       
       //test _ hours ago
       times.clear();
       times.add("20:44");
       DateTimeNatty dateTime2 = new DateTimeNatty("One hour ago, gigi bark!");
      // assertEquals(times, dateTime2.getTimeList());
       
       //test in 5 minutes 
       times.clear();
       times.add("21:49");
       DateTimeNatty dateTime3 = new DateTimeNatty("gigi sleep in 5 minutes");
      // assertEquals(times, dateTime3.getTimeList());
       
       //test in 5 hours 
       times.clear();
       times.add("00:44");
       DateTimeNatty dateTime4 = new DateTimeNatty("gigi sleep in 3 hours");
     //  assertEquals(times, dateTime4.getTimeList());
       
       //test in 5 second 
       times.clear();
       times.add("21:44");
       DateTimeNatty dateTime5 = new DateTimeNatty("gigi sleep in 1 second");
     //  assertEquals(times, dateTime5.getTimeList());
       
       //test in 4 minutes from now
       times.clear();
       times.add("21:50");
       DateTimeNatty dateTime6 = new DateTimeNatty("runrun 4 minutes from now");
    //   assertEquals(times, dateTime6.getTimeList());

       
     //test in 4 hour from now
       times.clear();
       times.add("01:45");
       DateTimeNatty dateTime7 = new DateTimeNatty("runrun 4 hour from now");
     //  assertEquals(times, dateTime7.getTimeList());
       
   }
}
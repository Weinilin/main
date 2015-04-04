package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateTimeNattyTest {

    @Test
    public void test() {
        ArrayList<String> dates = new ArrayList<String>();
     //   DateTimeNatty dateTime  = new DateTimeNatty("add start reading ST2334 notes by today 8 am tomorrow 2330");
       // DateTimeNatty dateTime1  = new DateTimeNatty("8am today and 9 am tomorrow");
        
        //if 1 time and 2 dates, current time set
        
      //  DateTimeNatty dateTime2  = new DateTimeNatty("pray today 9 am tomorrow");
        
        //if 0 date and 2 times, two current date 
        //DateTimeNatty dateTime3  = new DateTimeNatty("mds sale 8 - 9am");
        
        //if 1 date and 2 time, current date
       // DateTimeNatty dateTime4  = new DateTimeNatty("mds sale 7 at night to 3 in morning 2nd of may");
        
        //if 0 times and 2 date, current time
       // DateTimeNatty dateTime5  = new DateTimeNatty("mds sale 2nd of may this fri");
    }


        @Test
        public void testNull() {
            ArrayList<String> dates = new ArrayList<String>();

            // test if nothing detected
            assertEquals(dates, new DateTimeNatty(""));
        }

        @Test
        /*
         * test DD/MM/YYYY format
         */
        public void testDDMMYYY() {
            ArrayList<String> dates = new ArrayList<String>();
            // test without year
            dates.add("Sun 01/Mar/2015 02:30:00");
            assertEquals(dates, new DateTimeNatty("mds sale due 1/3 2:30am"));

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
            assertEquals(dates, DateParser.extractDate("mds sale at 12 jan."));

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
            dates.add("31/03/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start in after 3 days"));

            // test keyword 4: next _ days
            dates.clear();
            dates.add("01/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start in next 4 days"));

            // test keyword 4: _ days from now
            dates.clear();
            dates.add("02/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 5 days from now"));

            // test day without s
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 5 day from now"));
            
         // test keyword 4: __day later
            dates.clear();
            dates.add("02/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 5 days later"));
            
         // test keyword 4: in __day times
            dates.clear();
            dates.add("02/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start in 5 days time"));
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
            dates.add("03/04/2015");
            assertEquals(dates, DateParser.extractDate("mds sale tomorrow"));

            // test keyword 5: tmr
            dates.clear();
            dates.add("Fri 03/04/2015 10:51");
            assertEquals(dates, new DateTimeNatty("gigi from tmr"));

            // test keyword 5: the following day
            dates.clear();
            dates.add("02/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale the following day"));

            // test keyword 5: the next day
            dates.clear();
            dates.add("02/04/2015");
            assertEquals(dates, DateParser.extractDate("mds sale the next day"));

            // test keyword 5: after today
            dates.clear();
            dates.add("29/03/2015");
            assertEquals(dates, DateParser.extractDate("mds sale the next day"));

            // test keyword 5: after tomorrow
            dates.clear();
            dates.add("30/03/2015");
            assertEquals(dates, DateParser.extractDate("mds sale after tomorrow"));
            
            // test keyword 5: after tmr
            dates.clear();
            dates.add("30/03/2015");
            assertEquals(dates, DateParser.extractDate("mds sale after tmr"));

            // test keyword 5: after today
            dates.clear();
            dates.add("29/03/2015");
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
            dates.add("04/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 1 week later"));
            
         // test keyword 6: next __month 
            dates.clear();
            dates.add("28/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start next 1 months "));
           
         // test keyword 6 : after __ weeks 
            dates.clear();
            dates.add("04/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale after 1 weeks"));
          
         // test keyword 6 : in __ weeks times
            dates.clear();
            dates.add("04/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale in 1 week time"));
            
            // test keyword 6 :  __ weeks from now on
            dates.clear();
            dates.add("04/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale in 1 week from now on"));
            
            // test s sensitivity(with s behind week or no s)
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 1 weeks later"));

            // test keyword 6: month later
            dates.clear();
            dates.add("28/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 1 month later"));
            
            // test keyword 6: after __month 
            dates.clear();
            dates.add("28/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start after 1 month "));
            
         // test keyword 6:  __month from now on
            dates.clear();
            dates.add("28/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 1 month from now on"));
            
            // test keyword 6: in __month times
            dates.clear();
            dates.add("28/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start in 1 month time"));
            
            // test keyword 6: next __month 
            dates.clear();
            dates.add("28/04/2015");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start next 1 month"));

            // test s sensitivity(with s behind month or no s)
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 1 months later"));

            // test keyword 6: year later
            dates.clear();
            dates.add("28/03/2018");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 3 year later"));

            // test the rest of aliases after __ year 
            dates.clear();
            dates.add("28/03/2018");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start after 3 year"));
            
            //  test the rest of aliases in __ year times
            dates.clear();
            dates.add("28/03/2018");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start in 3 year times"));
            
            //  test the rest of aliases next __ year 
            dates.clear();
            dates.add("28/03/2018");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start next 3 year"));
            
            //  test the rest of aliases __ year  from now on
            dates.clear();
            dates.add("28/03/2018");
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 3 year from now on"));

            // test s sensitivity(with s behind year or no s)
            assertEquals(dates,
                    DateParser.extractDate("mds sale start 3 years later"));
            
         // test the word format of number
            assertEquals(dates,
                    DateParser.extractDate("mds sale start three years later"));
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

            dates.add("29/03/2015");
            assertEquals(dates, DateParser.extractDate("mds sale this sun"));

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
 
    
    //notes:
    //1. can't detect 25/03 or 25/03/2015
    //2. don noe which is the start and end date and time.
    //3. 8am-9 does not work
    //4. time past current time does not set it to next day
    //5. 8-9am does not work
    //6. 7 at night to 3 in the morn 2nd of may --> the 2nd of may ==> infinite loop
    //7. can't detect 3 in morn/ 3 in morning
    // 8.morning 2nd of may --> 8:00 02/May/2015
    // 9. can't detect tmr
    // 10. can't detect OcToBor
    // 11. can't detect the following day
    // 12. after tomorrow --> detect as tomorrow
    // 13. after today --> detect as today
    // 14. can't detect past midnight/before midnight/past noon/ past noon
    // 15. 


package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * take note some test case is sensitive to the current time and date
 * ensure the time and date are set to current time and date according
 * For example: after 3 days .....
 * @author WeiLin
 *
 */
public class DateTimeNattyDateOnlyTest {
    @Test
    public void test() {

        // test when two date in a input
        ArrayList<String> dates = new ArrayList<String>();
        DateTimeNatty dateTime = new DateTimeNatty(
                "add start reading ST2334 notes by today 8 am tomorrow 2330");
        dates.add("04/04/2015");
        dates.add("05/04/2015");
        assertEquals(dates, dateTime.getDateList());

    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of days
     */
    public void daysApartKeywordTest() {
        ArrayList<String> dates = new ArrayList<String>();
        // test keyword 4: after _ days
        dates.clear();
        dates.add("07/04/2015");
        DateTimeNatty dateTime = new DateTimeNatty(
                "mds sale start in after 3 days");
        assertEquals(dates, dateTime.getDateList());

        // test keyword 4: 3 _ days ago
        dates.clear();
        dates.add("01/04/2015");
        DateTimeNatty dateTime12 = new DateTimeNatty("mds sale 3 days ago");
        assertEquals(dates, dateTime12.getDateList());

        // test keyword 4: next _ days
        dates.clear();
        dates.add("04/04/2015");
        dates.add("08/04/2015");
        DateTimeNatty dateTime1 = new DateTimeNatty(
                "mds sale start in next 4 days");
        assertEquals(dates, dateTime1.getDateList());

        // test keyword 4: _ days from now
        dates.clear();
        dates.add("09/04/2015");
        DateTimeNatty dateTime2 = new DateTimeNatty(
                "mds sale start 5 day from now");
        assertEquals(dates, dateTime2.getDateList());

        // test keyword 4: in __day times
        dates.clear();
        dates.add("09/04/2015");
        DateTimeNatty dateTime3 = new DateTimeNatty(
                "mds sale start in 5 days time");
        assertEquals(dates, dateTime3.getDateList());

        // test keyword 4: _ days after
        dates.clear();
        dates.add("07/04/2015");
        DateTimeNatty dateTime4 = new DateTimeNatty(
                "mds sale start in 3 days after");
        assertEquals(dates, dateTime4.getDateList());

        // test keyword 4: _ days after (in word form)
        dates.clear();
        dates.add("07/04/2015");
        DateTimeNatty dateTime5 = new DateTimeNatty(
                "mds sale start in three days after");
        assertEquals(dates, dateTime5.getDateList());

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
        dates.add("05/04/2015");
        DateTimeNatty dateTime = new DateTimeNatty("mds sale from tomorrow");
        assertEquals(dates, dateTime.getDateList());

        // test keyword 5: the next day
        dates.clear();
        dates.add("05/04/2015");
        DateTimeNatty dateTime1 = new DateTimeNatty("mds sale the next day");
        assertEquals(dates, dateTime1.getDateList());

        // the day before
        dates.clear();
        dates.add("05/01/2015");
        DateTimeNatty dateTime2 = new DateTimeNatty(
                "mds sale six day before 11 Jan");
        assertEquals(dates, dateTime2.getDateList());

        // the monday before
        dates.clear();
        dates.add("11/01/2015");
        DateTimeNatty dateTime3 = new DateTimeNatty(
                "mds sale six monday before before 11 Jan");
        assertEquals(dates, dateTime3.getDateList());

        // the tues after
        dates.clear();
        dates.add("07/04/2015");
        Date1Parser date = new Date1Parser("mds sale 1 tues after 4/4");
        date.getDateList();
        String input = date.getInputLeft();
        DateTimeNatty dateTime4 = new DateTimeNatty(input );
        assertEquals(dates, dateTime4.getDateList());

        // the day after
        dates.clear();
        dates.add("11/04/2015");

        DateTimeNatty dateTime5 = new DateTimeNatty(
                "mds sale seven day after 4 Apr");
        assertEquals(dates, dateTime5.getDateList());

    }

    @Test
    public void testDateAlternatives() {
        ArrayList<String> dates = new ArrayList<String>();

        // next wed or thru
        dates.clear();
        dates.add("10/04/2015");
        dates.add("05/04/2015");

        DateTimeNatty dateTime5 = new DateTimeNatty(
                "mds sale next friday or sunday");
        assertEquals(dates, dateTime5.getDateList());

        // next wed and thru
        dates.clear();
        dates.add("11/04/2015");
        dates.add("05/04/2015");

        DateTimeNatty dateTime6 = new DateTimeNatty(
                "mds sale next sat and sunday");
        assertEquals(dates, dateTime6.getDateList());
        
        
        // date and date
        dates.clear();
        dates.add("24/01/2015");
        dates.add("25/01/2015");

        DateTimeNatty dateTime7 = new DateTimeNatty(
                "mds sale 24 jan and 25 jan");
        assertEquals(dates, dateTime7.getDateList());
        
        //  date or date
        dates.clear();
        dates.add("24/01/2015");
        dates.add("14/01/2015");

        DateTimeNatty dateTime8 = new DateTimeNatty(
                "mds sale 24 jan or 14 jan");
        assertEquals(dates, dateTime8.getDateList());

    }

    @Test
    /*
     * do take note that I use current date extract from laptop Thus, the date
     * result from date parser is the current date + number of week/month/year
     * Interpret by the vocab
     */
    public void testWeekMonthYearApartKeyword() {
        ArrayList<String> dates = new ArrayList<String>();

        // test keyword 6: next __month
        dates.clear();
        dates.add("04/04/2015");
        dates.add("04/05/2015");
        DateTimeNatty dateTime1 = new DateTimeNatty(
                "mds sale start next 1 months ");
        assertEquals(dates, dateTime1.getDateList());

        // test keyword 6 : after __ weeks
        dates.clear();
        dates.add("11/04/2015");
        DateTimeNatty dateTime2 = new DateTimeNatty("mds sale after 1 weeks");
        assertEquals(dates, dateTime2.getDateList());

        // test keyword 6 : in __ weeks times
        dates.clear();
        dates.add("11/04/2015");
        DateTimeNatty dateTime3 = new DateTimeNatty("mds sale in 1 week time");
        assertEquals(dates, dateTime3.getDateList());

        // test keyword 6 : __ weeks from now on
        dates.clear();
        dates.add("04/04/2015");
        dates.add("11/04/2015");
        DateTimeNatty dateTime4 = new DateTimeNatty(
                "mds sale in 1 week from now on");
        assertEquals(dates, dateTime4.getDateList());

        // test keyword 4: 3 _ weeks ago
        dates.clear();
        dates.add("14/03/2015");
        DateTimeNatty dateTime16 = new DateTimeNatty("mds sale 3 weeks ago");
        assertEquals(dates, dateTime16.getDateList());

        // test keyword 6: after __month
        dates.clear();
        dates.add("04/05/2015");
        DateTimeNatty dateTime5 = new DateTimeNatty(
                "mds sale start after 1 month");
        assertEquals(dates, dateTime5.getDateList());

        // test keyword 6: in __month from now on
        dates.clear();
        dates.add("04/04/2015");
        dates.add("04/05/2015");
        DateTimeNatty dateTime6 = new DateTimeNatty(
                "mds sale in 1 month from now on");
        assertEquals(dates, dateTime6.getDateList());

        // test keyword 6: start __month from now on
        dates.clear();
        dates.add("01/05/2015");
        DateTimeNatty dateTime7 = new DateTimeNatty(
                "mds sale start start 1 month from now on");
        assertEquals(dates, dateTime7.getDateList());

        // test keyword 6: in __month times
        dates.clear();
        dates.add("04/05/2015");
        DateTimeNatty dateTime15 = new DateTimeNatty(
                "mds sale start in 1 month time");
        assertEquals(dates, dateTime15.getDateList());

        // test keyword 6: next __month
        dates.clear();
        dates.add("04/04/2015");
        dates.add("04/05/2015");
        DateTimeNatty dateTime8 = new DateTimeNatty(
                "mds sale start next 1 month");
        assertEquals(dates, dateTime8.getDateList());

        // test keyword 4: 3 _ month ago
        dates.clear();
        dates.add("04/01/2015");
        DateTimeNatty dateTime18 = new DateTimeNatty("mds sale 3 month ago");
        assertEquals(dates, dateTime18.getDateList());

        // test the rest of aliases after __ year
        dates.clear();
        dates.add("04/04/2018");
        DateTimeNatty dateTime10 = new DateTimeNatty(
                "mds sale start after 3 year");
        assertEquals(dates, dateTime10.getDateList());

        // test the rest of aliases in __ year times
        dates.clear();
        dates.add("04/04/2018");
        DateTimeNatty dateTime11 = new DateTimeNatty(
                "mds sale start in 3 year times");
        assertEquals(dates, dateTime11.getDateList());

        // test the rest of aliases next __ year
        dates.clear();
        dates.add("04/04/2015");
        dates.add("04/04/2018");
        DateTimeNatty dateTime12 = new DateTimeNatty(
                "mds sale start next 3 year");
        assertEquals(dates, dateTime12.getDateList());

        // test the rest of aliases __ year in .... from now on
        dates.clear();
        dates.add("04/04/2015");
        dates.add("04/04/2018");
        DateTimeNatty dateTime13 = new DateTimeNatty(
                "mds sale in 3 year from now on");
        assertEquals(dates, dateTime13.getDateList());

        // test keyword 4: _ years ago
        dates.clear();
        dates.add("04/04/2012");
        DateTimeNatty dateTime17 = new DateTimeNatty("mds sale 3 year ago");
        assertEquals(dates, dateTime17.getDateList());

        // test the word format of number
        dates.clear();
        dates.add("04/04/2018");
        DateTimeNatty dateTime14 = new DateTimeNatty(
                "mds sale start three year from now on");
        assertEquals(dates, dateTime14.getDateList());
    }
    
    
    public void MonthInWordDDTest(){
        ArrayList<String> dates = new ArrayList<String>();

        // test 4th of April in the year of 2015 in sentence
        dates.clear();
        dates.add("04/04/2015");
        DateTimeNatty dateTime1 = new DateTimeNatty(
                "mummy is nagging 4th of April in the year of 2015");
        assertEquals(dates, dateTime1.getDateList());
        
        // test without year
        dates.clear();
        dates.add("10/04/2015");
        DateTimeNatty dateTime2 = new DateTimeNatty(
                "Today is april 10");
        assertEquals(dates, dateTime2.getDateList());
        
        
        // test with year
        dates.clear();
        dates.add("10/04/2015");
        DateTimeNatty dateTime3 = new DateTimeNatty(
                "Today is april 10 2015");
        assertEquals(dates, dateTime3.getDateList());
        
        
        // test all of the date in word
        dates.clear();
        dates.add("05/04/2015");
        DateTimeNatty dateTime4 = new DateTimeNatty(
                "just joking, today is five april 2015");
        assertEquals(dates, dateTime4.getDateList());
        
        
        // test date that is in mm/dd/yyyy all in word
        dates.clear();
        dates.add("20/05/2015");
        DateTimeNatty dateTime5 = new DateTimeNatty(
                "is it april fool day,  may 20 2015");
        assertEquals(dates, dateTime5.getDateList());
      
    }
    
    @Test
    /*
     * test complication of sync with Date1Parser and DateTimeNattyParser
     */
    public void testComplication(){
        ArrayList<String> dates = new ArrayList<String>();
        //test the complication of next  wed and sat
        //Since the detection of next weekday is on the date1Parser and next sun or thrus 
        //could only detect on natty
        dates.clear();
        dates.add("11/04/2015");
        dates.add("08/04/2015");
        Date1Parser d1p = new Date1Parser("mds sale next sat and wed");
        String userInput = d1p.getInputLeft();
        DateTimeNatty dateTime1 = new DateTimeNatty(
                userInput);
        assertEquals(dates, dateTime1.getDateList());
        
    }


}

// notes:
// 1. can't detect 25/03 or 25/03/2015
// 2. don noe which is the start and end date and time.
// 3. 8am-9 does not work
// 4. time past current time does not set it to next day
// 5. 8-9am does not work
// 6. 7 at night to 3 in the morn 2nd of may --> the 2nd of may ==> infinite
// loop
// 7. can't detect 3 in morn/ 3 in morning
// 8.morning 2nd of may --> 8:00 02/May/2015
// 9. can't detect tmr
// 10. can't detect OcToBor
// 11. can't detect the following day
// 12. after tomorrow --> detect as tomorrow
// 13. after today --> detect as today
// 14. can't detect past midnight/before midnight/past noon/ past noon
// 15. can't detect ..... later
// 16. can only have 2 dates: IN ___ days from now
// 17: start in three days ago --> become three day later (only work 3 day ago)
// 18. 6 - 6pm -- > result 18:00, 18:00
// 19. 1pm - 6 --> 6 is in the morning 


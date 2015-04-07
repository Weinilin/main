package parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;

/**
 * Extract date and time from a library known as natty.
 * Time: 1) 24-hour format 2) _ hour/minute/second ago 3) in _ hour/minute/second
 * 4) _ hour/minute/second from now 5) 0600h/06:00 hours/6pm/6am/5:40a.m./3a/4p
 * 6) noon/afternoon/midnight
 * Date: 1) three/3 weeks/month/day/year ago 2) three/3 weeks/month/day/year from now
 * 3) next weeks/month/day/year 4) after three/3 weeks/month/day/year 5) three/3 weeks/month/day/year ago after
 * 6) today/tomorrow/yesterday 7) _ or _ / _ or _ 8) day/weekday before 9) day/weekday after 10) word in month DD
 * @author WeiLin
 *
 */
public class DateTimeNattyParser {
    private ArrayList<String> storageOfDate = new ArrayList<String>();
    private ArrayList<String> storageOfTime = new ArrayList<String>();
    private ArrayList<String> dateTextInUserInput = new ArrayList<String>();
    private String userInputLeftAfterParsing;
    private String description;
    private int indexTime;
    private int indexDate;

    public DateTimeNattyParser() {

    }

    public DateTimeNattyParser(String userInput) {
        extractDateTime(userInput, userInput, storageOfDate, storageOfTime, 0,
                0);
    }

    public void extractDateTime(String userInput, String leftOverInput,
            ArrayList<String> dates, ArrayList<String> times,
            int indexPrevTime, int indexPrevDate) {
        List<DateGroup> groups = new ArrayList<DateGroup>();

        storageOfDate.clear();
        storageOfTime.clear();
        storageOfDate.addAll(dates);
        storageOfTime.addAll(times);
        
        Parser dateTimeParser = new Parser();

        indexTime = indexPrevTime;
        indexDate = indexPrevDate;
        
        // userInputLeftAfterParsing : remove those detected --> prevent
        // infinite loop
        userInputLeftAfterParsing = leftOverInput;
        userInputLeftAfterParsing = switchAllToLowerCase(userInputLeftAfterParsing);
        userInput = switchAllToLowerCase(userInput);

        // description --> remove those that is detected and used.(like eve
        // detect but will be discarded)
        description = leftOverInput;

      //  System.out
        //        .println("groups: " + groups + " userInput: " + leftOverInput);
        
        while (!dateTimeParser.parse(userInputLeftAfterParsing).isEmpty()) {
            groups = dateTimeParser.parse(userInputLeftAfterParsing);
         //   System.out.println(" userInputLeftAfterParsing: "
           //         + userInputLeftAfterParsing);
            parseDateAndTime(groups, userInput);

       //     System.out.println("dateTime: " + storageOfDate + " "
         //           + storageOfTime);
        }
    }

    /**
     * to prevent case sensitive, switch all to lower case
     * 
     * @param userInput
     * @return the user input all in lower case.
     */
    private String switchAllToLowerCase(String userInput) {
        userInput = userInput.replaceAll("\\s+", " ");
        userInput = " " + userInput.toLowerCase() + " ";
        return userInput;
    }
    
    /**
     * get the left over of user input after all detection of time and date
     * @return part of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get the date time list containing date and time in <weekday> <dd/mm/yyy>
     * 
     * @return a list containing date in <weekday> <dd/mm/yyyy>
     */

    public ArrayList<String> getDateList() {
        return storageOfDate;
    }

    /**
     * get the date time list containing date and time in <hh:mm>
     * 
     * @return a list containing date and time in <hh:mm>
     */
    public ArrayList<String> getTimeList() {
        return storageOfTime;
    }

    /**
     * 1. change the date to String 2. change from<weekday> <month in word>
     * <day> <hh:mm:ss> <timezone> <year> to <weekday> <day/month/year> <hh:mm>
     * 3. bypass those like eve that should be in description 
     * 
     * @param dates
     *            : contains all of the date detected
     * @param parseMapF
     *            : contains which date and time is detected
     */

    private void changeDateFormat(List<Date> dates,
            Map<String, List<ParseLocation>> parseMap, String matchingValue,
            int position) {
      
        for (int i = 0; i < dates.size(); i++) {
            String time = getTime(dates.get(i));
            String date = getDate(dates.get(i));

            if (parseMap.containsKey("date")
                    && i + 1 <= parseMap.get("date").size()
                    || parseMap.containsKey("relative_date_span")
                    || parseMap.containsKey("day_of_week")) {
                storageOfDate.add(date);
                setDatePosition(position, matchingValue);
            }

            if ((parseMap.containsKey("explicit_time") && i + 1 <= parseMap
                    .get("explicit_time").size())
                    || parseMap.containsKey("relative_time_span")) {
                storageOfTime.add(time);
                setTimePosition(position, matchingValue);
            }

        //    System.out.println("date: " + storageOfDate + " sdate: "
          //          + dates.get(i) + " time: " + storageOfTime + " i: " + i);
        }
    }

    /**
     * parse the date and time
     * 
     * @param groups
     *            : contains the date and time in format : <weekday> <month in
     *            word> <day> <hh:mm:ss> <timezone> <year>
     * @param userInput
     *            : contain the user input
     * @return dates: store all of the detected date and time in it
     */
    private void parseDateAndTime(List<DateGroup> groups, String userInput) {
        List<Date> dates = null;
        for (DateGroup group : groups) {
            dates = (group.getDates());

            String matchingValue = group.getText();
            String syntaxTree = group.getSyntaxTree().toStringTree();
            Map<String, List<ParseLocation>> parseMap = group
                    .getParseLocations();

            int position = getPosition(userInput, matchingValue);

            assert position != -1 : "Extra conjunction detected, thus no exact text "
                    + "detect in user input. User pls remove all conjuction before date and time";

            userInputLeftAfterParsing = userInputLeftAfterParsing.replaceFirst(
                    matchingValue, "");

       //     System.out.println("parseDate: " + dates + " mv: " + matchingValue
         //          + " userInputLeftAfterParsing: "
           //         + userInputLeftAfterParsing + " syntaxTree: " + syntaxTree
             //       + " parseMap: " + parseMap);

            // so as not to detect the 10 in run 10 rounds
            if (!isNumeric(matchingValue) && !matchingValue.equals("eve")) {
                description = description.replaceFirst(matchingValue, "");
                addMatchedDateTextInUserInput(matchingValue);
                changeDateFormat(dates, parseMap, matchingValue, position);
            }

        }
    }

    /**
     * get the position
     * 
     * @param userInput
     * @param matchingValue
     * @return
     */
    private int getPosition(String userInput, String matchingValue) {
        return userInput.indexOf(matchingValue);
       
    }
    /**
     * set the start and end date in the right position in arrayList
     *
     * @param indexMatch
     *            : index of the current one
     */
    private void setDatePosition(int indexMatch, String matchingValue) {

        if (storageOfDate.size() == 2
                && (indexMatch < indexDate || matchingValue.contains("now"))) {
            String tempForDate = storageOfDate.get(0);
            storageOfDate.set(0, storageOfDate.get(1));
            storageOfDate.set(1, tempForDate);
            indexDate = indexMatch;
        }

    }

    /**
     * set the start and end time in the right position in arrayList
     *
     * @param indexMatch
     *            : index of the current one
     */
    private void setTimePosition(int indexMatch, String matchingValue) {

        if (storageOfTime.size() == 2 && indexMatch < indexTime) {
            String tempForTime = storageOfTime.get(0);
            storageOfTime.set(0, storageOfTime.get(1));
            storageOfTime.set(1, tempForTime);
            indexTime = indexMatch;

        }
    }

    /**
     * check is it numeric
     * 
     * @param numberOfDaysFromNow
     * @return
     */
    private boolean isNumeric(String matchingValue) {
        boolean isNumeric = false;
        matchingValue = matchingValue.replaceAll("\\s+", " ");
        matchingValue = matchingValue.trim();
        String[] partOfMatchingValue = matchingValue.split(" ");

        Pattern numberDectector = Pattern.compile("\\b\\d+\\b");
        Matcher matchedWithNumber = numberDectector
                .matcher(partOfMatchingValue[0]);
        
        if (matchedWithNumber.find()) {
            matchingValue = matchingValue.replaceAll(matchedWithNumber.group(), "");
            if(matchingValue.length() == 0 && partOfMatchingValue.length == 1)
            isNumeric = true;
        }
      
        return isNumeric;
    }

    /**
     * add the matching date text detected in user input to the arraylist
     * 
     * @param matchingValue
     *            : date text detected
     */
    private void addMatchedDateTextInUserInput(String matchingValue) {
        dateTextInUserInput.add(matchingValue);
    }

    /**
     * convert the time to string format(HH:MM)
     * 
     * @param dates
     *            in the format of date and <weekday> <month in word> <day>
     *            <hh:mm:ss> <timezone> <year>
     * @return the Time in string format of <hh:mm>
     */
    private String getTime(Date dates) {
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy");

        newDateFormat.applyPattern("HH:mm");
        String time = newDateFormat.format(dates);

        return time;
    }

    /**
     * convert the date to string format <weekday> <day/month/year>
     * 
     * @param dates
     *            : in the format of date and <weekday> <month in word> <day>
     *            <hh:mm:ss> <timezone> <year>
     * @return the Time in string format of <weekday> <day/month/year>
     * 
     */
    private String getDate(Date dates) {
        SimpleDateFormat newDateFormat = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy");

        newDateFormat.applyPattern("dd/MM/yyyy");
        String date = newDateFormat.format(dates);

        return date;
    }

    /**
     * get the date text detected in the userInput
     * 
     * @return a list containing text detected
     */
    public ArrayList<String> getDateTextInUserInput() {
        return dateTextInUserInput;
    }
}

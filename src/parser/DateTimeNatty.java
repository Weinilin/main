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

public class DateTimeNatty {
    private  final String DATE_FORMAT = "EEE dd/MM/yyyy HH:mm";
    private  final String NUMBERIC_KEYWORD = "(\\b\\d{0,3}\\b)";
    private  ArrayList<String> storageOfDate = new ArrayList<String>();
    private  ArrayList<String> storageOfTime = new ArrayList<String>();
    private  ArrayList<String> dateTextInUserInput = new ArrayList<String>();
    private  String userInputLeftAfterParsing;
    private String description;
    private  int index;

    public DateTimeNatty(String userInput) {
        List<DateGroup> groups = new ArrayList<DateGroup>();
        // List<Date> dates = new ArrayList<Date>();
        storageOfDate.clear();
        storageOfTime.clear();
        Parser dateTimeParser = new Parser();
      
        // userInputLeftAfterParsing : remove those detected --> prevent infinite loop
        userInputLeftAfterParsing = userInput;
        //description --> remove those that is detected and used.(like eve detect but will be discarded)
        description = userInput;
        System.out.println("groups: " + groups + " userInput: " + userInput);
        while (!dateTimeParser.parse(userInputLeftAfterParsing).isEmpty()) {
            groups = dateTimeParser.parse(userInputLeftAfterParsing);
            System.out.println(" userInput: " + userInputLeftAfterParsing);
            parseDateAndTime(groups, userInput);

            // changeDateFormat(dateTimeList, dates);
            System.out.println("dateTime: " + storageOfDate + " "
                    + storageOfTime);

            // System.out.println(" userInput: " + userInputLeftAfterParsing);
        }
    }

    public String getDescription(){
        return description;
    }
    /**
     * get the date time list containing date and time in <weekday> <dd/mm/yyy>
     * 
     * @return a list containing date in <weekday> <dd/mm/yyyy>
     */

    public  ArrayList<String> getDateList() {
        return storageOfDate;
    }

    /**
     * get the date time list containing date and time in <hh:mm>
     * 
     * @return a list containing date and time in <hh:mm>
     */
    public  ArrayList<String> getTimeList() {
        return storageOfTime;
    }
    
    /**
     * 1. change the date to String 2. change from<weekday> <month in word>
     * <day> <hh:mm:ss> <timezone> <year> to <weekday> <day/month/year> <hh:mm>
     * 
     * @param dates
     *            : contains all of the date detected
     * @param parseMap : contains which date and time is detected
     */

    private void changeDateFormat(List<Date> dates, Map<String, List<ParseLocation>> parseMap) {
        System.out.println("CHANGEdates: " + dates);
        for (int i = 0; i < dates.size(); i++) {
            String time = getTime(dates.get(i));
            String date = getDate(dates.get(i));

            if (parseMap.containsKey("date") && i + 1 <= parseMap.get("date").size()) {
                storageOfDate.add(date);
            }

            if ((parseMap.containsKey("explicit_time") && i + 1 <= parseMap.get("explicit_time").size())
                    || parseMap.containsKey("relative_time_span")) {
                storageOfTime.add(time);
            }

            System.out.println("date: " + storageOfDate + " time: "
                    + storageOfTime + " i: " + i);
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
            int line = group.getLine();
            int column = group.getPosition();
            String matchingValue = group.getText();
            String syntaxTree = group.getSyntaxTree().toStringTree();
            Map<String, List<ParseLocation>> parseMap = group.getParseLocations();

            assert userInputLeftAfterParsing.indexOf(matchingValue) != -1 : "Extra conjunction detected, thus no exact text "
                    + "detect in user input. User pls remove all conjuction before date and time";

            userInputLeftAfterParsing = userInputLeftAfterParsing.replaceFirst(
                    matchingValue, "");
            //userInputLeftAfterParsing = userInputLeftAfterParsing.trim();

            System.out.println("parseDate: " + dates + " mv: " + matchingValue
                    + " userInputLeftAfterParsing: "
                    + userInputLeftAfterParsing + " line: " + line
                    + " syntaxTree: " + syntaxTree + " column: " + column
                    + " parseMap: " + parseMap);

            // so as not to detect the 10 in run 10 rounds
            if (!isNumeric(matchingValue) && !matchingValue.equals("eve")) {
                System.out.println("description: "+description);
                description = description.replaceFirst(matchingValue, "");
                addMatchedDateTextInUserInput(matchingValue);
                changeDateFormat(dates, parseMap);
            }

            // int indexMatch = userInput.indexOf(matchingValue);
            setThePosition(column);

        }
    }

    /**
     * set the start and end date in the right position in arrayList
     *
     * @param indexMatch
     *            : index of the current one
     */
    private void setThePosition(int indexMatch) {

        if (storageOfTime.size() == 2 && indexMatch < index) {
            String tempForTime = storageOfTime.get(0);
            storageOfTime.set(0, storageOfTime.get(1));
            storageOfTime.set(1, tempForTime);
        }
        if (storageOfDate.size() == 2 && indexMatch < index) {
            String tempForDate = storageOfDate.get(0);
            storageOfDate.set(0, storageOfDate.get(1));
            storageOfDate.set(1, tempForDate);
        }
        index = indexMatch;
    }

    /**
     * check is it numeric
     * 
     * @param numberOfDaysFromNow
     * @return
     */
    private boolean isNumeric(String matchingValue) {
        return matchingValue.matches(NUMBERIC_KEYWORD);
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
        // DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
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
        // DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
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

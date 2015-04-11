package parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.Parser;

/**
 * Extract date and time from a library known as natty. Time: 1) _
 * hour/minute/second ago 2) in _ hour/minute/second 3) _ hour/minute/second
 * from now 5) 0600h/06:00 hours 5) noon/afternoon/midnight/morning Date: 1)
 * three/3 weeks/month/day/year ago 2) three/3 weeks/month/day/year from now 3)
 * next weeks/month/day/year 6) today/tomorrow/yesterday 7) _ or _ / _ or _ 8)
 * day/weekday before 9) day/weekday after 10) word in month DD
 * 
 * @author A0112823R
 *
 */
public class DateTimeNattyParser {
    private final String MONNTHINWORD_DD_YYYY_KEYWORD = "\\b(january|febuary|march|april|may|june|july|august|september|octobor|november|december)"
            + "|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)(\\s|)(\\w+|)(-|)\\w+(th|nd|rd|st|)(\\s|)(\\d+|)\\b";
    private final String HOUR_FORMAT_KEYWORD = "\\b\\d+(\\s|)(hour|hr)(s|)\\b";
    private ArrayList<String> storageOfDate = new ArrayList<String>();
    private ArrayList<String> storageOfTime = new ArrayList<String>();
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
            int indexPrevTime, int indexPrevDate)
            throws IllegalArgumentException {
        List<DateGroup> groups = new ArrayList<DateGroup>();

        storageOfDate.clear();
        storageOfTime.clear();
        storageOfDate.addAll(dates);
        storageOfTime.addAll(times);

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

        testValidTime(userInputLeftAfterParsing);
        testValidDate(userInputLeftAfterParsing);

        Parser dateTimeParser = new Parser();
        while (!dateTimeParser.parse(userInputLeftAfterParsing).isEmpty()) {

            groups = dateTimeParser.parse(userInputLeftAfterParsing);
            parseDateAndTime(groups, userInput);
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
     * 
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
     * <day> <HH:mm:ss> <timezone> <year> to <weekday> <day/month/year> <hh:mm>
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
        }
    }

    /**
     * parse the date and time
     * 
     * @param groups
     *            : contains the date and time in format : <weekday> <month in
     *            word> <day> <HH:mm:ss> <timezone> <year>
     * @param userInput
     *            : contain the user input
     * @return dates: store all of the detected date and time in it
     */
    private void parseDateAndTime(List<DateGroup> groups, String userInput) {
        List<Date> dates = null;
        for (DateGroup group : groups) {
            dates = (group.getDates());

            String matchingValue = group.getText();

            // String syntaxTree = group.getSyntaxTree().toStringTree();
            Map<String, List<ParseLocation>> parseMap = group
                    .getParseLocations();

            int position = getPosition(userInput, matchingValue);

            assert position != -1 : " can't exact text "
                    + "detect in user input.";

            userInputLeftAfterParsing = remove(matchingValue,
                    userInputLeftAfterParsing);

            // so as not to detect the 10 in run 10 rounds
            if (!isNumeric(matchingValue) && !matchingValue.equals("eve")) {
                description = remove(matchingValue, description);

                changeDateFormat(dates, parseMap, matchingValue, position);
            }

        }
    }

    private String remove(String matchingValue, String text) {
        text = text.trim();
        matchingValue = matchingValue.replaceAll("\\s+", " ");
        String[] partOfMatchingValue = matchingValue.split(" ");

        for (int i = 0; i < partOfMatchingValue.length; i++) {
            text = text.replaceFirst(partOfMatchingValue[i], "");
        }
        return text;
    }

    /**
     * 1) test 24 hour format.(HHMM <hour, hours, hr, hrs)
     * 
     * @param userInputLeft
     * @throws : IllegalArgumentException : hour exceed 23 and min exceed 59
     */
    private void testValidTime(String userInputLeft)
            throws IllegalArgumentException {
        try {
            Pattern timeDetector = Pattern.compile(HOUR_FORMAT_KEYWORD);
            Matcher containTime = timeDetector.matcher(userInputLeft);

            while (containTime.find()) {
                Parser dateTimeParser = new Parser();
                if (dateTimeParser.parse(userInputLeft).isEmpty()) {
                    throw new IllegalArgumentException(
                            "24-hour time is invalid!");
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * test 1. day exceed max day of that month 2) month out of range
     * 
     * @param leftOverUserInput
     * @throws IllegalArgumentException
     *             : month or day out of range
     */
    private void testValidDate(String leftOverUserInput)
            throws IllegalArgumentException {

        Pattern dateDetector = Pattern.compile(MONNTHINWORD_DD_YYYY_KEYWORD);
        Matcher containDate = dateDetector.matcher(leftOverUserInput);

        while (containDate.find()) {
            String date = containDate.group();

            testEmptyGroupAfterParse(date);

            int month = MonthParser.convertMonthToNumber(date);

            int day = NumberParser.getNumber(date);

            int year = YearParser.getYear(date);

            testValidMonth(month);
            testValidDay(day, year, month);

        }
    }

    /**
     * if the group is empty after parse means that the date exceed the max day
     * that mean or the month is out of range (1-12)
     * 
     * @param leftOverUserInput
     * @throws IllegalArgumentException
     *             : month or max day exceeded
     */
    private void testEmptyGroupAfterParse(String leftOverUserInput)
            throws IllegalArgumentException {
        try {
            Parser dateTimeParser = new Parser();
            if (dateTimeParser.parse(leftOverUserInput).isEmpty()) {
                throw new IllegalArgumentException("Date key is invalid!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * throws and catch exception of invalid month
     * 
     * @param month
     * @throws IllegalArgumentException
     *             : month entered out of range
     */
    private void testValidMonth(int month) throws IllegalArgumentException {
        try {
            if (month == 0) {
                throw new IllegalArgumentException("Invalid Month Keyed!");
            }
        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * check if the date is valid if the year and the month have this day For
     * example feb only have max 29 days
     * 
     * @param day
     * @throws IllegalArgumentException
     *             : when day keyed exceed the max day
     */
    private void testValidDay(int day, int year, int month)
            throws IllegalArgumentException {
        try {
            Calendar calendar = Calendar.getInstance();

            if (year != 0) {
                calendar.set(Calendar.YEAR, year);
            }
            calendar.set(Calendar.MONTH, month - 1);

            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            if (day == 0 || maxDays < day) {
                throw new IllegalArgumentException(
                        "Invalid Day Keyed! Exceed the maximum day in that month");
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
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
        matchingValue = matchingValue.replaceAll("\\s+", " ");
        matchingValue = matchingValue.trim();
        String[] partOfMatchingValue = matchingValue.split(" ");

        return userInput.indexOf(partOfMatchingValue[0]);

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

    private boolean isNumeric(String matchingValue) {
        boolean isNumeric = false;
        matchingValue = matchingValue.replaceAll("\\s+", " ");
        matchingValue = matchingValue.trim();
        String[] partOfMatchingValue = matchingValue.split(" ");

        Pattern numberDectector = Pattern.compile("\\b\\d+\\b");
        Matcher matchedWithNumber = numberDectector
                .matcher(partOfMatchingValue[0]);

        if (matchedWithNumber.find()) {
            matchingValue = matchingValue.replaceAll(matchedWithNumber.group(),
                    "");
            if ((matchingValue.length() == 0 && partOfMatchingValue.length == 1)
                    || (partOfMatchingValue.length == 2 && isEqualToMeridiem(partOfMatchingValue))) {
                isNumeric = true;
            }
        }

        return isNumeric;
    }

    /**
     * natty could detect <digit> a from "tutorial 11 preparation today" thus
     * must bypass it
     * 
     * @param partOfMatchingValue
     * @return
     */
    private boolean isEqualToMeridiem(String[] partOfMatchingValue) {
        return partOfMatchingValue[1].equals("a")
                || partOfMatchingValue[1].equals("p")
                || partOfMatchingValue[1].equals("am")
                || partOfMatchingValue[1].equals("pm")
                || partOfMatchingValue[1].equals("a.m.")
                || partOfMatchingValue[1].equals("p.m.");
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

}

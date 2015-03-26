package parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;

public class DateParser {
    private static final String DDMMYYYY_KEYWORD = "\\b(on |at |from |to |)\\d+([/.]\\d+[/.]\\d+|[/]\\d+\\b)\\b";
    private static final String DD_MONNTHINWORD_YYYY_KEYWORD = "(on |at |from |to |)\\b\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(january|febuary|march|april|may|june|july|august"
            + "|september|octobor|november|december)(\\s|\\S)(\\d+|)";
    private static final String DD_SHORTFORMMONTHINWORD_YYYY_KEYWORD = "(on |at |from |to |)\\b\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(jan|feb|mar|apr|may|jun|jul|aug"
            + "|sep|oct|nov|dec)(\\s|\\S)(\\d+|)";
    private static final String AFTER_DAYS_APART_KEYWORD = "\\b(after \\w+ day(s|))\\b|(\\w+ day(s|) after)|\\b(next(\\s\\w+\\s)day(s|)"
            + "\\b)|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b";
    private static final String DAYS_APART_VOCAB_KEYWORD = "\\b((tomorrow|tmr)|(the\\s|)following day|(the\\s|)next day"
            + "|(after today)|today|(after (tomorrow|tmr))|fortnight|(the\\s|)next year)\\b";
    private static final String WEEKS_MONTHS_YEARS_APART_KEYWORD = " \\b(in \\w+ (week|month|year)(s|) time(s|))\\b|"
            + "\\b(\\w+ (week|month|year)(?!~)(s|) later)|(after \\w+ (week|month|year)(s|))|"
            + "(\\w+ (week|month|year)(s|) after)\\b";
    private static final String WEEKDAY_APART_KEYWORD = " next (mon|tues|wed|thurs|fri|sat|sun)";
    private static final String NUMBERIC_KEYWORD = "(\\b\\d+\\b)";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String UNWANTED_ALPHA = "(day(s|)|from now|after|next|later)|\\s";
    private static String[] wordOfNumDays = { "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine", "ten" };
    private static final String TODAY_TEXT = "today";
    private static final String TOMORROW_TEXT = "tomorrow";
    private static final String NEXT_DAY_TEXT = "next day";
    private static final String NEXT_YEAR_TEXT = "next year";
    private static final String FOLLOWING_DAY_TEXT = "following day";
    private static final String AFTER_TODAY_TEXT = "after today";
    private static final String AFTER_TOMORROW_TEXT = "after tomorrow";
    private static final String WEEK_TEXT = "week";
    private static final String MONTH_TEXT = "month";
    private static final String YEAR_TEXT = "year";
    private static final String FORTNIGHT_TEXT = "fortnight";
    private static final int DATE_FORMAT_1 = 1;
    private static final int DATE_FORMAT_2 = 2;
    private static final int DATE_FORMAT_3 = 3;
    private static final int DATE_FORMAT_4 = 4;
    private static final int DATE_FORMAT_5 = 5;
    private static final int DATE_FORMAT_6 = 6;
    private static final int DATE_FORMAT_7 = 7;
    private static final int WEEK_UNIT = 7;
    private static final int FORTNIGHT_UNIT = 14;
    private static String inputToBeDetected;
    private static int index;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public DateParser(String dateTime) {
        processFormattedString(dateTime);
    }

    public DateParser() {
    }

    // need another constructor for parsing unformatted string
    private void processFormattedString(String dateTime) {
        day = Integer.parseInt(dateTime.substring(0, 2));
        month = Integer.parseInt(dateTime.substring(3, 5));
        year = Integer.parseInt(dateTime.substring(6, 10));
        hour = Integer.parseInt(dateTime.substring(11, 13));
        minute = Integer.parseInt(dateTime.substring(14, 16));
    }

    public long getDateTimeInMilliseconds() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDateTime = formatDateTime();
        Date date = null;

        try {
            date = sdf.parse(formattedDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTimeInMilliseconds = date.getTime();

        return dateTimeInMilliseconds;
    }

    public String formatDateTime() {
        String formattedDateTime = day + "/" + month + "/" + year + " " + hour
                + ":" + minute;
        return formattedDateTime;
    }

    /**
     * 
     * @param userInput
     * @return String in the format of dd/mm/yyyy and return the current date if
     *         nothing is detected
     */
    public static ArrayList<String> extractDate(String userInput) {
        ArrayList<String> dateOfTheTask = new ArrayList<String>();

        userInput = switchAllToLowerCase(userInput);
        userInput = removeThoseHashTag(userInput);
        inputToBeDetected = userInput;
        for (int i = 1; i <= 7; i++) {
            dateOfTheTask = selectDetectionMethod(i, dateOfTheTask, userInput);
        }

        return dateOfTheTask;
    }

    /**
     * indication of ~ ~means that user want it to be in description ~~ is an
     * escaped character
     * 
     * @param userInput
     * @return user input without ~
     */
    private static String removeThoseHashTag(String userInput) {
        ArrayList<Integer> hashTagIndex = new ArrayList<Integer>();
        Pattern hashTagDetector = Pattern.compile("~");
        Matcher containHashTag = hashTagDetector.matcher(userInput);

        while (containHashTag.find()) {
            hashTagIndex.add(containHashTag.start());

        }
        if (!hashTagIndex.isEmpty()) {
            userInput = userInput.substring(0, hashTagIndex.get(0))
                    + userInput.substring(hashTagIndex.get(1));
        }
        return userInput;
    }

    /**
     * to prevent case sensitive, switch all to lower case
     * 
     * @param userInput
     * @return the user input all in lower case.
     */
    private static String switchAllToLowerCase(String userInput) {
        userInput = userInput.toLowerCase() + ".";
        return userInput;
    }

    /**
     * select detection method of different format
     * 
     * @param dateFormat
     * @param dates
     * @return all of the dates detected.
     */
    private static ArrayList<String> selectDetectionMethod(int dateFormat,
            ArrayList<String> dates, String userInput) {

        if (dateFormat == DATE_FORMAT_1) {
            dates = spotDDMMYYYYKeyword(userInput, dates);
        } else if (dateFormat == DATE_FORMAT_2) {
            dates = spotDDMonthInWordYYYY(inputToBeDetected,
                    DD_MONNTHINWORD_YYYY_KEYWORD, dates);
        } else if (dateFormat == DATE_FORMAT_3) {
            dates = spotDDMonthInWordYYYY(inputToBeDetected,
                    DD_SHORTFORMMONTHINWORD_YYYY_KEYWORD, dates);
        } else if (dateFormat == DATE_FORMAT_4) {
            dates = spotAfterDaysApartKeyword(userInput, dates);
        } else if (dateFormat == DATE_FORMAT_5) {
            dates = spotDaysApartVocab(userInput, dates);
        } else if (dateFormat == DATE_FORMAT_6) {
            dates = spotWeekMonthYearApartKeyword(userInput, dates);
        } else if (dateFormat == DATE_FORMAT_7) {
            dates = spotWeekdayApartKeyWord(userInput, dates);
        }

        return dates;
    }

    /**
     * spot date of next mon-sun could be in short form(mon-sun) or
     * monday-sunday
     * 
     * @param userInput
     * @param storageOfDate
     * @return date in DD/MM/YYYY format
     */
    private static ArrayList<String> spotWeekdayApartKeyWord(String userInput,
            ArrayList<String> storageOfDate) {
        String dateOfTheTask = "", nextWeekdayInput = "";
        Pattern dateDetector = Pattern.compile(WEEKDAY_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputToBeDetected);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            nextWeekdayInput = containDate.group();
            inputToBeDetected = inputToBeDetected.replaceAll(nextWeekdayInput,
                    "");
            nextWeekdayInput = nextWeekdayInput.trim();
            Calendar calendar = Calendar.getInstance();
            int todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            String[] containsWeekday = nextWeekdayInput.split(" ");
            int dayOfTheWeek = detectDayOfWeek(containsWeekday[1]);
            dateOfTheTask = getFinalDateWithDayOfWeekAdded(dateOfTheTask,
                    dayOfTheWeek, todayDayOfWeek);
            storageOfDate.add(dateOfTheTask);

            int indexMatched = toGetIndex.start();
            setThePosition(storageOfDate, indexMatched);
        }
        return storageOfDate;
    }

    /**
     * get the final date after day of weeks is being added
     * 
     * @param dateOfTheTask
     * @param dayOfTheWeek
     * @param todayDayOfWeek
     * @return date in DD/MM/YYYY
     */
    private static String getFinalDateWithDayOfWeekAdded(String dateOfTheTask,
            int dayOfTheWeek, int todayDayOfWeek) {

        if (todayDayOfWeek == dayOfTheWeek) {
            dateOfTheTask = addToTheCurrentDateByDays(7);
        } else if (todayDayOfWeek > dayOfTheWeek) {
            dateOfTheTask = addToTheCurrentDateByDays(7 - todayDayOfWeek
                    + dayOfTheWeek);
        } else {
            dateOfTheTask = addToTheCurrentDateByDays(dayOfTheWeek
                    - todayDayOfWeek);
        }
        return dateOfTheTask;
    }

    /**
     * detect after no weeks, after no months, after no years s is not sensitive
     * 
     * @param userInput
     * @param storageOfDate
     * @return date in DD/MM/YYYY
     */
    private static ArrayList<String> spotWeekMonthYearApartKeyword(
            String userInput, ArrayList<String> storageOfDate) {
        String dateOfTheTask = "", uniqueKeyword = "";

        Pattern dateDetector = Pattern
                .compile(WEEKS_MONTHS_YEARS_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputToBeDetected);
        Matcher matchWithIndex = dateDetector.matcher(userInput);

        while (containDate.find() && matchWithIndex.find()) {
            uniqueKeyword = containDate.group();
            inputToBeDetected = inputToBeDetected.replaceAll(uniqueKeyword, "");

            int numberOfDays = getNumberOfDaysDetected(uniqueKeyword);
            int numberOfMonths = getNumberOfMonthDetected(uniqueKeyword);
            int numberOfYears = getNumberOfYearsDetected(uniqueKeyword);
            if (numberOfDays != 0) {
                dateOfTheTask = addToTheCurrentDateByDays(numberOfDays);
            } else if (numberOfMonths != 0) {
                dateOfTheTask = addToTheCurrentDateByMonth(numberOfMonths);
            } else if (numberOfYears != 0) {
                dateOfTheTask = addToTheCurrentDateByYear(numberOfYears);
            }

            storageOfDate.add(dateOfTheTask);

            int indexMatch = matchWithIndex.start();
            setThePosition(storageOfDate, indexMatch);
        }

        return storageOfDate;
    }

    /**
     * detect tomorrow, the following day, after tomorrow, after today
     * 
     * @param userInput
     * @param storageOfDate
     * @return dates in DD/MM/YYYY
     */
    private static ArrayList<String> spotDaysApartVocab(String userInput,
            ArrayList<String> storageOfDate) {
        String dateOfTheTask = "";
        Pattern dateDetector = Pattern.compile(DAYS_APART_VOCAB_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputToBeDetected);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            String daysApartVocab = containDate.group();
            inputToBeDetected = inputToBeDetected.replaceAll(
                    DAYS_APART_VOCAB_KEYWORD, "");

            int numberOfDays = getNumberOfDaysDetected(daysApartVocab);
            dateOfTheTask = addToTheCurrentDateByDays(numberOfDays);
            storageOfDate.add(dateOfTheTask);

            int indexMatch = toGetIndex.start();
            setThePosition(storageOfDate, indexMatch);
        }

        return storageOfDate;
    }

    /**
     * set the start and end date in the right position in arrayList
     * 
     * @param storageOfDate
     * @param indexMatch
     */
    private static void setThePosition(ArrayList<String> storageOfDate,
            int indexMatch) {
        String temp;
        if (storageOfDate.size() == 2 && indexMatch < index) {
            temp = storageOfDate.get(0);
            storageOfDate.set(0, storageOfDate.get(1));
            storageOfDate.set(1, temp);
        }
        index = indexMatch;
    }

    /**
     * add number of days to the current date
     * 
     * @param numberOfDay
     * @return date after adding number of days in DD/MM/YYYY.
     */
    private static String addToTheCurrentDateByDays(int numberOfDay) {
        String dateOfTheTask = "";

        DateFormat date = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, numberOfDay);
        dateOfTheTask = date.format(cal.getTime());
        return dateOfTheTask;
    }

    /**
     * when the number of day is in word format change from word format to
     * integer format
     * 
     * @param numberOfDaysFromNow
     * @return number of day in integer.
     */
    private static int determineIntDaysFromWords(String numberOfDaysFromNow) {
        int numberOfDays = 1;
        for (int i = 0; i < wordOfNumDays.length; i++) {
            if (numberOfDaysFromNow.equals(wordOfNumDays[i])) {
                break;
            }
            numberOfDays++;
        }

        // 11 means nothing from array(wordOfNumDays) is equal, no number of day detect
        if (numberOfDays == 11) {
            numberOfDays = 0;
        }
        return numberOfDays;
    }

    private static boolean isNumeric(String numberOfDaysFromNow) {
        return numberOfDaysFromNow.matches(NUMBERIC_KEYWORD);
    }

    /**
     * detect date: after (no. in word or int) days, next (no in word or int)
     * day is okay to have s behind or no s behind day
     * 
     * @param userInput
     * @return the date in DD/MM/YYYY format.
     */
    private static ArrayList<String> spotAfterDaysApartKeyword(
            String userInput, ArrayList<String> storageOfDate) {
        String uniqueKeyword = "", dateOfTask = "";

        Pattern dateDetector = Pattern.compile(AFTER_DAYS_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputToBeDetected);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            uniqueKeyword = containDate.group();
            inputToBeDetected = inputToBeDetected.replaceAll(
                    AFTER_DAYS_APART_KEYWORD, "");

            String numberOfDaysFromNow = removeAllOtherThanNumberOfDay(uniqueKeyword);
            int numberOfDays = getNumberOfDaysDetected(numberOfDaysFromNow);
            dateOfTask = addToTheCurrentDateByDays(numberOfDays);
            storageOfDate.add(dateOfTask);

            int indexMatch = toGetIndex.start();
            setThePosition(storageOfDate, indexMatch);
        }
        return storageOfDate;
    }

    /**
     * detect day of week from mon-sun
     * 
     * @param userInput
     * @return day of week
     */
    private static int detectDayOfWeek(String userInput) {
        int dayOfWeek = 0;
        // System.out.println("input: "+input);
        if (userInput.contains("mon")) {
            dayOfWeek = 1;
        } else if (userInput.contains("tues")) {
            dayOfWeek = 2;
        } else if (userInput.contains("wed")) {
            dayOfWeek = 3;
        } else if (userInput.contains("thrus")) {
            dayOfWeek = 4;
        } else if (userInput.contains("fri")) {
            dayOfWeek = 5;
        } else if (userInput.contains("sat")) {
            dayOfWeek = 6;
        } else if (userInput.contains("sun")) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * get number of day detected
     * 
     * @param uniqueKeyword
     * @return date in DD/MM/YYYY
     */
    private static int getNumberOfDaysDetected(String uniqueKeyword) {
        int numberOfDays = 0;

        if (uniqueKeyword.equals(TODAY_TEXT)) {
            numberOfDays = 0;
        } else if (uniqueKeyword.equals(TOMORROW_TEXT)
                || uniqueKeyword.equals("tmr")) {
            numberOfDays = 1;
        } else if (uniqueKeyword.contains(NEXT_DAY_TEXT)) {
            numberOfDays = 1;
        } else if (uniqueKeyword.contains(FOLLOWING_DAY_TEXT)) {
            numberOfDays = 1;
        } else if (uniqueKeyword.equals(AFTER_TODAY_TEXT)) {
            numberOfDays = 1;
        } else if (uniqueKeyword.equals(AFTER_TOMORROW_TEXT)
                || uniqueKeyword.equals("after tmr")) {
            numberOfDays = 2;
        } else if (uniqueKeyword.contains(WEEK_TEXT)) {
            int numberOfWeek = isolateTheNumberInString(uniqueKeyword);
            numberOfDays = WEEK_UNIT * numberOfWeek;
        } else if (uniqueKeyword.contains(FORTNIGHT_TEXT)) {
            int numberOfFornight = isolateTheNumberInString(uniqueKeyword);
            numberOfDays = FORTNIGHT_UNIT * numberOfFornight;
        } else if (isNumeric(uniqueKeyword)) {
            numberOfDays = Integer.parseInt(uniqueKeyword);
        } else {
            numberOfDays = determineIntDaysFromWords(uniqueKeyword);
        }

        return numberOfDays;
    }

    /**
     * get number of month detected
     * 
     * @param uniqueKeyword
     * @return date in DD/MM/YYYY
     */
    private static int getNumberOfMonthDetected(String uniqueKeyword) {
        int numberOfMonth = 0;

        if (uniqueKeyword.contains(MONTH_TEXT)) {
            numberOfMonth = isolateTheNumberInString(uniqueKeyword);
        }

        return numberOfMonth;
    }

    /**
     * get number of year detected in user input
     * 
     * @param uniqueKeyword
     * @return date in DD/MM/YYYY
     */
    private static int getNumberOfYearsDetected(String uniqueKeyword) {
        int numberOfYear = 0;

        if (uniqueKeyword.contains(NEXT_YEAR_TEXT)) {
            numberOfYear = 1;
        } else if (uniqueKeyword.contains(YEAR_TEXT)) {
            numberOfYear = isolateTheNumberInString(uniqueKeyword);
        }

        return numberOfYear;
    }

    /**
     * add the number of year to current year based on what is detect
     * 
     * @param numberOfYear
     * @return date in DD/MM/YYYY
     */
    private static String addToTheCurrentDateByYear(int numberOfYear) {
        String dateOfTheTask = "";

        DateFormat date = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, numberOfYear);
        dateOfTheTask = date.format(cal.getTime());

        return dateOfTheTask;
    }

    /**
     * add the number of month to current year based on what is detect
     * 
     * @param numberOfMonth
     * @return date in DD/MM/YYYY
     */
    private static String addToTheCurrentDateByMonth(int numberOfMonth) {
        String dateOfTheTask = "";

        DateFormat date = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, numberOfMonth);
        dateOfTheTask = date.format(cal.getTime());

        return dateOfTheTask;
    }

    /**
     * get the number of days/weeks/months/years
     * 
     * @param uniqueKeyword
     * @return days/weeks/months/years
     */
    private static int isolateTheNumberInString(String uniqueKeyword) {

        uniqueKeyword = removeInFromInput(uniqueKeyword);
        String[] partsOfUniqueKeyword = uniqueKeyword.split(" ");
        String containOnlyNumber = partsOfUniqueKeyword[0];
        int number = determineTheNumber(containOnlyNumber);
        return number;
    }

    /**
     * remove the word "in" in input so that every 1st String in the input is
     * digit
     * 
     * @param uniqueKeyword
     * @return String without "in"
     */
    private static String removeInFromInput(String uniqueKeyword) {
        uniqueKeyword = uniqueKeyword.replaceAll(" in ", "");
        return uniqueKeyword;
    }

    /**
     * determine the number of day.
     * 
     * @param containNumber
     * @return number of day in integer
     */
    private static int determineTheNumber(String containNumber) {
        int numberOfDays;
        if (isNumeric(containNumber)) {
            numberOfDays = Integer.parseInt(containNumber);
        } else {
            numberOfDays = determineIntDaysFromWords(containNumber);
        }
        return numberOfDays;
    }

    /**
     * remove all other except for number of day
     * 
     * @param uniqueKeyword
     * @return number of day in String
     */
    private static String removeAllOtherThanNumberOfDay(String uniqueKeyword) {
        String numberOfDaysFromNow = uniqueKeyword.replaceAll(UNWANTED_ALPHA,
                "");
        return numberOfDaysFromNow;
    }

    /**
     * detect DD Month in word/DD Month in word YYYY with space in between or no
     * space in between DD could be in 2 or 2nd or 2nd of , 2 or 3th or 3th of,
     * 4 or 4th or 4th of etc
     * 
     * @param userInput
     * @return date in DD/MM/YYYY
     */
    private static ArrayList<String> spotDDMonthInWordYYYY(String userInput,
            String keyword, ArrayList<String> storageOfDate) {
        String dateOfTheTask = "";

        Pattern dateDetector = Pattern.compile(keyword);
        Matcher containDate = dateDetector.matcher(userInput);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            dateOfTheTask = containDate.group();
            inputToBeDetected = inputToBeDetected.replaceAll(keyword, "");
            DateFormat date = new SimpleDateFormat(DATE_FORMAT);
            Calendar calendar = Calendar.getInstance();

            int day = getDay(dateOfTheTask);
            int year = getYear(dateOfTheTask);
            int month = convertMonthToNumber(dateOfTheTask);
            System.out.println("month: "+month+" dateOfTheTask: "+dateOfTheTask);
            testValidMonth(month);
       //     testValidDay(day, year, month);

            setDateIntoCalendar(day, month - 1, year, calendar);

            dateOfTheTask = date.format(calendar.getTime());

            storageOfDate.add(dateOfTheTask);

            int indexOfDatePosition = toGetIndex.start();
            setThePosition(storageOfDate, indexOfDatePosition);

        }

        return storageOfDate;
    }

    /**
     * set day, month, year into the calendar after detected
     * 
     * @param dateOfTheTask
     * @param calendar
     */
    private static void setDateIntoCalendar(int day, int month, int year,
            Calendar calendar) {
        calendar.set(Calendar.DATE, day);
        if (year != 0) {
            calendar.set(Calendar.YEAR, year);
        }
        calendar.set(Calendar.MONTH, month);
    }

    /**
     * get the day from the date It follow singapore date format
     * 
     * @param dateOfTheTask
     * @return day
     */
    private static int getDay(String dateOfTheTask) {
        int day = 0;
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher containDay = numberPattern.matcher(dateOfTheTask);
        String numberText;

        if (containDay.find()) {
            numberText = containDay.group();
            day = Integer.parseInt(numberText);
        }
        return day;
    }

    /**
     * get the month of the date It follow singapore date format
     * 
     * @param dateOfTheTask
     * @return month
     */
    private static int getMonth(String[] ddmmyyyy) {
        // String[] ddmmyyyy = splitTheStringIntoPart(dateOfTheTask);
        int month = Integer.parseInt(ddmmyyyy[1]);
        return month;
    }

    /**
     * get the year from the date it follows singapore date format
     * 
     * @param dateOfTheTask
     * @return year
     */
    private static int getYear(String dateOfTheTask) {
        int year = 0;
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher containYear = numberPattern.matcher(dateOfTheTask);
        String numberText;

        while (containYear.find()) {
            numberText = containYear.group();
            if (numberText.length() == 4) {
                year = Integer.parseInt(numberText);
            }
        }
        return year;
    }

    /**
     * change month in word to month in int
     * 
     * @param dateOfTheTask
     * @return month in int
     */
    private static int convertMonthToNumber(String dateOfTheTask) {
        int month = 0;
        dateOfTheTask = dateOfTheTask.replaceAll("\\d+", "");
        if (dateOfTheTask.contains("jan") || dateOfTheTask.contains("january")) {
            month = 1;
        } else if (dateOfTheTask.contains("feb")
                || dateOfTheTask.contains("february")) {
            month = 2;
        } else if (dateOfTheTask.contains("mar")
                || dateOfTheTask.contains("march")) {
            month = 3;
        } else if (dateOfTheTask.contains("apr")
                || dateOfTheTask.contains("april")) {
            month = 4;
        } else if (dateOfTheTask.contains("may")) {
            month = 5;
        } else if (dateOfTheTask.contains("jun")
                || dateOfTheTask.contains("june")) {
            month = 6;
        } else if (dateOfTheTask.contains("jul")
                || dateOfTheTask.contains("july")) {
            month = 7;
        } else if (dateOfTheTask.contains("aug")
                || dateOfTheTask.contains("august")) {
            month = 8;
        } else if (dateOfTheTask.contains("sep")
                || dateOfTheTask.contains("september")) {
            month = 9;
        } else if (dateOfTheTask.contains("oct")
                || dateOfTheTask.contains("october")) {
            month = 10;
        } else if (dateOfTheTask.contains("nov")
                || dateOfTheTask.contains("november")) {
            month = 11;
        } else if (dateOfTheTask.contains("dec")
                || dateOfTheTask.contains("december")) {
            month = 12;
        }
        // System.out.println("month: "+ month);
        return month;
    }

    /**
     * detct DD/MM and DD/MM/YYYY
     * 
     * @param userInput
     * @return date in DD/MM/YYYY
     */
    private static ArrayList<String> spotDDMMYYYYKeyword(String userInput,
            ArrayList<String> storageOfDate) {
        String dateOfTheTask = "";

        Pattern dateDetector = Pattern.compile(DDMMYYYY_KEYWORD);
        Matcher containDate = dateDetector.matcher(userInput);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            Calendar calendar = Calendar.getInstance();
            dateOfTheTask = containDate.group();
            inputToBeDetected = inputToBeDetected.replaceAll(DDMMYYYY_KEYWORD,
                    "");

            dateOfTheTask = dateOfTheTask.replaceAll("on |from |at |to ", "");
            String[] ddMMYYYY = splitTheStringIntoPart(dateOfTheTask);

            // so we could detect both USA(YYYY/MM/DD) and Singapore(DD/MM/YYYY)
            // date format
            String containYearAndDay = getYearAndDayInAString(ddMMYYYY);

            int day = getDay(containYearAndDay);
            int year = getYear(containYearAndDay);
            int month = getMonth(ddMMYYYY);

            testValidMonth(month);
          //  testValidDay(day, year, month);

            setDateIntoCalendar(day, month - 1, year, calendar);

            DateFormat date = new SimpleDateFormat(DATE_FORMAT);
            dateOfTheTask = date.format(calendar.getTime());

            storageOfDate.add(dateOfTheTask);

            int indexPositionOfThisDate = toGetIndex.start();
            setThePosition(storageOfDate, indexPositionOfThisDate);
        }
        return storageOfDate;
    }

    /**
     * get year and day in a string so we could detect both USA(YYYY/MM/DD) and
     * Singapore(DD/MM/YYYY) date format
     * 
     * @param ddMMYYYY
     * @return a string of year and day
     */
    private static String getYearAndDayInAString(String[] ddMMYYYY) {
        String containYearAndDay;
        if (ddMMYYYY.length == 3) {
            containYearAndDay = ddMMYYYY[0] + " " + ddMMYYYY[2];
        } else {
            containYearAndDay = ddMMYYYY[0];
        }
        return containYearAndDay;
    }

    /**
     * throws and catch exception of invalid month
     * 
     * @param month
     */
    private static void testValidMonth(int month) {
        try {
            if (month <= 0 || month > 12) {
                throw new Exception("Invalid Month Keyed!");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * check if the date is valid if the year and the month have this day For
     * example feb only have max 29 days
     * 
     * @param day
     */
    private static void testValidDay(int day, int year, int month) {
        try {
            Calendar calendar = Calendar.getInstance();

            if (year != 0) {
                calendar.set(Calendar.YEAR, year);
            }
            calendar.set(Calendar.MONTH, month);

            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            if (exceedMaxDaysOnThatMonth(day, maxDays)) {
                throw new Exception("Invalid Day Keyed! Exceed the maximum day in that month");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * check if the day detected is more than the max day in that month
     * 
     * @param day
     * @param maxDays
     * @return true if it exceed, false if it does not
     */
    private static boolean exceedMaxDaysOnThatMonth(int day, int maxDays) {
        return maxDays < day;
    }

    /**
     * split the date DD/MM/YYYY to day, month and year in the array of String
     * 
     * @param dateOfTheTask
     * @return day, month and year in string array.
     */
    private static String[] splitTheStringIntoPart(String dateOfTheTask) {
        String[] ddmmyyyy = null;
        if (dateOfTheTask.contains("/")) {
            ddmmyyyy = dateOfTheTask.split("(/)");
        } else if (dateOfTheTask.contains(".")) {
            ddmmyyyy = dateOfTheTask.split("(\\.)");
        }

        return ddmmyyyy;
    }

}

package parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * 1) dd/mm/yyyy 2) dd/mm 3) dd month in word/shortform month yyyy 4) dd month in word/shortform month 
 * 5) _ day/week/month/year later 6) the following day/tmr/after today/after tomorrow/fortnight 
 * 6) on/by/due/due on/from/to/next/this weekday
 * @author WeiLin
 *
 */
public class Date1Parser {

    private static final String DDMMYYYY_KEYWORD = "\\b\\d+([/.-]\\d+[/.-]\\d+|[/.-]\\d+\\b)\\b";
    private static final String BEFORE_AFTER_MMDD_KEYWORD = "\\b\\w+(\\s|)(day(s|) before|the day(s|) before|(mon|tues|wed|thurs|fri|sat|sun)(day|nesday|urday|)(s|)( before| after)) (\\d+([/.]\\d+[/.-]\\d+|[/.-]\\d+)\\b)";
    private static final String DETECT_ONLY_BY_NATTY_KEYWORD = "(\\b(next|this|at|by|due on|on|due) (mon|tues|wed|thurs|fri|sat|sun)(day|nesday|urday|)(s|) (or|and) "
            + "(mon|tues|wed|thurs|fri|sat|sun)(day|nesday|urday|)(s|)\\b)";
    private static final String DD_MONNTHINWORD_YYYY_KEYWORD = "\\b(\\w+|)(-|)\\w+(th|nd|rd|st|)(\\s|\\S)(of |)(january\\b|febuary\\b|march\\b|april\\b|may\\b|june\\b|july\\b|august\\b"
            + "|september\\b|octobor\\b|november\\b|december\\b)(\\s|)((in (the|) (year|yr)(s|))|)(\\s|)(\\d+\\b|)";
    private static final String DD_SHORTFORMMONTHINWORD_YYYY_KEYWORD = "\\b(\\w+|)(-|)\\w+(th|nd|rd|st|)(\\s|\\S)(of |)(jan\\b|feb\\b|mar\\b|apr\\b|may\\b|jun\\b|jul\\b|aug\\b"
            + "|sep\\b|oct\\b|nov\\b|dec\\b)(\\s|\\S)((in (the|) (year|yr)(s|))|)(\\s|)(\\d+\\b|)";
    private static final String AFTER_DAYS_APART_KEYWORD = "\\b((\\w+ day(s|) after)"
            + "\\b)|(\\w+ day(s|) later)\\b";
    private static final String DAYS_APART_VOCAB_KEYWORD = "(\\btmr\\b|\\b(the\\s|)following day(s|)\\b|\\b(after today)"
            + "\\b|\\b(after (tomorrow|tmr)\\b)|(\\bfortnight\\b)\\b)";
    private static final String THIS_WEEKDAY_APART_KEYWORD = "\\b(on|by|due on|due|from|to|at|@|this)( this|)\\s(mon|tues|wed|thurs|fri|sat|sun)(day|nesday|urday|)(s|)\\b";
    private static final String NEXT_WEEKDAY_APART_KEYWORD = "\\b(on|by|due on|due|from|to|at|@|next)( next|) (mon|tues|wed|thurs|fri|sat|sun)(day|nesday|urday|)(s|)\\b";
    private static final String WEEKS_MONTHS_YEARS_APART_KEYWORD = "(\\b(\\w+ (week|wk|month|mth|year|yr)(s|) later\\b)|\\b(\\w+ (week|wk|month|mth|year|yr)(s|) after)\\b)";
    private static final String NUMBERIC_KEYWORD = "(\\b\\d+\\b)";
    private static final String TO_BE_REMOVED_KEYWORD = "\\b(@ |due on |on |at |from |to |by |due |next |this |((in (the|) (year|yr)(s|))))\\b";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private String[] wordOfNumDays = { "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen" };
    private static final int WEEK_UNIT = 7;
    private static final int FORTNIGHT_UNIT = 14;
    private String inputLeft;
    private String inputThatDetectNatty = "";
    private int index;
    private ArrayList<String> storageOfDate = new ArrayList<String>();
    private String feedback;

    public Date1Parser (){
        
    }
    public Date1Parser(String userInput) throws IllegalArgumentException {
        extractDate(userInput, userInput);
    }

    public void extractDate(String userInput, String leftOverInput)  throws IllegalArgumentException{
        userInput = switchAllToLowerCase(userInput);
        leftOverInput = switchAllToLowerCase(leftOverInput);
        inputLeft = leftOverInput;
        goThroughDetectionMethod(userInput);
    }
    
    /**
     * return feedback(exception) to user.
     * @return
     */
    public String getFeedBack() {
        return feedback;
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
     * get the input left
     * 
     * @return the input left after removing all the date detected
     */
    public String getInputLeft() {
        return inputThatDetectNatty + " " + inputLeft;
    }

    /**
     * 
     * @param userInput
     * @return String in the format of dd/mm/yyyy and return the current date if
     *         nothing is detected
     */
    public ArrayList<String> getDateList() {
        return storageOfDate;
    }
    
    /**
     * get the last index of the position of date in user input
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * go through the whole detection of different formats
     * 
     * @param dateFormat
     * @param dates
     * @return all of the dates detected in DD/MM/YYYY
     */
    private void goThroughDetectionMethod(String userInput) throws IllegalArgumentException{

        spotInputWithMMDDBEFOREAFTER();
        spotInputCouldDetectOnlyNatty();
        spotDDMonthInWordYYYY(userInput, DD_MONNTHINWORD_YYYY_KEYWORD);
        spotDDMonthInWordYYYY(userInput, DD_SHORTFORMMONTHINWORD_YYYY_KEYWORD);
        spotDDMMYYYYKeyword(userInput);
        spotAfterDaysApartKeyword(userInput);
        spotDaysApartVocab(userInput);
        spotWeekMonthYearApartKeyword(userInput);
        spotThisWeekdayApartKeyWord(userInput);
        spotNextWeekdayApartKeyWord(userInput);

    }

    /**
     * to prevent the detection in this dateParser as only natty could read the keyword with the right
     * date
     */
    private void spotInputCouldDetectOnlyNatty() {
   
        Pattern detector = Pattern.compile(DETECT_ONLY_BY_NATTY_KEYWORD);
        Matcher containComplicationWord = detector.matcher(inputLeft);
        
        while (containComplicationWord.find()) {
            inputLeft = inputLeft.replaceAll(DETECT_ONLY_BY_NATTY_KEYWORD, "");
            inputThatDetectNatty = inputThatDetectNatty + " " + containComplicationWord.group();
        }
    }

    /**
     * to ensure that the natty could detect the days before or after a date that is dd/mm/yyyy
     * format 
     * @param userInput
     */
    private void spotInputWithMMDDBEFOREAFTER() {
        String dateOfTheTask = "";
       
        Pattern detector = Pattern.compile(BEFORE_AFTER_MMDD_KEYWORD);
        Matcher containComplicationWord = detector.matcher(inputLeft);

        while (containComplicationWord.find()) {
            String word = "";
            dateOfTheTask = spotDDMMYYYYKeyword(inputLeft);
            storageOfDate.remove(dateOfTheTask);
            
            String[] partOfDate = splitTheStringIntoPart(dateOfTheTask);
            
            assert partOfDate.length != 3 : "could not change the date format to dd/mm/yyyy";            
            assert partOfDate[1] != null: "wrong detection of month";
            
            String monthInWord = convertMonthToWord(partOfDate[1]);

            word = containComplicationWord.group();
            word = word.replaceAll(DDMMYYYY_KEYWORD, "");

            inputThatDetectNatty = inputThatDetectNatty + word + " "
                    + partOfDate[0] + " " + monthInWord + " " + partOfDate[2];
            inputLeft = inputLeft.replaceAll(BEFORE_AFTER_MMDD_KEYWORD, "");
            

        }

    }

    /**
     * spot date of next mon-sun could be in short form(mon-sun) or
     * monday-sunday
     * 
     * @param userInput
     * @param storageOfDate
     * @return date in DD/MM/YYYY format
     */
    private void spotNextWeekdayApartKeyWord(String userInput) {
        String dateOfTheTask = "", nextWeekdayInput = "";
        Pattern dateDetector = Pattern.compile(NEXT_WEEKDAY_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            nextWeekdayInput = containDate.group();
            inputLeft = inputLeft.replaceAll(nextWeekdayInput, "");
            nextWeekdayInput = nextWeekdayInput.trim();
            Calendar calendar = Calendar.getInstance();
            int todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            nextWeekdayInput = removeUnwantedParts(nextWeekdayInput);
            int dayOfTheWeek = detectDayOfWeek(nextWeekdayInput);
            dateOfTheTask = getNextWeekayDate(dayOfTheWeek, todayDayOfWeek);
            storageOfDate.add(dateOfTheTask);

            int indexMatched = toGetIndex.start();
            setThePosition(indexMatched);
        }
    }

    /**
     * get the final date after day of weeks is being added
     * 
     * @param dateOfTheTask
     * @param dayOfTheWeek
     * @param todayDayOfWeek
     * @return date in DD/MM/YYYY
     */
    private String getNextWeekayDate(int dayOfTheWeek, int todayDayOfWeek) {
        String dateOfTheTask = "";

        if (todayDayOfWeek == dayOfTheWeek) {
            dateOfTheTask = addToTheCurrentDateByDays(7);
        } else {
            dateOfTheTask = addToTheCurrentDateByDays(7 - todayDayOfWeek
                    + dayOfTheWeek);
        }

        return dateOfTheTask;
    }

    /**
     * detect after no weeks, after no months, after no years s is not sensitive
     * 
     * @param userInput
     */
    private void spotWeekMonthYearApartKeyword(String userInput) {
        String dateOfTheTask = "", uniqueKeyword = "";
        int numberOfDays = 0, numberOfMonths = 0, numberOfYears = 0;
        Pattern dateDetector = Pattern
                .compile(WEEKS_MONTHS_YEARS_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher matchWithIndex = dateDetector.matcher(userInput);

        while (containDate.find() && matchWithIndex.find()) {
            uniqueKeyword = containDate.group();
            inputLeft = inputLeft.replaceAll(uniqueKeyword, "");

            if (uniqueKeyword.contains("week") || uniqueKeyword.contains("wk")) {

                numberOfDays = getNumberOfDaysDetected(uniqueKeyword);
                dateOfTheTask = addToTheCurrentDateByDays(numberOfDays);

            } else if (uniqueKeyword.contains("month") || uniqueKeyword.contains("mth")) {

                numberOfMonths = getNumberOfMonthDetected(uniqueKeyword);
                dateOfTheTask = addToTheCurrentDateByMonth(numberOfMonths);

            } else if (uniqueKeyword.contains("year") || uniqueKeyword.contains("yr")) {

                numberOfYears = getNumberOfYearsDetected(uniqueKeyword);
                dateOfTheTask = addToTheCurrentDateByYear(numberOfYears);
            }

            storageOfDate.add(dateOfTheTask);

            int indexMatch = matchWithIndex.start();
            setThePosition(indexMatch);
        }

    }

    /**
     * spot date of this mon-sun could be in short form(mon-sun) or
     * monday-sunday
     * 
     * @param userInput
     */
    private void spotThisWeekdayApartKeyWord(String userInput) {
        String dateOfTheTask = "", thisWeekdayInput = "";
        Pattern dateDetector = Pattern.compile(THIS_WEEKDAY_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            thisWeekdayInput = containDate.group();
            inputLeft = inputLeft.replaceAll(thisWeekdayInput, "");
            thisWeekdayInput = thisWeekdayInput.trim();
            Calendar calendar = Calendar.getInstance();
            int todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            thisWeekdayInput = removeUnwantedParts(thisWeekdayInput);
           
            int dayOfTheWeek = detectDayOfWeek(thisWeekdayInput);
            dateOfTheTask = getThisWeekayDate(dayOfTheWeek, todayDayOfWeek);
            storageOfDate.add(dateOfTheTask);

            int indexMatched = toGetIndex.start();
            setThePosition(indexMatched);
        }
    }

    /**
     * get this week date
     * 
     * @param dateOfTheTask
     * @param dayOfTheWeek
     * @param todayDayOfWeek
     * @return DD/MM/YYYY
     */
    private String getThisWeekayDate(int dayOfTheWeek, int todayDayOfWeek)
            throws IllegalArgumentException {
        String dateOfTheTask = "";

        try {
            if (todayDayOfWeek <= dayOfTheWeek) {
                dateOfTheTask = addToTheCurrentDateByDays(dayOfTheWeek
                        - todayDayOfWeek);
            } else {
                throw new IllegalArgumentException(
                        "This weekday entered has pass!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        return dateOfTheTask;
    }

    /**
     * detect tomorrow, the following day, after tomorrow, after today
     * 
     * @param userInput
     */
    private void spotDaysApartVocab(String userInput) {
        String dateOfTheTask = "";
        Pattern dateDetector = Pattern.compile(DAYS_APART_VOCAB_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            String daysApartVocab = containDate.group();
            inputLeft = inputLeft.replaceAll(DAYS_APART_VOCAB_KEYWORD, "");

            int numberOfDays = getNumberOfDaysDetected(daysApartVocab);
            dateOfTheTask = addToTheCurrentDateByDays(numberOfDays);
            storageOfDate.add(dateOfTheTask);

            int indexMatch = toGetIndex.start();
            setThePosition(indexMatch);
        }

    }

    /**
     * set the start and end date in the right position in arrayList
     * 
     * @param indexMatch
     */
    private void setThePosition(int indexMatch) {
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
    private String addToTheCurrentDateByDays(int numberOfDay) {
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
    private int determineIntDaysFromWords(String numberOfDaysFromNow) {
        int numberOfDays = 1;
        for (int i = 0; i < wordOfNumDays.length; i++) {
            if (numberOfDaysFromNow.equals(wordOfNumDays[i])) {
                break;
            }
            numberOfDays++;
        }

        // 20 means nothing from array(wordOfNumDays) is equal, no number of day
        // detect
        if (numberOfDays == 20) {
            numberOfDays = 0;
        }
        return numberOfDays;
    }

    /**
     * check if the text is in digit or word form
     * 
     * @param text
     *            : represent the text that could be in numbering or word form
     * @return true if in digit otherwise false
     */
    private boolean isNumeric(String text) {
        return text.matches(NUMBERIC_KEYWORD);
    }

    /**
     * detect date: after (no. in word or int) days, next (no in word or int)
     * day is okay to have s behind or no s behind day
     * 
     * @param userInput
     */
    private void spotAfterDaysApartKeyword(String userInput) {
        String uniqueKeyword = "", dateOfTask = "";

        Pattern dateDetector = Pattern.compile(AFTER_DAYS_APART_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            uniqueKeyword = containDate.group();
            inputLeft = inputLeft.replaceAll(AFTER_DAYS_APART_KEYWORD, "");

            int numberOfDays = getNumberOfDaysDetected(uniqueKeyword);
            dateOfTask = addToTheCurrentDateByDays(numberOfDays);
            storageOfDate.add(dateOfTask);

            int indexMatch = toGetIndex.start();
            setThePosition(indexMatch);
        }
    }

    /**
     * detect day of week from mon-sun
     * 
     * @param userInput
     * @return day of week
     */
    private int detectDayOfWeek(String userInput) {
        int dayOfWeek = 0;

        if (userInput.contains("mon")) {
            dayOfWeek = 1;
        } else if (userInput.contains("tues")) {
            dayOfWeek = 2;
        } else if (userInput.contains("wed")) {
            dayOfWeek = 3;
        } else if (userInput.contains("thurs")) {
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
    private int getNumberOfDaysDetected(String uniqueKeyword) {
        int numberOfDays = 0;

        if (uniqueKeyword.equals("tmr")) {
            numberOfDays = 1;
        } else if (uniqueKeyword.contains("following day")) {
            numberOfDays = 1;
        } else if (uniqueKeyword.equals("after today")) {
            numberOfDays = 1;
        } else if (uniqueKeyword.equals("after tomorrow")
                || uniqueKeyword.equals("after tmr")) {
            numberOfDays = 2;
        } else if (uniqueKeyword.contains("week") || uniqueKeyword.contains("wk")) {
            int numberOfWeek = isolateTheNumberInString(uniqueKeyword);
            numberOfDays = WEEK_UNIT * numberOfWeek;
        } else if (uniqueKeyword.contains("fortnight")) {
            int numberOfFornight = isolateTheNumberInString(uniqueKeyword);
            numberOfDays = FORTNIGHT_UNIT * numberOfFornight;
        } else {
            numberOfDays = isolateTheNumberInString(uniqueKeyword);
        }

        return numberOfDays;
    }

    /**
     * get number of month detected
     * 
     * @param uniqueKeyword
     * @return date in DD/MM/YYYY
     */
    private int getNumberOfMonthDetected(String uniqueKeyword) {
        int numberOfMonth = 0;

        if (uniqueKeyword.contains("month") || uniqueKeyword.contains("mth")) {
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
    private int getNumberOfYearsDetected(String uniqueKeyword) {
        int numberOfYear = 0;

        if (uniqueKeyword.contains("year") || uniqueKeyword.contains("yr")) {
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
    private String addToTheCurrentDateByYear(int numberOfYear) {
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
    private String addToTheCurrentDateByMonth(int numberOfMonth) {
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
    private int isolateTheNumberInString(String uniqueKeyword) {
        uniqueKeyword = remove1stWordFromInput(uniqueKeyword);
        String[] partsOfUniqueKeyword = uniqueKeyword.split(" ");
        String containOnlyNumber = partsOfUniqueKeyword[0];
        int number = determineTheNumber(containOnlyNumber);
        return number;
    }

    /**
     * remove the 1st word and space in input so that every 1st String in the
     * input is digit
     * 
     * @param uniqueKeyword
     * @return String without "in"
     */
    private String remove1stWordFromInput(String uniqueKeyword) {
        uniqueKeyword = uniqueKeyword.replaceAll(
                "\\bin\\b|\\bafter\\b|\\bnext\\b", "");
        uniqueKeyword = uniqueKeyword.trim();
        return uniqueKeyword;
    }

    /**
     * determine the number of day.
     * 
     * @param containNumber
     * @return number of day in integer
     */
    private int determineTheNumber(String containNumber) {
        int numberOfDays;
        if (isNumeric(containNumber)) {
            numberOfDays = Integer.parseInt(containNumber);
        } else {
            numberOfDays = determineIntDaysFromWords(containNumber);
        }
        return numberOfDays;
    }

    /**
     * detect DD Month in word/DD Month in word YYYY with space in between or no
     * space in between DD could be in 2 or 2nd or 2nd of , 2 or 3th or 3th of,
     * 4 or 4th or 4th of etc
     * 
     * @param userInput
     */
    private void spotDDMonthInWordYYYY(String userInput, String keyword) throws 
    IllegalArgumentException{
        String dateOfTheTask = "";

        Pattern dateDetector = Pattern.compile(keyword);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            dateOfTheTask = containDate.group();

            DateFormat date = new SimpleDateFormat(DATE_FORMAT);
            Calendar calendar = Calendar.getInstance();
           
            dateOfTheTask = removeUnwantedParts(dateOfTheTask);
            int day = getDay(dateOfTheTask);
            int year = getYear(dateOfTheTask);
            int month = convertMonthToNumber(dateOfTheTask);

            if(day == 0){         
                break;
            }
            
            testValidMonth(month);
            testValidDay(day, year, month);

            setDateIntoCalendar(day, month - 1, year, calendar);

            dateOfTheTask = date.format(calendar.getTime());

            storageOfDate.add(dateOfTheTask);
            inputLeft = inputLeft.replaceAll(keyword, "");

            int indexOfDatePosition = toGetIndex.start();
            setThePosition(indexOfDatePosition);

        }
    }

    /**
     * set day, month, year into the calendar after detected
     * 
     * @param dateOfTheTask
     * @param calendar
     */
    private void setDateIntoCalendar(int day, int month, int year,
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
    private int getDay(String dateOfTheTask) {
        int day = 0;
        Pattern numberPattern = Pattern
                .compile("\\d+|\\w+");
        Matcher containDay = numberPattern.matcher(dateOfTheTask);
        String text;
        
        if (containDay.find()) {
            text = containDay.group();

            if (isNumeric(text)) {
                day = Integer.parseInt(text);
            } else if (text.equals("twenty")) {
                text = dateOfTheTask.replaceAll(text, "");
                day = 20 + getNextWord(text);
            } else if (text.equals("thirty")) {
                text = dateOfTheTask.replaceAll(text, "");
                day = 30 + getNextWord(text);
            } else {
                day = determineIntDaysFromWords(text);
            }
        }
        return day;
    }

    private int getNextWord(String text) {
        int day = 0;
        Pattern numberPattern = Pattern.compile("\\w+");
        Matcher containDay = numberPattern.matcher(text);

        if (containDay.find()) {
            text = containDay.group();

            day = determineIntDaysFromWords(text);
        }
        return day;
    }

    /**
     * get the month of the date It follow singapore date format
     * 
     * @param dateOfTheTask
     * @return month
     */
    private int getMonth(String dateOfTheTask) {
        String[] ddmmyyyy = splitTheStringIntoPart(dateOfTheTask);
        int month = Integer.parseInt(ddmmyyyy[1]);
        return month;
    }

    /**
     * get the year from the date it follows singapore date format
     * 
     * @param dateOfTheTask
     * @return year
     */
    private int getYear(String dateOfTheTask) {
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
    private int convertMonthToNumber(String dateOfTheTask) {
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

        return month;
    }

    /**
     * detct DD/MM and DD/MM/YYYY
     * 
     * @param userInput
     * @return date dd/mm/yyyy
     * @throws IllegalArgumentException
     */
    private String spotDDMMYYYYKeyword(String userInput) throws IllegalArgumentException{
        String dateOfTheTask = "";

        Pattern dateDetector = Pattern.compile(DDMMYYYY_KEYWORD);
        Matcher containDate = dateDetector.matcher(inputLeft);
        Matcher toGetIndex = dateDetector.matcher(userInput);

        while (containDate.find() && toGetIndex.find()) {
            Calendar calendar = Calendar.getInstance();
            inputLeft = inputLeft.replaceAll(DDMMYYYY_KEYWORD, "");
            
            dateOfTheTask = containDate.group();

            dateOfTheTask = removeUnwantedParts(dateOfTheTask);

            // so we could detect both (YYYY/MM/DD) and (DD/MM/YYYY)
            String containYearAndDay = getYearAndDayInAString(dateOfTheTask);

            int day = getDay(containYearAndDay);
            int year = getYear(containYearAndDay);
            int month = getMonth(dateOfTheTask);

            testValidMonth(month);
            testValidDay(day, year, month);

            setDateIntoCalendar(day, month - 1, year, calendar);

            DateFormat date = new SimpleDateFormat(DATE_FORMAT);
            dateOfTheTask = date.format(calendar.getTime());

            storageOfDate.add(dateOfTheTask);

            int indexPositionOfThisDate = toGetIndex.start();
            setThePosition(indexPositionOfThisDate);

        }
        return dateOfTheTask;

    }

    /**
     * convert month from integer to word
     * 
     * @param string
     * @return
     */
    private String convertMonthToWord(String monthInWord) {
        String month = "";

        if (monthInWord.equals("01")) {
            month = "jan";
        } else if (monthInWord.equals("02")) {
            month = "feb";
        } else if (monthInWord.equals("03")) {
            month = "mar";
        } else if (monthInWord.equals("04")) {
            month = "apr";
        } else if (monthInWord.equals("05")) {
            month = "may";
        } else if (monthInWord.equals("06")) {
            month = "jun";
        } else if (monthInWord.equals("07")) {
            month = "jul";
        } else if (monthInWord.equals("08")) {
            month = "aug";
        } else if (monthInWord.equals("09")) {
            month = "september";
        } else if (monthInWord.equals("10")) {
            month = "oct";
        } else if (monthInWord.equals("11")) {
            month = "nov";
        } else if (monthInWord.equals("12")) {
            month = "dec";
        }

        return month;
    }

    /**
     * get year and day in a string so we could detect both (YYYY/MM/DD) and
     * (DD/MM/YYYY) date format
     * 
     * @param ddMMYYYY
     * @return a string of year and day
     */
    private String getYearAndDayInAString(String dateOfTheTask) {
        String containYearAndDay;
        String[] ddMMYYYY = splitTheStringIntoPart(dateOfTheTask);
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
    private void testValidMonth(int month) throws IllegalArgumentException {
        try {
            if (month <= 0 || month > 12) {
                throw new IllegalArgumentException("Invalid Month Keyed!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            feedback = e.getMessage();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * check if the date is valid if the year and the month have this day For
     * example feb only have max 29 days
     * 
     * @param day
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

            if (day == 0 || exceedMaxDaysOnThatMonth(day, maxDays)) {
                throw new IllegalArgumentException(
                        "Invalid Day Keyed! Exceed the maximum day in that month");
            }
           
        } catch (IllegalArgumentException e) {
            feedback = e.getMessage();
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * check if the day detected is more than the max day in that month
     * 
     * @param day
     * @param maxDays
     * @return true if it exceed, false if it does not
     */
    private boolean exceedMaxDaysOnThatMonth(int day, int maxDays) {
        return maxDays < day;
    }

    /**
     * split the date DD/MM/YYYY to day, month and year in the array of String
     * 
     * @param dateOfTheTask
     * @return day, month and year in string array.
     */
    private String[] splitTheStringIntoPart(String dateOfTheTask) {
        String[] ddmmyyyy = null;
        if (dateOfTheTask.contains("/")) {
            ddmmyyyy = dateOfTheTask.split("(/)");
        } else if (dateOfTheTask.contains(".")) {
            ddmmyyyy = dateOfTheTask.split("(\\.)");
        } else if (dateOfTheTask.contains("-")) {
            ddmmyyyy = dateOfTheTask.split("(\\-)");
        }

        return ddmmyyyy;
    }

    /**
     * remove the conjunction
     * 
     * @param timeWithUnwantedPart
     * @return time free from conjunction
     */
    public String removeUnwantedParts(String timeWithUnwantedPart) {
        String time;
        time = timeWithUnwantedPart.replaceAll(TO_BE_REMOVED_KEYWORD, "");
        return time;
    }
}

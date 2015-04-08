package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TimeParser {
    private static final String TIME_TO_TIME_KEYWORD = "[^/:,.]\\b(((\\d+[.:,](\\d+)|\\d+)(-| to | - )(\\d+[.:,](\\d+)|\\d+)(\\s|)(am|pm|)))\\b";
    private static final String HOURS_APART_KEYWORD = "\\b(start at \\b(on |at |from |to |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm|))\\b for \\d+ (hour|hours|hr|hrs))\\b";
    private static final String TWELVE_HOUR_KEYWORD = "\\b(on |at |from |to |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
         //   + "|\\b(@ |due on |on |at |from |to |by |due )\\d{2}\\b"; --> error
    private static final String TIME_WITH_OCLOCK_KEYWORD = "\\b(\\d+[:.,]\\d+|\\d+)(\\s|)o('|’)clock\\b";
    private static final String NOON_MIDNIGHT_KEYWORD = "(\\b(on |at |from |to |)noon\\b)|(\\b(on |at |from |to |)midnight\\b)";
    private static final String BEFORE_NOON_BEFORE_MIDNIGHT_KEYWORD = "(\\b(from |to |)(before midnight|before noon)\\b)";
    private static final String MORNING_AFTERNOON_NIGHT_KEYWORD = "(\\b(from |to |)(\\d+[.:,](\\d+)|\\d+)(\\s|)(o'clock|am|pm|)( in (the |)(morning|morn)\\b| in (the |)afternoon\\b| in (the |)night\\b| at (the |)night\\b| at (the |)afternoon\\b"
            + "| at (the |)morning\\b| at (the |)morn\\b))";
    private static final String TWENTY_FOUR_HH_KEYWORD = "(\\b\\d{1,2}[:.,]\\d{2}\\b)"
            + "|[^./]\\b\\d{3,4}(\\s|\\S)(hour(s|)|hr(s|)|)\\b";
    private static final String PAST_NOON_PAST_MIDNIGHT_KEYWORD = "(\\b(from |to |)(past midnight|past noon|after noon|after midnight)\\b)";
    private static final String TO_BE_REMOVED_KEYWORD = "(before midnight|before noon|"
            + "in afternoon|in night|in (morning|morn)|at afternoon|at night|at (morning|morn)|in the afternoon|in the night|in the (morning|morn)|at the afternoon|at the night|at the (morning|morn)|o'clock|past noon|past"
            + "midnight|noon|midnight|\\s|-|to|at|from|hours|hour|hrs|hr|(@ |due on |on |at |from |to |by |due |o’clock))";
    private static int index;
    private static String userInputLeft;

    public static ArrayList<String> extractTime(String userInput) {
        ArrayList<String> storageOfTime = new ArrayList<String>();
        userInput = removeThoseHashTag(userInput);
        userInput = switchAllToLowerCase(userInput);
        userInputLeft = userInput;

        storageOfTime = goThroughTimeFormat(storageOfTime, userInput);

        return storageOfTime;
    }

    /**
     * indication of ~~ means that user want it to be in description ~~ means
     * escaped char
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
     * go through the list of time format
     * 
     * @param timeFormat
     * @param storageOfTime
     * @param userInput
     * @return
     */
    private static ArrayList<String> goThroughTimeFormat(
            ArrayList<String> storageOfTime, String userInput) {

        storageOfTime = spotTimeToTimeKeyword(storageOfTime, userInput);
        storageOfTime = spotHourApartKeyword(storageOfTime);
        storageOfTime = spotBeforeMidnightOrNoonKeyword(storageOfTime,
                userInput);
        storageOfTime = spotMorningAfternoonNightKeyword(storageOfTime,
                userInput);
        storageOfTime = spotPastMidnightOrNoonKeyword(storageOfTime, userInput);
        storageOfTime = spotNoonOrMidnight(storageOfTime, userInput);
        storageOfTime = spotHHOclockKeyword(storageOfTime, userInput);
        storageOfTime = spotTwelveHourFormat(storageOfTime, userInput);
        storageOfTime = spotTwentyFourHHKeyword(storageOfTime, userInput);

        return storageOfTime;
    }

    /**
     * spot time with past midnight, past noon,
     * 
     * @param storageOfTime
     * @param userInput
     * @return HH:MM
     */
    private static ArrayList<String> spotPastMidnightOrNoonKeyword(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern.compile(PAST_NOON_PAST_MIDNIGHT_KEYWORD);
        Matcher matchedWithTime = timeDetector.matcher(userInputLeft);
        Matcher matchedForIndex = timeDetector.matcher(userInput);

        while (matchedWithTime.find()) {
            userInputLeft = userInputLeft.replaceAll(
                    PAST_NOON_PAST_MIDNIGHT_KEYWORD, "");
            String time = matchedWithTime.group();

            if (matchedForIndex.find()) {
                int indexNext = matchedForIndex.start();
                if (time.contains("noon")) {
                    storageOfTime.add("12:01");
                } else if (time.contains("midnight")) {
                    storageOfTime.add("00:01");
                }
                index = indexNext;
                setThePositionForTime(storageOfTime, indexNext);
            }
        }
        return storageOfTime;
    }

    /**
     * get 24 hour format time (HH:MM)
     * 
     * @param storageOfTime
     * @param userInput
     * @return HH:MM time
     */
    private static ArrayList<String> spotTwentyFourHHKeyword(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern.compile(TWENTY_FOUR_HH_KEYWORD);
        Matcher containTime = timeDetector.matcher(userInputLeft);
        Matcher toGetIndex = timeDetector.matcher(userInput);

        while (containTime.find()) {
            String time = containTime.group();
            testValidTime(time);
            userInputLeft = userInputLeft.replaceAll(time, "");

            if (toGetIndex.find()) {
                int indexNext = toGetIndex.start();
                time = removeUnwantedParts(time);
                time = changeToHourFormat(time);

                assert checkValid24HourTime(time) == true : "Wrong convention of time";

                storageOfTime.add(time);
                setThePositionForTime(storageOfTime, indexNext);
            }
        }
        return storageOfTime;
    }

    /**
     * detect time + o'clock and have a default morning
     * 
     * @param storageOfTime
     * @param userInput
     * @return time in hour format
     */
    private static ArrayList<String> spotHHOclockKeyword(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern.compile(TIME_WITH_OCLOCK_KEYWORD);
        Matcher containTime = timeDetector.matcher(userInputLeft);
        Matcher toGetIndex = timeDetector.matcher(userInput);

        while (containTime.find()) {
            String time = containTime.group();

            testValidTime(time);
            userInputLeft = userInputLeft.replaceAll(time, "");

            if (toGetIndex.find()) {
                int indexNext = toGetIndex.start();
                time = removeUnwantedParts(time);
                time = changeToHourFormat(time);

                assert checkValid24HourTime(time) == true : "Wrong convention of time";

                storageOfTime.add(time);
                setThePositionForTime(storageOfTime, indexNext);
            }
        }
        return storageOfTime;
    }

    /**
     * to prevent case sensitive, switch all to lower case
     * 
     * @param userInput
     * @return the user input all in lower case.
     */
    private static String switchAllToLowerCase(String userInput) {
        userInput = userInput.replaceAll("\\s+", " ");
        userInput = " " + userInput.toLowerCase() + " ";
        return userInput;
    }

    /**
     * detect the start at __ am/pm for __ hour
     * 
     * @param storageOfTime
     * @return arraylist of time.
     */
    private static ArrayList<String> spotHourApartKeyword(
            ArrayList<String> storageOfTime) {

        Pattern containTime = Pattern.compile(HOURS_APART_KEYWORD);
        Matcher toGetIndex = containTime.matcher(userInputLeft);

        while (toGetIndex.find()) {
            String time = toGetIndex.group();
            userInputLeft = userInputLeft.replaceAll(time, "");

            String startTime = detectStartTime(time);
            testValidTime(startTime);
            startTime = changeToHourFormat(startTime);
            int hhInTime = getHH(startTime);

            String numberOfHour = detectNumberOfHour(time);
            int numberOfHours = Integer.parseInt(numberOfHour);

            hhInTime = addTimeWithHours(numberOfHours, hhInTime);
            String hhTimeInString = toString(hhInTime);
            String minTime = getMinutes(startTime);

            time = hhTimeInString + ":" + minTime;

            assert checkValid24HourTime(time) == true : "Wrong convention of time";

            storageOfTime.add(startTime);
            storageOfTime.add(time);
        }
        return storageOfTime;
    }

    /**
     * convert the integer of HH to string
     * 
     * @param hhInTime
     * @return HH in string
     */
    private static String toString(int hhInTime) {
        String hhTimeInString;
        if (hhInTime < 10) {
            hhTimeInString = "0" + hhInTime;
        } else {
            hhTimeInString = "" + hhInTime;
        }
        return hhTimeInString;
    }

    /**
     * add time with the number of hour
     * 
     * @param numberOfHours
     * @param hhInTime
     * @return HH in time
     */
    private static int addTimeWithHours(int numberOfHours, int hhInTime) {

        hhInTime = hhInTime + numberOfHours;

        if (hhInTime > 23) {
            hhInTime = hhInTime - 24;
        }
        return hhInTime;
    }

    /**
     * detect time with pm/am
     * 
     * @param time
     * @return time with am/pm
     */
    private static String detectStartTime(String time) {
        String digit1 = "(\\d+[.:,](\\d+)|\\d+)((\\s|)(am|pm|))";

        Pattern containTime = Pattern.compile(digit1);
        Matcher matchedWithTime = containTime.matcher(time);

        if (matchedWithTime.find()) {
            time = matchedWithTime.group();
        }
        return time;
    }

    /**
     * detect digit represent the number of hours
     * 
     * @param time
     * @return number of hour
     */
    private static String detectNumberOfHour(String time) {
        String digit = "(\\d+)";
        String numberOfHour = "";
        Pattern timeDetector1 = Pattern.compile(digit);
        Matcher matchedWithTime1 = timeDetector1.matcher(time);

        while (matchedWithTime1.find()) {
            numberOfHour = matchedWithTime1.group();
        }

        return numberOfHour;
    }

    /**
     * detect the time like 6 in morning or 6 in afternoon or 6 in night
     * 
     * @param storageOfTime
     * @return time in HH:MM format
     */
    private static ArrayList<String> spotMorningAfternoonNightKeyword(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern.compile(MORNING_AFTERNOON_NIGHT_KEYWORD);
        Matcher matchedWithTime = timeDetector.matcher(userInputLeft);
        Matcher matchedForIndex = timeDetector.matcher(userInput);

        while (matchedWithTime.find()) {
            String time = matchedWithTime.group();
            userInputLeft = userInputLeft.replaceAll(time, "");
            if (matchedForIndex.find()) {
                int indexNext = matchedForIndex.start();

                if (time.contains("morning") || time.contains("morn")) {
                    time = removeUnwantedParts(time);
                    testValidTime(time);
                    time = changeToHourFormat(time + "am");
                } else if (time.contains("afternoon") || time.contains("night")) {
                    time = removeUnwantedParts(time);
                    testValidTime(time);
                    time = changeToHourFormat(time + "pm");
                }

                storageOfTime.add(time);
                assert checkValid24HourTime(time) == true : "Wrong convertion of time";
                setThePositionForTime(storageOfTime, indexNext);
            }
        }
        return storageOfTime;
    }

    /**
     * detect start time and end time. able to detect HH:MM or 12hour format
     * with pm or am or none to or - HH:MM or 12hour format pm or am example: 1)
     * 12:30 - 1pm 2) 12pm to 1:30pm
     * 
     * @param storageOfTime
     * @return arrayList containing all of the time.
     */

    private static ArrayList<String> spotTimeToTimeKeyword(
            ArrayList<String> storageOfTime, String userInput) {

        Pattern timeDetector = Pattern.compile(TIME_TO_TIME_KEYWORD);
        Matcher matchedWithTime = timeDetector.matcher(userInputLeft);

        String[] timeList;
        String toBeAdded = "";

        while (matchedWithTime.find()) {
            String time = matchedWithTime.group();
            userInputLeft = userInputLeft.replaceAll(time, "");
            timeList = time.split("-|to");

            testValidTime(timeList[1]);
            timeList[1] = changeToHourFormat(timeList[1]);
            int hhTime = getHH(timeList[1]);

            String timeWitham = changeToHourFormat(timeList[0] + "am");
            String timeWithpm = changeToHourFormat(timeList[0] + "pm");

            int hhTimeWitham = getHH(timeWitham);
            int hhTimeWithpm = getHH(timeWithpm);

            toBeAdded = getTheStartTime(toBeAdded, timeWitham, timeWithpm,
                    hhTimeWitham, hhTimeWithpm, hhTime);
            toBeAdded = changeToHourFormat(toBeAdded);

            assert checkValid24HourTime(timeList[1]) == true : "Wrong convertion of time";
            assert checkValid24HourTime(toBeAdded) == true : "Wromg convertion of time";

            storageOfTime.add(toBeAdded);
            storageOfTime.add(timeList[1]);
        }
        return storageOfTime;
    }

    /**
     * detect the start time when pm/am is not added right next to start time.
     * by see which one nearer to the end Time after taking it as pm or am.
     * 
     * @param toBeAdded
     * @param timeWitham
     * @param timeWithpm
     * @param hhInTimeWitham
     * @param hhInTimeWithpm
     * @param hhOfEndTime
     * @return the right timing when switch to hour format when am or pm is not
     *         on the back of the time.
     */
    private static String getTheStartTime(String toBeAdded, String timeWitham,
            String timeWithpm, int hhInTimeWitham, int hhInTimeWithpm,
            int hhOfEndTime) {
        if (hhInTimeWitham < hhOfEndTime && hhInTimeWithpm < hhOfEndTime) {
            toBeAdded = getTheNearestTime(timeWitham, timeWithpm,
                    hhInTimeWitham, hhInTimeWithpm);
        } else if (hhInTimeWitham < hhOfEndTime) {
            toBeAdded = timeWitham;
        } else if (hhInTimeWithpm < hhOfEndTime) {
            toBeAdded = timeWithpm;
        } else if (hhInTimeWitham == hhInTimeWithpm) {
            toBeAdded = timeWithpm;
        }
        return toBeAdded;
    }

    /**
     * when both start time hour format is less than the end time. Find out
     * which start time is nearer to the end time
     * 
     * @param timeWitham
     * @param timeWithpm
     * @param amTime1stNum
     * @param hhInTimeWithpm
     * @return the nearer start time when the am/pm is not input.
     */
    private static String getTheNearestTime(String timeWitham,
            String timeWithpm, int hhInTimeWitham, int hhInTimeWithpm) {
        String toBeAdded;
        if (hhInTimeWitham < hhInTimeWithpm) {
            toBeAdded = timeWithpm;
        } else {
            toBeAdded = timeWitham;
        }
        return toBeAdded;
    }

    /**
     * get the HH of the time in hour format(HH:MM)
     * 
     * @param time
     * @return HH
     */
    private static int getHH(String time) {
        int index = getIndex(time);
        int hhInTime = Integer.parseInt(time.substring(0, index));
        return hhInTime;
    }

    /**
     * get the MM of the time in hour format(HH:MM)
     * 
     * @param pmTime1
     * @return HH
     */
    private static String getMinutes(String pmTime1) {
        int index = getIndex(pmTime1);
        String partOfString2 = pmTime1.substring(index + 1);
        return partOfString2;
    }

    /**
     * throw and catch exception for invalid time for both 24 hour and 12 hour
     * format
     * 
     * @param time
     */
    private static void testValidTime(String time)
            throws IllegalArgumentException {
        int timeInHour, timeInMin;
        boolean ifTwelveHour;

        try {
            time = removeUnwantedParts(time);
            ifTwelveHour = checkTwelveOrTwentyFourFormat(time);
            time = removePMOrAmOrOclock(time);

            if (time.contains(":") || time.contains(".") || time.contains(",")) {
                timeInHour = getHH(time);
                timeInMin = Integer.parseInt(getMinutes(time));
            } else if (time.length() <= 2) {
                timeInHour = Integer.parseInt(time);
                timeInMin = 0;
            } else if (time.length() == 3) {
                timeInHour = Integer.parseInt(time.substring(0, 1));
                timeInMin = Integer.parseInt(time.substring(1));
            } else if (time.length() == 4) {
                timeInHour = Integer.parseInt(time.substring(0, 2));
                timeInMin = Integer.parseInt(time.substring(2));
            } else {
                timeInHour = 0;
                timeInMin = 0;
            }

            getExceptionForInvalidTime(timeInHour, timeInMin, ifTwelveHour);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * get the exception for either 24 hour and 12 hour format
     * 
     * @param timeInHour
     * @param timeInMin
     * @param ifTwelveHour
     * @throws IllegalArgumentException
     */
    private static void getExceptionForInvalidTime(int timeInHour,
            int timeInMin, boolean ifTwelveHour)
            throws IllegalArgumentException {
        if (ifTwelveHour) {
            test12HourFormat(timeInHour, timeInMin);
        } else {
            test24HourFormat(timeInHour, timeInMin);
        }
    }

    /**
     * test 12 hour format of 0 <= hour <= 12 and 0 <= minutes <= 60
     * 
     * @param timeInHour
     * @param timeInMin
     * @throws IllegalArgumentException
     */
    private static void test12HourFormat(int timeInHour, int timeInMin)
            throws IllegalArgumentException {
        if (timeInHour > 12 || timeInHour < 0 || timeInMin < 0
                || timeInMin > 59) {
            throw new IllegalArgumentException("Time entered is invalid!");
        }
    }

    /**
     * check if it is 24 hour format or 12 hour format
     * 
     * @param time
     * @return 12 hour format return true and 24 hour format return false
     */
    private static boolean checkTwelveOrTwentyFourFormat(String time) {
        boolean ifTwelveHour;

        if (time.contains("am") || time.contains("o'clock")
                || time.contains("pm")) {
            ifTwelveHour = true;
        } else {
            ifTwelveHour = false;
        }

        return ifTwelveHour;
    }

    /**
     * test 24 hour format of 0 <= hour <= 23 and 0 <= minutes <= 60
     * 
     * @param timeInHour
     * @param timeInMin
     * @throws IllegalArgumentException
     */
    private static void test24HourFormat(int timeInHour, int timeInMin)
            throws IllegalArgumentException {
        if (timeInHour > 23 || timeInHour < 0 || timeInMin < 0
                || timeInMin > 59) {
            throw new IllegalArgumentException("Time entered is invalid!");
        }
    }

    /**
     * This is use to test whether my algo manipulate change to 24 hours time
     * format correctly without exceeding the boundary
     * 
     * @param time
     * @return true if 24 hour format does not exceed boundary, false when 24
     *         hours do exceed boundary
     */
    private static boolean checkValid24HourTime(String time) {
        int timeInHour, timeInMin;
        boolean validTime = true;

        time = removePMOrAmOrOclock(time);
        time = removeUnwantedParts(time);
        if (time.length() > 2) {
            timeInHour = getHH(time);
            timeInMin = Integer.parseInt(getMinutes(time));
        } else {
            timeInHour = getHH(time);
            timeInMin = 0;
        }
        if (timeInHour > 23 || timeInHour < 0 || timeInMin < 0
                || timeInMin > 59) {
            validTime = false;
        }
        return validTime;
    }

    /**
     * remove the pm and am from the time
     * 
     * @param time
     * @return time without am or pm
     */
    private static String removePMOrAmOrOclock(String time) {
        time = time.replaceAll("\\s+|pm|am|o'clock", "");
        return time;
    }

    /**
     * detect HH:MM/HH with pm and am behind.
     * 
     * @param storageOfTime
     * @return storage of time containing the time detected.
     */
    private static ArrayList<String> spotTwelveHourFormat(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern.compile(TWELVE_HOUR_KEYWORD);
        Matcher matchedWithTime = timeDetector.matcher(userInputLeft);
        Matcher matchedForIndex = timeDetector.matcher(userInput);

        while (matchedWithTime.find()) {
            String time = matchedWithTime.group();
            testValidTime(time);

            userInputLeft = userInputLeft.replaceAll(time, "");

            if (matchedForIndex.find()) {
                int indexNext = matchedForIndex.start();

                time = removeUnwantedParts(time);
                time = changeToHourFormat(time);

                assert checkValid24HourTime(time) == true : "Wrong convertion of time";

                storageOfTime.add(time);
                setThePositionForTime(storageOfTime, indexNext);
            }
        }

        return storageOfTime;

    }

    private static void setThePositionForTime(ArrayList<String> storageOfTime,
            int indexNext) {
        if (storageOfTime.size() == 2 && indexNext < index) {
            String temp = storageOfTime.get(0);
            storageOfTime.set(0, storageOfTime.get(1));
            storageOfTime.set(1, temp);
        }
        index = indexNext;
    }

    /**
     * detect noon, midnight
     * 
     * @param storageOfTime
     * @return storage of time containing the time detected.
     */
    private static ArrayList<String> spotNoonOrMidnight(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern.compile(NOON_MIDNIGHT_KEYWORD);
        Matcher matchedWithTime = timeDetector.matcher(userInputLeft);
        Matcher matchedForIndex = timeDetector.matcher(userInput);

        while (matchedWithTime.find()) {

            userInputLeft = userInputLeft.replaceAll(NOON_MIDNIGHT_KEYWORD, "");
            String time = matchedWithTime.group();

            if (matchedForIndex.find()) {
                int indexNext = matchedForIndex.start();
                if (time.contains("noon")) {
                    storageOfTime.add("12:00");
                } else if (time.contains("midnight")) {
                    storageOfTime.add("00:00");
                }

                setThePositionForTime(storageOfTime, indexNext);
            }

        }
        return storageOfTime;
    }

    /**
     * detect before noon and before midnight
     * 
     * @param storageOfTime
     * @return the time in hour format.
     */
    private static ArrayList<String> spotBeforeMidnightOrNoonKeyword(
            ArrayList<String> storageOfTime, String userInput) {
        Pattern timeDetector = Pattern
                .compile(BEFORE_NOON_BEFORE_MIDNIGHT_KEYWORD);
        Matcher matchedWithTime = timeDetector.matcher(userInputLeft);
        Matcher matchedForIndex = timeDetector.matcher(userInput);

        while (matchedWithTime.find()) {
            userInputLeft = userInputLeft.replaceAll(
                    BEFORE_NOON_BEFORE_MIDNIGHT_KEYWORD, "");
            String time = matchedWithTime.group();

            if (matchedForIndex.find()) {
                int indexNext = matchedForIndex.start();
                if (time.contains("noon")) {
                    storageOfTime.add("11:59");
                } else if (time.contains("midnight")) {
                    storageOfTime.add("23:59");
                }
                index = indexNext;
                setThePositionForTime(storageOfTime, indexNext);
            }
        }
        return storageOfTime;
    }

    /**
     * change all of the time inputed to hour format(HH:MM)
     * 
     * @param time
     * @return time in hour format (HH:MM)
     */
    private static String changeToHourFormat(String time) {
        if (time.contains("am")) {
            time = removePMOrAmOrOclock(time);
            time = switchToAmHour(time);
        } else if (time.contains("pm")) {
            time = removePMOrAmOrOclock(time);
            time = switchToPMHour(time);
        } else if (time.contains(".") || time.contains(",")) {
            time = changePuncToSemicolon(time);
        } else if (!time.contains(":") && time.length() <= 2) {
            time = time + ":00";
        } else if (time.length() == 3) {
            time = time.substring(0, 1) + ":" + time.substring(1);
        } else if (time.length() == 4) {
            time = time.substring(0, 2) + ":" + time.substring(2);
        }

        time = putOneZeroAtFront(time);

        return time;
    }

    /**
     * when the length is equal to 4 --> H:MM (eg: 2:30) Thus, have to put one
     * zero in front to HH:MM (eg: 02:30)
     * 
     * @param time
     * @return HH:MM
     */
    private static String putOneZeroAtFront(String time) {
        time = time.trim();
        if (time.length() == 4) {
            time = "0" + time;
        }
        return time;
    }

    /**
     * switch to 24 hour time format of the time contain am
     * 
     * @param time
     * @return time in HH:MM
     */
    private static String switchToAmHour(String time) {
        time = time.trim();
        int index = getIndex(time);
        if (time.length() > 2 && time.charAt(0) == '1' && time.charAt(1) == '2') {
            time = "00" + ":" + time.substring(index + 1);
        } else if (time.length() == 2 && time.charAt(0) == '1'
                && time.charAt(1) == '2') {
            time = "00:00";
        } else if (time.length() == 1) {
            time = "0" + time + ":00";
        } else if (time.length() == 2) {
            time = time + ":00";
        } else if (time.length() == 4) {
            time = "0" + time.substring(0, index) + ":"
                    + time.substring(index + 1);
        } else if (time.length() == 5) {
            time = time.substring(0, index) + ":" + time.substring(index + 1);
        }
        return time;
    }

    /**
     * change the . or , to :
     * 
     * @param time
     * @return time with :
     */
    private static String changePuncToSemicolon(String time) {
        time = time.replaceAll("\\.|\\,", ":");
        return time;
    }

    /**
     * get the index of the separation of the HH and MM which is either : or .
     * 
     * @param time
     * @return the index of : or . depend which is detect
     */
    private static int getIndex(String time) {
        int indexForPunc = -1;
        if (time.contains(":")) {
            indexForPunc = time.indexOf(":");
        } else if (time.contains(".")) {
            indexForPunc = time.indexOf(".");
        } else if (time.contains(",")) {
            indexForPunc = time.indexOf(",");
        }
        return indexForPunc;
    }

    /**
     * if pm is detected behind the time, switch it to hour format from hour at
     * 13 onwards to 00
     * 
     * @param time
     * @return hour format for time in pm.
     */
    private static String switchToPMHour(String time) {
        int timeHour = 13, timeNormal = 1, userHourTime;
        String minTime;

        if (time.contains(":") || time.contains(".") || time.contains(",")) {
            userHourTime = getHH(time);
            minTime = getMinutes(time);
        } else {
            userHourTime = Integer.parseInt(time);
            minTime = "00";
        }

        while (timeHour != 24) {
            if (userHourTime == 12) {
                time = "12";
                break;
            } else if (userHourTime == timeNormal) {
                time = String.valueOf(timeHour);
                break;
            }
            timeHour++;
            timeNormal++;
        }
        time = time + ":" + minTime;
        return time;
    }

    /**
     * remove the conjunction
     * 
     * @param timeWithUnwantedPart
     * @return time free from conjunction
     */
    public static String removeUnwantedParts(String timeWithUnwantedPart) {
        String time;
        time = timeWithUnwantedPart.replaceAll(TO_BE_REMOVED_KEYWORD, "");
        return time;
    }

}

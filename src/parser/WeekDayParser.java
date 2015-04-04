package parser;

import java.util.Calendar;

public class WeekDayParser {

    public WeekDayParser() {

    }

    /**
     * add the weekday to the date
     * 
     * @param storageOfDate
     */
    public static String getWeekDay(String date) {

        Calendar calendar = Calendar.getInstance();

        int day = getDay(date);
        int year = getYear(date);
        int month = getMonth(date);
        setDateIntoCalendar(day, month - 1, year, calendar);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String weekdayInWord = convertToWord(dayOfWeek);
        return weekdayInWord;
    }

    /**
     * convert to word for all week day integer
     * 
     * @param dayOfWeek
     * @return
     */
    private static String convertToWord(int dayOfWeek) {
        String dayOfWeekInWord = "";

        if (dayOfWeek == 1) {
            dayOfWeekInWord = "Mon";
        } else if (dayOfWeek == 2) {
            dayOfWeekInWord = "Tues";
        } else if (dayOfWeek == 3) {
            dayOfWeekInWord = "Wed";
        } else if (dayOfWeek == 4) {
            dayOfWeekInWord = "Thur";
        } else if (dayOfWeek == 5) {
            dayOfWeekInWord = "Fri";
        } else if (dayOfWeek == 6) {
            dayOfWeekInWord = "Sat";
        } else if (dayOfWeek == 0) {
            dayOfWeekInWord = "Sun";
        }
        return dayOfWeekInWord;
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
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
    }

    /**
     * get the day from the date It follow singapore date format
     * 
     * @param dateOfTheTask
     * @return day
     */
    private static int getDay(String dateOfTheTask) {
        String[] ddMMYYYY = splitTheStringIntoPart(dateOfTheTask);
        int day = Integer.parseInt(ddMMYYYY[0]);

        return day;
    }

    /**
     * get the month of the date It follow singapore date format
     * 
     * @param dateOfTheTask
     * @return month
     */
    private static int getMonth(String dateOfTheTask) {
        String[] ddMMYYYY = splitTheStringIntoPart(dateOfTheTask);
        int month = Integer.parseInt(ddMMYYYY[1]);
        return month;
    }

    /**
     * get the year from the date it follows singapore date format
     * 
     * @param dateOfTheTask
     * @return year
     */
    private static int getYear(String dateOfTheTask) {
        String[] ddMMYYYY = splitTheStringIntoPart(dateOfTheTask);
        int year = Integer.parseInt(ddMMYYYY[2]);

        return year;
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
        } else if (dateOfTheTask.contains("-")) {
            ddmmyyyy = dateOfTheTask.split("(\\-)");
        }
        return ddmmyyyy;
    }

}

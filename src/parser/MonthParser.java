package parser;

/**
 * 
 * @author A0112823R
 *
 */
public class MonthParser {

    public MonthParser() {

    }

    /**
     * change month in word to month in int
     * 
     * @param dateOfTheTask
     * @return month in int
     */
    public static int convertMonthToNumber(String dateOfTheTask) {
        int month = 0;

        if (dateOfTheTask.contains("jan")) {
            month = 1;
        } else if (dateOfTheTask.contains("feb")) {
            month = 2;
        } else if (dateOfTheTask.contains("mar")) {
            month = 3;
        } else if (dateOfTheTask.contains("apr")) {
            month = 4;
        } else if (dateOfTheTask.contains("may")) {
            month = 5;
        } else if (dateOfTheTask.contains("jun")) {
            month = 6;
        } else if (dateOfTheTask.contains("jul")) {
            month = 7;
        } else if (dateOfTheTask.contains("aug")) {
            month = 8;
        } else if (dateOfTheTask.contains("sep")) {
            month = 9;
        } else if (dateOfTheTask.contains("oct")) {
            month = 10;
        } else if (dateOfTheTask.contains("nov")) {
            month = 11;
        } else if (dateOfTheTask.contains("dec")) {
            month = 12;
        }

        return month;
    }

    /**
     * get the month of the date in the format of dd/mm/yyyy
     * 
     * @param dateOfTheTask
     * @return month
     */
    public static int getMonth(String dateOfTheTask) {
        String[] ddmmyyyy = splitTheStringIntoPart(dateOfTheTask);

        assert ddmmyyyy.length != 3 || ddmmyyyy[1] != null;

        int month = Integer.parseInt(ddmmyyyy[1]);

        return month;
    }

    /**
     * split the date DD/MM/YYYY to day, month and year in the array of String
     * 
     * @param dateOfTheTask
     * @return day, month and year in string array.
     */
    public static String[] splitTheStringIntoPart(String dateOfTheTask) {
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

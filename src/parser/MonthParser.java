package parser;

import javax.swing.JOptionPane;

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

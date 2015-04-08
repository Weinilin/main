package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayParser {
    private static String[] wordOfNumDays = { "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen" };
    private static final String NUMBERIC_KEYWORD = "(\\b\\d+\\b)";
    private static final String DAY_KEYWORD = "(\\d+|\\w+)";
    private static final String MONTH_IN_WORD_KEYWORD = "(january|febuary|march|april|may|june|july|august|september|octobor|november|december)"
            + "|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)";

    public DayParser() {

    }

    /**
     * get the day from the date
     * 
     * @param dateOfTheTask
     * @return day
     */
    public static int getNumberOfDay(String dateOfTheTask) {

        int day = 0;
        dateOfTheTask = dateOfTheTask.replaceAll(MONTH_IN_WORD_KEYWORD, "");
        Pattern numberPattern = Pattern.compile(DAY_KEYWORD);
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

    
    private static boolean isNumeric(String text) {
        return text.matches(NUMBERIC_KEYWORD);
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

        // 20 means nothing from array(wordOfNumDays) is equal, no number of day
        // detect
        if (numberOfDays == 20) {
            numberOfDays = 0;
        }
        return numberOfDays;
    }

    /**
     * get next word for case like twenty-two
     * two would be return
     * @param text
     * @return next word
     */
    private static int getNextWord(String text) {
        int day = 0;
        Pattern numberPattern = Pattern.compile("\\w+");
        Matcher containDay = numberPattern.matcher(text);
       
        if (containDay.find()) {
            text = containDay.group();
            day = determineIntDaysFromWords(text);
        }
        return day;
    }

    
  
}

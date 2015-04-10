package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author A0112823R
 *
 */
public class NumberParser {
    private static String[] wordOfNumDays = { "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen" };
    private static final String NUMBERIC_KEYWORD = "(\\b\\d+\\b)";
    private static final String DAY_KEYWORD = "(\\d+|\\w+)";
    private static final String MONTH_IN_WORD_KEYWORD = "(january|febuary|march|april|may|june|july|august|september|octobor|november|december)"
            + "|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)";

    public NumberParser() {

    }

    /**
     * get the 1st number in word or digit before week, month, year
     * 
     * @param dateOfTheTask
     * @return number
     */
    public static int getNumber(String dateOfTheTask) {

        int number = 0;
        dateOfTheTask = dateOfTheTask.replaceAll(MONTH_IN_WORD_KEYWORD, "");
        Pattern numberPattern = Pattern.compile(DAY_KEYWORD);
        Matcher containNumber = numberPattern.matcher(dateOfTheTask);

        String text;

        if (containNumber.find()) {
            text = containNumber.group();

            if (isNumeric(text)) {
                number = Integer.parseInt(text);
            } else if (text.equals("twenty")) {
                text = dateOfTheTask.replaceAll(text, "");
                number = 20 + getNextWord(text);
            } else if (text.equals("thirty")) {
                text = dateOfTheTask.replaceAll(text, "");
                number = 30 + getNextWord(text);
            } else {
                number = determineIntFromWords(text);
            }
        }
        return number;
    }

    
    private static boolean isNumeric(String text) {
        return text.matches(NUMBERIC_KEYWORD);
    }

    /**
     * when the number of day is in word format change from word format to
     * integer format
     * 
     * @param numberInWord
     * @return number of day in integer.
     */
    private static int determineIntFromWords(String numberInWord) {
       
        int number = 1;
        
        for (int i = 0; i < wordOfNumDays.length; i++) {
            if (numberInWord.equals(wordOfNumDays[i])) {
                break;
            }
            number++;
        }

        // 20 means nothing from array(wordOfNumDays) is equal, no number of day
        // detect
        if (number == 20) {
            number = 0;
        }
        return number;
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
        Matcher containNumber = numberPattern.matcher(text);
       
        if (containNumber.find()) {
            text = containNumber.group();
            day = determineIntFromWords(text);
        }
        return day;
    }

    
  
}

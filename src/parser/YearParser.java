package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author A0112823R
 *
 */
public class YearParser {

    public YearParser() {

    }

    public static int getYear(String dateOfTheTask) {
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
}

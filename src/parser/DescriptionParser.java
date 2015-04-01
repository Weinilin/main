package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DescriptionParser {
    public static final String TIME_TO_TIME_KEYWORD = "(from|)[^/:,.]\\b(((\\d+[.:,](\\d+)|\\d+)(-| to | - )(\\d+[.:,](\\d+)|\\d+)(\\s|)(am|pm|)))\\b";
    private static final String HOURS_APART_KEYWORD = "\\b(start at \\b(on |at |from |to |due | due on |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b for \\d+ hour(\\s|))\\b";
    private static final String TIME_AM_OR_PM_KEYWORD = "\\b(due on |on |at |from |to |by |due |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
    private static final String TIME_WITH_OCLOCK_KEYWORD = "\\b(due on |on |at |from |to |by |due |)((\\d+|\\d+[:.,]\\d+)(\\s|)o'clock)\\b";
    private static final String NOON_MIDNIGHT_KEYWORD = "(\\b(due on |on |at |from |to |by |due |)noon\\b)|(\\b(due on |on |at |from |to |by |due |)midnight\\b)";
    private static final String BEFORE_NOON_BEFORE_MIDNIGHT_KEYWORD = "(\\b(due on |on |at |from |to |by |due |)(before midnight|before noon)\\b)";
    private static final String MORNING_AFTERNOON_NIGHT_KEYWORD = "(\\b(due on |on |at |from |to |by |due |)"
            + "(\\d+[.:,](\\d+)|\\d+)(\\s|\\S)(o'clock|am|pm|)( in (the |)(morning|morn)\\b| in (the |)afternoon\\b| in (the |)night\\b| at (the |)night\\b| at (the |)afternoon\\b"
                    + "| at (the |)morning\\b| at (the |)morn\\b))";
    private static final String TWENTY_FOUR_HH_KEYWORD = "(due on |on |at |from |to |by |due |)(\\b\\d+[:.,]\\d+\\b)";
    private static final String PAST_NOON_PAST_MIDNIGHT_KEYWORD = "(\\b(due on |on |at |from |to |by |due |)(past midnight|past noon|after noon|after midnight)\\b)";
    private static final String DDMMYYYY_KEYWORD = "\\b(on |at |from |to |by |due | due on |)(\\d+([/.]\\d+[/.]\\d+|[/.]\\d+\\b)\\b)";
    private static final String DD_MONNTHINWORD_YYYY_KEYWORD = "\\b(due on |on |at |from |to |by |due |)(\\b\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(january\\b|febuary\\b|march\\b|april\\b|may\\b|june\\b|july\\b|august\\b"
            + "|september\\b|octobor\\b|november\\b|december\\b)(\\s|\\S)(\\d+\\b|))";
    private static final String DD_SHORTFORMMONTHINWORD_YYYY_KEYWORD = "\\b(due on |on |at |from |to |by |due |)(\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(jan\\b|feb\\b|mar\\b|apr\\b|may\\b|jun\\b|jul\\b|aug\\b"
            + "|sep\\b|oct\\b|nov\\b|dec\\b)(\\s|\\S)(\\d+\\b|))";
    private static final String AFTER_DAYS_APART_KEYWORD = "(due on |on |at |from |to |by |due |)(\\b(after \\w+ day(s|))\\b|(\\w+ day(s|) after)|\\b(next(\\s\\w+\\s)day(s|)"
            + "\\b)|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b)";
    private static final String DAYS_APART_VOCAB_KEYWORD = "(due on |on |at |from |to |by |due |)(\\b((tomorrow|tmr)\\b|\\b(the\\s|)following day(s|)\\b|\\b(the\\s|)next day(s|)\\b|\\b(after today)"
            + "\\b|\\btoday\\b|\\b(after (tomorrow|tmr)\\b)|\\bfortnight\\b|\\b(the\\s|)next year(s|))\\b)";
    private static final String WEEKS_MONTHS_YEARS_APART_KEYWORD = "(due on |on |at |from |to |by |due |)(\\b(in \\w+ (week|month|year)(s|) time(s|))\\b|"
            + "\\b(\\w+ (week|month|year)(s|) later\\b)|\\b(after \\w+ (week|month|year)(s|)\\b)|"
            + "\\b(\\w+ (week|month|year)(s|) after)\\b)";
    private static final String AFTER_WEEKDAY_APART_KEYWORD = "(due on |on |at |from |to |by |due |)next (wednesday|(mon|tues|wed|thurs|fri|sat|sun)(day|))";
    private static final String THIS_WEEKDAY_APART_KEYWORD = "(due on |on |at |from |to |by |due |)this (wednesday|(mon|tues|wed|thurs|fri|sat|sun)(day|))";
	private static final String UNWANTED = "(\\bend at\\b|\\bstart at\\b|\\band\\b|\\.|~)";
	private String description;

	public DescriptionParser(String userInput) {
		String description;
		userInput = switchAllToLowerCase(userInput);
		userInput = replace(userInput, TIME_TO_TIME_KEYWORD);
		userInput = replace(userInput, HOURS_APART_KEYWORD);
		userInput = replace(userInput, BEFORE_NOON_BEFORE_MIDNIGHT_KEYWORD);
		userInput = replace(userInput, MORNING_AFTERNOON_NIGHT_KEYWORD);
		userInput = replace(userInput, PAST_NOON_PAST_MIDNIGHT_KEYWORD);
		userInput = replace(userInput, NOON_MIDNIGHT_KEYWORD);
		userInput = replace(userInput, TIME_AM_OR_PM_KEYWORD);
		userInput = replace(userInput, TIME_WITH_OCLOCK_KEYWORD);
		userInput = replace(userInput, TWENTY_FOUR_HH_KEYWORD);
		userInput = replace(userInput, DDMMYYYY_KEYWORD);
		userInput = replace(userInput, DD_MONNTHINWORD_YYYY_KEYWORD);
		userInput = replace(userInput, DD_SHORTFORMMONTHINWORD_YYYY_KEYWORD);
		userInput = replace(userInput, AFTER_DAYS_APART_KEYWORD);
		userInput = replace(userInput, DAYS_APART_VOCAB_KEYWORD);
		userInput = replace(userInput, WEEKS_MONTHS_YEARS_APART_KEYWORD);
		userInput = replace(userInput, AFTER_WEEKDAY_APART_KEYWORD);
		userInput = replace(userInput, THIS_WEEKDAY_APART_KEYWORD);
		userInput = userInput.replaceAll(UNWANTED, "");
		description = userInput.replaceAll("\\s+|,", " ");
		description = description.trim();
		setDescription(description);
	}

	/**
	 * to prevent case sensitive, switch all to lower case
	 * @param userInput
	 * @return the user input all in lower case. 
	 */
	private String switchAllToLowerCase(String userInput) {
	    userInput = userInput.replaceAll("\\s+", " ");
		userInput = " " + userInput.toLowerCase() + " ";
		return userInput;
	}

	/**
	 * if time or date with ~....~ keep it else remove them
	 * ~...~ escaped char
	 * @param userInput
	 * @param keyword
	 * @return user input without the time or date. 
	 */
	private String replace(String userInput, String keyword) {
		Pattern detector = Pattern.compile(keyword);
		Matcher contain = detector.matcher(userInput);
		String temp = userInput;

		while (contain.find()) {
			int startIndex = contain.start();
			int endIndex =contain.end();
			
			if ((startIndex == 0 || endIndex == userInput.length() || (userInput.charAt(startIndex - 1) != '~' && 
					userInput.charAt(endIndex) != '~'))) {
				temp = temp.replaceAll(contain.group(), "");
			}
		}
		return temp;
	}

	/**
	 * set description
	 * @param detectedDescription
	 */
	private void setDescription(String detectedDescription) {
		description = detectedDescription;

	}

	/**
	 * Get the description.
	 * @return the description 
	 */
	public String getDescription() {
		return description;
	}

}

package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DescriptionParser {
	private static final String TIME_KEYWORD_1 = "(((\\d+[.:](\\d+|)|\\d+)(-| to | - )(\\d+[.:](\\d+|)|\\d+)(\\s|)(am|pm)))";

	private static final String TIME_KEYWORD_2 = "(start at \\b(on |at |from |to |due |by |@ |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b for \\d+ hour(\\s|))";
	
	private static final String TIME_KEYWORD_6 = "\\b(on |at |from |to |due |by |@ |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
	private static final String TIME_KEYWORD_4 = "\\b(on |at |from |to |due |by |@ |)noon | (on |at |from |to |)midnight";
	private static final String TIME_KEYWORD_3 = "((from |to |)(before midnight|before noon))";
	private static final String TIME_KEYWORD_5 = "((from |to |)(\\d+[.:](\\d+|)|\\d+)( in (morning|morn)| in afternoon| in night| at night| at afternoon| at morning))";
	private static final String  DATE_KEYWORD1 = "\\b(on |at |from |to |due |by |@ |)\\d+([/.]\\d+[/.]\\d+|[/]\\d+\\b)\\b";
	private static final String  DATE_KEYWORD3_1 = "(on |at |from |to |due |by |@ |)\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(january|february|march|april|may|june|july|august"
			+ "|september|october|november|december)(\\s|\\S)(\\d+)";
	private static final String  DATE_KEYWORD3_2 = "(on |at |from |to |due |by |@ |)\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(january|february|march|april|may|june|july|august"
			+ "|september|october|november|december)";
	private static final String  DATE_KEYWORD3_3 = "(on |at |from |to |due |by |@ |)\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(jan|feb|mar|apr|may|jun|jul|aug"
			+ "|sep|oct|nov|dec)(\\s|\\S)(\\d+)";
	private static final String  DATE_KEYWORD3_4 = "(on |at |from |to |due |by |@ |)\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(jan|feb|mar|apr|may|jun|jul|aug"
			+ "|sep|oct|nov|dec)";
	private static final String DATE_KEYWORD4 = "\\b(after \\w+ days)\\b|(\\w+ day(s|) after)|\\b(next(\\s\\w+\\s)days"
			+ "\\b)|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b";
	private static final String DATE_KEYWORD4_1 = "\\b(after \\w+ day)\\b|(\\w+ day(s|) after)|\\b(next(\\s\\w+\\s)day"
			+ "\\b)|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b";
	private static final String DATE_KEYWORD5 ="\\b(on |at |from |to |due |by |@ |)((tomorrow|tmr)|the following day|following day|(the next day)"
			+ "|(after today)|today|(after (tomorrow|tmr))|fortnight|(the next year)|next year)\\b";

	private static final String DATE_KEYWORD6 = " \\b(in \\w+ (week|month|year)(s|) times)\\b|(in \\w+ (week|month|year)(s|) time)|"
			+ "\\b(\\w+ (week|month|year)(s|) later)|(after \\w+ (week|month|year))|(after \\w+ (weeks|months|years))|"
			+ "(\\w+ (week|month|year)(s|) after)\\b";
	private static final String TIME_KEYWORD_7 = "(\\d+|\\d+[:.]\\d+)(\\s|)o'clock";
	private static final String UNWANTED = "(end at|start at|and)";
	private String description;

	public DescriptionParser(String userInput) {
		String detectedDescription;
		userInput = switchAllToLowerCase(userInput);
		userInput = replaceBoth(userInput, DATE_KEYWORD1);
		userInput = replaceBoth(userInput, DATE_KEYWORD3_1);
		userInput = replaceBoth(userInput, DATE_KEYWORD3_2);
		userInput = replaceBoth(userInput, DATE_KEYWORD3_3);
		userInput = replaceBoth(userInput, DATE_KEYWORD3_4);
		userInput = replaceBoth(userInput, DATE_KEYWORD4);
		userInput = replaceBoth(userInput, DATE_KEYWORD4_1);
		userInput = replaceBoth(userInput, DATE_KEYWORD5);
		userInput = replaceBoth(userInput, DATE_KEYWORD6);
		userInput = replaceBoth(userInput, TIME_KEYWORD_1);
		userInput = replaceBoth(userInput, TIME_KEYWORD_2);
		userInput = replaceBoth(userInput, TIME_KEYWORD_3);
		userInput = replaceBoth(userInput, TIME_KEYWORD_4);
		userInput = replaceBoth(userInput, TIME_KEYWORD_5);
		userInput = replaceBoth(userInput, TIME_KEYWORD_6);
		userInput = replaceBoth(userInput, TIME_KEYWORD_7);
		userInput = userInput.replaceAll(UNWANTED, "");
		detectedDescription = userInput.replaceAll("\\s+|,", " ");
		detectedDescription = detectedDescription.trim();
		setDescription(detectedDescription);

	}
	
	/**
	 * to prevent case sensitive, switch all to lower case
	 * @param userInput
	 * @return the user input all in lower case. 
	 */
	private String switchAllToLowerCase(String userInput) {
		userInput = userInput.toLowerCase() + ".";
		return userInput;
	}

	/**
	 * replace it with empty
	 * @param userInput
	 * @param ignoreKeyword
	 * @param keyword
	 * @return user input without the one.
	 */
	private String replaceBoth(String userInput, String keyword) {
		Pattern detector = Pattern.compile(keyword);
		Matcher contain = detector.matcher(userInput);

		while (contain.find()) {
			int startIndex = contain.start();
			int endIndex =contain.end();

			if (startIndex == 0 || endIndex == userInput.length() || (userInput.charAt(startIndex - 1) != '~' && userInput.charAt(endIndex) != '~')) {
				userInput = userInput.replaceAll(contain.group(), "");
			}
		}
		return userInput;
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

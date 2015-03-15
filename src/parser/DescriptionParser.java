package parser;


public class DescriptionParser {
	private static final String  DATE_KEYWORD_FOR_TIMED = "from (\\d+[/]\\d+[/]\\d+) to (\\d+[/]\\d+[/]\\d+)";
	private static final String  DATE_KEYWORD1 = "\\b(on |at |from |to |)\\d+([/.]\\d+[/.]\\d+|[/]\\d+\\b)\\b";
	private static final String  DATE_KEYWORD2 = "(on |at |from |to |)\\b\\d+(\\s|\\S)"
			+ "(jan|january|feb|february|mar|march|apr|april|may|jun|june"
			+ "|jul|july|aug|august|sep|september|oct|october|nov"
			+ "|november|dec|december)"
			+ "(\\s|\\S|\\b)(\\d+|\\b)\\b";
	private static final String DATE_KEYWORD3 = "\\b(after \\w+ day)|(\\w+ day(s|) after)|"
			+ "(next(\\s\\w+\\s)day)|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b";
	private static final String DATE_KEYWORD4 ="\\b(tomorrow|(the\\s|)following day|(the\\s|)next day"
			+ "|(after today)|today|(after tomorrow)|fortnight)\\b";
	private static final String DATE_KEYWORD5 = " \\b(in \\w+ (week|month|year)(s|) time(s|))\\b|"
			+ "\\b(\\w+ (week|month|year)(s|) later)|(after \\w+ (week|month|year)(s|))|"
			+ "(\\w+ (week|month|year)(s|) after)\\b";
	private static final String TIME_KEYWORD_1 = "(((\\d+[.:](\\d+|)|\\d+)(-| to | - )(\\d+[.:](\\d+|)|\\d+)(\\s|)(am|pm)))";
	private static final String TIME_KEYWORD_2 = "\\b(on |at |from |to |- |-|)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
	private static final String TIME_KEYWORD_3 = "\\b(on |at |from |to |)noon";
	private static final String TIME_KEYWORD_4 = "\\b(\\d+[:.]\\d+(\\s|)(-|to|))\\b";
	
	public DescriptionParser() {
		
	}
	
	/**
	 * Get the description.
	 * @param userInput
	 * @return the description 
	 */
	public static String getDescription(String userInput) {
		String description;
		userInput = userInput.replaceAll(DATE_KEYWORD_FOR_TIMED , "");
		userInput = userInput.replaceAll(DATE_KEYWORD1, "");
		userInput = userInput.replaceAll(DATE_KEYWORD2, "");
		userInput = userInput.replaceAll(DATE_KEYWORD3, "");
		userInput = userInput.replaceAll(DATE_KEYWORD4, "");
		userInput = userInput.replaceAll(DATE_KEYWORD5, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_1, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_2, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_3, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_4, "");
		description = userInput.replaceAll("\\s+|,", " ");
		description = description.trim();
		
		return description;
	}

}

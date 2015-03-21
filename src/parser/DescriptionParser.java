package parser;


public class DescriptionParser {
	private static final String TIME_KEYWORD_1 = "(((\\d+[.:](\\d+|)|\\d+)(-| to | - )(\\d+[.:](\\d+|)|\\d+)(\\s|)(am|pm)))";
	private static final String TIME_KEYWORD_2 = "\\b(on |at |from |to |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
	private static final String TIME_KEYWORD_3 = "\\b(on |at |from |to |)noon | (on |at |from |to |)midnight";
	private static final String TIME_KEYWORD_4 = "(before midnight|before noon)";
	private static final String TIME_KEYWORD_5 = "(\\d+( in morning| in afternoon| in night))";
	private static final String  DATE_KEYWORD1 = "\\b(on |at |from |to |)\\d+([/.]\\d+[/.]\\d+|[/]\\d+\\b)\\b";
	private static final String  DATE_KEYWORD2 = "(on |at |from |to |)\\b\\d+(\\s|\\S)(january|february|march|april|may|june|july|august"
			+ "|september|october|november|december)(\\s|\\S)(\\d+|)";
	private static final String  DATE_KEYWORD3 = "(on |at |from |to |)\\b\\d+(\\s|\\S)(jan|feb|mar|apr|jun|jul|aug"
			+ "|sep|oct|nov|dec)(\\s|\\S)(\\d+|)";
	private static final String DATE_KEYWORD4 = "\\b(after \\w+ day(s|))|(\\w+ day(s|) after)|(next(\\s\\w+\\s)day(s|))"
			+ "|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b";
	private static final String DATE_KEYWORD5 ="\\b(tomorrow|(the\\s|)following day|(the\\s|)next day"
			+ "|(after today)|today|(after tomorrow)|fortnight)\\b";
	private static final String DATE_KEYWORD6 = " \\b(in \\w+ (week|month|year)(s|) time(s|))\\b|"
			+ "\\b(\\w+ (week|month|year)(s|) later)|(after \\w+ (week|month|year)(s|))|"
			+ "(\\w+ (week|month|year)(s|) after)\\b";
	private static final String UNWANTED = "(end at|start at|and|due|by|to|at)";
	private String description;

	public DescriptionParser(String userInput) {
		String detectedDescription;
		userInput = userInput.replaceAll(DATE_KEYWORD1, "");
		userInput = userInput.replaceAll(DATE_KEYWORD2, "");
		userInput = userInput.replaceAll(DATE_KEYWORD3, "");
		userInput = userInput.replaceAll(DATE_KEYWORD4, "");
		userInput = userInput.replaceAll(DATE_KEYWORD5, "");
		userInput = userInput.replaceAll(DATE_KEYWORD6, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_1, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_2, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_3, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_4, "");
		userInput = userInput.replaceAll(TIME_KEYWORD_5 , "");
		userInput = userInput.replaceAll(UNWANTED, "");
		detectedDescription = userInput.replaceAll("\\s+|,", " ");
		detectedDescription = detectedDescription.trim();
		setDescription(detectedDescription);

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

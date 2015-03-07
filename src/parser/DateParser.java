package parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;

public class DateParser {
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
	private static final String NUMBERIC_KEYWORD = "(\\b\\d+\\b)";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String UNWANTED_ALPHA = "(day(s|)|from now|after|next|later)|\\s";
	private static final String EMPTY_STRING = "";
	private static final String ONE_DAY = "one";
	private static final String TWO_DAY = "two";
	private static final String THREE_DAY = "three";
	private static final String FOUR_DAY = "four";
	private static final String FIVE_DAY = "five";
	private static final String SIX_DAY = "six";
	private static final String SEVEN_DAY = "seven";
	private static final String EIGHT_DAY = "eight";
	private static final String NINE_DAY = "nine";
	private static final String TEN_DAY = "ten";
	private static final String TODAY_TEXT = "today";
	private static final String TOMORROW_TEXT = "tomorrow";
	private static final String NEXT_DAY_TEXT = "next day";
	private static final String FOLLOWING_DAY_TEXT = "following day";
	private static final String AFTER_TODAY_TEXT = "after today";
	private static final String AFTER_TOMORROW_TEXT = "after tomorrow";
	private static final String WEEK_TEXT = "week";
	private static final String MONTH_TEXT = "month";
	private static final String YEAR_TEXT = "year";
	private static final String FORTNIGHT_TEXT = "fortnight";
	private static final String INVALID_TEXT = "date entered do not exist!";
	private static final int DATE_FORMAT_0 = 0;
	private static final int DATE_FORMAT_1 = 1;
	private static final int DATE_FORMAT_2 = 2;
	private static final int DATE_FORMAT_3 = 3;
	private static final int DATE_FORMAT_4 = 4;
	private static final int DATE_FORMAT_5 = 5;
	private static final int WEEK_UNIT = 7;
	private static final int FORTNIGHT_UNIT = 14;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	public DateParser(String dateTime) {
		parseFormattedString(dateTime);
	}

	public DateParser() {
	}

	//need another constructor for parsing unformatted string
	private void parseFormattedString(String dateTime) {
		day = Integer.parseInt(dateTime.substring(0, 2));
		month = Integer.parseInt(dateTime.substring(3, 5));
		year = Integer.parseInt(dateTime.substring(6, 10));
		hour = Integer.parseInt(dateTime.substring(12, 14));
		minute = Integer.parseInt(dateTime.substring(14, 16));
	}


	public long getDateTimeInMilliseconds() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String formattedDateTime = formatDateTime();
		Date date = null;

		try {
			date = sdf.parse(formattedDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long dateTimeInMilliseconds = date.getTime();

		return dateTimeInMilliseconds;
	}

	public String formatDateTime() {
		String formattedDateTime = day + "/" + month + "/" + year + " " + hour + ":" + minute ;
		return formattedDateTime;
	}


	/**
	 * 
	 * @param userInput
	 * @return String in the format of dd/mm/yyyy 
	 * and return the current date if nothing is detected
	 */
	public ArrayList<String> extractDate(String userInput){
		ArrayList<String> dateOfTheTask = new ArrayList<String>();

		userInput = switchAllToLowerCase(userInput);
		for(int i = 0; i <= 6; i++){
			dateOfTheTask = selectDetectionMethod(userInput, i);
			//System.out.println("dsize: "+dateOfTheTask.size()
			//		+ " i: "+ i);
			if(dateOfTheTask.size() != 0 && dateOfTheTask.get(0) != ""){
				//System.out.println("date: "+dateOfTheTask.get(0));
				break;
			}	
			//	if(isDateFormatRight(dateOfTheTask)){
			//	break;
			//	}
		}

		return dateOfTheTask;
	}

	private String switchAllToLowerCase(String userInput) {
		userInput = userInput.toLowerCase();
		return userInput;
	}

	private ArrayList<String> selectDetectionMethod(String userInput, int dateFormat) {
		String dateOfTheTask = "";

		ArrayList<String> dates = new ArrayList<String>();

		//System.out.println("dateFormat: "+dateFormat);
		if(dateFormat == DATE_FORMAT_0){
			dates = spotDateFormat0(userInput);
		}
		else if(dateFormat == DATE_FORMAT_1){
			dates.add(spotDateFormat1(userInput));
		}
		else if(dateFormat == DATE_FORMAT_2){
			//dateOfTheTask = spotDateFormat2(userInput);
			dates.add(spotDateFormat2(userInput));
		}
		else if(dateFormat == DATE_FORMAT_3){
			//dateOfTheTask = spotDateFormat3(userInput);	
			dates.add(spotDateFormat3(userInput));
		}
		else if(dateFormat == DATE_FORMAT_4){
			//dateOfTheTask = spotDateFormat4(userInput);
			dates.add(spotDateFormat4(userInput));
		}
		else if(dateFormat == DATE_FORMAT_5){
			//dateOfTheTask = spotDateFormat5(userInput);	
			dates.add(spotDateFormat5(userInput));
		}
		else{
			DateFormat date = new SimpleDateFormat(DATE_FORMAT);
			Calendar cal = Calendar.getInstance();
			dateOfTheTask = date.format(cal.getTime());
			dates.add(dateOfTheTask);
		}
		return dates;
	}

	private ArrayList<String> spotDateFormat0(String userInput) {
		ArrayList<String> dateOfTheTask = new ArrayList<String>();
		String uniqueKeyword = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD_FOR_TIMED);
		Matcher containDate = dateDetector.matcher(userInput);

		while(containDate.find()){
			uniqueKeyword = containDate.group();
			uniqueKeyword = removeToAndFrom(uniqueKeyword);
			dateOfTheTask = divideDate(uniqueKeyword);
		}

		return dateOfTheTask;
	}

	private ArrayList<String> divideDate(String uniqueKeyword) {
		ArrayList<String> dateOfTheTask = new ArrayList<String>();
		uniqueKeyword  = uniqueKeyword.trim();
		//System.out.println("0. date:"+uniqueKeyword);
		String[] dates = uniqueKeyword.split(" ");
		//System.out.println("1. date: "+dates[0]);
		dateOfTheTask.add(dates[0]);
		//	System.out.println("1. date: "+dateOfTheTask.get(0));
		dateOfTheTask.add(dates[1]);
		//System.out.println("2. date: "+dateOfTheTask.get(1));

		return dateOfTheTask;
	}

	private String removeToAndFrom(String uniqueKeyword) {
		uniqueKeyword = uniqueKeyword.replaceAll("from", "");
		uniqueKeyword = uniqueKeyword.replaceAll("to ", "");
		return uniqueKeyword;
	}

	private String spotDateFormat5(String userInput) {
		String dateOfTheTask = "", uniqueKeyword = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD5);
		Matcher containDate = dateDetector.matcher(
				userInput);

		while(containDate.find()){
			uniqueKeyword = containDate.group();
		}

		if(isDateFormatRight(uniqueKeyword)){
			dateOfTheTask = getUsingDateFormat5(uniqueKeyword);	
		}

		return dateOfTheTask;
	}

	private String getUsingDateFormat5(String uniqueKeyword) {
		String dateOfTask;
		dateOfTask = getDateAfterAddOn(uniqueKeyword);
		return dateOfTask;
	}

	private String spotDateFormat4(String userInput) {
		String dateOfTheTask = "", uniqueKeyword = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD4);
		Matcher containDate = dateDetector.matcher(userInput);

		while(containDate.find()){
			uniqueKeyword = containDate.group();
		}

		if(isDateFormatRight(uniqueKeyword)){
			dateOfTheTask = getUsingDateFormat4(uniqueKeyword);	
		}

		return dateOfTheTask;
	}

	private String addToTheCurrentDateByDays(int numberOfDay) {
		String dateOfTheTask;
		DateFormat date = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, numberOfDay);
		dateOfTheTask = date.format(cal.getTime());
		return dateOfTheTask;
	}

	private int determineIntDaysFromWords(String numberOfDaysFromNow) {
		int numberOfDays = 0;
		if(numberOfDaysFromNow.equals(ONE_DAY)){
			numberOfDays = 1;
		}
		else if(numberOfDaysFromNow.equals(TWO_DAY)){
			numberOfDays = 2;
		}
		else if(numberOfDaysFromNow.equals(THREE_DAY)){
			numberOfDays = 3;
		}
		else if(numberOfDaysFromNow.equals(FOUR_DAY)){
			numberOfDays = 4;
		}
		else if(numberOfDaysFromNow.equals(FIVE_DAY)){
			numberOfDays = 5;
		}
		else if(numberOfDaysFromNow.equals(SIX_DAY)){
			numberOfDays = 6;
		}
		else if(numberOfDaysFromNow.equals(SEVEN_DAY)){
			numberOfDays = 7;
		}
		else if(numberOfDaysFromNow.equals(EIGHT_DAY)){
			numberOfDays = 8;
		}
		else if(numberOfDaysFromNow.equals(NINE_DAY)){
			numberOfDays = 9;
		}
		else if(numberOfDaysFromNow.equals(TEN_DAY)){
			numberOfDays = 10;
		}
		return numberOfDays;
	}

	private boolean isNumeric(String numberOfDaysFromNow) {
		return numberOfDaysFromNow.matches(NUMBERIC_KEYWORD);
	}

	private String spotDateFormat3(String userInput) {
		String uniqueKeyword = "", dateOfTask = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD3);
		Matcher containDate = dateDetector.matcher(userInput);

		while(containDate.find()){
			uniqueKeyword = containDate.group();
		}
		if(isDateFormatRight(uniqueKeyword)){
			dateOfTask = getUsingDateFormat3(uniqueKeyword);	
		}

		return dateOfTask;
	}

	private String getUsingDateFormat3(String uniqueKeyword) {
		String dateOfTask;
		String numberOfDaysFromNow = removeAllOtherThanNumberOfDay(uniqueKeyword);
		dateOfTask = getDateAfterAddOn(numberOfDaysFromNow);
		return dateOfTask;
	}

	private String getUsingDateFormat4(String uniqueKeyword) {
		String dateOfTask;
		dateOfTask = getDateAfterAddOn(uniqueKeyword);
		return dateOfTask;
	}

	private String getDateAfterAddOn(String uniqueKeyword) {
		int numberOfDays;
		String dateOfTask = "";

		if(uniqueKeyword.equals(TODAY_TEXT)){
			numberOfDays = 0;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.equals(TOMORROW_TEXT)){
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.contains(NEXT_DAY_TEXT)){
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.contains(FOLLOWING_DAY_TEXT)){
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.equals(AFTER_TODAY_TEXT)){
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.equals(AFTER_TOMORROW_TEXT)){
			numberOfDays = 2;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.contains(WEEK_TEXT)){
			int numberOfWeek = isolateTheNumberInString(uniqueKeyword);
			numberOfDays = WEEK_UNIT * numberOfWeek;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else if(uniqueKeyword.contains(MONTH_TEXT)){
			int numberOfMonth = isolateTheNumberInString(uniqueKeyword);
			dateOfTask = addToTheCurrentDateByMonth(numberOfMonth);

		}
		else if(uniqueKeyword.contains(YEAR_TEXT)){
			int numberOfYear = isolateTheNumberInString(uniqueKeyword);
			dateOfTask = addToTheCurrentDateByYear(numberOfYear);
		}
		else if(uniqueKeyword.contains(FORTNIGHT_TEXT)){
			int numberOfFornight = isolateTheNumberInString(uniqueKeyword);
			numberOfDays = FORTNIGHT_UNIT * numberOfFornight;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}
		else{
			numberOfDays = determineTheNumber(uniqueKeyword);
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}

		return dateOfTask;
	}

	private String addToTheCurrentDateByYear(int numberOfYear) {
		String dateOfTheTask;
		DateFormat date = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, numberOfYear);
		dateOfTheTask = date.format(cal.getTime());
		return dateOfTheTask;
	}

	private String addToTheCurrentDateByMonth(int numberOfMonth) {
		String dateOfTheTask;
		DateFormat date = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, numberOfMonth);
		dateOfTheTask = date.format(cal.getTime());
		return dateOfTheTask;
	}

	private int isolateTheNumberInString(String uniqueKeyword) {
		String containOnlyNumber;
		int number;
		uniqueKeyword = removeIn(uniqueKeyword); //so the 1st word represent number
		String[] partsOfUniqueKeyword = uniqueKeyword.split(" ");
		containOnlyNumber = partsOfUniqueKeyword[0];
		number = determineTheNumber(containOnlyNumber);
		return number;
	}

	private String removeIn(String uniqueKeyword) {
		uniqueKeyword = uniqueKeyword.replaceAll(" in ", "");
		return uniqueKeyword;
	}

	private int determineTheNumber(String containNumber) {
		int numberOfDays;
		if(isNumeric(containNumber)){
			numberOfDays = Integer.parseInt(containNumber);
		}
		else{
			numberOfDays = determineIntDaysFromWords(containNumber);
		}
		return numberOfDays;
	}

	private String removeAllOtherThanNumberOfDay(String uniqueKeyword) {
		String numberOfDaysFromNow = uniqueKeyword.replaceAll(UNWANTED_ALPHA, EMPTY_STRING);
		//System.out.println("number: "+ numberOfDaysFromNow);
		return numberOfDaysFromNow;
	}

	private boolean isDateFormatRight(String dateOfTheTask) {
		return dateOfTheTask != "" || dateOfTheTask.equals(INVALID_TEXT);
	}

	private String spotDateFormat2(String userInput) {
		String dateOfTheTask = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD2);
		Matcher containDate = dateDetector.matcher(
				userInput);

		while(containDate.find()){
			dateOfTheTask = containDate.group();

			//System.out.println("dateOFTASKfeb: "+dateOfTheTask);
			DateFormat date = new SimpleDateFormat(DATE_FORMAT);
			Calendar calendar = Calendar.getInstance();
			int day = getDay(dateOfTheTask);
			calendar.set(Calendar.DATE, day);
			int year = getYear(dateOfTheTask);
			if(year != 0){
				calendar.set(Calendar.YEAR, year);
			}
			int month = detectMonthByWord(dateOfTheTask) - 1;
			calendar.set(Calendar.MONTH, month);
			dateOfTheTask = date.format(calendar.getTime());
			//System.out.println("FEVdate: "+dateOfTheTask);
			if(isDateValid2(dateOfTheTask)){
				dateOfTheTask = INVALID_TEXT;
			}
		}

		return dateOfTheTask;
	}

	private boolean isDateValid2(String dateOfTheTask) {
		int maxDays;
		boolean isInvalidDate = false;

		Calendar calendar = Calendar.getInstance();
		int day = getDay(dateOfTheTask);
		int year = getYear(dateOfTheTask);
		calendar.set(Calendar.YEAR, year);
		int month = detectMonthByWord(dateOfTheTask) - 1;
		calendar.set(Calendar.MONTH, month);
		maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		if(exceedMaxDaysOnThatMonth(day, maxDays)){
			isInvalidDate = true;
		}
		return isInvalidDate;
	}

	private int getDay(String dateOfTheTask) {
		int day = 0;
		Pattern numberPattern = Pattern.compile("\\d+");
		Matcher containDateFormat2 = numberPattern.matcher(
				dateOfTheTask);
		String numberText;

		while(containDateFormat2.find()){
			numberText = containDateFormat2.group();
			if(numberText.length() <= 2)
				day = Integer.parseInt(numberText);
		}
		return day;
	}
	private int getYear(String dateOfTheTask) {   	
		int year = 0;
		Pattern numberPattern = Pattern.compile("\\d+");
		Matcher containDateFormat2 = numberPattern.matcher(
				dateOfTheTask);
		String numberText;

		while(containDateFormat2.find()){
			numberText = containDateFormat2.group();
			if(numberText.length() == 4){
				year = Integer.parseInt(numberText);
			}
		}
		return year;
	}

	private int detectMonthByWord(String dateOfTheTask) {
		int month = 0;
		dateOfTheTask = dateOfTheTask.replaceAll("\\d+", "");
		if(dateOfTheTask.contains("jan") || dateOfTheTask.contains("january")){
			month = 1;
		}
		else if(dateOfTheTask.contains("feb") || dateOfTheTask.contains("february")){
			month = 2;
		}
		else if(dateOfTheTask.contains("mar") || dateOfTheTask.contains("march")){
			month = 3;
		}
		else if(dateOfTheTask.contains("apr") || dateOfTheTask.contains("april")){
			month = 4;
		}
		else if(dateOfTheTask.contains("may")){
			month = 5;
		}
		else if(dateOfTheTask.contains("jun") || dateOfTheTask.contains("june")){
			month = 6;
		}
		else if(dateOfTheTask.contains("jul") || dateOfTheTask.contains("july")){
			month = 7;
		}
		else if(dateOfTheTask.contains("aug") || dateOfTheTask.contains("august")){
			month = 8;
		}
		else if(dateOfTheTask.contains("sep") || dateOfTheTask.contains("september")){
			month = 9;
		}
		else if(dateOfTheTask.contains("oct") || dateOfTheTask.contains("october")){
			month = 10;
		}
		else if(dateOfTheTask.contains("nov") || dateOfTheTask.contains("november")){
			month = 11;
		}
		else if(dateOfTheTask.contains("dec") || dateOfTheTask.contains("december")){
			month = 12;
		}

		//System.out.println("month: "+ month);
		return month;
	}

	private String spotDateFormat1(String userInput) {
		String dateOfTheTask = "";
		String[] partsOfString;

		Pattern dateDetector = Pattern.compile(DATE_KEYWORD1);
		Matcher containDate = dateDetector.matcher(
				userInput);

		while(containDate.find()){
			Calendar calendar = Calendar.getInstance();
			dateOfTheTask = containDate.group();

			partsOfString = splitTheStringIntoPart(dateOfTheTask);
			int day = extractDayAndYear(partsOfString, calendar);
			//System.out.println("1. day : "+ day);
			calendar.set(Calendar.DATE, day);

			DateFormat date = new SimpleDateFormat(DATE_FORMAT);
			dateOfTheTask = date.format(calendar.getTime());
			//System.out.println("1. date : "+ dateOfTheTask);

			int month = extractMonthByNumber(partsOfString) - 1;
			calendar.set(Calendar.MONTH, month);
			//	System.out.println("1. month : "+ month);
			dateOfTheTask = date.format(calendar.getTime());
			//System.out.println("1. date : "+ dateOfTheTask);
			if(isDateValid1(dateOfTheTask)){
				dateOfTheTask = INVALID_TEXT;
			}
		}
		return dateOfTheTask;
	}

	private boolean isDateValid1(String dateOfTheTask) {
		boolean isInvalidDate = false;
		int day, maxDays, month;
		String[] partsOfString;

		Calendar calendar = Calendar.getInstance();
		partsOfString = splitTheStringIntoPart(dateOfTheTask);
		month = extractMonthByNumber(partsOfString) - 1;	
		if(ifMonthValid(month)){
			// System.out.println("month: "+month);
			day = extractDayAndYear(partsOfString, calendar);
			calendar.set(Calendar.MONTH, month);
			maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			//System.out.println("dya: "+day+ " max: "+maxDays);
			if(exceedMaxDaysOnThatMonth(day, maxDays)){
				isInvalidDate = true;
			}
		}
		return isInvalidDate;
	}

	private boolean ifMonthValid(int month) {
		return month >= 0 || month <= 11;
	}

	private int extractDayAndYear(String[] partsOfString,
			Calendar calendar) {
		int year = 0, day = 0;
		if(isEndStringContainYear(partsOfString)){
			year = Integer.parseInt(partsOfString[2]);
			day = Integer.parseInt(partsOfString[0]);
			calendar.set(Calendar.YEAR, year);
		}
		else if(isFrontStringContainYear(partsOfString)){
			year = Integer.parseInt(partsOfString[0]);
			day = Integer.parseInt(partsOfString[2]);
			calendar.set(Calendar.YEAR, year);
		}
		else{
			day = Integer.parseInt(partsOfString[0]);
		}
		//System.out.println("year: "+year);
		return day;
	}

	private boolean exceedMaxDaysOnThatMonth(int day, int maxDays) {
		return day > maxDays;
	}

	private boolean isFrontStringContainYear(String[] partsOfString) {
		return partsOfString.length == 3 && partsOfString[0].length() == 4;
	}

	private boolean isEndStringContainYear(String[] partsOfString) {
		return partsOfString.length == 3 && partsOfString[2].length() == 4;
	}

	private int extractMonthByNumber(String[] partsOfString) {
		int month;
		//System.out.println("length: "+partsOfString.length);
		month = Integer.parseInt(partsOfString[1]);
		return month;
	}

	private String[] splitTheStringIntoPart(String dateOfTheTask) {
		String[] partsOfString = null;
		if(dateOfTheTask.contains("/")){
			partsOfString = dateOfTheTask.split("(/)");
		}
		else if(dateOfTheTask.contains(".")){
			partsOfString = dateOfTheTask.split("(\\.)");
		}

		return partsOfString;
	}

}

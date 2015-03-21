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
	private static final String  DATE_KEYWORD1 = "\\b(on |at |from |to |)\\d+([/.]\\d+[/.]\\d+|[/]\\d+\\b)\\b";
	//	private static final String  DATE_KEYWORD2 = "(on |at |from |to |)\\b\\d{0,}(\\s|\\S)(january|february|march|april|may|june|july|august"
	//		+ "|september|october|november|december)(\\s|\\S)(\\d+|)";
	private static final String  DATE_KEYWORD3 = "(on |at |from |to |)\\b\\d{0,}(th|nd|rd|)(\\s|\\S)(of |)(jan|feb|mar|apr|may|jun|jul|aug"
			+ "|sep|oct|nov|dec)(\\s|\\S)(\\d+|)";
	private static final String DATE_KEYWORD4 = "\\b(after \\w+ day(s|))\\b|(\\w+ day(s|) after)|\\b(next(\\s\\w+\\s)day(s|)"
			+ "\\b)|(\\w+ day(s|) from now)|(\\w+ day(s|) later)\\b";
	private static final String DATE_KEYWORD5 ="\\b((tomorrow|tmr)|(the\\s|)following day|(the\\s|)next day"
			+ "|(after today)|today|(after (tomorrow|tmr))|fortnight|(the\\s|)next year)\\b";
	private static final String DATE_KEYWORD6 = " \\b(in \\w+ (week|month|year)(s|) time(s|))\\b|"
			+ "\\b(\\w+ (week|month|year)(?!~)(s|) later)|(after \\w+ (week|month|year)(s|))|"
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
	private static final String NEXT_YEAR_TEXT = "next year";
	private static final String FOLLOWING_DAY_TEXT = "following day";
	private static final String AFTER_TODAY_TEXT = "after today";
	private static final String AFTER_TOMORROW_TEXT = "after tomorrow";
	private static final String WEEK_TEXT = "week";
	private static final String MONTH_TEXT = "month";
	private static final String YEAR_TEXT = "year";
	private static final String FORTNIGHT_TEXT = "fortnight";
	private static final String INVALID_TEXT = "date entered do not exist!";
	private static final int DATE_FORMAT_1 = 1;
	private static final int DATE_FORMAT_2 = 2;
	private static final int DATE_FORMAT_3 = 3;
	private static final int DATE_FORMAT_4 = 4;
	private static final int DATE_FORMAT_5 = 5;
	private static final int DATE_FORMAT_6 = 6;
	private static final int WEEK_UNIT = 7;
	private static final int FORTNIGHT_UNIT = 14;
	private static String detectUserInput;
	private static int index;

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
		hour = Integer.parseInt(dateTime.substring(11, 13));
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
	public static ArrayList<String> extractDate(String userInput) {
		ArrayList<String> dateOfTheTask = new ArrayList<String>();

		userInput = switchAllToLowerCase(userInput);
		userInput = removeThoseHashTag(userInput);
		detectUserInput = userInput;
		for (int i = 0; i <= 6; i++) {
			dateOfTheTask = selectDetectionMethod(i, dateOfTheTask);
		}

		return dateOfTheTask;
	}

	/**
	 * indication of ~ means that user want it to be in description
	 * @param userInput
	 * @return user input without ~
	 */
	private static String removeThoseHashTag(String userInput) {
		ArrayList<Integer> hashTagIndex = new ArrayList<Integer>();
		Pattern hashTagDetector = Pattern.compile("~");
		Matcher containHashTag = hashTagDetector.matcher(userInput);

		while (containHashTag.find()) {
			hashTagIndex.add(containHashTag.start());

		}
		if (!hashTagIndex.isEmpty()) {
			userInput = userInput.substring(0, hashTagIndex.get(0)) + userInput.substring(hashTagIndex.get(1));
			System.out.println("userInput: "+userInput);
		}
		return userInput;
	}

	/**
	 * to prevent case sensitive, switch all to lower case
	 * @param userInput
	 * @return the user input all in lower case. 
	 */
	private static String switchAllToLowerCase(String userInput) {
		userInput = userInput.toLowerCase() + ".";
		return userInput;
	}

	/**
	 * select detection method of different format  
	 * @param dateFormat
	 * @param dates
	 * @return all of the dates detected. 
	 */
	private static ArrayList<String> selectDetectionMethod(int dateFormat,
			ArrayList<String> dates) {

		if (dateFormat == DATE_FORMAT_1) {
			dates = spotDateFormat1(detectUserInput, dates);
		} else if (dateFormat == DATE_FORMAT_2) {
			//	dates = spotDateFormat2(detectUserInput, DATE_KEYWORD2, dates);
		} else if (dateFormat == DATE_FORMAT_3) {	
			dates = spotDateFormat2(detectUserInput,dates);
		} else if (dateFormat == DATE_FORMAT_4) {
			dates = spotDateFormat4(detectUserInput, dates);
		} else if (dateFormat == DATE_FORMAT_5) {	
			dates = spotDateFormat5(detectUserInput, dates);
		} else if (dateFormat == DATE_FORMAT_6) {
			dates = spotDateFormat6(detectUserInput, dates);
		}

		return dates;
	}

	/**
	 * detect after no weeks, after no months, after no years 
	 * s is not senstive
	 * @param userInput
	 * @param storageOfDate
	 * @return date in DD/MM/YYYY
	 */
	private static ArrayList<String> spotDateFormat6(String userInput, ArrayList<String> storageOfDate) {
		String dateOfTheTask = "", uniqueKeyword = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD6);
		Matcher containDate = dateDetector.matcher(detectUserInput);
		Matcher matchWithIndex = dateDetector.matcher(userInput);

		while (containDate.find() && matchWithIndex.find()) {
			uniqueKeyword = containDate.group();
			int indexMatch = matchWithIndex.start();
			detectUserInput = detectUserInput.replaceAll(uniqueKeyword, "");
			dateOfTheTask = getUsingDateFormat6(uniqueKeyword);	
			storageOfDate.add(dateOfTheTask);
			setThePosition(storageOfDate, indexMatch);
		}

		return storageOfDate;
	}

	private static String getUsingDateFormat6(String uniqueKeyword) {
		String dateOfTask;
		dateOfTask = getDateAfterAddOn(uniqueKeyword);
		return dateOfTask;
	}

	/**
	 * detect tomorrow, the following day, after tomorrow, after today
	 * @param userInput
	 * @param storageOfDate
	 * @return dates in DD/MM/YYYY
	 */
	private static ArrayList<String> spotDateFormat5(String userInput, ArrayList<String> storageOfDate) {
		String dateOfTheTask = "", uniqueKeyword = "";
		Pattern dateDetector = Pattern.compile(DATE_KEYWORD5);
		Matcher containDate = dateDetector.matcher(detectUserInput);
		Matcher toGetIndex = dateDetector.matcher(userInput);

		while (containDate.find() && toGetIndex.find()) {
			uniqueKeyword = containDate.group();
			int indexMatch = toGetIndex.start();
			detectUserInput = detectUserInput.replaceAll(DATE_KEYWORD5, "");
			dateOfTheTask = getUsingDateFormat5(uniqueKeyword);	
			storageOfDate.add(dateOfTheTask);
			setThePosition(storageOfDate, indexMatch);
		}

		return storageOfDate;
	}

	/**
	 * set the start and end date in the right position in arrayList
	 * @param storageOfDate
	 * @param indexMatch
	 */
	private static void setThePosition(ArrayList<String> storageOfDate,
			int indexMatch) {
		String temp; 
		if (storageOfDate.size() == 2 && indexMatch < index) {
			temp = storageOfDate.get(0);
			storageOfDate.set(0, storageOfDate.get(1));
			storageOfDate.set(1, temp);				
		}
		index = indexMatch;
	}

	/**
	 * add number of days to the current date
	 * @param numberOfDay
	 * @return date after adding number of days in DD/MM/YYYY.
	 */
	private static String addToTheCurrentDateByDays(int numberOfDay) {
		String dateOfTheTask;
		DateFormat date = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, numberOfDay);
		dateOfTheTask = date.format(cal.getTime());
		return dateOfTheTask;
	}

	/**
	 * when the number of day is in word format change from word format to integer format
	 * @param numberOfDaysFromNow
	 * @return number of day in integer.
	 */
	private static int determineIntDaysFromWords(String numberOfDaysFromNow) {
		int numberOfDays = 0;
		if (numberOfDaysFromNow.equals(ONE_DAY)) {
			numberOfDays = 1;
		} else if (numberOfDaysFromNow.equals(TWO_DAY)) {
			numberOfDays = 2;
		} else if (numberOfDaysFromNow.equals(THREE_DAY)) {
			numberOfDays = 3;
		} else if (numberOfDaysFromNow.equals(FOUR_DAY)) {
			numberOfDays = 4;
		} else if (numberOfDaysFromNow.equals(FIVE_DAY)) {
			numberOfDays = 5;
		} else if (numberOfDaysFromNow.equals(SIX_DAY)) {
			numberOfDays = 6;
		} else if (numberOfDaysFromNow.equals(SEVEN_DAY)) {
			numberOfDays = 7;
		} else if (numberOfDaysFromNow.equals(EIGHT_DAY)) {
			numberOfDays = 8;
		} else if (numberOfDaysFromNow.equals(NINE_DAY)) {
			numberOfDays = 9;
		} else if (numberOfDaysFromNow.equals(TEN_DAY)) {
			numberOfDays = 10;
		}
		return numberOfDays;
	}

	private static boolean isNumeric(String numberOfDaysFromNow) {
		return numberOfDaysFromNow.matches(NUMBERIC_KEYWORD);
	}

	/**
	 * detect date: after (no. in word or int) days, next (no in word or int) day
	 * is okay to have s behind or no s behind day
	 * @param userInput
	 * @return the date in DD/MM/YYYY format.
	 */
	private static ArrayList<String> spotDateFormat4(String userInput, ArrayList<String> storageOfDate) {
		String uniqueKeyword = "", dateOfTask = "";

		Pattern dateDetector = Pattern.compile(DATE_KEYWORD4);
		Matcher containDate = dateDetector.matcher(detectUserInput);
		Matcher toGetIndex = dateDetector.matcher(userInput);

		while (containDate.find() && toGetIndex.find()) {
			uniqueKeyword = containDate.group();
			int indexMatch = toGetIndex.start();
			uniqueKeyword = containDate.group();
			detectUserInput = detectUserInput.replaceAll(DATE_KEYWORD4, "");
			dateOfTask = getUsingDateFormat4(uniqueKeyword);	
			storageOfDate.add(dateOfTask);

			setThePosition(storageOfDate, indexMatch);
		}
		return storageOfDate;
	}

	private static String getUsingDateFormat4(String uniqueKeyword) {
		String dateOfTask;
		String numberOfDaysFromNow = removeAllOtherThanNumberOfDay(uniqueKeyword);
		dateOfTask = getDateAfterAddOn(numberOfDaysFromNow);
		return dateOfTask;
	}

	private static String getUsingDateFormat5(String uniqueKeyword) {
		String dateOfTask;
		dateOfTask = getDateAfterAddOn(uniqueKeyword);
		return dateOfTask;
	}

	/**
	 * add on the number of day to the current date 
	 * @param uniqueKeyword
	 * @return date in DD/MM/YYYY
	 */
	private static String getDateAfterAddOn(String uniqueKeyword) {
		int numberOfDays;
		String dateOfTask = "";

		if(uniqueKeyword.equals(TODAY_TEXT)) {
			numberOfDays = 0;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.equals(TOMORROW_TEXT) || uniqueKeyword.equals("tmr")) {
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.contains(NEXT_DAY_TEXT)) {
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.contains(NEXT_YEAR_TEXT)) {
			int numberOfYear = 1;
			dateOfTask = addToTheCurrentDateByYear(numberOfYear);
		} else if(uniqueKeyword.contains(FOLLOWING_DAY_TEXT)) {
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.equals(AFTER_TODAY_TEXT)) {
			numberOfDays = 1;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.equals(AFTER_TOMORROW_TEXT) || uniqueKeyword.equals("after tmr")) {
			numberOfDays = 2;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.contains(WEEK_TEXT)) {
			int numberOfWeek = isolateTheNumberInString(uniqueKeyword);
			numberOfDays = WEEK_UNIT * numberOfWeek;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else if(uniqueKeyword.contains(MONTH_TEXT)) {
			int numberOfMonth = isolateTheNumberInString(uniqueKeyword);
			dateOfTask = addToTheCurrentDateByMonth(numberOfMonth);
		} else if(uniqueKeyword.contains(YEAR_TEXT)) {
			int numberOfYear = isolateTheNumberInString(uniqueKeyword);
			dateOfTask = addToTheCurrentDateByYear(numberOfYear);
		} else if(uniqueKeyword.contains(FORTNIGHT_TEXT)) {
			int numberOfFornight = isolateTheNumberInString(uniqueKeyword);
			numberOfDays = FORTNIGHT_UNIT * numberOfFornight;
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		} else {
			numberOfDays = determineTheNumber(uniqueKeyword);
			dateOfTask = addToTheCurrentDateByDays(numberOfDays);
		}

		return dateOfTask;
	}

	/**
	 * add the number of year to current year based on what is detect
	 * @param numberOfYear
	 * @return date in DD/MM/YYYY
	 */
	private static String addToTheCurrentDateByYear(int numberOfYear) {
		String dateOfTheTask;
		DateFormat date = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, numberOfYear);
		dateOfTheTask = date.format(cal.getTime());
		return dateOfTheTask;
	}

	/**
	 * add the number of month to current year based on what is detect
	 * @param numberOfMonth
	 * @return date in DD/MM/YYYY
	 */
	private static String addToTheCurrentDateByMonth(int numberOfMonth) {
		String dateOfTheTask;
		DateFormat date = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, numberOfMonth);
		dateOfTheTask = date.format(cal.getTime());
		return dateOfTheTask;
	}

	private static int isolateTheNumberInString(String uniqueKeyword) {
		String containOnlyNumber;
		int number;
		uniqueKeyword = removeIn(uniqueKeyword); //so the 1st word represent number
		String[] partsOfUniqueKeyword = uniqueKeyword.split(" ");
		containOnlyNumber = partsOfUniqueKeyword[0];
		number = determineTheNumber(containOnlyNumber);
		return number;
	}

	private static String removeIn(String uniqueKeyword) {
		uniqueKeyword = uniqueKeyword.replaceAll(" in ", "");
		return uniqueKeyword;
	}

	/**
	 * determine the number of day. 
	 * @param containNumber
	 * @return number of day in integer
	 */
	private static int determineTheNumber(String containNumber) {
		int numberOfDays;
		if(isNumeric(containNumber)) {
			numberOfDays = Integer.parseInt(containNumber);
		} else {
			numberOfDays = determineIntDaysFromWords(containNumber);
		}
		return numberOfDays;
	}

	/**
	 * remove all other except for number of day
	 * @param uniqueKeyword
	 * @return number of day in String
	 */
	private static String removeAllOtherThanNumberOfDay(String uniqueKeyword) {
		String numberOfDaysFromNow = uniqueKeyword.replaceAll(UNWANTED_ALPHA, EMPTY_STRING);
		//System.out.println("number: "+ numberOfDaysFromNow);
		return numberOfDaysFromNow;
	}

	/**
	 * detect DD Month in word/DD Month in word YYYY with space in between or no space in between
	 * DD could be in 2 or 2nd or 2nd of , 2 or 3th or 3th of, 4 or 4th or 4th of etc 
	 * @param userInput
	 * @return date in DD/MM/YYYY
	 */
	private static ArrayList<String> spotDateFormat2(String userInput, ArrayList<String> storageOfDate) {
		String dateOfTheTask = "";

		Pattern dateDetector = Pattern.compile(DATE_KEYWORD3);
		Matcher containDate = dateDetector.matcher(userInput);
		Matcher toGetIndex = dateDetector.matcher(userInput);

		while (containDate.find() && toGetIndex.find()) {
			int indexMatch = toGetIndex.start();
			dateOfTheTask = containDate.group();
			detectUserInput = detectUserInput.replaceAll(DATE_KEYWORD3, "");
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
			} else {
				storageOfDate.add(dateOfTheTask);
			}
			setThePosition(storageOfDate, indexMatch);
		}


		return storageOfDate;
	}



	/**
	 * get the number of month and year in the calendar and check if the day 
	 * entered exceed the max day in that month and year.
	 * @param dateOfTheTask
	 * @return true if the date entered is valid and false if it is invalid.
	 */
	private static boolean isDateValid2(String dateOfTheTask) {
		int maxDays;
		boolean isInvalidDate = false;

		Calendar calendar = Calendar.getInstance();
		int day = getDay(dateOfTheTask);
		int year = getYear(dateOfTheTask);
		calendar.set(Calendar.YEAR, year);
		int month = detectMonthByWord(dateOfTheTask) - 1;
		calendar.set(Calendar.MONTH, month);
		maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (exceedMaxDaysOnThatMonth(day, maxDays)) {
			isInvalidDate = true;
		}
		return isInvalidDate;
	}

	/**
	 * get the day from the date
	 * @param dateOfTheTask
	 * @return day
	 */
	private static int getDay(String dateOfTheTask) {
		int day = 0;
		Pattern numberPattern = Pattern.compile("\\d+");
		Matcher containDateFormat2 = numberPattern.matcher(
				dateOfTheTask);
		String numberText;

		while (containDateFormat2.find()) {
			numberText = containDateFormat2.group();
			if (numberText.length() <= 2) {
				day = Integer.parseInt(numberText);
			}
		}
		return day;
	}

	/**
	 * get the year from the date
	 * @param dateOfTheTask
	 * @return year
	 */
	private static int getYear(String dateOfTheTask) {   	
		int year = 0;
		Pattern numberPattern = Pattern.compile("\\d+");
		Matcher containDateFormat2 = numberPattern.matcher(
				dateOfTheTask);
		String numberText;

		while (containDateFormat2.find()) {
			numberText = containDateFormat2.group();
			if (numberText.length() == 4) {
				year = Integer.parseInt(numberText);
			}
		}
		return year;
	}

	/**
	 * change month in word to month in int
	 * @param dateOfTheTask
	 * @return month in int
	 */
	private static int detectMonthByWord(String dateOfTheTask) {
		int month = 0;
		dateOfTheTask = dateOfTheTask.replaceAll("\\d+", "");
		if (dateOfTheTask.contains("jan") || dateOfTheTask.contains("january")) {
			month = 1;
		} else if (dateOfTheTask.contains("feb") || dateOfTheTask.contains("february")) {
			month = 2;
		} else if (dateOfTheTask.contains("mar") || dateOfTheTask.contains("march")) {
			month = 3;
		} else if (dateOfTheTask.contains("apr") || dateOfTheTask.contains("april")) {
			month = 4;
		} else if (dateOfTheTask.contains("may")) {
			month = 5;
		} else if (dateOfTheTask.contains("jun") || dateOfTheTask.contains("june")) {
			month = 6;
		} else if (dateOfTheTask.contains("jul") || dateOfTheTask.contains("july")) {
			month = 7;
		} else if (dateOfTheTask.contains("aug") || dateOfTheTask.contains("august")) {
			month = 8;
		} else if (dateOfTheTask.contains("sep") || dateOfTheTask.contains("september")) {
			month = 9;
		} else if (dateOfTheTask.contains("oct") || dateOfTheTask.contains("october")) {
			month = 10;
		} else if (dateOfTheTask.contains("nov") || dateOfTheTask.contains("november")) {
			month = 11;
		} else if (dateOfTheTask.contains("dec") || dateOfTheTask.contains("december")) {
			month = 12;
		}
		//System.out.println("month: "+ month);
		return month;
	}

	/**
	 * detct DD/MM and DD/MM/YYYY
	 * @param userInput
	 * @return date in DD/MM/YYYY
	 */
	private static ArrayList<String> spotDateFormat1(String userInput, ArrayList<String> storageOfDate) {
		String dateOfTheTask = "";
		String[] partsOfString;

		Pattern dateDetector = Pattern.compile(DATE_KEYWORD1);
		Matcher containDate = dateDetector.matcher(userInput);
		Matcher toGetIndex = dateDetector.matcher(userInput);

		while (containDate.find() && toGetIndex.find()) {
			int indexMatch = toGetIndex.start();
			Calendar calendar = Calendar.getInstance();
			dateOfTheTask = containDate.group();
			detectUserInput = detectUserInput.replaceAll(DATE_KEYWORD1, "");

			dateOfTheTask = dateOfTheTask.replaceAll("on |from |at |to ", "");
			partsOfString = splitTheStringIntoPart(dateOfTheTask);
			//System.out.println("partOfString " +partsOfString[0]);
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
			if(!isDateValid1(dateOfTheTask)){
				storageOfDate.add(dateOfTheTask);
			}

			//System.out.println("1. date : "+ dateOfTheTask);
			setThePosition(storageOfDate, indexMatch);
		}
		return storageOfDate;
	}

	/**
	 * check if the date is valid if the year and the month have this day
	 * For example feb only have max 29 days
	 * @param dateOfTheTask
	 * @return true if the date is valid, false is it is not valid
	 */
	private static boolean isDateValid1(String dateOfTheTask) {
		boolean isInvalidDate = false;
		int day, maxDays, month;
		String[] partsOfString;

		Calendar calendar = Calendar.getInstance();
		partsOfString = splitTheStringIntoPart(dateOfTheTask);
		month = extractMonthByNumber(partsOfString) - 1;	
		if (ifMonthValid(month)) {
			// System.out.println("month: "+month);
			day = extractDayAndYear(partsOfString, calendar);
			calendar.set(Calendar.MONTH, month);
			maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			//System.out.println("dya: "+day+ " max: "+maxDays);
			if (exceedMaxDaysOnThatMonth(day, maxDays)) {
				isInvalidDate = true;
			}
		}
		return isInvalidDate;
	}

	/**
	 * check if user type the month wrongly more than the 12th month.
	 * @param month
	 * @return true if month is valid else false if month is invalid
	 */
	private static boolean ifMonthValid(int month) {
		return month >= 0 || month <= 11;
	}

	/**
	 * get the day and set the calendar to the right yaer
	 * @param partsOfString
	 * @param calendar
	 * @return the day
	 */
	private static int extractDayAndYear(String[] partsOfString,
			Calendar calendar) {
		int year = 0, day = 0;
		if (isEndStringContainYear(partsOfString)) {
			year = Integer.parseInt(partsOfString[2]);
			day = Integer.parseInt(partsOfString[0]);
			calendar.set(Calendar.YEAR, year);
		} else if (isFrontStringContainYear(partsOfString)) {
			year = Integer.parseInt(partsOfString[0]);
			day = Integer.parseInt(partsOfString[2]);
			calendar.set(Calendar.YEAR, year);
		} else {
			day = Integer.parseInt(partsOfString[0]);
		}
		//System.out.println("year: "+year);
		return day;
	}

	/**
	 * check if the day detected is more than the max day in that month
	 * @param day
	 * @param maxDays
	 * @return true if it exceed, false if it does not
	 */
	private static boolean exceedMaxDaysOnThatMonth(int day, int maxDays) {
		return day > maxDays;
	}

	private static boolean isFrontStringContainYear(String[] partsOfString) {
		return partsOfString.length == 3 && partsOfString[0].length() == 4;
	}

	private static boolean isEndStringContainYear(String[] partsOfString) {
		return partsOfString.length == 3 && partsOfString[2].length() == 4;
	}

	private static int extractMonthByNumber(String[] partsOfString) {
		int month;
		//System.out.println("length: "+partsOfString.length);
		month = Integer.parseInt(partsOfString[1]);
		return month;
	}

	/**
	 * split the date DD/MM/YYYY to day, month and year in the array of String
	 * @param dateOfTheTask
	 * @return day, month and year in string array.
	 */
	private static String[] splitTheStringIntoPart(String dateOfTheTask) {
		String[] partsOfString = null;
		if (dateOfTheTask.contains("/")) {
			partsOfString = dateOfTheTask.split("(/)");
		} else if (dateOfTheTask.contains(".")) {
			partsOfString = dateOfTheTask.split("(\\.)");
		}

		return partsOfString;
	}

}

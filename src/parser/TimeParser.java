package parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class TimeParser {
	private static final String TIME_KEYWORD_1 = "(((\\d+[.:](\\d+|)|\\d+)(-| to | - )(\\d+[.:](\\d+|)|\\d+)(\\s|)(am|pm)))";
	private static final String TIME_KEYWORD_2 = "(start at \\b(on |at |from |to |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b for \\d+ hour(\\s|))";
	private static final String TIME_KEYWORD_6 = "\\b(on |at |from |to |)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
	private static final String TIME_KEYWORD_4 = "\\b(on |at |from |to |)noon | (on |at |from |to |)midnight";
	private static final String TIME_KEYWORD_3 = "((from |to |)(before midnight|before noon))";
	private static final String TIME_KEYWORD_5 = "((from |to |)(\\d+[.:](\\d+|)|\\d+)( in morning| in afternoon| in night))";
	private static final String TO_BE_REMOVED_KEYWORD = "(\\s|-|to|at|from|noon|midnight|before midnight|before noon"
			+ "in afternoon|in night|in morning)";
	private static final String INVALID_TIME = "Time entered is invalid";
	private static final int TIME_FORMAT_1 = 1;
	private static final int TIME_FORMAT_2 = 2;
	private static final int TIME_FORMAT_3 = 3;
	private static final int TIME_FORMAT_4 = 4;
	private static final int TIME_FORMAT_5 = 5;
	private static final int TIME_FORMAT_6 = 6;
	private static int index;

	private static String detectUserInput;
	public static ArrayList<String> extractTime(String userInput) {
		ArrayList<String> storageOfTime = new ArrayList<String>();
		TimeParser.detectUserInput = userInput;
		for (int i = 1; i <= 6; i++) {
			storageOfTime = goThroughTimeFormat(i, storageOfTime, userInput);
		}
		/*
		if (!userInput.contains("due") && !userInput.contains("by")) {
			String hourTimeInString;
			if (storageOfTime.size() == 1) {
				int hourTime =  get1stNumber(storageOfTime.get(0)); 
				hourTime = hourTime + 1;
				if (hourTime < 10) {
					hourTimeInString = "0" + hourTime;
				} else {
					hourTimeInString = "" + hourTime;
				}
				String minTime = get2ndNumber(storageOfTime.get(0));
				String endTime = hourTimeInString +":" + minTime;
				storageOfTime.add(endTime);
			} 
		}	
		 */
		System.out.println("time: "+ storageOfTime);
		return storageOfTime;
	}

	private static ArrayList<String> goThroughTimeFormat(int timeFormat, 
			ArrayList<String> storageOfTime, String userInput) {
		if (timeFormat == TIME_FORMAT_1) {
			storageOfTime = detectUsingFormat1(storageOfTime);
		} else if (timeFormat == TIME_FORMAT_2) {
			storageOfTime = detectUsingFormat2(storageOfTime);
		} else if (timeFormat == TIME_FORMAT_3) {
			storageOfTime = detectUsingFormat3(storageOfTime, userInput);
		} else if (timeFormat == TIME_FORMAT_4) {
			storageOfTime = detectUsingFormat4(storageOfTime, userInput);
		} else if (timeFormat == TIME_FORMAT_5) {
			storageOfTime = detectUsingFormat5(storageOfTime, userInput);
		} else if (timeFormat == TIME_FORMAT_6) {
			storageOfTime = detectUsingFormat6(storageOfTime, userInput);
		}
		return storageOfTime;
	}

	/**
	 * detect the start at __ am/pm for __ hour
	 * @param storageOfTime
	 * @return arraylist of time.
	 */
	private static ArrayList<String> detectUsingFormat2(
			ArrayList<String> storageOfTime) {
		ArrayList<String> tempStorage = new ArrayList<String>();
		String hourTimeInString;
		Pattern timeDetector = Pattern.compile(TIME_KEYWORD_2);
		Matcher matchedWithTime = timeDetector.matcher(detectUserInput);

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();

			detectUserInput = detectUserInput.replaceAll(time, "");
			tempStorage = detectDigit(time);
			String startTime = changeToHourFormat(tempStorage.get(0));

			int numberOfHours = Integer.parseInt(tempStorage.get(1));

			int hourTime =  get1stNumber(startTime); 
			hourTime = hourTime + numberOfHours;
			if (hourTime < 10) {
				hourTimeInString = "0" + hourTime;
			} else {
				hourTimeInString = "" + hourTime;
			}
			String minTime = get2ndNumber(startTime);
			time = hourTimeInString +":" + minTime;

			assert checkValid24HourTime(time) == true;

			storageOfTime.add(startTime);
			storageOfTime.add(time);

			//	index.add(matchedForIndex.start());
		}
		return storageOfTime;
	}

	/**
	 * detect all of the ___pm/am and the digit represent the number of hours in the time
	 * @param time
	 * @return arrayList of digit.
	 */
	private static ArrayList<String> detectDigit(String time) {
		String digit = "(\\d+)";
		String digit1 = "(\\d+[.:](\\d+|)|\\d+)((\\s|)(am|pm))";
		ArrayList<String> tempStorageOfTime = new ArrayList<String>();


		Pattern timeDetector = Pattern.compile(digit1);
		Matcher matchedWithTime = timeDetector.matcher(time);

		while (matchedWithTime.find()) {
			time = time.replaceAll(digit1, "");
			tempStorageOfTime.add(matchedWithTime.group());
		}

		Pattern timeDetector1 = Pattern.compile(digit);
		Matcher matchedWithTime1 = timeDetector1.matcher(time);

		while (matchedWithTime1.find()) {
			tempStorageOfTime.add(matchedWithTime1.group());
		}

		return tempStorageOfTime;
	}

	/**
	 * detect the time like 6 in morning or 6 in afternoon or 6 in night
	 * @param storageOfTime
	 * @return time in HH:MM format
	 */
	private static ArrayList<String> detectUsingFormat5(
			ArrayList<String> storageOfTime, String userInput) {
		Pattern timeDetector = Pattern.compile(TIME_KEYWORD_5);
		Matcher matchedWithTime = timeDetector.matcher(detectUserInput);
		Matcher matchedForIndex = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();
			detectUserInput = detectUserInput.replaceAll(time, "");
			if (matchedForIndex.find()) {
				int indexNext = matchedForIndex.start();

				if (time.contains("in morning")) {
					setThePositionForTime(storageOfTime, time + "am", indexNext);
				} else if (time.contains("in afternoon") || time.contains("in night")) {
					setThePositionForTime(storageOfTime, time + "pm", indexNext);
				}

				assert checkValid24HourTime(time) == true;
			}
		}
		return storageOfTime;
	}

	/**
	 * detect start time and end time. 
	 * detect HH:MM/12hour format pm/am/none to/- HH:MM/12hour format pm/am
	 * example 12:30 - 1pm/12pm to 1:30pm
	 * @param storageOfTime
	 * @return arrayList containing all of the time.
	 */
	private static ArrayList<String> detectUsingFormat1(ArrayList<String> storageOfTime) {
		Pattern timeDetector = Pattern.compile(TIME_KEYWORD_1);
		Matcher matchedWithTime = timeDetector.matcher(detectUserInput);

		String[] timeList;
		String toBeAdded = "";

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();
			detectUserInput = detectUserInput.replaceAll(time, "");
			timeList = time.split("-|to");

			//System.out.println("1time: "+time);
			timeList[1] = changeToHourFormat(timeList[1]);

			String amTime1 = changeToHourFormat(timeList[0]+"am");
			String pmTime1 = changeToHourFormat(timeList[0]+"pm");
			System.out.println("timeList[0] "+ pmTime1);
			int amTime1stNum = get1stNumber(amTime1);
			int pmTime1stNum = get1stNumber(pmTime1);
			int time1stNum = get1stNumber(timeList[1]);

			toBeAdded = detectWhichOneIsRight(toBeAdded, amTime1, pmTime1,
					amTime1stNum, pmTime1stNum, time1stNum);

			assert checkValid24HourTime(timeList[1]) == true;
			assert checkValid24HourTime(toBeAdded) == true;

			storageOfTime.add(toBeAdded);	
			storageOfTime.add(timeList[1]);
		}
		return storageOfTime;
	}

	/**
	 * detect the start time when pm/am is not added right next to start time. 
	 * @param toBeAdded
	 * @param amTime1
	 * @param pmTime1
	 * @param amTime1stNum
	 * @param pmTime1stNum
	 * @param time1stNum
	 * @return the right timing when switch to hour format when am or pm is not on the back of the time.
	 */
	private static String detectWhichOneIsRight(String toBeAdded, String amTime1,
			String pmTime1, int amTime1stNum, int pmTime1stNum, int time1stNum) {
		if (amTime1stNum < time1stNum && pmTime1stNum < time1stNum) {
			toBeAdded = whenBothLessThan(amTime1, pmTime1, amTime1stNum,
					pmTime1stNum);
		} else if (amTime1stNum < time1stNum) {
			toBeAdded = amTime1;
		} else if (pmTime1stNum < time1stNum) {
			toBeAdded = pmTime1;
		} else if (amTime1stNum == pmTime1stNum) {
			toBeAdded = pmTime1;
		}
		return toBeAdded;
	}

	/**when both start time hour format is less than the end time.
	 * 
	 * @param amTime1
	 * @param pmTime1
	 * @param amTime1stNum
	 * @param pmTime1stNum
	 * @return the right start time when the am/pm is not input. 
	 */
	private static String whenBothLessThan(String amTime1, String pmTime1,
			int amTime1stNum, int pmTime1stNum) {
		String toBeAdded;
		if (amTime1stNum < pmTime1stNum) {
			toBeAdded = pmTime1;
		} else {
			toBeAdded = amTime1;
		}
		return toBeAdded;
	}

	/**
	 * get the HH of the time in hour format(HH:MM)
	 * @param pmTime1
	 * @return HH
	 */
	private static int get1stNumber(String pmTime1) {
		int index = getIndex(pmTime1);
		int partOfString1 = Integer.parseInt(pmTime1.substring(0, index));
		return partOfString1;
	}

	/**
	 * get the MM of the time in hour format(HH:MM)
	 * @param pmTime1
	 * @return HH
	 */
	private static String get2ndNumber(String pmTime1) {
		int index = getIndex(pmTime1);
		String partOfString2 = pmTime1.substring(index + 1);
		return partOfString2;
	}

	private static boolean checkValid24HourTime(String time) {
		time = removePMOrAm(time);
		boolean validTime = false;
		if (time.length() > 2) {
			int index = getIndex(time);
			int partOfString1 = Integer.parseInt(time.substring(0, index));
			int partOfString2 = Integer.parseInt(time.substring(index + 1));
			if (partOfString1 < 24 && partOfString1 >= 0
					&& partOfString2 >= 0 && partOfString2 <=59) {
				validTime = true;
			}
		}
		return validTime;
	}

	private static String removePMOrAm(String time) {
		time  = time.replaceAll("\\s+|pm|am", "");
		return time;
	}

	/**
	 * detect HH:MM/HH with pm and am behind.
	 * @param storageOfTime
	 * @return storage of time containing the time detected.
	 */
	private static ArrayList<String> detectUsingFormat6(ArrayList<String> storageOfTime, String userInput) {
		Pattern timeDetector = 
				Pattern.compile(TIME_KEYWORD_6);
		Matcher matchedWithTime = timeDetector.matcher(detectUserInput);
		Matcher matchedForIndex = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();
			//check detection:
			//System.out.println("time: "+time);
				detectUserInput = detectUserInput.replaceAll(time, "");
				if (matchedForIndex.find()) {
					int indexNext = matchedForIndex.start();
					setThePositionForTime(storageOfTime, time, indexNext);
				//	System.out.println("6. Index: " + indexNext);
				//System.out.println("Index: ");
			}
		}

		return storageOfTime;

	}

	private static void setThePositionForTime(ArrayList<String> storageOfTime,
			String time, int indexNext) {
		if (storageOfTime.size() == 1 &&   indexNext < index) {
			String temp = storageOfTime.get(0);
			time = removeUnwantedParts(time);
			time = changeToHourFormat(time);
			time = removePMOrAm(time);
			storageOfTime.set(0, time);
			storageOfTime.add(temp);
		} else {
			time = removeUnwantedParts(time);
			time = changeToHourFormat(time);
			time = removePMOrAm(time);
			storageOfTime.add(time);
		}
		index = indexNext;
	}
	/**
	 * detect noon, midnight
	 * @param storageOfTime
	 * @return storage of time containing the time detected.
	 */
	private static ArrayList<String> detectUsingFormat4(ArrayList<String> storageOfTime, String userInput) {
		Pattern timeDetector = 
				Pattern.compile(TIME_KEYWORD_4);
		Matcher matchedWithTime = timeDetector.matcher(detectUserInput);
		Matcher matchedForIndex = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();

			if (matchedForIndex.find()) {
				int indexNext = matchedForIndex.start();
				//System.out.println("index: "+indexNext);
				if (time.contains("noon")) {
					setThePositionForTime(storageOfTime, time + "12:00 pm", indexNext);
				} else if (time.contains("midnight")) {
					setThePositionForTime(storageOfTime, time + "00:00 am", indexNext);
				}
			}

			detectUserInput = detectUserInput.replaceAll(TIME_KEYWORD_4, "");
		}  
		//System.out.println("2.timeDe: "+storageOfTime.get(0));
		return storageOfTime;
	}

	/**
	 * detect before noon and before midnight 
	 * @param storageOfTime
	 * @return the time in hour format.
	 */
	private static ArrayList<String> detectUsingFormat3(ArrayList<String> storageOfTime, String userInput) {
		Pattern timeDetector = 
				Pattern.compile(TIME_KEYWORD_3);
		Matcher matchedWithTime = timeDetector.matcher(detectUserInput);
		Matcher matchedForIndex = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			detectUserInput = detectUserInput.replaceAll(TIME_KEYWORD_3, "");
			String time = matchedWithTime.group();
			if (matchedForIndex.find()) {
				int indexNext = matchedForIndex.start();
				if (time.contains("noon")) {
					setThePositionForTime(storageOfTime, time + "11:59 am", indexNext);
				} else if (time.contains("midnight")) {
					setThePositionForTime(storageOfTime, time + "23:59 pm", indexNext);
					//storageOfTime.add("23:59");
				}
				index = indexNext;
			} 
		}
		//System.out.println("2.timeDe: "+storageOfTime.get(0));
		return storageOfTime;
	}

	private static boolean checkValid12HourTime(String time) {
		boolean validTime = false;
		String amOrPM = getAmOrPm(time);
		time = removePMOrAm(time);

		if(time.length() > 2){
			int index = getIndex(time);
			int partOfString1 = Integer.parseInt(time.substring(0, index));
			int partOfString2 = Integer.parseInt(time.substring(index + 1));
			if (partOfString1 < 24 && (partOfString1 > 0 || (amOrPM.equals("am") && partOfString1 == 0))
					&& partOfString2 >= 0 && partOfString2 <=59) {
				validTime = true;
			}
		}
		else{
			int partOfString = Integer.parseInt(time);
			if (partOfString < 13 && partOfString > 0) {
				validTime = true;
			}		
		}
		return validTime;
	}

	/**
	 * detect the time contain am or pm
	 * @param time
	 * @return am or pm depend which one is detected.
	 */
	private static String getAmOrPm(String time) {	
		String amOrPm = "";
		if (time.contains("am")) {
			amOrPm = "am";
		} else if (time.contains("pm")) {
			amOrPm = "pm";
		}
		return amOrPm;
	}

	/**
	 * change all of the time inputed to hour format(HH:MM)
	 * @param time
	 * @return time in hour format (HH:MM)
	 */
	private static String changeToHourFormat(String time) {
		if(time.contains("am")) {
			time = removePMOrAm(time);
			int index = getIndex(time);	
			if (time.length() > 2 && time.charAt(0) == '1' && time.charAt(1) == '2') {
				time = "00" + ":" + time.substring(index+1);
			} else if (time.length() == 2 && time.charAt(0) == '1' && time.charAt(1) == '2') {
				time = "00:00";
			} else if (time.length() <= 2) {
				time = time +":00";
			} else if (time.length() > 2) {
				time = time.substring(0, index) + ":" + time.substring(index+1);
			}
		} else if (time.contains("pm")) {
			time = removePMOrAm(time);
			if (! time.contains(":")) {
				time = switchToPMHour(time);
			} else if (time.contains(":")) {
				time = switchToPMHour(time);
			}		
		}
		return time;
	}

	/**
	 * get the index of the separation of the HH and MM which is either : or .
	 * @param time
	 * @return the index of : or . depend which is detect
	 */
	private static int getIndex(String time) {
		int index = time.indexOf(".");
		if (index == -1) {
			index = time.indexOf(":");
		}
		return index;
	}

	/**
	 * if pm is detected behind the time, switch it to hour format from hour at 13 onwards to 00
	 * @param time
	 * @return hour format for time in pm.
	 */
	private static String switchToPMHour(String time) {
		int timeHour = 13, timeNormal = 1, userHourTime;
		String minTime; 

		if(time.contains(":")){
			userHourTime = get1stNumber(time);
			minTime = get2ndNumber(time);
		} else {
			userHourTime = Integer.parseInt(time);
			minTime = "00";
		}

		while (timeHour != 24) {
			if (userHourTime == 12) {
				time = "12";
				break;
			} else if (userHourTime == timeNormal) {
				time = String.valueOf(timeHour);
				break;
			} 
			timeHour++;
			timeNormal++;
		}
		time = time + ":" + minTime;
		return time;		
	}

	/**
	 * remove the conjunction
	 * @param timeWithUnwantedPart
	 * @return time free from conjunction
	 */
	public static String removeUnwantedParts(String timeWithUnwantedPart) {
		String time;
		time  = timeWithUnwantedPart.replaceAll(TO_BE_REMOVED_KEYWORD, "");
		return time;
	}

}

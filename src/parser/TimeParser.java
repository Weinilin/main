package parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class TimeParser {
	private static final String TIME_KEYWORD_1 = "(((\\d+[.:](\\d+|)|\\d+)(-| to | - )(\\d+[.:](\\d+|)|\\d+)(\\s|)(am|pm)))";
	private static final String TIME_KEYWORD_2 = "\\b(on |at |from |to |- |-|)(\\d+[.:,]\\d+|\\d+)((\\s|)(am|pm))\\b";
	private static final String TIME_KEYWORD_3 = "\\b(on |at |from |to |)noon";
	private static final String TIME_KEYWORD_4 = "\\b(\\d+[:.]\\d+(\\s|)(-|to|))\\b";
	private static final String TO_BE_REMOVED_KEYWORD = "(am|pm|\\s|-|to|at|from)";
	private static final String INVALID_TIME = "Time entered is invalid";
	private static final int TIME_FORMAT_1 = 1;
	private static final int TIME_FORMAT_2 = 2;
	private static final int TIME_FORMAT_3 = 3;
	private static final int TIME_FORMAT_4 = 4;
	private static String userInput;


	public ArrayList<String> extractTime(String userInput) {
		ArrayList<String> storageOfTime = new ArrayList<String>();
		TimeParser.userInput = userInput;
		for(int i = 1; i <= 4; i++){
			storageOfTime = goThroughTimeFormat(i, storageOfTime);
			//	System.out.println("check main: " + storageOfTime.get(0));
		}

		//checkIfAllTimeInvalid(storageOfTime);
		return storageOfTime;
	}

	private void checkIfAllTimeInvalid(ArrayList<String> storageOfTime) {
		int numberOfTimeStored = storageOfTime.size();
		int numberOfInvalidMsg = countNumberOfInvalidMsg(storageOfTime);
		if(isAllTimeKeyedInvalid(numberOfTimeStored, numberOfInvalidMsg)){
			storageOfTime.add(INVALID_TIME);
		}
	}

	private boolean isAllTimeKeyedInvalid(int numberOfTimeStored,
			int numberOfInvalidMsg) {
		return numberOfInvalidMsg == numberOfTimeStored;
	}

	private int countNumberOfInvalidMsg(ArrayList<String> storageOfTime) {
		int numberOfInvalidMsg = 0;
		for(int j = 0; j < storageOfTime.size(); j++){
			if(storageOfTime.get(j).equals(INVALID_TIME)){
				numberOfInvalidMsg++;
				storageOfTime.remove(j);
			}
		}
		return numberOfInvalidMsg;
	}

	private ArrayList<String> goThroughTimeFormat(int timeFormat, 
			ArrayList<String> storageOfTime) {
		if(timeFormat == TIME_FORMAT_1){
			storageOfTime = detectUsingFormat1(storageOfTime);
		}
		else if(timeFormat == TIME_FORMAT_2){
			storageOfTime = detectUsingFormat2(storageOfTime);
		}
		else if(timeFormat == TIME_FORMAT_3){
			storageOfTime = detectUsingFormat3(storageOfTime);
		}
		else if(timeFormat == TIME_FORMAT_4){
			storageOfTime = detectUsingFormat4(storageOfTime);
		}
		return storageOfTime;
	}

	private ArrayList<String> detectUsingFormat1(ArrayList<String> storageOfTime) {
		Pattern timeDetector = Pattern.compile(TIME_KEYWORD_1);
		Matcher matchedWithTime = timeDetector.matcher(userInput);
		String[] timeList;
		String toBeAdded = "";

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();
			userInput = userInput.replaceAll(time, "");
			timeList = time.split("-|to");

			//System.out.println("1time: "+time);
			timeList[1] = changeToHourFormat(timeList[1]);
			storageOfTime = selectAndAddCorrectTime(storageOfTime, timeList[1]);
			String amTime1 = changeToHourFormat(timeList[0]+"am");
			String pmTime1 = changeToHourFormat(timeList[0]+"pm");
			if(!checkValid24HourTime(amTime1) && !checkValid24HourTime(pmTime1)){
				storageOfTime.add(INVALID_TIME);
			}
			int amTime1stNum = get1stNumber(amTime1);
			int pmTime1stNum = get1stNumber(pmTime1);
			int time1stNum = get1stNumber(timeList[1]);
			//System.out.println("1time: "+(amTime1stNum < time1stNum)  + (pmTime1stNum < time1stNum));
			//System.out.println("1time: "+amTime1stNum+" pm: "+pmTime1stNum+" time: "+time1stNum);

			toBeAdded = detectWhichOneIsRight(toBeAdded, amTime1, pmTime1,
					amTime1stNum, pmTime1stNum, time1stNum);
			storageOfTime.add(toBeAdded);

		}
		return storageOfTime;
	}

	private String detectWhichOneIsRight(String toBeAdded, String amTime1,
			String pmTime1, int amTime1stNum, int pmTime1stNum, int time1stNum) {
		if(amTime1stNum < time1stNum && pmTime1stNum < time1stNum){
			toBeAdded = whenBothLessThan(amTime1, pmTime1, amTime1stNum,
					pmTime1stNum);
		}
		else if(amTime1stNum < time1stNum){
			toBeAdded = amTime1;
		}
		else if(pmTime1stNum < time1stNum){
			toBeAdded = pmTime1;
		}
		else if(amTime1stNum == pmTime1stNum){
			toBeAdded = pmTime1;
		}
		return toBeAdded;
	}

	private String whenBothLessThan(String amTime1, String pmTime1,
			int amTime1stNum, int pmTime1stNum) {
		String toBeAdded;
		if(amTime1stNum < pmTime1stNum){
			toBeAdded = pmTime1;
		}
		else{
			toBeAdded = amTime1;
		}
		return toBeAdded;
	}

	private ArrayList<String> selectAndAddCorrectTime(ArrayList<String> storageOfTime, String timeList) {
		if(checkValid24HourTime(timeList)){
			storageOfTime.add(timeList); 

		}
		else{
			storageOfTime.add(INVALID_TIME);
		}
		return storageOfTime;
	}

	private int get1stNumber(String pmTime1) {
		int index = getIndex(pmTime1);
		int partOfString1 = Integer.parseInt(pmTime1.substring(0, index));
		return partOfString1;
	}

	private int spotIndexOfTimeWithAMPM(String[] timeList) {
		int index = -1;
		for(int i = 0; i < timeList.length; i++){
			if(timeList[i].contains("am") || timeList[i].contains("pm")){
				index = i;
			}
		}
		return index;
	}

	private ArrayList<String> detectUsingFormat4(ArrayList<String> storageOfTime) {
		Pattern timeDetector = Pattern.compile(TIME_KEYWORD_4);
		Matcher matchedWithTime = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();
			if(checkValid24HourTime(time)){
				userInput = userInput.replaceAll(time, "");
				time = removeUnwantedParts(time);
				//System.out.println("24Htime: "+time);
				int index = getIndex(time);
				time = time.substring(0, index) + ":" + time.substring(index+1);
				storageOfTime.add(time);
			} 
			else{
				storageOfTime.add(INVALID_TIME);
			}
		}

		return storageOfTime;
	}

	private boolean checkValid24HourTime(String time) {
		time = removeUnwantedParts(time);
		boolean validTime = false;
		if(time.length() > 2){
			int index = getIndex(time);
			int partOfString1 = Integer.parseInt(time.substring(0, index));
			int partOfString2 = Integer.parseInt(time.substring(index + 1));
			if(partOfString1 < 24 && partOfString1 >= 0
					&& partOfString2 >= 0 && partOfString2 <=59){
				validTime = true;
			}
		}
		return validTime;
	}

	private ArrayList<String> detectUsingFormat3(ArrayList<String> storageOfTime) {
		Pattern timeDetector = 
				Pattern.compile(TIME_KEYWORD_3);
		Matcher matchedWithTime = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			userInput = userInput.replaceAll(TIME_KEYWORD_3, "");
			storageOfTime.add("12:00");      
		}  
		//System.out.println("2.timeDe: "+storageOfTime.get(0));
		return storageOfTime;
	}

	private ArrayList<String> detectUsingFormat2(ArrayList<String> storageOfTime) {
		Pattern timeDetector = 
				Pattern.compile(TIME_KEYWORD_2);
		Matcher matchedWithTime = timeDetector.matcher(userInput);

		while (matchedWithTime.find()) {
			String time = matchedWithTime.group();
			if(checkValid12HourTime(time)){
				userInput = userInput.replaceAll(time, "");
				//System.out.println("1. beforeTime: "+time);
				time = changeToHourFormat(time);
				//System.out.println("1. afterTime: "+time);
				storageOfTime.add(time);
			} 
			else{
				storageOfTime.add(INVALID_TIME);
			}
		}

		return storageOfTime;

	}

	private boolean checkValid12HourTime(String time) {
		boolean validTime = false;
		String amOrPM = getAmOrPm(time);
		time = removeUnwantedParts(time);

		if(time.length() > 2){
			int index = getIndex(time);
			int partOfString1 = Integer.parseInt(time.substring(0, index));
			int partOfString2 = Integer.parseInt(time.substring(index + 1));
			if(partOfString1 < 24 && (partOfString1 > 0 || (amOrPM.equals("am") && partOfString1 == 0))
					&& partOfString2 >= 0 && partOfString2 <=59){
				validTime = true;
			}
		}
		else{
			int partOfString = Integer.parseInt(time);
			if(partOfString < 13 && partOfString > 0){
				validTime = true;
			}		
		}
		return validTime;
	}

	private String getAmOrPm(String time) {	
		String amOrPm = "";
		if(time.contains("am")){
			amOrPm = "am";
		}
		else if(time.contains("pm")){
			amOrPm = "pm";
		}
		return amOrPm;
	}

	private String changeToHourFormat(String time) {

		if(time.contains("am")){
			time = removeUnwantedParts(time);
			int index = getIndex(time);	
			if(time.length() > 2 && time.charAt(0) == '1' && time.charAt(1) == '2'){
				time = "00" + ":" + time.substring(index+1);
			}
			else if(time.length() == 2 && time.charAt(0) == '1' && time.charAt(1) == '2'){
				time = "00:00";
			}
			else if(time.length() <= 2){
				time = time +":00";
			}
			else if(time.length() > 2){
				time = time.substring(0, index) + ":" + time.substring(index+1);
			}
		}
		else if(time.contains("pm")){
			time = removeUnwantedParts(time);
			if(time.length() <= 2 && time.length() > 0){
				time = switchToPMHour(time) + ":00";
			}
			else if(time.length() > 2){
				int index = getIndex(time);		
				time = switchToPMHour(time.substring(0, index)) + ":" + time.substring(index+1);
			}		
		}
		return time;
	}

	private int getIndex(String time) {
		int index = time.indexOf(".");
		if(index == -1){
			index = time.indexOf(":");
		}
		return index;
	}

	private String switchToPMHour(String time) {
		int timeHour = 13, timeNormal = 1;

		while(timeHour != 24){
			if(time.length() == 2 && time.charAt(0) == '1' && time.charAt(1) == '2'){
				time = "12";
				break;
			}
			if(time.charAt(0) == (char)(timeNormal + 48)){
				time = String.valueOf(timeHour);
				break;
			}
			timeHour++;
			timeNormal++;
		}
		return time;		
	}

	public String removeUnwantedParts(String timeWithUnwantedPart) {
		String time;
		time  = timeWithUnwantedPart.replaceAll(TO_BE_REMOVED_KEYWORD, "");
		return time;
	}
}

package src.parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
	
	public TimeParser(){
		
	}
	
	public String extractTime(String userInput){
		Pattern timeDetector = 
				Pattern.compile("(\\b\\d+[.:,-]\\d+|\\d+)(am|pm| am| pm)\\b");
		Matcher matchedWithTime = timeDetector.matcher(listOfTaskData);

       storageOfTimePerTasks = new ArrayList<String>();

		
        while (matchedWithTime.find()) {
            storageOfTimePerTasks.add(matchedWithTime.group());      
        }
	}
}

package parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class IndexParser {

	private static final String NO_INDEX_IS_ENTER_MSG = "no index is enter!";
	private static final int NO_INDEX_ENTERED_NUMBER = -1;

	public IndexParser(){

	}

	/**
	 * 
	 * 
	 * @param userInput - input from user
	 * @return positive index if number is detected, -1 if no number detected
	 */
	public int extractIndex(String userInput){
		int index;
		String number = removeEverythingExceptIndex(userInput);
		if(!number.equals(NO_INDEX_IS_ENTER_MSG)){
			index = Integer.parseInt(number);
		}
		else{
			index = NO_INDEX_ENTERED_NUMBER;
		}
		return index;
	}

	private String removeEverythingExceptIndex(String userInput) {
		String number = ""; 
		Pattern indexDetector = Pattern.compile("\\d+");
		Matcher indexMatch = indexDetector.matcher(userInput);
		if(indexMatch.find()){
			number = indexMatch.group();
		}
		else{
			number = NO_INDEX_IS_ENTER_MSG;
		}
		return number;
	}

}

package parser;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexParser {
	
	public IndexParser() {

	}

	/**
	 * Extract the index.
	 * @param userInput - input from user
	 * @return positive index if number is detected, throw exception error if no digit is 
	 * entered by the user. Since the program could not execute the command without the
	 * index (a digit) being detect.
	 */
	public int extractIndex(String userInput) {
		int index = 0;
		try {
			String number = getIndex(userInput);
			index = Integer.parseInt(number);
		} catch (NoSuchElementException e) {
			System.err.println("NoDigitException: " + e.getMessage());
		}
		return index;
	}

	/**
	 * Use to get the index from a user input containing a lot of other text.
	 * @param userInput
	 * @return the index key by the user or the exception error when no digit is detect
	 * @throws NoSuchElementException ---> when no digit is detect, the user never key in the 
	 * index and the command could not be execute without the index.
	 */	
	private String getIndex(String userInput) throws NoSuchElementException {
		String number = ""; 
		Pattern indexDetector = Pattern.compile("\\d+");
		Matcher indexMatch = indexDetector.matcher(userInput);
		if (indexMatch.find()) {
			number = indexMatch.group();
		} else {
			throw new NoSuchElementException("Index is not entered!!!"); 
		}
		return number;
	}
}

package parser;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	public int getIndex(String userInput) {
		int index = 0;
		Logger logger = Logger.getLogger("IndexParser");
		try {
			logger.log(Level.INFO, "going to start processing");
			String number = getNumber(userInput);
			index = Integer.parseInt(number);
		} catch (Exception e) {
			System.err.println("NoDigitException: " + e.getMessage());
			logger.log(Level.WARNING, "processing error", e);
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
	private String getNumber(String userInput) throws Exception {
		String number = ""; 
		Pattern indexDetector = Pattern.compile("\\d+");
		Matcher indexMatch = indexDetector.matcher(userInput);
		if (indexMatch.find()) {
			number = indexMatch.group();
		} else {
			throw new Exception ("Index is not entered!!!"); 
		}
		return number;
	}
}

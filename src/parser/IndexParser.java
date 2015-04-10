package parser;

import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * get the 1st digit for every user input
 * 
 * @author A0112823R
 *
 */
public class IndexParser {
    private int index;
    private String input;

    public IndexParser(String userInput) {
        input = userInput;
    }

    /**
     * set the index
     * 
     * @param detectedIndex
     */
    private void setIndex(int detectedIndex) {
        index = detectedIndex;

    }

    /**
     * Extract the index.
     * 
     * @return positive index if number is detected, throw exception error if no
     *         digit is entered by the user. Since the program could not execute
     *         the command without the index (a digit) being detect.
     * @throws NumberFormatException
     *             when user enters a non-integer string
     */
    public int getIndex() throws NumberFormatException {
        int detectedIndex = 0;
        Logger logger = Logger.getLogger("IndexParser");
       
        try {
            logger.log(Level.INFO, "going to start processing for detecting of index");
            String number = getNumber(input);
            detectedIndex = Integer.parseInt(number);
        
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "No digit of input detected!");
            throw new NumberFormatException(input + " is not a number!\n");
        }
        
        setIndex(detectedIndex);
        
        return index;
    }

    /**
     * Use to get the index from a user input containing a lot of other text.
     * 
     * @param userInput
     * @return the index key by the user or the exception error when no digit is
     *         detect
     * @throws NumberFormatException
     *             ---> when no digit is detect, the user never key in the index
     *             and the command could not be execute without the index.
     */
    private String getNumber(String userInput) throws NumberFormatException {
       
        String number = "";
        Pattern indexDetector = Pattern.compile("\\d+");
        Matcher indexMatch = indexDetector.matcher(userInput);
        
        if (indexMatch.find()) {
            number = indexMatch.group();
        } else {
            throw new NumberFormatException("Index is not entered!!!");
        }
        
        return number;
    }
}

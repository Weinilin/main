package parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EscapedTextParser {
    private String textEscaped = "";

    public EscapedTextParser(String userInput) {
        textEscaped = removeThoseHashTag(userInput);
    }

    /**
     * get the escaped text ~....~
     * 
     * @return the input left after removing all the time detected
     */
    public String getEscapedText() {
        return textEscaped;
    }

    /**
     * indication of ~~ means that user want it to be in description ~~ means
     * escaped char
     * 
     * @param userInput
     * @return user input without ~
     */
    private String removeThoseHashTag(String userInput) {
        ArrayList<Integer> hashTagIndex = new ArrayList<Integer>();
        Pattern hashTagDetector = Pattern.compile("~");
        Matcher containHashTag = hashTagDetector.matcher(userInput);

        while (containHashTag.find()) {
            hashTagIndex.add(containHashTag.start());

        }
        
        if (hashTagIndex.size() >= 2) {
            for (int i = 0; i < hashTagIndex.size(); i += 2) {
                textEscaped = textEscaped
                        + " "
                        + userInput.substring(hashTagIndex.get(i) + 1,
                                hashTagIndex.get(i + 1));
            }
        }
        
        textEscaped = textEscaped.trim();
        return textEscaped;
    }
}

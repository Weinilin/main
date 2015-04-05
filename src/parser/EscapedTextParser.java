package parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * get the escaped test which enclose by ~
 * @author WeiLin
 *
 */
public class EscapedTextParser {
    private String textEscaped = "";

    public EscapedTextParser(String userInput) {
        ArrayList<String> hashTag = storeEscapedTexts(userInput);
        
        for (int i = 0; i < hashTag.size(); i++) {
            textEscaped = textEscaped + " " + hashTag.get(i);
        }
      
        textEscaped = removeThoseHashTag(textEscaped);
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
     * remove ~
     * 
     * @param userInput
     * @return escaped text without ~
     */
    private String removeThoseHashTag(String textEscaped) {
       
        textEscaped =  textEscaped.replaceAll("\\~", "");
        textEscaped = textEscaped.trim();
        return textEscaped;
    }

    /**
     * store all of the escaped text into a arrayList
     * @param userInput
     * @return
     */
    private ArrayList<String> storeEscapedTexts(String userInput) {
        ArrayList<Integer> hashTagIndex = new ArrayList<Integer>();
        ArrayList<String> hashTag = new ArrayList<String>();
        Pattern hashTagDetector = Pattern.compile("\\~");
        Matcher containHashTag = hashTagDetector.matcher(userInput);

        while (containHashTag.find()) {

            hashTagIndex.add(containHashTag.start());
            
            if (hashTagIndex.size() == 2) {
                hashTag.add(userInput.substring(hashTagIndex.get(0),
                        hashTagIndex.get(1) + 1));
                hashTagIndex.clear();
            }

        }
        return hashTag;
    }
}

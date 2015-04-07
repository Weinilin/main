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
    private ArrayList<String> escapedTextList  = new ArrayList<String>();

    public EscapedTextParser(String userInput) {
        escapedTextList = storeEscapedTexts(userInput);
        
        for (int i = 0; i < escapedTextList.size(); i++) {
            textEscaped = textEscaped + " " + escapedTextList.get(i);
        }
      
       // textEscaped = removeThoseHashTag(textEscaped);
       
    }

    /**
     * get the escaped text ~....~ + ~.....~ + etc
     * 
     * @return the input left after removing all the time detected
     */
    public String getEscapedText() {
        System.out.println("ININescapedText: "+textEscaped);
        return textEscaped;
    }

    /**
     * get the ArrayList of escaped text 
     * @return ArrayList of escaped text
     */
    public ArrayList<String> getListOfEscapedText(){
        return escapedTextList; 
        
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

package parser;

/**
 * get the decription for the user input
 * @author WeiLin
 *
 */
public class DescriptionParser {
    private String description;

    public DescriptionParser(String userInput) throws Exception {
        String partOfDescription;
     
        userInput = removeTheExtraSpace(userInput);
        String lowerCaseInput = switchAllToLowerCase(userInput);

        partOfDescription = getPartOfDescription(userInput);
        
        String escapedText = getEscapedText(userInput);

        String description = piecePartsOfDescription(escapedText,
                partOfDescription, lowerCaseInput, userInput);

        description = removeTheExtraSpace(description);
        setDescription(description);
    }

    /**
     * get the left over user input after extracting out time and date
     * @param 
     * @return left over user input after extracting out time and date
     * @throws Exception 
     */
    private String getPartOfDescription(String userInput) throws Exception {
        String partOfDescription;
        DateTimeParser dateTimeParser = new DateTimeParser(userInput);
        partOfDescription = dateTimeParser.getUserInputLeft();
        partOfDescription = partOfDescription.trim();
        partOfDescription = removeTheExtraSpace(partOfDescription);
        return partOfDescription;
    }

    /**
     * get the escape text 
     * @param userInput 
     * @return escape text from ~~
     */
    private String getEscapedText(String userInput) {
        EscapedTextParser escapedTextParser = new EscapedTextParser(userInput);
        String escapedText = escapedTextParser.getEscapedText();
        escapedText = escapedText.toLowerCase();
        return escapedText;
    }

    
    private String removeTheExtraSpace(String partOfDescription) {
        partOfDescription = partOfDescription.trim();
        partOfDescription = partOfDescription.replaceAll("\\s+", " ");
        return partOfDescription;
    }

    /**
     * piece the left over user input after extracting out time and date, the escape text together
     * And remove the conjunction that is in front of time and date.
     * @param escapedText
     * @param partOfDescription
     * @param lowerCaseInput
     * @param userInput
     * @return description
     */
    private String piecePartsOfDescription(String escapedText,
            String partOfDescription, String lowerCaseInput, String userInput) {
        String description = "";
        int indexDescription = 0, indexEscapedText = 0;

        String[] eachWordInDescription = splitStringByWhitespace(partOfDescription);
        String[] eachEscapedText = splitStringByWhitespace(escapedText);
        String[] eachWordLowerCaseInput = splitStringByWhitespace(lowerCaseInput);
        String[] eachWordUserInput = splitStringByWhitespace(userInput);

        for (int i = 0; i < eachWordLowerCaseInput.length; i++) {
           
            indexDescription = byPassConjunction(indexDescription, indexEscapedText, eachWordInDescription, eachEscapedText,
                    eachWordLowerCaseInput, i);

            if (indexDescription < eachWordInDescription.length
                    && eachWordLowerCaseInput[i]
                            .equals(eachWordInDescription[indexDescription])) {
                
                description = description + " " + eachWordUserInput[i];
                
                indexDescription++;

            } else if (indexEscapedText < eachEscapedText.length
                    && eachWordLowerCaseInput[i].equals(eachEscapedText[indexEscapedText])) {
                
                eachWordUserInput[i] = eachWordUserInput[i].replaceAll("\\~",
                        "");
                description = description + " " + eachWordUserInput[i];
                          
                indexEscapedText++;              
            }
            

        }
        return description;
    }

    private String[] splitStringByWhitespace(String partOfDescription) {
        String[] eachWordInDescription = partOfDescription.split("\\s+");
        return eachWordInDescription;
    }

    /**
     * by pass the conjunction if it is link with date or time
     * 
     @param j
     *            : index of eachWordInDescription
     * @param k
     *            : index of eachEscapedText
      * @param eachWordInDescription
     *            : left over input after extracting time and date
     * @param eachEscapedText
     *            : escaped text detected
     * @param eachWordLowerCaseInput
     *            : user input in lower case
      * @param i
     *            : index of eachWordLowerCaseInput
     * @return
     */
    private int byPassConjunction(int j, int k, String[] eachWordInDescription,
            String[] eachEscapedText, String[] eachWordLowerCaseInput, int i) {

        if (j < eachWordInDescription.length
                && isConjunction(eachWordLowerCaseInput[i])
                && eachWordLowerCaseInput[i].equals(eachWordInDescription[j])) {

            if (isTheNextWordDate(j, k, eachWordInDescription, eachEscapedText,
                    eachWordLowerCaseInput, i)
                    || isNextWordEmpty(j, k, eachWordInDescription,
                            eachEscapedText, eachWordLowerCaseInput, i)) {
                j++;
            }
        }
        return j;
    }

    /**
     * check if the next word of the left over input empty and it is empty not because of escaped text
     * but is because of the time and date extracted out.
     @param indexOfDescription
     *            : index of eachWordInDescription
     * @param indexEscapedText
     *            : index of eachEscapedText
     * @param eachWordInDescription
     *            : left over input after extracting time and date
     * @param eachEscapedText
     *            : escaped text detected
     * @param eachWordLowerCaseInput
     *            : user input in lower case
     * @param indexUserInput
     *            : index of eachWordLowerCaseInput
     * @return true if the next word is empty otherwise false.
     */
    private boolean isNextWordEmpty(int indexOfDescription, int indexEscapedText,
            String[] eachWordInDescription, String[] eachEscapedText,
            String[] eachWordLowerCaseInput, int indexUserInput) {
        
        return indexOfDescription == eachWordInDescription.length - 1
                && indexUserInput < eachWordLowerCaseInput.length && (indexEscapedText < eachEscapedText.length
                && !eachWordLowerCaseInput[indexUserInput + 1]
                        .equals(eachEscapedText[indexEscapedText]) || indexEscapedText == eachEscapedText.length);
    }

    /**
     * check if the next word in the user input time or date If yes, the left
     * over next word in left over input will not be equal with the user input
     * next word and user input next word will not be equal to escaped text.
     * 
     * @param indexOfDescription
     *            : index of eachWordInDescription
     * @param indexEscapedText
     *            : index of eachEscapedText
     * @param eachWordInDescription
     *            : left over input after extracting time and date
     * @param eachEscapedText
     *            : escaped text detected
     * @param eachWordLowerCaseInput
     *            : user input in lower case
     * @param indexUserInput
     *            : index of eachWordLowerCaseInput
     * @return true if next word contain time and date otherwise false
     */
    private boolean isTheNextWordDate(int indexOfDescription, int indexEscapedText,
            String[] eachWordInDescription, String[] eachEscapedText,
            String[] eachWordLowerCaseInput, int indexUserInput) {

        return indexOfDescription + 1 < eachWordInDescription.length
                && indexUserInput + 1 < eachWordLowerCaseInput.length
                && !eachWordInDescription[indexOfDescription + 1]
                        .equals(eachWordLowerCaseInput[indexUserInput + 1])
                && (indexEscapedText < eachEscapedText.length
                        && !eachWordLowerCaseInput[indexUserInput + 1]
                                .equals(eachEscapedText[indexEscapedText]) || indexEscapedText == eachEscapedText.length);
    }

    /**
     * check if the text conjunction
     * 
     * @param string
     *            : word
     * @return true if it is conjunction otherwise false
     */
    private boolean isConjunction(String text) {
        return text.matches("\\b(@|due on|on|at|from|to|by|due)\\b");
    }

    /**
     * to prevent case sensitive, switch all to lower case
     * 
     * @param userInput
     * @return the user input all in lower case.
     */
    private String switchAllToLowerCase(String userInput) {
        userInput = userInput.toLowerCase();
        userInput = userInput.replaceAll("\\~", "");
        return userInput;
    }

    /**
     * set description
     * 
     * @param detectedDescription
     */
    private void setDescription(String detectedDescription) {
        description = detectedDescription;

    }

    /**
     * Get the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}

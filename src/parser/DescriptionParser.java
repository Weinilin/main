package parser;

/**
 * get the description for the user input
 * 
 * @author WeiLin
 *
 */
public class DescriptionParser {
    private String description;

    public DescriptionParser(String userInput, String userInputLeft)
            throws Exception {
        String partOfDescription;

        userInput = removeTheExtraSpace(userInput);

        // to match with partOfDescription since all is set to lowercase during
        // detection of time and date to prevent case sensitive
        String lowerCaseInput = switchAllToLowerCase(userInput);

        partOfDescription = removeTheExtraSpace(userInputLeft);

        String escapedText = getEscapedText(userInput);

        String description = piecePartsOfDescription(escapedText,
                partOfDescription, lowerCaseInput, userInput);

        description = removeTheExtraSpace(description);
        setDescription(description);
    }

    /**
     * get the escape text which is surround with ~ eg: ~2dec~
     * 
     * @param userInput
     * @return escape text from ~~
     */
    private String getEscapedText(String userInput) {
        EscapedTextParser escapedTextParser = new EscapedTextParser(userInput);
        String escapedText = escapedTextParser.getEscapedText();
        escapedText = removeTheExtraSpace(escapedText);
        escapedText = switchAllToLowerCase(escapedText);
        // System.out.println("escapedText: " + escapedText);
        return escapedText;
    }

    private String removeTheExtraSpace(String partOfDescription) {
        partOfDescription = partOfDescription.trim();
        partOfDescription = partOfDescription.replaceAll("\\s+", " ");
        return partOfDescription;
    }

    /**
     * piece the left over user input (after extracting out time and date), the
     * escape text together and remove the conjunction that is in front of time
     * and date.
     * 
     * @param escapedText
     *            : those surround with ~ eg: ~12 dec~
     * @param partOfDescription
     *            : after extracting time, date and escaped text
     * @param lowerCaseInput
     *            : to match with part of description
     * @param userInput
     *            : to be piece together if it is not date and time in the way
     *            user typed
     * @return description
     */
    private String piecePartsOfDescription(String escapedText,
            String partOfDescription, String lowerCaseInput, String userInput) {
        String description = "";
        int indexLeftOverInput = 0, indexEscapedText = 0;

        String[] eachWordInLeftOverInput = splitStringByWhitespace(partOfDescription);
        String[] eachEscapedText = splitStringByWhitespace(escapedText);
        String[] eachWordLowerCaseInput = splitStringByWhitespace(lowerCaseInput);
        String[] eachWordUserInput = splitStringByWhitespace(userInput);

        for (int i = 0; i < eachWordLowerCaseInput.length; i++) {
            // System.out.println("eachWordUserInput[i]: "
            // + eachWordLowerCaseInput[i]);
            boolean isByPassConjunction = isByPassConjunction(
                    indexLeftOverInput, indexEscapedText,
                    eachWordInLeftOverInput, eachEscapedText,
                    eachWordLowerCaseInput, i);

            if (isByPassConjunction) {
                indexLeftOverInput++;
                i++;
            }

            if (indexLeftOverInput < eachWordInLeftOverInput.length
                    && eachWordLowerCaseInput[i]
                            .equals(eachWordInLeftOverInput[indexLeftOverInput])) {

                description = description + " " + eachWordUserInput[i];

                // System.out.println("eachWordUserInput[i]: "
                // + eachWordLowerCaseInput[i]
                // + " eachWordInDescription[indexEscapedText]: "
                // + eachWordInLeftOverInput[indexLeftOverInput]
                // + " description: " + description);
                indexLeftOverInput++;

            } else if (indexEscapedText < eachEscapedText.length
                    && eachWordLowerCaseInput[i]
                            .equals(eachEscapedText[indexEscapedText])) {

                eachWordUserInput[i] = eachWordUserInput[i].replaceAll("\\~",
                        "");
                description = description + " " + eachWordUserInput[i];
                // System.out.println("eachWordUserInput[i]: "
                // + eachWordUserInput[i]
                // + " eachEscapedText[indexEscapedText]: "
                // + eachEscapedText[indexEscapedText] + " description: "
                // + description);

                indexEscapedText++;
            } else if (indexEscapedText < eachEscapedText.length
                    && indexLeftOverInput < eachWordInLeftOverInput.length) {

                if (isEscTextDescripInOneWord(indexLeftOverInput,
                        indexEscapedText, eachWordInLeftOverInput,
                        eachEscapedText, eachWordLowerCaseInput, i)) {

                    eachWordUserInput[i] = eachWordUserInput[i].replaceAll(
                            "\\~", "");

                    description = description + " " + eachWordUserInput[i];
                    indexEscapedText++;
                    indexLeftOverInput++;
                }

            }
        }
        return description;
    }

    /**
     * user could type !!!~12 dec~ or ~12dec~!!!! help to ensure that even for
     * this scenario, the it could still detect as a description.
     * 
     * @param indexLeftOverInput
     * @param indexEscapedText
     * @param eachWordInLeftOverInput
     * @param eachEscapedText
     * @param eachWordLowerCaseInput
     * @param i
     *            : index for each word in user input
     * @return true if it contain in !!!!~12 dec~ or ~12 dec~!!!! otherwise
     *         false
     */
    private boolean isEscTextDescripInOneWord(int indexLeftOverInput,
            int indexEscapedText, String[] eachWordInLeftOverInput,
            String[] eachEscapedText, String[] eachWordLowerCaseInput, int i) {

        return eachWordLowerCaseInput[i]
                .equals(eachWordInLeftOverInput[indexLeftOverInput]
                        + eachEscapedText[indexEscapedText])
                || eachWordLowerCaseInput[i]
                        .equals(eachEscapedText[indexEscapedText]
                                + eachWordInLeftOverInput[indexLeftOverInput]);
    }

    private String[] splitStringByWhitespace(String partOfDescription) {
        String[] eachWordInDescription = partOfDescription.split("\\s+");
        return eachWordInDescription;
    }

    /**
     * by pass the conjunction if it is link with date or time
     * 
     * @param indexLeftOverInput
     *            : index of eachWordInLeftOverInput
     * @param indexEscapedText
     *            : index of eachEscapedText
     * @param eachWordInLeftOverInput
     *            : left over input after extracting time and date
     * @param eachEscapedText
     *            : escaped text detected
     * @param eachWordLowerCaseInput
     *            : user input in lower case
     * @param i
     *            : index of eachWordLowerCaseInput
     * @return
     */
    private boolean isByPassConjunction(int indexLeftOverInput,
            int indexEscapedText, String[] eachWordInLeftOverInput,
            String[] eachEscapedText, String[] eachWordLowerCaseInput, int i) {

        boolean isByPassConjunction = false;
        if (indexLeftOverInput < eachWordInLeftOverInput.length
                && isConjunction(eachWordLowerCaseInput[i])
                && eachWordLowerCaseInput[i]
                        .equals(eachWordInLeftOverInput[indexLeftOverInput])) {

            if (isTheNextWordDate(indexLeftOverInput, indexEscapedText,
                    eachWordInLeftOverInput, eachEscapedText,
                    eachWordLowerCaseInput, i)
                    || isNextWordEmpty(indexLeftOverInput, indexEscapedText,
                            eachWordInLeftOverInput, eachEscapedText,
                            eachWordLowerCaseInput, i)) {
                isByPassConjunction = true;
            }
            // System.out.println("isTheNextWordDate: "+isTheNextWordDate(indexLeftOverInput,
            // indexEscapedText,
            // eachWordInLeftOverInput, eachEscapedText,
            // eachWordLowerCaseInput, i));
        }
        return isByPassConjunction;
    }

    /**
     * check if the next word of the left over input empty and it is empty not
     * because of escaped text but is because of the time and date extracted
     * out.
     * 
     * @param indexLeftOverInput
     *            : index of eachWordInLeftOverInput
     * @param indexEscapedText
     *            : index of eachEscapedText
     * @param eachWordInLeftOverInput
     *            : left over input after extracting time and date
     * @param eachEscapedText
     *            : escaped text detected
     * @param eachWordLowerCaseInput
     *            : user input in lower case
     * @param indexUserInput
     *            : index of eachWordLowerCaseInput
     * @return true if the next word is empty otherwise false.
     */
    private boolean isNextWordEmpty(int indexLeftOverInput,
            int indexEscapedText, String[] eachWordInLeftOverInput,
            String[] eachEscapedText, String[] eachWordLowerCaseInput,
            int indexUserInput) {

        return indexLeftOverInput == eachWordInLeftOverInput.length - 1
                && indexUserInput + 1 < eachWordLowerCaseInput.length
                && (indexEscapedText < eachEscapedText.length
                        && !eachWordLowerCaseInput[indexUserInput + 1]
                                .equals(eachEscapedText[indexEscapedText]) || indexEscapedText == eachEscapedText.length);
    }

    /**
     * check if the next word in the user input is time or date If yes, the next
     * word in left over input will not be equal to the next word in user input
     * and next word in user input will not be equal to escaped text.
     * 
     * @param indexLeftOverInput
     *            : index of eachWordInLeftOverInput
     * @param indexEscapedText
     *            : index of eachEscapedText
     * @param eachWordInLeftOverInput
     *            : left over input after extracting time and date
     * @param eachEscapedText
     *            : escaped text detected
     * @param eachWordLowerCaseInput
     *            : user input in lower case
     * @param indexUserInput
     *            : index of eachWordLowerCaseInput
     * @return true if next word contain time and date otherwise false
     */
    private boolean isTheNextWordDate(int indexLeftOverInput,
            int indexEscapedText, String[] eachWordInLeftOverInput,
            String[] eachEscapedText, String[] eachWordLowerCaseInput,
            int indexUserInput) {

        return indexLeftOverInput + 1 < eachWordInLeftOverInput.length

                && indexUserInput + 1 < eachWordLowerCaseInput.length

                && !eachWordInLeftOverInput[indexLeftOverInput + 1]
                        .equals(eachWordLowerCaseInput[indexUserInput + 1])

                && (indexEscapedText == eachEscapedText.length || (indexEscapedText < eachEscapedText.length && !eachWordLowerCaseInput[indexUserInput + 1]
                        .equals(eachEscapedText[indexEscapedText]))

                        && !(eachWordInLeftOverInput[indexLeftOverInput + 1] + eachEscapedText[indexEscapedText])
                                .equals(eachWordLowerCaseInput[indexUserInput + 1])

                        && !(eachEscapedText[indexEscapedText] + eachWordInLeftOverInput[indexLeftOverInput + 1])
                                .equals(eachWordLowerCaseInput[indexUserInput + 1]));
    }

    /**
     * check if the text is conjunction
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

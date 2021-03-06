package parser;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandParser {
	private static final String ADD_STATEMENT = new String("add");
	private static final String SHOW_STATEMENT = new String("show"); 
	private static final String DELETE_STATEMENT = new String("delete");
	private static final String EDIT_STATEMENT = new String("edit"); 
	private static final String SEARCH_STATEMENT = new String("search");
	private static final String UNDO_STATEMENT = new String("undo");
	private static final String MARK_STATEMENT = new String("mark");
	private static final String HELP_STATEMENT = new String("help"); 
	private static final String EXIT_STATEMENT = new String("exit"); 
	
	public CommandParser(){

	}

	/**
	 * use to detect different command Type(add, undo, mark, edit, show, delete, search,help, exit)
	 * @param userInput
	 * @return command type
	 */
	public static String getCommandType(String userInput) {
		String commandType = getFirstWord(userInput);
		try {
			if(commandType.equals(ADD_STATEMENT)){
				commandType = ADD_STATEMENT;
			} else if (commandType.equals(UNDO_STATEMENT)) {
				commandType = UNDO_STATEMENT;
			} else if (commandType.equals(MARK_STATEMENT)) {
				commandType = MARK_STATEMENT;
			} else if (commandType.equals(EDIT_STATEMENT )) {
				commandType = EDIT_STATEMENT;
			} else if (commandType.equals(SHOW_STATEMENT)) {
				commandType = SHOW_STATEMENT;
			} else if (commandType.equals(SEARCH_STATEMENT)) {
				commandType = SEARCH_STATEMENT;
			} else if (commandType.equals(DELETE_STATEMENT)) {
				commandType = DELETE_STATEMENT;
			} else if (commandType.equalsIgnoreCase(HELP_STATEMENT)) {
				commandType = HELP_STATEMENT;
			} else if (commandType.equals(EXIT_STATEMENT)) {
				commandType = EXIT_STATEMENT;
			} else {
				throw new NoSuchElementException("Invalid Command!");
			}
		} catch (NoSuchElementException e1) {
			System.err.println("InvalidCommandException: " + e1.getMessage());
			Logger logger = Logger.getLogger("CommandParser");
			logger.log(Level.INFO, "going to start processing");
			logger.log(Level.WARNING, "processing error", e1);
			logger.log(Level.INFO, "end of processing");
		}
		return commandType;
	}

	/**
	 * Use to detect the command type.
	 * @param userCommand
	 * @return the first word which is the command type to the caller
	 */
	private static String getFirstWord(String userCommand) {
		String splitOfCommand[] = userCommand.split(" ", 2);
		String firstWord = splitOfCommand[0];
		return firstWord;
	}
}


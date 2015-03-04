package parser;

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
	private static final String COMMAND_ERROR = "Unknown Command \n";

	public CommandParser(){

	}
	//return command type
	public static String determineCommandType(String userCommand){
		String commandType = getFirstWord(userCommand);

		if(commandType.equals(ADD_STATEMENT)){
			commandType = ADD_STATEMENT;
		}
		else if(commandType.equals(UNDO_STATEMENT)){
			commandType = UNDO_STATEMENT;
		}
		else if(commandType.equals(MARK_STATEMENT)){
			commandType = MARK_STATEMENT;
		}
		else if(commandType.equals(EDIT_STATEMENT )){
			commandType = EDIT_STATEMENT;
		}
		else if(commandType.equals(SHOW_STATEMENT)){
			commandType = SHOW_STATEMENT;
		}
		else if(commandType.equals(SEARCH_STATEMENT)){
			commandType = SEARCH_STATEMENT;
		}	
		else if(commandType.equals(DELETE_STATEMENT)){
			commandType = DELETE_STATEMENT;
		}
		else if(commandType.equalsIgnoreCase(HELP_STATEMENT)) {
			commandType = HELP_STATEMENT;
		}
		else if(commandType.equals(EXIT_STATEMENT)){
			commandType = EXIT_STATEMENT;
		}
		else{
			commandType = COMMAND_ERROR;
		}
		return commandType;
	}

	private static String getFirstWord(String userCommand) {
		String splitOfCommand[] = userCommand.split(" ", 2);
		String firstWord = splitOfCommand[0];
		return firstWord;
	}
}


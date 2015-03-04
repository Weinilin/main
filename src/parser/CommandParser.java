


public class CommandParser {
	private static final String ADD_STATEMENT = new String("add");
	private static final String DISPLAY = new String("display"); 
	private static final String DELETE_STATEMENT = new String("delete"); 
	private static final String SEARCH_BY_KEYWORD = new String("search");
	private static final String EXIT = new String("exit"); 
	private static final String COMMAND_ERROR = "Unknown Command \n";

	public CommandParser(){

	}

	public static String determineCommandType(String userCommand){
		String commandType = getFirstWord(userCommand);

		if(commandType.equals(ADD_STATEMENT)){
			commandType = ADD_STATEMENT;
		}
		else if(commandType.equals(DISPLAY)){
			commandType = DISPLAY;
		}
		else if(commandType.equals(SEARCH_BY_KEYWORD)){
			commandType = SEARCH_BY_KEYWORD;
		}	
		else if(commandType.equals(DELETE_STATEMENT)){
			commandType = DELETE_STATEMENT;
		}
		else if(commandType.equals(EXIT)){
			commandType = EXIT;
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


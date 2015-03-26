package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * CommandHandler for "edit description" function.
 * 
 * the field description will change while others remains
 * 
 * @author A0114463M
 *
 */
class EditDescriptionHandler extends UndoableCommandHandler {

	private static final String HELP_MESSAGE = "edit description <index> <new description>\n\t update the task description only\n";
	private ArrayList<String> aliases = new ArrayList<String>(
	                                        Arrays.asList("ed"));
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}

	    
	@Override
	protected String execute(String command, String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return HELP_MESSAGE;
	}

	@Override
	void undo() {
		// TODO Auto-generated method stub

	}

}

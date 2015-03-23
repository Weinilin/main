/**
 * 
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

/**
 * CommandHandler for "clear" function
 * 
 * @author A0114463M 
 */

class ClearHandler extends CommandHandler {
	private static final String HELP_MESSAGE = "clear\n\t delete all tasks\n";
	private static final String ALL_CLEAR_MESSAGE = "All tasks cleared\n";
	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("clear", "dall", "deleteall"));
	
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}

	@Override
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		taskList.clear();
		memory.removeAll();
		return ALL_CLEAR_MESSAGE;
	}

	@Override
	public String getHelp() {
		return HELP_MESSAGE;
	}

}

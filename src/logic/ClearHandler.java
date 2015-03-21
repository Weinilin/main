/**
 * 
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

/**
 * Clear all tasks for CommandHandler
 * @author Mr.Emosdnah
 *
 */

public class ClearHandler extends CommandHandler {
	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("clear", "dall", "deleteall"));
	
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}

	@Override
	String execute(String command, String parameter, ArrayList<Task> taskList) {
		taskList.clear();
		memory.removeAll();
		return "All tasks cleared\n";
	}

	@Override
	public String getHelp() {
		return "clear\n\t delete all tasks\n";
	}

}

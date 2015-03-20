package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

import application.Task;

/**
 * Command handler for showing/searching tasks
 * 
 * 
 * showing all tasks in the taskList by passing no
 * parameters OR search for tasks containing the 
 * keyword
 * 
 * @author A0114463M
 */
public class ShowHandler extends CommandHandler{

	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("show", "s", "display"));
	private static final Logger showLogger =
			Logger.getLogger(DeleteHandler.class.getName());
	
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}
	
	@Override
	String execute(String command, String parameter, ArrayList<Task> taskList) {
		showLogger.entering(getClass().getName(), "entering show handler");
		
		String[] token = parameter.split(" ");
		if (isHelp(token)) {
			return getHelp();
		}
		
		int i = 1;
		String result = new String();
		if (parameter.trim() == "") {
			taskList = memory.getTaskList();
			if (taskList.isEmpty()) {
				showLogger.log(Level.FINE, "empty list");
				return "Empty List!\n";
			}
			else {
				for (Task td: taskList) {
					result += i + ". \n" + td.toString() + "\n";
					i++;
				}
				showLogger.log(Level.FINE, "show all tasks");
				return result;
			}
		}
		else {
			ArrayList<Task> searchList = memory.searchDescription(parameter);
			if (searchList.isEmpty()) {
				showLogger.log(Level.FINE, "no results found containing " + parameter);
				return "No task containing " + parameter +"\n";
			}
			else {
				taskList = new ArrayList<Task>(searchList);
				for (Task td: searchList) {
					result += i + ". \n" + td.toString() + "\n";
					i++;
				}
				showLogger.log(Level.FINE, "show all tasks containing keyword " + parameter);
				return result;
			}
		}	
	}

	private boolean isHelp(String[] token) {
		return token[0].toLowerCase() == "help";
	}
	
	@Override
	public String getHelp() {
		return "show\n\t show all tasks in TaskManager\nshow [keyword]\n\t show all tasks containing the keyword";
	}
}

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
		
		if (isEmpty(parameter)) {
			taskList.clear();
			taskList.addAll(0, memory.getTaskList());
			if (taskList.isEmpty()) {
				showLogger.log(Level.FINE, "empty list");
				return "There is no task\n";
			}
			else {
				showLogger.log(Level.FINE, "show all tasks");
				return "";
			}
		}
		else {
			ArrayList<Task> searchList = memory.searchDescription(parameter);
			if (searchList.isEmpty()) {
				showLogger.log(Level.FINE, "no results found containing " + parameter);
				return "No task containing " + parameter +"\n";
			}
			else {
				updateTaskList(taskList, searchList);
				showLogger.log(Level.FINE, "show all tasks containing keyword " + parameter);
				return "";
			}
		}	
	}

	/**
	 * update the taskList in LogicController and write changes to Memory
	 * @param taskList
	 * @param searchList
	 */
	private void updateTaskList(ArrayList<Task> taskList,
			ArrayList<Task> searchList) {
		taskList.clear();
		taskList.addAll(0, searchList);
	}

	/**
	 * @param parameter
	 * @return
	 */
	private boolean isEmpty(String parameter) {
		return parameter.trim().equals("");
	}

	/**
	 * check if user if looking for help 
	 * @param token
	 * @return
	 */
	private boolean isHelp(String[] token) {
		return token[0].toLowerCase().trim().equals("help");
	}
	
	@Override
	public String getHelp() {
		return "show\n\t show all tasks in TaskManager\nshow [keyword]\n\t show all tasks containing the keyword\n";
	}
}

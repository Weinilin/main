/* 
 * @author A0114463M
 */
package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;

import database.Memory;
import application.Task;

import java.util.logging.Level;
import java.util.logging.Logger;;

/**
 * CommandHandler for "add" function.
 * 
 * Adding tasks is achieved by "add [task information]"
 * New task included in parameter invokes the static method createNewTask
 * in CommandHandler abstract class through various parsers.
 * The new task is added to the memory 
 * 
 * @author A0114463M
 *
 */
public class AddHandler extends CommandHandler {

	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("add", "a", "new", "+"));
	private static final Logger addLogger = 
			Logger.getLogger(AddHandler.class.getName());
	
	@Override
	public ArrayList<String> getAliases() {
		// TODO Auto-generated method stub
		return aliases;
	}

	@Override
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		String[] token = parameter.split(" ");
		if (token[0].toLowerCase().trim().equals("help") ||
			parameter.trim().equals("")) {
			return getHelp();
		}
		else {
			addLogger.entering(getClass().getName(), "Add non empty task");
			Task newTask = CommandHandler.createNewTask(parameter);
			// a non empty task is created
			assert (newTask != null);	
			if (memory.addTask(newTask)) {
				addLogger.log(Level.FINE, "Add sucess");
				return "Task \"" + newTask.getDescription() + "\" is added\n";
			} 
			else {
				addLogger.log(Level.SEVERE, "Error adding new task!");
				throw new Error("Fatal error! Unable to add Task");
			}	
		}
	}

	@Override
	public String getHelp() {
		return "add <task information>\n\t add a new task to TaskManager\n";
	}

}

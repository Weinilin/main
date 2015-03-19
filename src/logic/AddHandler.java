/* 
 * @author A0114463M
 */
package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import application.Task;

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

	private static final Logger addLogger = 
			Logger.getLogger(AddHandler.class.getName());
	
	@Override
	protected String getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		addLogger.entering(getClass().getName(), "entering adding tasks");
		
		String[] token = parameter.split(" ");
		if (isHelp(token)) {
			return getHelp();
		}

		Task newTask = CommandHandler.createNewTask(parameter);
		// a non empty task is created
		assert (newTask != null);	
		if (taskList.add(newTask)) {
			memory.addTask(newTask);
			taskList = memory.getTaskList();
			addLogger.log(Level.FINE, "Add sucess");
			return newTask.toString();
		} 
		else {
			addLogger.log(Level.SEVERE, "Error adding new task!");
			throw new Error("Fatal error! Unable to add Task");
		}	
		
	}

	private boolean isHelp(String[] token) {
		return token[0].toLowerCase() == "help";
	}

	@Override
	public String getHelp() {
		return "add <task informatino>\n\t To add a new task to TaskManager";
	}

}

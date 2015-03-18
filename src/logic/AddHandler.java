/* 
 * @author A0114463M
 */
package logic;


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

	private static final Logger addLogger = 
			Logger.getLogger(AddHandler.class.getName());
	
	@Override
	protected String getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String execute(String command, String parameter, Memory memory) {
		if (parameter.trim() == "") {
			return getHelp();
		}
		else {
			addLogger.entering(getClass().getName(), "Add non empty task");
			Task newTask = CommandHandler.createNewTask(parameter);
			// a non empty task is created
			assert (newTask != null);	
			if (memory.addTask(newTask)) {
				addLogger.log(Level.FINE, "Add sucess");
				return newTask.toString();
			} 
			else {
				addLogger.log(Level.SEVERE, "Error adding new task!");
				throw new Error("Fatal error! Unable to add Task");
			}	
		}
	}

	@Override
	public String getHelp() {
		return "add <task informatino>\n\t To add a new task to TaskManager";
	}

}

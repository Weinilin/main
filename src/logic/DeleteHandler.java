/*
 *  @author A0114463Ms
 */
package logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import application.Task;
import parser.IndexParser;
import database.Memory;

/**
 * CommandHandler for deleting a task
 * 
 * Deleting task is achieved by "delete [index]"
 * The task is removed from memory upon a success removal and the task
 * is returned in String. null is returned for failed removal.
 * 
 * @author A0114463M
 *
 */
public class DeleteHandler extends CommandHandler {

	private static final Logger deleteLogger = 
			Logger.getLogger(DeleteHandler.class.getName());
	
	@Override
	protected String getAliases() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected String execute(String command, String parameter, Memory memory) {
		deleteLogger.log(Level.FINE, "preparing for delete");
		IndexParser ip = new IndexParser();
		Task removedTask = new Task();
		int index = ip.getIndex(parameter);
		try {
			removedTask = memory.removeTask(index);
		} catch (IndexOutOfBoundsException iob) {
			deleteLogger.log(Level.WARNING, "Index out of range", iob);
			return null;
		} 
		
		deleteLogger.log(Level.FINE, "Successfully deleted");
		return removedTask.toString();
	}

	@Override
	public String getHelp() {
		return "delete <index>\n\t To remove the respective task of the index from TaskManager";
	}

}

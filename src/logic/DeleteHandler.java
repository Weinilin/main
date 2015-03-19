/*
 *  @author A0114463Ms
 */
package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import application.Task;
import parser.IndexParser;

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
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		deleteLogger.entering(getClass().getName(), "preparing for delete");

		String[] token = parameter.split(" ");
		if (isHelp(token)) {
			return getHelp();
		}
		
		String goodFeedback = new String(), 
			   badFeedback = new String();		
		
		IndexParser ip = new IndexParser();
		Task removedTask = new Task();
		
		for (String t: token) {
			int index = ip.getIndex(token[0]);
			try {
				removedTask = taskList.remove(index);				
				if (removedTask != null) {
					memory.removeTask(removedTask);
				}
				goodFeedback += t + " ";
				deleteLogger.log(Level.FINE, "Removed " + removedTask.toString() + "\n");
			} catch (IndexOutOfBoundsException iob) {
				badFeedback += t + " ";
				deleteLogger.log(Level.WARNING, t + " is invalid!\n", iob);			
			} 
		}
		
		String feedback = "Successfully remmoved " + goodFeedback.trim() + "\n" +
						  "Invalid input " + badFeedback.trim() + "\n";	
		return feedback;
	}


	private boolean isHelp(String[] token) {
		return token[0].toLowerCase() == "help";
	}

	@Override
	public String getHelp() {
		return "delete <index>\n\t To remove the respective task of the index from TaskManager";
	}

}

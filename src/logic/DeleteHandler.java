/*
 *  @author A0114463Ms
 */
package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;

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
class DeleteHandler extends CommandHandler {

	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("delete", "d", "remove", "-"));
	private static final Logger deleteLogger = 
			Logger.getLogger(DeleteHandler.class.getName());
	
	@Override
	public ArrayList<String> getAliases() {
		// TODO Auto-generated method stub
		return aliases;
	}


	@Override
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		deleteLogger.entering(getClass().getName(), "preparing for delete");

		String[] token = parameter.split(" ");
		if (token[0].toLowerCase().trim().equals("help") ||
				parameter.trim().equals("")) {
			return getHelp();
		}
		
		if (token[0].toLowerCase().trim().equals("all")) {
			ClearHandler clrHandler = new ClearHandler();
			return clrHandler.execute(token[0], "", taskList);
		}
		
		String goodFeedback = new String(), 
			   badFeedback = new String();		
		
		IndexParser ip;
		ArrayList<Task> removedTask = new ArrayList<Task>();
		
		for (String t: token) {
			ip = new IndexParser(t);
			int index = ip.getIndex() - 1;
			try {
				removedTask.add(taskList.get(index));				
				goodFeedback += t + " ";
				deleteLogger.log(Level.FINE, "Removed " + removedTask.toString() + "\n");
			} catch (IndexOutOfBoundsException iob) {
				badFeedback += t + " ";
				deleteLogger.log(Level.WARNING, t + " is invalid!\n", iob);			
			} 
		}
		
		for (Task task: removedTask) {
			taskList.remove(task);
			memory.removeTask(task);
		}
		
		String feedback = "Removed tasks " + goodFeedback + "\n" +
						  "Invalid input " + badFeedback + "\n";	
		return feedback;
	}

	@Override
	public String getHelp() {
		return "delete <index>\n\t remove the respective task of the index from TaskManager\n";
	}

}

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
 * CommandHandler for "delete" function
 * 
 * Deleting task is achieved by "delete [index]"
 * The task is removed from memory upon a success removal and the task
 * is returned in String. null is returned for failed removal.
 * 
 * @author A0114463M
 *
 */
class DeleteHandler extends CommandHandler {

	private static final String HELP_MESSAGE = "delete <index>\n\t remove the respective task of the index from TaskManager\n";
	private static final String GOODFEEDBACK_MESSAGE = "Removed tasks %1$s\n";
	private static final String BADFEEDBACK_MESSAGE = "Invalid input %1$s\n";	
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
		if (isHelp(token) || isEmptyParameter(parameter)) {
			return getHelp();
		}
		
		if (isAll(token)) {
			ClearHandler clrHandler = new ClearHandler();
			return clrHandler.execute(token[0], "", taskList);
		}
		
		String goodFeedback = new String(), 
			   badFeedback = new String(),
			   feedback = new String();		
		
		IndexParser ip;
		ArrayList<Task> removedTask = new ArrayList<Task>();
		
		for (String t: token) {
			ip = new IndexParser(t);
			int index = ip.getIndex() - 1;
			try {
				removedTask.add(taskList.get(index));				
				goodFeedback = appendFeedback(goodFeedback, t);
				deleteLogger.log(Level.FINE, "Removed " + removedTask.toString() + "\n");
			} catch (IndexOutOfBoundsException iob) {
				badFeedback = appendFeedback(badFeedback, t);
				deleteLogger.log(Level.WARNING, t + " is invalid!\n", iob);			
			} 
		}
		
		deleteTasks(taskList, removedTask);
		
		generateFeedbackString(goodFeedback, badFeedback, feedback);
		return feedback;
	}


	private void generateFeedbackString(String goodFeedback,
			String badFeedback, String feedback) {
		if (!goodFeedback.equals("")) {
			feedback.concat(String.format(GOODFEEDBACK_MESSAGE, goodFeedback));
		}
		if (!badFeedback.equals("")) {
			feedback.concat(String.format(BADFEEDBACK_MESSAGE, badFeedback));
		}
	}


	/**
	 * delete the tasks in taskList and memory
	 * @param taskList
	 * @param removedTask
	 */
	private void deleteTasks(ArrayList<Task> taskList,
			ArrayList<Task> removedTask) {
		for (Task task: removedTask) {
			taskList.remove(task);
			memory.removeTask(task);
		}
	}


	/**
	 * append the indexes for valid deletion or invalid input
	 * @param goodFeedback
	 * @param index
	 * @return - feedback string
	 */
	private String appendFeedback(String feedback, String index) {
		feedback += index + " ";
		return feedback;
	}


	/**
	 * decide whether is delete all
	 * @param token
	 * @return
	 */
	private boolean isAll(String[] token) {
		return token[0].toLowerCase().trim().equals("all");
	}


	/**
	 * check if the argument user typed is empty
	 * @param parameter
	 * @return
	 */
	private boolean isEmptyParameter(String parameter) {
		return parameter.trim().equals("");
	}


	/**
	 * check if user is looking for help
	 * @param token
	 * @return
	 */
	private boolean isHelp(String[] token) {
		return token[0].toLowerCase().trim().equals("help");
	}

	@Override
	public String getHelp() {
		return HELP_MESSAGE;
	}

}

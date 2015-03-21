package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.IndexParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * CommandHandler for "mark" function
 * 
 * Mark a task as done by typing the keyword following 
 * by the index of task that is intended to be marked
 */
public class MarkHandler extends CommandHandler {

	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("mark", "done"));
	private static final Logger markLogger = 
			Logger.getLogger(MarkHandler.class.getName());
	
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}
	
	@Override
	String execute(String command, String parameter, ArrayList<Task> taskList) {
		markLogger.entering(getClass().getName(), "Entering marking");
		
		String[] token = parameter.split(" ");
		if (isHelp(token) || isEmpty(parameter)) {
			return getHelp();
		}
		
		String goodFeedback = new String();
		IndexParser ip;
		int index;
		for (String t: token) {
			ip = new IndexParser(t);
			index = ip.getIndex();
			try {
				goodFeedback = getTasks(taskList, goodFeedback, index, t);
			} catch (IndexOutOfBoundsException iob) {
				markLogger.log(Level.WARNING, "Invalid index", iob);
				return "Index invalid! Please check yout input\n";
			} 
		}
		
		return "Marked " + goodFeedback + "as done\n";
	}

	/**
	 * get the tasks than can be marked to done
	 * @param taskList
	 * @param goodFeedback
	 * @param index
	 * @param t
	 * @return
	 */
	private String getTasks(ArrayList<Task> taskList, String goodFeedback,
			int index, String t) {
		taskList.get(index - 1).setStatus("done");
		memory.markDone(index);		
		goodFeedback += t + " ";
		return goodFeedback;
	}

	/**
	 * check if the argument user typed is empty
	 * @param parameter
	 * @return
	 */
	private boolean isEmpty(String parameter) {
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
		return "mark [index]\n\t mark a task as done\n";
	}
}

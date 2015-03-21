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
		if (token[0].toLowerCase().trim().equals("help") || 
			parameter.trim().equals("")) {
			return getHelp();
		}
		
		IndexParser ip = new IndexParser(parameter);
		int index = ip.getIndex(token[0]);
		try {
			taskList.get(index).setStatus("done");
			memory.markDone(index);			
		} catch (IndexOutOfBoundsException iob) {
			markLogger.log(Level.WARNING, "Invalid index", iob);
			return "Index invalid! Please check yout input\n";
		} 
	
		return "Marked " + index + "as done\n";
	}
	
	@Override
	public String getHelp() {
		return "mark [index]\n\t mark a task as done\n";
	}
}

package logic;

import java.util.ArrayList;

import application.Task;
import parser.IndexParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MarkHandler extends CommandHandler {
	
	private static final Logger markLogger = 
			Logger.getLogger(MarkHandler.class.getName());
	
	@Override
	protected ArrayList<String> getAliases() {
		return null;
	}
	
	@Override
	String execute(String command, String parameter, ArrayList<Task> taskList) {
		markLogger.entering(getClass().getName(), "Entering marking");
		String[] token = parameter.split(" ");
		if (token[0].toLowerCase() == "help") {
			return getHelp();
		}
		IndexParser ip = new IndexParser();
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

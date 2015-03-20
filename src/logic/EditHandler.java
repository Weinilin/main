package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.IndexParser;

/**
 * CommandHandler for "edit" function
 * 
 * modify a task by specifying the field (description or time) and index
 * of the task in the ArrayList then the information that is intended to 
 * change
 * @author A0114463M
 *
 */
public class EditHandler extends CommandHandler {

	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("edit", "e", "update"));
	private static final Logger editLogger =
			Logger.getLogger(DeleteHandler.class.getName());
	
	@Override
	protected ArrayList<String> getAliases() {
		// TODO Auto-generated method stub
		return aliases;
	}

	@Override
	String execute(String command, String parameter, ArrayList<Task> taskList) {
		editLogger.entering(getClass().getName(), "preparing for editing tasks");
		
		String[] token = parameter.split(" ");
		if (isHelp(token)) {
			return getHelp();
		}
		
		IndexParser ip = new IndexParser();		
		int index = ip.getIndex(parameter);
		if (index < 0 || index > taskList.size()) {
			return "Invalid index! Please check your input\n";
		}
		
		Task removedTask, newTask = new Task();

	
		return null;
	}

	private boolean isHelp(String[] token) {
		return token[0].toLowerCase() == "help";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

}
// Depreciated
//	protected boolean editTask(String taskInformation) throws IndexOutOfBoundsException {
//		DeleteHandler dh = new DeleteHandler(memory);
//		AddHandler ah = new AddHandler(memory);
//		TaskData oldTask = new TaskData();
//		TaskData newTask = CommandHandler.createNewTask(taskInformation.substring(taskInformation.indexOf(" ")));	
//		try  {
//			oldTask = dh.deleteTask(taskInformation);
//			ah.addTask(newTask);
//		} catch (IndexOutOfBoundsException iob) {
//			return false;
//		}
//		
//		return true;
//	}
//}

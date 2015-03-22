package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import application.Task;
import parser.IndexParser;
import application.TaskComparator;


/**
 * CommandHandler for "edit" function
 * 
 * modify a task by specifying the field (description or time) and index
 * of the task in the ArrayList then the information that is intended to 
 * change
 * @author A0114463M
 *
 */
class EditHandler extends CommandHandler {

	private ArrayList<String> aliases = new ArrayList<String>(
			Arrays.asList("edit", "e", "update"));
	private static final Logger editLogger =
			Logger.getLogger(DeleteHandler.class.getName());
	
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}

	@Override
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		editLogger.entering(getClass().getName(), "preparing for editing tasks");
		
		String[] token = parameter.split(" ");
		if (token[0].toLowerCase().trim().equals("help") || 
			parameter.trim().equals("")) {
			return getHelp();
		}
		
		IndexParser ip = new IndexParser(parameter);		
		int index = ip.getIndex() - 1;
		if (index < 0 || index > taskList.size()) {
			editLogger.log(Level.WARNING, "Invalid number " + index);
			return "Invalid index! Please check your input\n";
		}
		
		Task removedTask = taskList.get(index),
			 newTask = new Task(removedTask);
		
		switch (token[0].toLowerCase()) {
			case "description":
				newTask.setDescription(parameter.replace(token[0], "").
									   replace(Integer.toString(index + 1), "").trim());
				break;
			case "time":
				String description = newTask.getDescription();
				newTask = CommandHandler.createNewTask(parameter.replace(token[0], "").
						   replace(Integer.toString(index + 1), "").trim());
				newTask.setDescription(description);				
				break;
			default:
				try {
					index = Integer.parseInt(token[0]) - 1;
					removedTask = taskList.remove(index);
					newTask = CommandHandler.createNewTask(
							  parameter.replaceFirst(token[0], "").trim());
				} catch (NumberFormatException nfe) {
					editLogger.log(Level.WARNING, "Not a number entered for edit", nfe);
				}
				break;
		}
		
		if (!newTask.equals(removedTask)) {
			taskList.remove(index);
			taskList.add(newTask);
			Collections.sort(taskList, new TaskComparator());
			memory.removeTask(removedTask);
			memory.addTask(newTask);
		}
		taskList = memory.getTaskList();
		return "";
	}

	@Override
	public String getHelp() {
		return "edit <index> <new task>\n\t edit the task by specifying the index\n"
				+ "edit description <index> <new description>\n\t update the task description only\n"
				+ "edit time <index> <time>\n\t update the time of task \n";
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

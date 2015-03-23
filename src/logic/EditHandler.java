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

	private static final String INVALID_INDEX_MESSAGE = "Invalid index! Please check your input\n";
	private static final String HELP_MESSAGE = "edit <index> <new task>\n\t edit the task by specifying the index\n"
			+ "edit description <index> <new description>\n\t update the task description only\n"
			+ "edit time <index> <time>\n\t update the time of task \n";
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
		if (isHelp(token) || isEmpty(parameter)) {
			return getHelp();
		}
		
		IndexParser ip = new IndexParser(parameter);		
		int index = ip.getIndex() - 1;
		if (index < 0 || index > taskList.size()) {
			editLogger.log(Level.WARNING, "Invalid number " + index);
			return INVALID_INDEX_MESSAGE;
		}
		
		Task removedTask = taskList.get(index),
			 newTask = new Task(removedTask);
		
		switch (token[0].toLowerCase()) {
			case "description":
				updateTaskByDescription(parameter, token, index, newTask);
				break;
			case "time":
				newTask = updateTaskByTime(parameter, token, index, newTask);				
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
		
		updateTaskList(taskList, index, removedTask, newTask);
		taskList = memory.getTaskList();
		return "";
	}

	/**
	 * set the new description of the task
	 * @param parameter
	 * @param token
	 * @param index
	 * @param newTask
	 */
	private void updateTaskByDescription(String parameter, String[] token,
			int index, Task newTask) {
		newTask.setDescription(getNewDescription(parameter, token, index));
	}

	/**
	 * set the new time of the task
	 * @param parameter
	 * @param token
	 * @param index
	 * @param newTask
	 * @return
	 */
	private Task updateTaskByTime(String parameter, String[] token, int index,
			Task newTask) {
		String description = newTask.getDescription();
		newTask = createTimeOnlyTask(parameter, token, index);
		newTask.setDescription(description);
		return newTask;
	}

	/**
	 * create a task with time only, description will be updated elsewhere
	 * @param parameter
	 * @param token
	 * @param index
	 * @return
	 */
	private Task createTimeOnlyTask(String parameter, String[] token, int index) {
		return CommandHandler.createNewTask(getNewDescription(parameter, token, index));
	}

	/**
	 * extract the new description of the task
	 * @param parameter
	 * @param token
	 * @param index
	 * @return
	 */
	private String getNewDescription(String parameter, String[] token, int index) {
		return parameter.replace(token[0], "").
							   replace(Integer.toString(index + 1), "").trim();
	}

	/**
	 * update the taskList in LogicController and Memory
	 * @param taskList
	 * @param index
	 * @param removedTask
	 * @param newTask
	 */
	private void updateTaskList(ArrayList<Task> taskList, int index,
			Task removedTask, Task newTask) {
		if (!newTask.equals(removedTask)) {
			taskList.remove(index);
			taskList.add(newTask);
			Collections.sort(taskList, new TaskComparator());
			memory.removeTask(removedTask);
			memory.addTask(newTask);
		}
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
	 * check if user the user is looking for help
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

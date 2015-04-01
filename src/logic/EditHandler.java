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
 * other edit handlers will be invoked by specifying the field 
 * (description or time) and index. If no field is selected, the whole
 * task will be updated. Index refers to the index of task in
 * the ArrayList then the information that is intended to change
 * @author A0114463M
 *
 */
class EditHandler extends UndoableCommandHandler {

    private static final String INVALID_INDEX_MESSAGE = "Invalid index! Please check your input\n";
    private static final String HELP_MESSAGE = "edit <index> <new task>\n\t edit the task by specifying the index\n"
            + "edit description <index> <new description>\n\t update the task description only\n"
            + "edit time <index> <time>\n\t update the time of task \n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("edit", "e", "update"));
    private static final Logger editLogger =
            Logger.getLogger(DeleteHandler.class.getName());
    Task oldTask, newTask;
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

        try {
            oldTask = taskList.get(index);
        } catch (IndexOutOfBoundsException iob) {
            return INVALID_INDEX_MESSAGE;
        }

        switch (token[0].toLowerCase()) {
            case "description":
                EditDescriptionHandler edh = new EditDescriptionHandler();
                return edh.execute(token[0], parameter.replaceFirst(token[0], "").trim(), taskList);
            case "time":
                EditTimeHandler eth = new EditTimeHandler();
                return eth.execute(token[0], parameter.replaceFirst(token[0], "").trim(), taskList);
            default:
                try {
                    index = Integer.parseInt(token[0]) - 1;
                } catch (NumberFormatException nfe) {
                    editLogger.log(Level.WARNING, "Not a number entered for edit", nfe);
                    return INVALID_INDEX_MESSAGE;
            }

            try {
                oldTask = taskList.remove(index);
                newTask = CommandHandler.createNewTask(parameter.replaceFirst(token[0], "").trim());
            } catch (IndexOutOfBoundsException iob) {
                return INVALID_INDEX_MESSAGE;
            }
                break;
        }

        updateTaskList(taskList);
        taskList = memory.getTaskList();
        return "";
    }

    /**
     * update the taskList in LogicController and Memory
     * @param taskList
     * @param index
     * @param removedTask
     * @param newTask
     */
    private void updateTaskList(ArrayList<Task> taskList) {
        if (newTask != oldTask && oldTask != null) {
            memory.removeTask(oldTask);
            memory.addTask(newTask);
            taskList.remove(oldTask);
            taskList.add(newTask);
            Collections.sort(taskList, new TaskComparator());
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

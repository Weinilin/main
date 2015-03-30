package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import parser.IndexParser;
import parser.DescriptionParser;
import application.Task;
import application.TaskComparator;
/**
 * CommandHandler for "edit description" function.
 * 
 * only the field description will change while others remains
 * 
 * @author A0114463M
 *
 */
class EditDescriptionHandler extends UndoableCommandHandler {
    private static final String INVALID_INDEX_MESSAGE = "Invalid index! Please check your input\n";
	private static final String HELP_MESSAGE = "edit description <index> <new description>\n\t update the task description only\n";
	private ArrayList<String> aliases = new ArrayList<String>(
	                                        Arrays.asList("ed"));
	Task oldTask, newTask = null;
	
	@Override
	protected ArrayList<String> getAliases() {
		return aliases;
	}

	    
	@Override
	protected String execute(String command, String parameter, ArrayList<Task> taskList) {
		String[] token = parameter.split(" ");
		if (token[0].toLowerCase().equals("help") || token[0].equals("")) {
			return getHelp();
		}
		
	    DescriptionParser dp = new DescriptionParser(parameter);
	    IndexParser ip = new IndexParser(parameter);
	    int index = ip.getIndex();
	    if (index < 0) {
            return INVALID_INDEX_MESSAGE;
	    }
	    
	    try {
	        oldTask = taskList.remove(index);
	        newTask.setDescription(dp.getDescription());
	        newTask.setStatus(oldTask.getStatus());
	        newTask.setDeadline(oldTask.getDeadline());
	        newTask.setEndDateTime(oldTask.getEndDateTime());
	        newTask.setStartDateTime(oldTask.getStartDateTime());
	    } catch (IndexOutOfBoundsException iob) {
            return INVALID_INDEX_MESSAGE;
	    }
	    
	    if (newTask != null && oldTask != null) {
	        memory.removeTask(oldTask);
	        memory.addTask(newTask);
            undoRedoManager.undo.push(this);
            taskList.remove(oldTask);
            taskList.add(newTask);
            Collections.sort(taskList, new TaskComparator());
	    }
	    return "Changed " + oldTask.getDescription() + " to " +
	    		newTask.getDescription() + "\n";
	}

	@Override
	public String getHelp() {
		return HELP_MESSAGE;
	}

	@Override
	void undo() {
        memory.addTask(oldTask);
        memory.removeTask(newTask);
	}

	@Override
	public CommandHandler getNewInstance() {
        return new EditDescriptionHandler();
    }
}

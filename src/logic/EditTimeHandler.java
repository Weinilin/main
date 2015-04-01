package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import parser.DateTimeParser;
import parser.IndexParser;
import application.Task;
import application.TaskComparator;
/**
 * CommandHandler for "edit time" function.
 * 
 * only the time fields (deadline, start and end) will change while others remains
 * 
 * @author A0114463M
 *
 */
class EditTimeHandler extends UndoableCommandHandler {
    private static final String INVALID_INDEX_MESSAGE = "Invalid index! Please check your input\n";
    private static final String HELP_MESSAGE = "edit time <index> <new time>\n\t update the task time only\n";
    private ArrayList<String> aliases = new ArrayList<String>(
                                            Arrays.asList("et"));
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
		
        DateTimeParser dtp = new DateTimeParser(parameter);
        IndexParser ip = new IndexParser(parameter);
        int index = ip.getIndex();
        String newDeadline = dtp.getDeadlineDate() + " " + dtp.getDeadlineTime(),
               newStartDateTime = dtp.getStartDate() + " " + dtp.getStartTime(),
               newEndDateTime = dtp.getEndDate() + " " + dtp.getEndTime();
        if (index < 0) {
            return INVALID_INDEX_MESSAGE;
        }
        
        try {
            oldTask = taskList.remove(index);
            newTask.setDescription(oldTask.getDescription());
            newTask.setStatus(oldTask.getStatus());
            newTask.setDeadline(newDeadline);
            newTask.setEndDateTime(newStartDateTime);
            newTask.setStartDateTime(newEndDateTime);
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
        return "";
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
    


}

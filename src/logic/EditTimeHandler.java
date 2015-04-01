package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import parser.DateTimeParser;
import parser.IndexParser;
import parser.TaskTypeParser;
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
    Task oldTask, newTask;
    
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
        TaskTypeParser ttp = new TaskTypeParser(parameter);
        IndexParser ip = new IndexParser(parameter);
        int index = ip.getIndex() - 1;
        String newDeadline = dtp.getDeadlineDate() + " " + dtp.getDeadlineTime(),
               newStartDateTime = dtp.getStartDate() + " " + dtp.getStartTime(),
               newEndDateTime = dtp.getEndDate() + " " + dtp.getEndTime();
        if (index < 0) {
            return INVALID_INDEX_MESSAGE;
        }
        
        try {
            oldTask = taskList.remove(index);
            newTask = new Task(oldTask);
        } catch (IndexOutOfBoundsException iob) {
            return INVALID_INDEX_MESSAGE;
        }

        newTask.setDeadline(newDeadline);
        newTask.setEndDateTime(newEndDateTime);
        newTask.setStartDateTime(newStartDateTime);
        newTask.setTaskType(ttp.getTaskType());
        
        performEdit(taskList);
        return "Task \"" + newTask.getDescription() + "\" has changed time\n";
    }

    private void performEdit(ArrayList<Task> taskList) {
        if ((newTask != oldTask) && (oldTask != null)) {
            memory.removeTask(oldTask);
            memory.addTask(newTask);
            recordMemoryChanges(taskList);
            Collections.sort(taskList, new TaskComparator());
        }
    }
    
    private void recordMemoryChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder editTimeRecorder = new UndoRedoRecorder(taskList);
        editTimeRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.EDIT, oldTask, newTask));
        updateTaskList(taskList);
        editTimeRecorder.recordUpdatedList(taskList);
        undoRedoManager.addNewRecord(editTimeRecorder);
    }
    
    private void updateTaskList(ArrayList<Task> taskList) {
        taskList.remove(oldTask);
        taskList.add(newTask);
    }
    
    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }


}

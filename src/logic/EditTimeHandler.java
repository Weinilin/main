package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import parser.MainParser;
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
    private static String CHANGE_MESSAGE = "Updated the time of %1$s to %2$s\n";
    private ArrayList<String> aliases = new ArrayList<String>(
                                            Arrays.asList("et"));
    Task oldTask, newTask = null;
    String feedback = "";
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }
        
    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception {
        reset();
    	String[] token = parameter.split(" ");
		if (token[0].toLowerCase().equals("help") || token[0].equals("")) {
			return getHelp();
		}
		
        MainParser parser = new MainParser(parameter);
        IndexParser ip = new IndexParser(token[1]);
        int index = ip.getIndex() - 1;
        String newStartDateTime = parser.getStartDate() + " " + parser.getStartTime(),
               newEndDateTime = parser.getEndDate() + " " + parser.getEndTime();
        if (index < 0) {
            return INVALID_INDEX_MESSAGE;
        }
        
        try {
            oldTask = taskList.get(index);
            newTask = new Task(oldTask);
        } catch (IndexOutOfBoundsException iob) {
            return INVALID_INDEX_MESSAGE;
        }

        newTask.setEndDateTime(newEndDateTime);
        newTask.setStartDateTime(newStartDateTime);
        newTask.setTaskType(parser.getTaskType());
        
        performEdit(taskList);
        return "Task \"" + newTask.getDescription() + "\" has changed time\n";
    }

    /**
     * execute the changes in Memory and update the feedback
     * @param taskList
     */
    private void performEdit(ArrayList<Task> taskList) {
        if (newTask != oldTask && oldTask != null) {
            if (memory.addTask(newTask) >= 0) {
                memory.removeTask(oldTask);
                recordChanges(taskList);            
                Collections.sort(taskList, new TaskComparator());
                feedback = String.format(CHANGE_MESSAGE, oldTask.getDescription(), newTask.getEndDateTime());
            }
            else {
                feedback = "New task is identical to some existing task\n";
            }
        }
        else {
            feedback = "Please check your input\n";
        }
    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder editTimeRecorder = new UndoRedoRecorder(taskList);
        editTimeRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.EDIT, oldTask, newTask));
        updateTaskList(taskList);
        editTimeRecorder.recordUpdatedList(taskList);
        undoRedoManager.addNewRecord(editTimeRecorder);
    }
    
    /**
     * reset the handler when it is called
     */
    private void reset() {
        newTask = null;
        oldTask = null;
        feedback = "";
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

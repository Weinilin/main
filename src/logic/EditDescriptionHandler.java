package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import parser.MainParser;
import parser.IndexParser;
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
	private static String CHANGE_MESSAGE = "Changed %1$s to %2$s \n";
	private ArrayList<String> aliases = new ArrayList<String>(
	                                        Arrays.asList("ed"));
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
		
	    MainParser parser = new MainParser(parameter.replaceFirst(token[0], ""));
	    IndexParser ip = new IndexParser(token[0]);
	    int index = ip.getIndex() - 1;
	    if (index < 0) {
            return INVALID_INDEX_MESSAGE;
	    }
	    if (isEmpty(parser.getDescription())) {
            return "No description for new task\n";
	    }
	    
	    try {
	        oldTask = taskList.get(index);
	        newTask = new Task(oldTask);
	    } catch (IndexOutOfBoundsException iob) {
            return INVALID_INDEX_MESSAGE;
	    }
	    
        newTask.setDescription(parser.getDescription());
        
	    performEdit(taskList);
	    return feedback;
	}
	
	/**
     * reset the handler when it is called
     */
    private void reset() {
        newTask = null;
        oldTask = null;
        feedback = "";
    }

    private boolean isEmpty(String string) {
        return string.trim().equals("");
    }
    
    /**
     * Perform the edit in Memory and update the feedback
     * @param taskList taskList shown to user
     */
    private void performEdit(ArrayList<Task> taskList) {
        if ((newTask != oldTask) && (oldTask != null)) {           
            memory.addTask(newTask);
            recordChanges(taskList);
            Collections.sort(taskList, new TaskComparator());
            feedback = String.format(CHANGE_MESSAGE, oldTask.getDescription(), newTask.getDescription());
        }
        else {
            feedback = "New task is identical to some existing task\n";
        }  

    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder editDescriptionRecorder = new UndoRedoRecorder(taskList);
        editDescriptionRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.EDIT, oldTask, newTask));
        updateTaskList(taskList);
        editDescriptionRecorder.recordUpdatedList(taskList);
        undoRedoManager.addNewRecord(editDescriptionRecorder);
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

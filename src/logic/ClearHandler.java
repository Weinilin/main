//@author A0114463M
package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

/**
 * CommandHandler for "clear" function
 * 
 */

class ClearHandler extends UndoableCommandHandler {
    private static final String HELP_MESSAGE = "clear\n\t delete all tasks\n";
    private static final String ALL_CLEAR_MESSAGE = "All tasks cleared\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("clear", "clr", "dall", "deleteall"));
    private ArrayList<Task> oldTaskList;
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        reset();
        oldTaskList = new ArrayList<Task>(taskList);
        recordChanges(taskList);
        memory.removeAll();
        return ALL_CLEAR_MESSAGE;
    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder clearRecorder = new UndoRedoRecorder(taskList);
        for (Task removedTask: oldTaskList) {
            clearRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.DELETE, removedTask, removedTask));
        }
        updateTaskList(taskList);
        clearRecorder.recordUpdatedList(taskList);
        undoRedoManager.addNewRecord(clearRecorder);
    }
    
    /**
     * update the taskList in CommandHandler
     */
    private void updateTaskList(ArrayList<Task> taskList) {
        taskList.clear();
    }
    
    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }

    @Override
    void reset() {
        oldTaskList.clear();
    }

}

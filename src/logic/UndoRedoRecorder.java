//@author A0114463M
package logic;

import java.util.ArrayList;
import java.util.Stack;
import application.Task;
/**
 * This class records all the actions performed during each single
 * line of command that user enters and successfully implemented to the system.
 * Multiple actions completed in a single line of command (such as deleting multiple
 * tasks) are recorded in one recorder.
 * 
 */
public class UndoRedoRecorder {

    private Stack<UndoRedoAction> actions = new Stack<UndoRedoAction>();
    private ArrayList<Task> currentTaskList = new ArrayList<Task>();
    private ArrayList<Task> changedTaskList = new ArrayList<Task>();
    
    UndoRedoRecorder(ArrayList<Task> taskList) {
        currentTaskList.clear();
        currentTaskList.addAll(taskList);
    }
    
    void recordUpdatedList(ArrayList<Task> updatedList) {
        changedTaskList.clear();
        changedTaskList.addAll(updatedList);
    }
    void appendAction(UndoRedoAction newAction) {
        actions.push(newAction);
    }
    
    ArrayList<Task> getCurrentTaskList() {
        return currentTaskList;
    }
    
    ArrayList<Task> getChangedTaskList() {
        return changedTaskList;
    }
    
    void performUndo() {
        for (UndoRedoAction ura: actions) {
            ura.undo();
        }
    }
    
    void performRedo() {
        for (UndoRedoAction ura: actions) {
            ura.redo();
        }
    }
    
    boolean isEmpty() {
        return actions.isEmpty();
    }
}

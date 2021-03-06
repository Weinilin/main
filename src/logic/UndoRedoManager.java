package logic;

import java.util.Stack;
import java.util.ArrayList;
import application.Task;

/**
 * This class manages all the actions that perform changes to 
 * memory by UndoableCommandHandlers. A redo stack is also implemented
 * to record actions that has been called for undo for redo purposes
 * @author A0114463M
 *
 */
class UndoRedoManager {

    private static UndoRedoManager undoRedoManager;
    private Stack<UndoRedoRecorder> undo, redo;
    
    private UndoRedoManager() {
        undo = new Stack<UndoRedoRecorder>();
        redo = new Stack<UndoRedoRecorder>();
    }
    
    public static UndoRedoManager getInstance() {
        if (undoRedoManager == null) {
            undoRedoManager = new UndoRedoManager();
        }
        return undoRedoManager;
    }
    
    public void addNewRecord(UndoRedoRecorder newRecord) {
        undo.push(newRecord);
        redo.clear();
    }
    
    public boolean canUndo() {
        return !undo.isEmpty();
    }
    
    public int getUndoSize() {
        return undo.size();
    }
    
    public int getRedoSize() {
        return redo.size();
    }
    
    public boolean canRedo() {
        return !redo.isEmpty();
    }
    
    public ArrayList<Task> undo() {
        if (canUndo()) {
            UndoRedoRecorder latestChange = undo.pop();
            latestChange.performUndo();
            redo.push(latestChange);
            return latestChange.getCurrentTaskList();
        }
        else {
            return new ArrayList<Task>();
        }
    }
    
    public ArrayList<Task> redo() { 
        if (canRedo()) {
            UndoRedoRecorder lastChange = redo.pop();
            lastChange.performRedo();
            undo.push(lastChange);
            return lastChange.getChangedTaskList();
        }
        else {
            return new ArrayList<Task>();
        }
    }
}

//@author A0114463M
package logic;

import java.util.Stack;
import java.util.ArrayList;
import application.Task;

/**
 * This class manages all the actions that perform changes to 
 * memory by UndoableCommandHandlers. A redo stack is also implemented
 * to record actions that has been called for undo for redo purposes
 *
 */
class UndoRedoManager {

    private static UndoRedoManager undoRedoManager;
    private Stack<UndoRedoRecorder> undo, redo;
    
    private UndoRedoManager() {
        undo = new Stack<UndoRedoRecorder>();
        redo = new Stack<UndoRedoRecorder>();
    }
    
    static UndoRedoManager getInstance() {
        if (undoRedoManager == null) {
            undoRedoManager = new UndoRedoManager();
        }
        return undoRedoManager;
    }
    
    void addNewRecord(UndoRedoRecorder newRecord) {
        undo.push(newRecord);
        redo.clear();
    }
    
    boolean canUndo() {
        return !undo.isEmpty();
    }
    
    int getUndoSize() {
        return undo.size();
    }
    
    int getRedoSize() {
        return redo.size();
    }
    
    boolean canRedo() {
        return !redo.isEmpty();
    }
    
    ArrayList<Task> undo() {
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
    
    ArrayList<Task> redo() { 
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

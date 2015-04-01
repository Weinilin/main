package logic;

import java.util.Stack;
import java.util.ArrayList;
import application.Task;
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
    
    public boolean canRedo() {
        return !redo.isEmpty();
    }
    
    public ArrayList<Task> undo() {
        UndoRedoRecorder latestChange = undo.pop();
        latestChange.performUndo();
        redo.push(latestChange);
        return latestChange.getTaskList();
    }
    
    public ArrayList<Task> redo() {
        UndoRedoRecorder lastChange = redo.pop();
        lastChange.performUndo();
        undo.push(lastChange);
        return lastChange.getTaskList();
    }
}

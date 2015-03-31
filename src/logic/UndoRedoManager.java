package logic;

import java.util.Stack;

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
    
    public void undo() {
        UndoRedoRecorder latestChange = undo.pop();
        latestChange.performUndo();
        redo.push(latestChange);
    }
    
    public void redo() {
        UndoRedoRecorder lastChange = redo.pop();
        lastChange.performUndo();
        undo.push(lastChange);
    }
}

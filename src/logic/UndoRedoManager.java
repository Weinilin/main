package logic;

import java.util.Stack;

class UndoRedoManager {

    private static UndoRedoManager undoRedoManager;
    public Stack<UndoableCommandHandler> undo, redo;
    
    private UndoRedoManager() {
        undo = new Stack<UndoableCommandHandler>();
        redo = new Stack<UndoableCommandHandler>();
    }
    
    public static UndoRedoManager getInstance() {
        if (undoRedoManager == null) {
            undoRedoManager = new UndoRedoManager();
        }
        return undoRedoManager;
    }
}

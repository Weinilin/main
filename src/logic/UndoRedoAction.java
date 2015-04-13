//@author A0114463M
package logic;

import application.Task;
import storage.Memory;
/**
 * This class serves as a record for all UndoableCommandHandlers
 * when they perform a single change to memory class. There are two methods
 * in this class: undo and redo. 
 * 
 */
class UndoRedoAction {
    public static enum ActionType {
        ADD, DELETE, EDIT, MARK, UNMARK
    };
    Memory memory = Memory.getInstance();
    private ActionType action;
    private Task oldTask, newTask;
//  private String oldPath, newPath;

    /**
     * Construct a new new record associating with task changes of edit
     * @param action - ActionType other than SETLOCATION
     * @param oldTask - task that has been removed from memory
     * @param newTask - task that has been added to memory
     * 
     */
    UndoRedoAction(ActionType action, Task oldTask, Task newTask) {
        this.action = action;
        this.oldTask = oldTask;
        this.newTask = newTask;
    }

//    /**
//     * not implementing undo or redo for set location  
//     * Construct a new record associating with path changes
//     * @param action - Actiontype SETLOCATION
//     * @param oldPath - old path of storage
//     * @param newPath - new path of storage
//     */
//    UndoRedoAction(ActionType action, String oldPath, String newPath) {
//        this.action = action;
//        this.oldPath = oldPath;
//        this.newPath = newPath;
//    }

    ActionType getActionType() {
        return action;
    }

    Task getOldTask() {
        return oldTask;
    }

    Task getNewTask() {
        return newTask;
    }
    
//  * not implementing undo or redo for set location 
//    public String getOldPath() {
//        return oldPath;
//    }
//
//    public String getNewPath() {
//        return newPath;
//    }

    boolean undo() {
        switch (action) {
            case ADD:
                return (memory.removeTask(getNewTask()) != null);
            case DELETE:
                return (memory.addTask(getOldTask()) >= 0);                
            case EDIT:
                return ((memory.addTask(getOldTask()) == 1 && (memory.removeTask(getNewTask()) != null)));
            case MARK:
                memory.markUndone(getOldTask());
                return true;
            case UNMARK:
                memory.markDone(getOldTask());
            default:
                return false;
        }
    }

    boolean redo() {
        switch (action) {
            case ADD:
                return (memory.addTask(getNewTask()) >= 0);
            case DELETE:
                return (memory.removeTask(getOldTask()) != null);
            case EDIT:
                return ((memory.addTask(getNewTask()) == 1 && (memory.removeTask(getOldTask()) != null)));
            case MARK:
                memory.markDone(getNewTask());
                return true;
            case UNMARK:
                memory.markUndone(getNewTask());
                return true;
            default:
                return false;
        }
    }
}

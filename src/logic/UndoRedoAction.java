/*
 *@author A0114463M
 */
package logic;

import application.Task;
import storage.Memory;
/**
 * This class serves as a record for all UndoableCommandHandlers
 * when they perform a single change to memory class. There are two methods
 * in this class: undo and redo. 
 * 
 * @author A0114463M
 *
 */
class UndoRedoAction {
    public static enum ActionType {
        ADD, DELETE, EDIT, MARK
    };
    Memory memory = Memory.getInstance();
    private ActionType action;
    private Task oldTask, newTask;
    private String oldPath, newPath;

    /**
     * Construct a new new record associating with task changes of edit
     * @param action - ActionType other than SETLOCATION
     * @param oldTask - task that has been removed from memory
     * @param newTask - task that has been added to memory
     * 
     */
    public UndoRedoAction(ActionType action, Task oldTask, Task newTask) {
        this.action = action;
        this.oldTask = oldTask;
        this.newTask = newTask;
    }

    /**
     * Construct a new record associating with path changes
     * @param action - Actiontype SETLOCATION
     * @param oldPath - old path of storage
     * @param newPath - new path of storage
     */
    public UndoRedoAction(ActionType action, String oldPath, String newPath) {
        this.action = action;
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    public ActionType getActionType() {
        return action;
    }

    public Task getOldTask() {
        return oldTask;
    }

    public Task getNewTask() {
        return newTask;
    }

    public String getOldPath() {
        return oldPath;
    }

    public String getNewPath() {
        return newPath;
    }

    public boolean undo() {
        switch (action) {
            case ADD:
                return (memory.removeTask(newTask) != null);
            case DELETE:
                return (memory.addTask(oldTask) == 1);                
            case EDIT:
                return ((memory.addTask(oldTask) == 1 && (memory.removeTask(newTask) != null)));
            case MARK:
                memory.markUndone(oldTask);
                return true;
            default:
                return false;
        }
    }

    public boolean redo() {
        switch (action) {
            case ADD:
                return (memory.addTask(newTask) == 1);
            case DELETE:
                return (memory.removeTask(oldTask) != null);
            case EDIT:
                return ((memory.addTask(newTask) == 1 && (memory.removeTask(oldTask) != null)));
            case MARK:
                memory.markDone(newTask);
                return true;
            default:
                return false;
        }
    }
}

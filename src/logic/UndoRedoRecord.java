package logic;

import application.Task;
import storage.Memory;
import storage.DatabaseLocationChanger;
/**
 * This class serves as a record for all UndoableCommandHandlers
 * when they perform a single change to memory class. There are two methods
 * in this class: undo and redo. 
 * 
 * @author A0114463M
 *
 */
class UndoRedoRecord {
	private static enum ActionType {
		ADD, DELETE, EDIT, MARK, SETLOCATION
	};
	Memory memory = Memory.getInstance();
	private ActionType action;
	private Task oldTask, newTask = null;
	private String oldPath, newPath = null;
	
	/**
	 * Construct a new new record associating with task changes
	 * @param action - ActionType other than SETLOCATION
	 * @param oldTask - task that has been removed from memory
	 * @param newTask - task that has been added to memory
	 * 
	 */
	public UndoRedoRecord(ActionType action, Task oldTask, Task newTask) {
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
	public UndoRedoRecord(ActionType action, String oldPath, String newPath) {
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
				return (memory.addTask(oldTask));
			case EDIT:
				return ((memory.addTask(oldTask) && (memory.removeTask(newTask) != null)));
			//case MARK:
			//	return (memory.)
			case SETLOCATION:
				DatabaseLocationChanger dlc = new DatabaseLocationChanger();
				return dlc.setDatabaseLocation(oldPath);
			default:
				return false;
		}
	}
	
	public boolean redo() {
		switch (action) {
			case ADD:
				return (memory.removeTask(oldTask) != null);
			case DELETE:
				return (memory.addTask(newTask));
			case EDIT:
				return ((memory.addTask(newTask) && (memory.removeTask(oldTask) != null)));
				//case MARK:
				//	return (memory.)
			case SETLOCATION:
				DatabaseLocationChanger dlc = new DatabaseLocationChanger();
				return dlc.setDatabaseLocation(newPath);
			default:
				return false;
		}
	}
}

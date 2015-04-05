package logic;

import java.util.ArrayList;

import application.Task;

/**
 * All handlers that performs changes to memory (such as add, delete or edit)
 * shall extend this class. UndoHandlers and RedoHandlers shall also extend this
 * class to access the changes and be able to revoke them.
 * UndoRedoManager is provided in this class such that the changes made to memory
 * by handlers are recorded, which is allowed to undo or redo these actions for 
 * UndoHandler and RedoHandler
 * @author A0114463M
 *
 */
abstract class UndoableCommandHandler extends CommandHandler {
    
    UndoRedoManager undoRedoManager = UndoRedoManager.getInstance();
    
    @Override
    abstract protected ArrayList<String> getAliases();

    @Override
    abstract protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception;

    @Override
    abstract public String getHelp();

    /**
     * Record the changes made to memory and generate a UndoRedoRecorder
     * for recording purpose. TaskList in LogicController is then updated
     * @param taskList
     */
    abstract void recordChanges(ArrayList<Task> taskList);
}

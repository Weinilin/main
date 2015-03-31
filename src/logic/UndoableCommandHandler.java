package logic;

import java.util.ArrayList;
import java.util.Stack;

import application.Task;

abstract class UndoableCommandHandler extends CommandHandler {
    
    UndoRedoManager undoRedoManager = UndoRedoManager.getInstance();
    
    @Override
    abstract protected ArrayList<String> getAliases();

    @Override
    abstract protected String execute(String command, String parameter, ArrayList<Task> taskList);

    @Override
    abstract public String getHelp();

    abstract void undo();    

}

package logic;

import java.util.ArrayList;
import java.util.Stack;

import application.Task;

abstract class UndoableCommandHandler extends CommandHandler {

	Stack<UndoableCommandHandler> undo, redo;

    UndoableCommandHandler() {
        undo = new Stack();
      	redo = new Stack();
    }

    UndoableCommandHandler(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    abstract protected ArrayList<String> getAliases();

    @Override
    abstract protected String execute(String command, String parameter);

    @Override
    abstract public String getHelp();

    abstract void undo();
}

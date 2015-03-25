package logic;

import java.util.ArrayList;

import application.Task;

abstract class UndoableCommandHandler extends CommandHandler {

	ArrayList<Task> taskList;
	
	UndoableCommandHandler() {
		taskList = new ArrayList<Task>();
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

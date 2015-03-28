/**
 * 
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

/**
 * CommandHandler for "clear" function
 * 
 * @author A0114463M 
 */

class ClearHandler extends UndoableCommandHandler {
    private static final String HELP_MESSAGE = "clear\n\t delete all tasks\n";
    private static final String ALL_CLEAR_MESSAGE = "All tasks cleared\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("clear", "clr", "dall", "deleteall"));
    private ArrayList<Task> oldTaskList;
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        oldTaskList = new ArrayList<Task>(taskList);
        undoRedoManager.undo.push(this);
        taskList.clear();
        memory.removeAll();
        return ALL_CLEAR_MESSAGE;
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }


    @Override
    void undo() {
        for (Task task: oldTaskList) {
            memory.addTask(task);
        }
    }
}

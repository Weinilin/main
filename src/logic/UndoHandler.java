package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

/**
 * CommandHandler for "undo" function
 * This class will access the undoRedoManager and see if undo actions can be
 * executed. Prompt message to user if no action or not enough actions can be undone,
 * otherwise revert the changes of memory and tasklist in LogicController.
 * 
 * @author A0114463M
 *
 */
class UndoHandler extends UndoableCommandHandler {

    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("undo", "u"));

    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        String[] token = parameter.split(" ");
        if (token[0].toLowerCase().equals("help")) {
            return getHelp();
        }

        if (undoRedoManager.canUndo()) {
            taskList.clear();
            taskList.addAll(undoRedoManager.undo());
            return "The last change has been discarded\n";
        }
        else {                  
            return "Nothing to undo\n";
        }
    }

    @Override
    public String getHelp() {
        // TODO Auto-generated method stub
        return "undo\n\t revoke latest change";
    }
    
    @Override
    void recordMemoryChanges(ArrayList<Task> taskList) {
        
    }
}

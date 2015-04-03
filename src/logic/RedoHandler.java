package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

/**
 * CommandHandler for "redo" function
 * This class will access the undoRedoManager and see if redo actions can be
 * executed. Prompt message to user if no action or not enough actions can be undone,
 * otherwise revert the changes of memory and tasklist in LogicController
 * @author A0114463M
 *
 */
class RedoHandler extends UndoableCommandHandler {

    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("redo", "r"));

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

        if (undoRedoManager.canRedo()) {
            taskList.clear();
            taskList.addAll(undoRedoManager.redo());
            return "The last undo has been discarded\n";
        }
        else {                  
            return "Nothing to redo\n";
        }
    }

    @Override
    public String getHelp() {
        // TODO Auto-generated method stub
        return "redo\n\t discard the undo actions";
    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        
    }
}

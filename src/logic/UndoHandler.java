package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.IndexParser;
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
    private static final String UNDO_STEPS_MESSAGE = "Revoked last %1$s changes\n";
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        int steps = 0;
        String[] token = parameter.split(" ");
        if (isHelp(token[0])) {
            return getHelp();
        }

        if (isUndoOnly(parameter)) {
            if (undoRedoManager.canUndo()) {
                updateTaskList(taskList);
                return "The last change has been discarded\n";
            }
            else {                  
                return "Nothing to undo\n";
            }
        }
        else {
            try {
                IndexParser ip = new IndexParser(token[0]);
                steps = ip.getIndex();
            } catch (NumberFormatException nfe) {
                return "Invalid steps to undo\n";
            }
            if (steps > undoRedoManager.getUndoSize()) {
                return "Not enought steps to undo\n";
            }
            else {
                for (int i = 0; i < steps; i++) {
                    updateTaskList(taskList);
                }
                return String.format(UNDO_STEPS_MESSAGE, Integer.toString(steps));
            }
                
        }
    }

    private void updateTaskList(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(undoRedoManager.undo());
    }

    private boolean isUndoOnly(String parameter) {
        return parameter.toLowerCase().trim().equals("");
    }

    private boolean isHelp(String parameter) {
        return parameter.toLowerCase().equals("help");
    }

    @Override
    public String getHelp() {
        return "undo\n\t revoke latest change";
    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        
    }
}

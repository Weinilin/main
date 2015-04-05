package logic;

import java.util.ArrayList;
import java.util.Arrays;

import parser.IndexParser;
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
    private static final String REDO_STEPS_MESSAGE = "Repeated last %1$s changes\n";
            

    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        int steps = 0;
        String[] token = parameter.split(" ");
        if (token[0].toLowerCase().equals("help")) {
            return getHelp();
        }
        
        if (isRedoOnly(parameter)) {
            if (undoRedoManager.canRedo()) {
                updateTaskList(taskList);
                return "The last undo has been discarded\n";
            }
            else {                  
                return "Nothing to redo\n";
            }
        }
        else {
            if (isAll(token[0])) {
                while (undoRedoManager.canRedo()) {
                    updateTaskList(taskList);
                }
                return "All undos has been repeated\n";
            }
            try {
                IndexParser ip = new IndexParser(token[0]);
                steps = ip.getIndex();
            } catch (NumberFormatException nfe) {
                return "Invalid steps to redo\n";
            }
            if (steps > undoRedoManager.getRedoSize()) {
                return "Not enought steps to redo\n";
            }
            else {
                for (int i = 0; i < steps; i++) {
                    updateTaskList(taskList);
                }
                return String.format(REDO_STEPS_MESSAGE, Integer.toString(steps));
            }
        }
    }
    
    private boolean isAll(String string) {
        return string.toLowerCase().trim().equals("all");
    }
    
    private void updateTaskList(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(undoRedoManager.redo());
    }
    
    private boolean isRedoOnly(String parameter) {
        return parameter.toLowerCase().trim().equals("");
    }
    
    @Override
    public String getHelp() {
        return "redo\n\t discard the undo actions";
    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        
    }
}

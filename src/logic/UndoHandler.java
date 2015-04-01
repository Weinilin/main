package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;

class UndoHandler extends UndoableCommandHandler {

    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("undo", "u"));

    @Override
    protected ArrayList<String> getAliases() {
        // TODO Auto-generated method stub
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        String[] token = parameter.split(" ");
        if (token[0].toLowerCase().equals("help")) {
            return getHelp();
        }

        if (undoRedoManager.canUndo()) {
            updateTaskList(undoRedoManager.undo());
            return "Revoked latest change\n";
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

    /**
     * update the taskList in CommandHandler
     */
    private void updateTaskList(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(0, memory.getTaskList());
    }

}

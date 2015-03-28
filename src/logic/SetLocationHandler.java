package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import storage.DatabaseLocationChanger;

public class SetLocationHandler extends UndoableCommandHandler {

    private static final String INVALID_PATH_MESSAGE = "Invalid path!\n";
    private static final String NEW_LOCATION_MESSAGE = "The file is now saved to %1$s\n";

    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("setlocation", "saveto", "st"));
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        DatabaseLocationChanger dlc = new DatabaseLocationChanger();
//        if (dlc.setDatabaseLocation(parameter) {
//            return String.format(NEW_LOCATION_MESSAGE, parameter);
//        }
//        else {
//            return INVALID_PATH_MESSAGE;
//        }
        return "";
    }

    @Override
    public String getHelp() {
        // TODO Auto-generated method stub
        return "setlocation <path>\n\t set the directory that tasks will be saved to\n";
    }

    @Override
    void undo() {

    }
    
    @Override
    public CommandHandler getNewInstance() {
        return new SetLocationHandler();
    }
}

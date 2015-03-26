/**
 * 
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter) {
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

    }
}

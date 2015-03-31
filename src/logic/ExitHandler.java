package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
/**
 * exit command for CommandHandler
 * 
 * @author A0114463M
 *
 */
public class ExitHandler extends CommandHandler {

    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("exit", "q", "quit"));
    @Override
    protected ArrayList<String> getAliases() {
        // TODO Auto-generated method stub
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        return null;
    }

    @Override
    public String getHelp() {
        return "";
    }

}

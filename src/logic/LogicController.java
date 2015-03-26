package logic;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

import java.util.logging.*;

import storage.Memory;
import application.Task;

/**
 * The main component that takes charge of deciding which 
 * handlers to call and execute
 * 
 * @author A0114463M
 */
public class LogicController {
    private static LogicController logicController;

    private Logger logger;

    static ArrayList<Task> taskList = new ArrayList<Task>();
    private List<CommandHandler> handlers = new ArrayList<CommandHandler>();
    private Hashtable<String, CommandHandler> handlerTable = 
            new Hashtable<String, CommandHandler>();

    private LogicController() {
        //logger = Logger.getLogger(this.getClass());
        //logger.log("Initiating LogicController");
        taskList = new ArrayList<Task>(Memory.getInstance().getTaskList());
        addHandlers();
        initializeHandlers();
    }

    /**
     * add all handlers to arraylist for iteration
     */
    private void addHandlers() {
        handlers.add(new AddHandler());
        handlers.add(new ClearHandler());
        handlers.add(new DeleteHandler());
        handlers.add(new EditHandler());
        handlers.add(new ExitHandler());
        handlers.add(new MarkHandler());
        handlers.add(new SetLocationHandler());
        handlers.add(new ShowHandler());
    }

    public static LogicController getInstance() {
        if (logicController == null) {
            logicController = new LogicController();
        }
        return logicController;
    }

    /**
     * Take the input from user from UI and call respective
     * handlers. Return the feedback to UI after each execution
     * @param userCommand
     * @return - feedback to user
     */
    public String executeCommand(String userCommand) {
        String command = userCommand.split(" ")[0];
        if (!handlerTable.containsKey(command)) {
            return "Unknown command!\n";
        }

        CommandHandler handler = handlerTable.get(command);
        String parameter = userCommand.replaceFirst(Pattern.quote(command), "").trim();
        return handler.execute(command, parameter);
    }

    /**
     * associate the aliases of each handlers to its owner
     * such that correct handlers can be invoked for execution
     * conflicting aliases will log error
     */
    private void initializeHandlers() {
        for (CommandHandler handler: handlers) {
            ArrayList<String> aliases = handler.getAliases();
            for (String cmd: aliases) {
                if (handlerTable.containsKey(cmd)) {
                    logger.log(Level.INFO, "conflicting command "+ cmd);
                }
                else {
                    handlerTable.put(cmd, handler);
                }
            }
        }
    }

    /**
     * return the taskList in LogicController
     * @return 
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

}

package logic;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;
import java.util.logging.Level;

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

    private static final Logger logger = 
            Logger.getLogger(LogicController.class.getName());
    private ArrayList<Task> taskList = new ArrayList<Task>();
    private CommandHandler[] handlers = {
                                         new AddHandler(),
                                         new ClearHandler(),
                                         new DeleteHandler(),
                                         new EditHandler(),
                                         new EditTimeHandler(),
                                         new EditDescriptionHandler(),
                                         new ExitHandler(),
                                         new MarkHandler(),
                                         new UndoHandler(),
                                         new RedoHandler(),
                                         new SetLocationHandler(),
                                         new ShowHandler()};

    private Hashtable<String, CommandHandler> handlerTable = 
            new Hashtable<String, CommandHandler>();

    private LogicController() {
        logger.entering(getClass().getName(), "Initiating LogicController");
        taskList = new ArrayList<Task>(Memory.getInstance().searchStatus("undone"));
        initializeHandlers();
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
        String[] inputToken = userCommand.trim().split(" ");
        
        if (isHelp(inputToken[0])) {
            if (isHelpOnly(inputToken) && (isUnknownCommand(inputToken[1]))) {
                String help = "";
                help = prepareHelp(help);
                return help;
            }
            else {
                return handlerTable.get(inputToken[1]).getHelp();
            }
        }
        
        if (isUnknownCommand(inputToken[0])) {
            return executeAddByDefault(userCommand);            
        }
        
        CommandHandler handler = handlerTable.get(inputToken[0]);
                
        String parameter = userCommand.replaceFirst(Pattern.quote(inputToken[0]), "").trim();
        return handler.execute(inputToken[0], parameter, taskList);
    }


    private String prepareHelp(String help) {
        for (CommandHandler handler: handlers)
            help += handler.getHelp();
        return help;
    }


    private boolean isHelpOnly(String[] inputToken) {
        return inputToken.length == 0;
    }

    private boolean isHelp(String command) {
        return command.trim().toLowerCase().equals("help");
    }
    private boolean isUnknownCommand(String command) {
        return !handlerTable.containsKey(command);
    }


    private String executeAddByDefault(String userCommand) {
        return handlerTable.get("add").execute("add", userCommand, taskList);
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

package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

import database.Memory;
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
	private List<CommandHandler> handlers = new ArrayList<CommandHandler>();
	private Hashtable<String, CommandHandler> handlerTable = 
			new Hashtable<String, CommandHandler>();
	
	private LogicController() {
		logger.entering(getClass().getName(), "Initiating LogicController");
		taskList = new ArrayList<Task>(Memory.getInstance().getTaskList());
		handlers.add(new AddHandler());
		handlers.add(new ClearHandler());
		handlers.add(new DeleteHandler());
		handlers.add(new EditHandler());
		handlers.add(new ExitHandler());
		handlers.add(new MarkHandler());
		handlers.add(new ShowHandler());
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
		String command = userCommand.split(" ")[0];
		if (!handlerTable.containsKey(command)) {
			return "Unknown command!\n";
		}
		
		CommandHandler handler = handlerTable.get(command);
		String parameter = userCommand.replaceFirst(Pattern.quote(command), "").trim();
		return handler.execute(command, parameter, taskList);
	}
	
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
	
	public ArrayList<Task> getTaskList() {
		return taskList;
	}
}

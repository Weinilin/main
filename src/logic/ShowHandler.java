package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

import application.Task;

/**
 * Command handler for showing/searching tasks
 * 
 * 
 * showing all tasks in the taskList by passing no
 * parameters OR search for tasks containing the 
 * keyword
 * 
 * @author A0114463M
 */
class ShowHandler extends CommandHandler{

    private static final String HELP_MESSAGE = "show\n\t show all tasks in TaskManager\nshow [keyword]\n\t show all tasks containing the keyword\n";
    private static final String EMPTY_LIST_MESSAGE = "There is no %1$stask\n";
    private static final String NOT_FOUND_MESSAGE = "No task containing %1$s\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("show", "s", "display", "search"));
    private static final Logger showLogger =
            Logger.getLogger(DeleteHandler.class.getName());

    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        
        String[] token = parameter.split(" ");
        if (isHelp(token)) {
            return getHelp();
        }

        if (isSearchStatus(parameter)) {
            return showStatus(parameter, taskList);
        }
        else {
            ArrayList<Task> searchList = memory.searchDescription(parameter);
            if (searchList.isEmpty()) {
                showLogger.log(Level.FINE, "no results found containing " + parameter);
                return String.format(NOT_FOUND_MESSAGE, parameter);
            }
            else {
                updateTaskList(taskList, searchList);
                showLogger.log(Level.FINE, "show all tasks containing keyword " + parameter);
                return "";
            }
        }        	
    }

    
    /**
     * check if the user is searching different status of tasks
     * @param parameter input from user
     * @return true if any of the status is needed to be shown
     */
    private boolean isSearchStatus(String parameter) {
        parameter = parameter.trim().toLowerCase();
        return (parameter.equals("undone") || parameter.equals("done") ||
                parameter.equals("all") || parameter.equals(""));
    }
    
    /**
     * Show different status of tasks 
     * @param parameter the status of the task that user want to show
     * @param taskList taskList to be displayed
     * @return feedback string
     */
    private String showStatus(String parameter, ArrayList<Task> taskList) {
        String feedback = new String();
        if (isEmpty(parameter) || isUndone(parameter)) {
            showUndoneTasks(taskList);
            if (taskList.isEmpty()) {
                feedback = String.format(EMPTY_LIST_MESSAGE, "undone ");
            }
            else {
                feedback = "Displaying all undone tasks\n";
            }
        }
        else {
            if (isDone(parameter)) {
                showDoneTasks(taskList);
                if (taskList.isEmpty()) {
                    feedback = String.format(EMPTY_LIST_MESSAGE, "done ");
                }
                else {
                    feedback = "Displaying all done tasks\n";
                }
            } 
            else if (isAll(parameter)){
                showAllTasks(taskList);
                if (taskList.isEmpty()) {
                    feedback = String.format(EMPTY_LIST_MESSAGE, "");
                }
                else {
                    feedback = "Displaying all tasks\n";
                }
            }
        }
        return feedback;
    }
    
    /**
     * check if the string given is String "all"
     * @param parameter
     * @return true if the String is "all"
     */
    private boolean isAll(String parameter) {
        return parameter.trim().toLowerCase().equals("all");
    }

    /**
     * check if the string given is String "done"
     * @param parameter
     * @return true if the String is "done"
     */
    private boolean isDone(String parameter) {
        return parameter.trim().toLowerCase().equals("done");
    }
    
    /**
     * show all the tasks that has the status of done
     * @param taskList
     */
    private void showDoneTasks(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(0, memory.searchStatus("done"));
    }
    
    /**
     * show all the tasks that has the status of undone
     * @param taskList
     */
    private void showUndoneTasks(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(0, memory.searchStatus("undone"));
    }
    
    /**
     * show all the tasks 
     * @param taskList
     */
    private void showAllTasks(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(0, memory.getTaskList());
    }

    /**
     * update the taskList in LogicController and write changes to Memory
     * @param taskList
     * @param searchList
     */
    private void updateTaskList(ArrayList<Task> taskList,
            ArrayList<Task> searchList) {
        taskList.clear();
        taskList.addAll(0, searchList);
    }

    /**
     * check if the string given is empty
     * @param parameter
     * @return true if the string is empty
     */
    private boolean isEmpty(String parameter) {
        return parameter.trim().equals("");
    }
    
    /**
     * check if the parameter given is String "undone"
     * @param parameter
     * @return true if the string is "undone"
     */
    private boolean isUndone(String parameter) {
        return parameter.trim().equals("undone");
    }

    /**
     * check if user if looking for help 
     * @param token
     * @return
     */
    private boolean isHelp(String[] token) {
        return token[0].toLowerCase().trim().equals("help");
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}

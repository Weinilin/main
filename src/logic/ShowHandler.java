//@author A0114463M
package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.text.ParseException;
import application.Task;
import parser.MainParser;

/**
 * Command handler for showing/searching tasks
 * 
 * 
 * showing all tasks in the taskList by passing no
 * parameters OR search for tasks containing the 
 * keyword
 */
class ShowHandler extends CommandHandler{

    private static final String HELP_MESSAGE = "show\n\t show all tasks in TaskManager\nshow [keyword]\n\t show all tasks containing the keyword\n";
    private static final String EMPTY_LIST_MESSAGE = "There is no %1$stask\n";
    private static final String FOUND_DATE_MESSAGE = "Showing tasks on %1$s\n";
    private static final String NOT_FOUND_DATE_MESSAGE = "No tasks on %1$s\n";
    private static final String FOUND_DATE_BEWTWEEN_MESSAGE = "Showing tasks from %1$s to %2$s\n";
    private static final String NOT_FOUND_DATE_BETWEEN_MESSAGE = "There are no task from %1$s to %2$s\n";
    private static final String NOT_FOUND_MESSAGE = "No task containing %1$s\n";
    private static final String FOUND_MESSAGE = "Showing all tasks containing \"%1$s\"\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("show", "s", "display", "search"));

    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception{
        
        String[] token = parameter.split(" ");
        if (isHelp(token)) {
            return getHelp();
        }

        if (isSearchStatus(parameter)) {
            return searchStatus(parameter, taskList);
        }
        
        parameter = extractWeek(parameter);    
        
        MainParser parser = new MainParser(parameter);
        String searchType = parser.getTaskType();
        if (isKeywordSearch(searchType)) {
            String keyword = parser.getDescription();
            return searchKeyword(keyword, taskList);            
        }    
        else if (searchType.equals("deadline")) {
            if (parser.getEndTime().equals("23:59")) {
                String date = parser.getEndDate().split(" ")[1];
                return searchDate(date, taskList);
            } 
            else {
                String time = parser.getEndDate().split(" ")[1] + " " + parser.getEndTime();
                return searchTime(time, taskList);
            }
        }
        else if (searchType.equals("time task")) {
            if (parser.getEndTime().equals("23:59") && parser.getStartTime().equals("23:59")) {
                String startDate = parser.getStartDate().split(" ")[1];
                String endDate = parser.getEndDate().split(" ")[1];
                return searchDate(startDate, endDate, taskList);
            }
            else {
                String startTime = parser.getStartDate().split(" ")[1] + " " + parser.getStartTime(),
                        endTime = parser.getEndDate().split(" ")[1] + " " + parser.getEndTime();
                return searchTime(startTime, endTime, taskList);
            }
        }
        else {
            return "";
        }
    }

    private String extractWeek(String parameter) {
        parameter = parameter.replaceFirst("this week", "this Monday to Friday");
        parameter = parameter.replaceFirst("next week", "next Monday to next Friday");
        return parameter;
    }

    private boolean isKeywordSearch(String searchType) {
        return searchType.equals("floating task");
    }

    /**
     * search the memory by the keyword if the task contains the keyword
     * @param keyword keyword to be searched
     * @param taskList - tasklist to be shown to user
     * @return feedback of search result
     */
    private String searchKeyword(String keyword, ArrayList<Task> taskList) {
        ArrayList<Task> searchList = memory.searchDescription(keyword);
        if (searchList.isEmpty()) {
            return String.format(NOT_FOUND_MESSAGE, keyword);
        }
        else {
            updateTaskList(taskList, searchList);
            return String.format(FOUND_MESSAGE, keyword);
        }
    }
    
    /**
     * search the memory for tasks that occurs on the date specified.
     * Deadline task with same date of the intended date will be added to taskList.
     * Time task that occurs on the day will be added to taskList
     * @param date intended date to be searched
     * @param taskList taskList to be shown to user
     * @return feedback string
     */
    private String searchDate(String date, ArrayList<Task> taskList) {
        ArrayList<Task> searchList = new ArrayList<Task>();
        try {
            searchList = memory.searchDate(date);
        } catch (ParseException pe) {
            return "Error parsing date\n";
        }
        if (searchList.isEmpty()) {
            return String.format(NOT_FOUND_DATE_MESSAGE, date);
        }
        else {
            updateTaskList(taskList, searchList);
            return String.format(FOUND_DATE_MESSAGE, date);
        }
    }
    
    /**
     * search the memory for tasks that occurs on the time specified.
     * Deadline task with time of one hour difference will be added to taskList.
     * Time task that occurs on the time will be added to taskList
     * @param date intended date to be searched
     * @param taskList taskList to be shown to user
     * @return feedback string
     */
    private String searchTime(String time, ArrayList<Task> taskList) {
        ArrayList<Task> searchList = new ArrayList<Task>();
        try {
            searchList = memory.searchTime(time);
        } catch (ParseException pe) {
            return "Error parsing time\n";
        }
        if (searchList.isEmpty()) {
            return String.format(NOT_FOUND_DATE_MESSAGE, time);
        }
        else {
            updateTaskList(taskList, searchList);
            return String.format(FOUND_DATE_MESSAGE, time);
        }
    }
    
    /**
     * search the memory for tasks that occurs on the time specified.
     * Deadline task with time of one hour difference will be added to taskList.
     * Time task that occurs on the time will be added to taskList
     * @param date intended date to be searched
     * @param taskList taskList to be shown to user
     * @return feedback string
     */
    private String searchTime(String startTime, String endTime, ArrayList<Task> taskList) {
        ArrayList<Task> searchList = new ArrayList<Task>();
        try {
            searchList = memory.searchTime(startTime, endTime);
        } catch (ParseException pe) {
            return "Error parsing time\n";
        }
        if (searchList.isEmpty()) {
            return String.format(NOT_FOUND_DATE_BETWEEN_MESSAGE, startTime, endTime);
        }
        else {
            updateTaskList(taskList, searchList);
            return String.format(FOUND_DATE_BEWTWEEN_MESSAGE, startTime, endTime);
        }
    }
    /**
     * search the memory for tasks that occurs on the date specified.
     * Deadline task with same date of the intended date will be added to taskList.
     * Timetask that occurs on the day will be added to taskList
     * @param date intended date to be searched
     * @param taskList taskList to be shown to user
     * @return feedback string
     */
    private String searchDate(String startDate, String endDate, ArrayList<Task> taskList) {
        ArrayList<Task> searchList = new ArrayList<Task>();
        try {
            searchList = memory.searchDate(startDate, endDate);
        } catch (ParseException pe) {
            return "Error parsing date\n";
        }
        if (searchList.isEmpty()) {
            return String.format(NOT_FOUND_DATE_BETWEEN_MESSAGE, startDate, endDate);
        }
        else {
            updateTaskList(taskList, searchList);
            return String.format(FOUND_DATE_BEWTWEEN_MESSAGE, startDate, endDate);
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
    private String searchStatus(String parameter, ArrayList<Task> taskList) {
        String feedback = new String();
        if (isEmpty(parameter) || isUndone(parameter)) {
            searchUndoneTasks(taskList);
            if (taskList.isEmpty()) {
                feedback = String.format(EMPTY_LIST_MESSAGE, "undone ");
            }
            else {
                feedback = "Displaying all undone tasks\n";
            }
        }
        else {
            if (isDone(parameter)) {
                searchDoneTasks(taskList);
                if (taskList.isEmpty()) {
                    feedback = String.format(EMPTY_LIST_MESSAGE, "done ");
                }
                else {
                    feedback = "Displaying all done tasks\n";
                }
            } 
            else if (isAll(parameter)){
                searchAllTasks(taskList);
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
    private void searchDoneTasks(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(0, memory.searchStatus("done"));
    }
    
    /**
     * show all the tasks that has the status of undone
     * @param taskList
     */
    private void searchUndoneTasks(ArrayList<Task> taskList) {
        taskList.clear();
        taskList.addAll(0, memory.searchStatus("undone"));
    }
    
    /**
     * show all the tasks 
     * @param taskList
     */
    private void searchAllTasks(ArrayList<Task> taskList) {
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

//@author A0114463M
package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.IndexParser;
import parser.MainParser;
/**
 * CommandHandler for "mark" function
 * 
 * Mark a task as done by typing the keyword following 
 * by the index of task that is intended to be marked
 */
class UnmarkHandler extends UndoableCommandHandler {

    private static final String HELP_MESSAGE = "mark [index]\n\t mark a task as done\n";
    private static final String INVALID_INDEX_MESSAGE = "Nothing to unmark for %1$s\n";
    private static final String MARKED_MESSAGE = "Marked %1$s as done and archieved\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("unmark", "undone", "um"));
    private ArrayList<Task> unmarkedTask = new ArrayList<Task>();

    private String goodFeedback = "",
                   badFeedback = "";
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception {
        reset();

        String[] token = parameter.split(" ");
        if (isHelp(token) || isEmpty(parameter)) {
            return getHelp();
        }

        try {
            IndexParser ip = new IndexParser(parameter);
            ip.getIndex();
            unmarkByIndex(taskList, token);
        } catch (NumberFormatException nfe) {
            unmarkByKeyword(taskList, parameter);
        }              
        recordChanges(taskList);
        
        for (Task undone: unmarkedTask) {
            memory.markUndone(undone);
        }        
        if (!goodFeedback.equals("")) {
            return String.format(MARKED_MESSAGE, goodFeedback);
        } else {
            return String.format(INVALID_INDEX_MESSAGE, badFeedback);
        }
        
    }

    /**
     * Mark the first task that contains the keyword given
     * @param taskList
     * @param parameter
     * @throws Exception
     */
    private void unmarkByKeyword(ArrayList<Task> taskList, String parameter)
            throws Exception {
        MainParser parser = new MainParser(parameter);
        ArrayList<Task> searchList = memory.searchDescription(parser.getDescription());
        markKeyword:
        for (Task task: searchList) {
            if (taskList.contains(task) && task.getStatus().equals("done")) {
                unmarkedTask.add(task);
                goodFeedback += task.getDescription();
                break markKeyword;                     
            }                
        }
    }

    /**
     * Mark the tasks by the indexes shown in UI
     * @param taskList
     * @param token
     */
    private void unmarkByIndex(ArrayList<Task> taskList, String[] token) throws Exception{
        IndexParser parser;
        for (String t: token) {
            parser = new IndexParser(t);
            try {                
                int index = parser.getIndex() - 1;
                unmarkedTask.add(taskList.get(index));
                goodFeedback += t + " ";
            } catch (NumberFormatException nfe) {
                badFeedback += t + " ";
            } catch (IndexOutOfBoundsException iob) {
                badFeedback += t + " ";
            } 
        }
    }

    /**
     * reset the handler when it is called
     */
    @Override
    void reset() {
        unmarkedTask.clear();
        goodFeedback = "";
        badFeedback = "";
    }

    /**
     * check if the argument user typed is empty
     * @param parameter
     * @return
     */
    private boolean isEmpty(String parameter) {
        return parameter.trim().equals("");
    }

    /**
     * check if user is looking for help
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
    
    void recordChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder markRecorder = new UndoRedoRecorder(taskList);
        
        for (Task task: unmarkedTask) {
            taskList.get(taskList.indexOf(task)).setStatus("undone");
            memory.markUndone(task);
            markRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.UNMARK, task, task));
        }
        if (!markRecorder.isEmpty()) {
            markRecorder.recordUpdatedList(taskList);
            undoRedoManager.addNewRecord(markRecorder);
        }
    }
}

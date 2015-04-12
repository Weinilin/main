//@author A0114463M
package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.IndexParser;
import parser.MainParser;

/**
 * CommandHandler for "delete" function
 * 
 * Deleting task is achieved by "delete [index]"
 * The task is removed from memory upon a success removal and the task
 * is returned in String. null is returned for failed removal.
 *
 */
class DeleteHandler extends UndoableCommandHandler {

    private static final String HELP_MESSAGE = "delete <index>\n\t remove the respective task of the index from TaskManager\n";
    private static final String GOODFEEDBACK_MESSAGE = "Removed task %1$s\n";
    private static final String BADFEEDBACK_MESSAGE = "Invalid input %1$s\n";	
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("delete", "d", "remove", "-"));
    private ArrayList<Task> removedTask = new ArrayList<Task>();
    private String goodFeedback = new String(), 
                   badFeedback = new String(),
                   feedback = new String();    
    private int index;
    @Override
    public ArrayList<String> getAliases() {
        // TODO Auto-generated method stub
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception{

        reset();
        String[] token = parameter.split(" ");
        if (isHelp(token) || isEmptyParameter(parameter)) {
            return getHelp();
        }

        if (isDeleteAll(token)) {
            ClearHandler clrHandler = new ClearHandler();
            return clrHandler.execute(token[0], "", taskList);
        }

        IndexParser ip = new IndexParser(parameter);
        try {
            index = ip.getIndex() - 1;
            deleteByIndex(taskList, token);
        } catch (NumberFormatException nfe) {
            deleteByKeyword(taskList, parameter);
        }
        
        recordChanges(taskList);
        
        if (!goodFeedback.equals("")) {
            feedback += String.format(GOODFEEDBACK_MESSAGE, goodFeedback);
        }
        if (!badFeedback.equals("")) {
            feedback += String.format(BADFEEDBACK_MESSAGE, badFeedback);
        }
        
        return feedback;
    }

    /**
     * remove the first occurence of the task containing the keyword
     * @param taskList - tasklist shown to user
     * @param parameter - the keyword that user intend to delete
     * @throws Exception
     */
    private void deleteByKeyword(ArrayList<Task> taskList, String parameter)
            throws Exception {
        MainParser parser = new MainParser(parameter);
        ArrayList<Task> searchList = memory.searchDescription(parser.getDescription());
        removeKeyword:
        for (Task task: searchList) {
            if (taskList.contains(task)) {
                removedTask.add(task);
                goodFeedback += task.getDescription();
                break removeKeyword;
            }
        }
//            for (Task task: taskList) {
//                if (task.getDescription().contains(parameter)) {
//                    removedTask.add(task);
//                    goodFeedback += task.getDescription();
//                    break;                    
//                }                
//            }
    }

    /**
     * @param taskList
     * @param token
     */
    private void deleteByIndex(ArrayList<Task> taskList, String[] token) throws Exception{
        for (String t: token) {
            IndexParser ip = new IndexParser(t);
            try {
                index = ip.getIndex() - 1;
            } catch (NumberFormatException nfe) {
                badFeedback = appendFeedback(badFeedback, t);
                continue;
            }
            try {
                removedTask.add(taskList.get(index));				
                goodFeedback = appendFeedback(goodFeedback, t);
            } catch (IndexOutOfBoundsException iob) {
                badFeedback = appendFeedback(badFeedback, t);
            } 
        }
    }
    
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder deleteRecorder = new UndoRedoRecorder(taskList);
        for (Task task: removedTask) {
            deleteRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.DELETE, task, task));
            taskList.remove(task);
            memory.removeTask(task);
        }
        if (!deleteRecorder.isEmpty()) {
            deleteRecorder.recordUpdatedList(taskList);
            undoRedoManager.addNewRecord(deleteRecorder);
        }
    }

    /**
     * reset the status of handler
     */
    @Override
    void reset() {
        removedTask.clear();
        goodFeedback = "";
        badFeedback = "";
        feedback = "";
        index = -1;
    }
    /**
     * append the indexes for valid deletion or invalid input
     * @param feedback
     * @param index
     * @return - feedback string
     */
    private String appendFeedback(String feedback, String index) {
        feedback += index + " ";
        return feedback;
    }


    /**
     * decide whether is delete all
     * @param token
     * @return true if the world given is all
     */
    private boolean isDeleteAll(String[] token) {
        return token[0].toLowerCase().trim().equals("all");
    }


    /**
     * check if the argument user typed is empty
     * @param parameter
     * @return true if the parameter given is empty
     */
    private boolean isEmptyParameter(String parameter) {
        return parameter.trim().equals("");
    }


    /**
     * check if user is looking for help
     * @param token
     * @return true if the parameter given is help only
     */
    private boolean isHelp(String[] token) {
        return token.length == 1 && token[0].toLowerCase().trim().equals("help");
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }

}

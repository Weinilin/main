package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.MainParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CommandHandler for "mark" function
 * 
 * Mark a task as done by typing the keyword following 
 * by the index of task that is intended to be marked
 * @author A0114463M
 */
class MarkHandler extends UndoableCommandHandler {

    private static final String HELP_MESSAGE = "mark [index]\n\t mark a task as done\n";
    private static final String INVALID_INDEX_MESSAGE = "Index %1$s is invalid! Please check yout input\n";
    private static final String MARKED_MESSAGE = "Marked %1$s as done. It has been archieved\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("mark", "done", "m"));
    private static final Logger markLogger = 
            Logger.getLogger(MarkHandler.class.getName());
    private ArrayList<Task> markedTask = new ArrayList<Task>();
    private ArrayList<Integer> markedTaskIndex = new ArrayList<Integer>();

    private String goodFeedback = "",
                   badFeedback = "";
    Integer index;
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

        MainParser ip = new MainParser(parameter);
        try {
            index = Integer.parseInt(token[0]);
            markByIndex(taskList, token);
        } catch (NumberFormatException nfe) {
            ArrayList<Task> searchList = memory.searchDescription(parameter);
            markKeyword:
            for (Task task: searchList) {
                if (taskList.contains(task)) {
                    markedTask.add(task);
                    markedTaskIndex.add(taskList.indexOf(task) + 1);
                    goodFeedback += task.getDescription();
                    break markKeyword;                     
                }                
            }
        }              
        recordChanges(taskList);
        
        for (Task done: markedTask) {
            memory.markDone(done);
        }        
        if (!goodFeedback.equals("")) {
            return String.format(MARKED_MESSAGE, goodFeedback);
        } else {
            return String.format(INVALID_INDEX_MESSAGE, badFeedback);
        }
        
    }

    /**
     * @param taskList
     * @param token
     */
    private void markByIndex(ArrayList<Task> taskList, String[] token) throws Exception{
        MainParser parser;
        for (String t: token) {
            parser = new MainParser(t);
            try {
                index = parser.getIndex();
                markedTask.add(taskList.get(index));
                markedTaskIndex.add(index);
                goodFeedback += t + " ";
            } catch (NumberFormatException nfe) {
                badFeedback += t + " ";
            } catch (IndexOutOfBoundsException iob) {
                badFeedback += t + " ";
                markLogger.log(Level.WARNING, "Invalid index", iob);
            } 
        }
    }

    /**
     * reset the handler when it is called
     */
    private void reset() {
        markedTask.clear();
        markedTaskIndex.clear();
        goodFeedback = "";
        badFeedback = "";
        index = null;
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
        for (int index: markedTaskIndex) {
            Task task = taskList.get(index);
            memory.markDone(task);
            markRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.MARK, task, task));
        }
        for (Task task: markedTask) {
            taskList.remove(task);
        }
        if (!markRecorder.isEmpty()) {
            markRecorder.recordUpdatedList(taskList);
            undoRedoManager.addNewRecord(markRecorder);
        }
    }
}

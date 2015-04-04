package logic;

import java.util.ArrayList;
import java.util.Arrays;

import application.Task;
import parser.IndexParser;

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
            Arrays.asList("mark", "done"));
    private static final Logger markLogger = 
            Logger.getLogger(MarkHandler.class.getName());
    private ArrayList<Task> markedTask = new ArrayList<Task>();
    private ArrayList<Integer> markedTaskIndex = new ArrayList<Integer>();
    
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
        reset();
        markLogger.entering(getClass().getName(), "Entering marking");

        String[] token = parameter.split(" ");
        if (isHelp(token) || isEmpty(parameter)) {
            return getHelp();
        }

        String goodFeedback = "";
        String badFeedback = "";
        IndexParser ip;
        int index;
        for (String t: token) {
            ip = new IndexParser(t);
            try {
                index = ip.getIndex() - 1;
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
     * reset the handler when it is called
     */
    private void reset() {
        markedTask.clear();
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
            markRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.MARK, task, task));
            task.setStatus("done");
            taskList.remove(task);
            memory.markDone(task);
        }
        if (!markRecorder.isEmpty()) {
            markRecorder.recordUpdatedList(taskList);
            undoRedoManager.addNewRecord(markRecorder);
        }
    }
}

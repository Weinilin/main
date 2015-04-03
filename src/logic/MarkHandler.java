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
 */
class MarkHandler extends UndoableCommandHandler {

    private static final String HELP_MESSAGE = "mark [index]\n\t mark a task as done\n";
    private static final String INVALID_INDEX_MESSAGE = "Index invalid! Please check yout input\n";
    private static final String MARKED_MESSAGE = "Marked %1$s as done\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("mark", "done"));
    private static final Logger markLogger = 
            Logger.getLogger(MarkHandler.class.getName());
    private ArrayList<Task> markedTask = new ArrayList<Task>();
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) {
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
            index = ip.getIndex() - 1;
            try {
                taskList.get(index).setStatus("done");
                markedTask.add(taskList.get(index));
                goodFeedback += t + " ";
            } catch (IndexOutOfBoundsException iob) {
                badFeedback += t + " ";
                markLogger.log(Level.WARNING, "Invalid index", iob);
            } 
        }
        
        for (Task done: markedTask) {
            memory.markDone(done);
        }
        
        return String.format(MARKED_MESSAGE, goodFeedback);
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
    
    @Override
    void recordMemoryChanges(ArrayList<Task> taskList) {
        
    }
}

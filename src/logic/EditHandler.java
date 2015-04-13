//@author A0114463M
package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import application.Task;
import application.TaskCreator;
import parser.IndexParser;
import application.TaskComparator;


/**
 * CommandHandler for "edit" function
 * 
 * other edit handlers will be invoked by specifying the field 
 * (description or time) and index. If no field is selected, the whole
 * task will be updated. Index refers to the index of task in
 * the ArrayList then the information that is intended to change
 *
 *
 */
class EditHandler extends UndoableCommandHandler {

    private static final String INVALID_INDEX_MESSAGE = "Invalid index! Please check your input\n";
    private static final String HELP_MESSAGE = "edit <index> <new task>\n\t edit the task by specifying the index\n"
            + "edit description <index> <new description>\n\t update the task description only\n"
            + "edit time <index> <time>\n\t update the time of task \n";
    private static String CHANGE_MESSAGE = "Updated %1$s to %2$s\n";
    private ArrayList<String> aliases = new ArrayList<String>(
            Arrays.asList("edit", "e", "update"));
    Task oldTask, newTask = null;
    String feedback = "";
    @Override
    protected ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception {
        reset();
        String feedback = "";
        String[] token = parameter.split(" ");
        if (isHelp(token) || isEmpty(parameter)) {
            return getHelp();
        }

        IndexParser ip = new IndexParser(parameter);		
        int index = ip.getIndex() - 1;
        if (index < 0 || index > taskList.size()) {
            return INVALID_INDEX_MESSAGE;
        }

        try {
            oldTask = taskList.get(index);
        } catch (IndexOutOfBoundsException iob) {
            return INVALID_INDEX_MESSAGE;
        }

        switch (token[0].toLowerCase()) {
            case "description":
            case "des":
                EditDescriptionHandler edh = new EditDescriptionHandler();
                return edh.execute(token[0], parameter.replaceFirst(token[0], "").trim(), taskList);
            case "time":
                EditTimeHandler eth = new EditTimeHandler();
                return eth.execute(token[0], parameter.replaceFirst(token[0], "").trim(), taskList);
            default:
                try {
                    index = ip.getIndex() - 1;
                } catch (NumberFormatException nfe) {
                    return INVALID_INDEX_MESSAGE;
                }  
                try {
                    TaskCreator taskCreator = new TaskCreator(parameter.replaceFirst(token[0], ""));
                    newTask = taskCreator.createNewTask();         
                    feedback = taskCreator.getFeedback();   
                } catch (Exception e) {
                    return feedback;
                }        
                if (isEmptyDescription(newTask)) {
                    return "No description for new task\n";
                }
                try {
                    oldTask = taskList.get(index);                   
                } catch (IndexOutOfBoundsException iob) {
                    return INVALID_INDEX_MESSAGE;
                }
                break;
        }

        performEdit(taskList);
        return feedback;
    }

    /**
     * reset the handler when it is called
     */
    @Override
    void reset() {
        newTask = null;
        oldTask = null;
        feedback = "";
    }
    
    /**
     * execute the changes in Memory and update the feedback
     * @param taskList
     */
    private void performEdit(ArrayList<Task> taskList) {
        if (newTask != oldTask && oldTask != null) {
            memory.removeTask(oldTask);
            recordChanges(taskList);            
            Collections.sort(taskList, new TaskComparator());
            feedback = String.format(CHANGE_MESSAGE, oldTask.getDescription(), newTask.getDescription());
        }
        else {
            feedback = "New task is identical to some existing task\n";
        }             
    }
    
    
    private boolean isEmptyDescription(Task task) {
        return isEmpty(task.getDescription());
    }
       
    @Override
    void recordChanges(ArrayList<Task> taskList) {
        UndoRedoRecorder editRecorder = new UndoRedoRecorder(taskList);
        editRecorder.appendAction(new UndoRedoAction(UndoRedoAction.ActionType.EDIT, oldTask, newTask));
        updateTaskList(taskList);
        editRecorder.recordUpdatedList(taskList);
        undoRedoManager.addNewRecord(editRecorder);
    }
    
    private void updateTaskList(ArrayList<Task> taskList) {
        taskList.remove(oldTask);
        taskList.add(newTask);
    }
    
    /**
     * check if the argument user typed is empty
     * @param parameter
     * @return true if 
     */
    private boolean isEmpty(String parameter) {
        return parameter.trim().equals("");
    }

    /**
     * check if user the user is looking for help
     * @param token
     * @return true if the parameter given is help
     */
    private boolean isHelp(String[] token) {
        return token[0].toLowerCase().trim().equals("help");
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }


}

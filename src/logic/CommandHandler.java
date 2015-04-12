/*
 *@author A0114463M
 */

package logic;

import java.util.ArrayList;

import application.Task;
import storage.Memory;
/**
 * All handlers in Logic (except undo) shall extend this class so that all
 * handlers have functions of execute() and getHelp()
 */
public abstract class CommandHandler {

    Memory memory = Memory.getInstance();
    /**
     * get all the aliases acceptable to the command such that
     * they can invoke the handler
     * @return string that contains the command 
     */
    abstract protected ArrayList<String> getAliases();
    
    /**
     * execute the command based on the input from user (such as "add",
     * "delete", etc) and the parameter for executing the command. Return
     * the feedback as a String object for now.
     *@param command - command extracted from user input
     * @param parameter - parameter for executing the command based on user input
     *@return feedback String to UI after each execution of the command
     */
    abstract protected String execute(String command, String parameter, ArrayList<Task> taskList) throws Exception;

    /**
     * get help String for each of the commands when user types "[command] help"
     */
    abstract public String getHelp();   
    
}

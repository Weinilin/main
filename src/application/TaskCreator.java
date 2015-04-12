//@author A0114463M
package application;

import parser.MainParser;
/**
 * A constructor for task objects, returns feedback when constructing the new 
 * object to its caller
 *
 */
public class TaskCreator {

    private String taskInformation = "";
    private String feedback = "";
    
    public TaskCreator(String taskInformation) {
        this.taskInformation = taskInformation;
    }
    
    /**
     * creates a new task by a string containing the information of the task.
     * It will call the respective parsers to get information about the new
     * task
     *@param taskInformation - the input from user that specifies the task
     *@return new task object created based on the input from user.
     */
    public Task createNewTask() throws Exception{
        MainParser parser = new MainParser(taskInformation);
        feedback = parser.getFeedback();
        String description = parser.getDescription();
        String taskType = parser.getTaskType();
        String startDateTime = parser.getStartDate() + " " + parser.getStartTime();
        String endDateTime = parser.getEndDate() + " " + parser.getEndTime();
 
        Task newTask = new Task(taskType, description, startDateTime, endDateTime, "undone");
        return newTask;
    }
    
    public void setNewString(String newInformation) {
        taskInformation = newInformation;
    }
    
    public String getFeedback() {
        return feedback;
    }
}

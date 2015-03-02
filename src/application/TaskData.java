package application;

import java.io.Serializable;

public class TaskData implements Serializable	{
	/****Attribute****/
	private static final long serialVersionUID = 0;
	
	private String taskType;
	private String description;
	private DateTime startDateTime;
	private DateTime endDateTime;
	private DateTime deadline;
	private String status;
	
	//(improvement to be made)
	//1. need to check validity of data
	//2. separate constructor for different task type
	public TaskData(String taskType, String description, DateTime startDateTime, DateTime endDateTime, DateTime deadline, String status) {
		taskType = this.taskType;
		description = this.description;
		startDateTime = this.startDateTime;
		endDateTime = this.endDateTime;
		deadline = this.deadline;
		status = this.status;
	}
	
	public void setStartDateTime(DateTime newStartDateTime) {
		startDateTime = newStartDateTime;
	}
	
	public void setEndDateTime(DateTime newEndDateTime) {
		endDateTime = newEndDateTime;
	}
	
	public void setDeadline(DateTime newDeadline) {
		deadline = newDeadline;
	}

	public void setStatus(String newStatus) {
		status = newStatus;
	}
	
	public void setTasktype(String newTaskType) {
		taskType = newTaskType;
	}
	
	public String getTaskType() {
		return taskType;
	}
	
	public DateTime getStartDateTime() {	
		return startDateTime;
	}
	
	public DateTime getEndDateTime(){
		return endDateTime;
	}
	
	public DateTime getDeadLine(){
		return deadline;
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getDescription() {
		return description;
	}

}

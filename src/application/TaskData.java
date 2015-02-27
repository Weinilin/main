package application;

public class TaskData {
	/****Attribute****/
	private String taskID;
	private String taskType;
	private String description;
	private DateTime startDateTime;
	private DateTime endDateTime;
	private DateTime deadline;
	private String status;
	
	//(improvement to be made)
	//1. need to check validity of data
	//2. separate constructor for different task type
	public TaskData(String taskID, String taskType, String description, DateTime startDateTime, DateTime endDateTime, DateTime deadline, String status) {
		this.taskID = taskID;
		this.taskType = taskType;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.deadline = deadline;
		this.status = status;
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
	
	public String getTaskID() {
		return taskID;
	}

}

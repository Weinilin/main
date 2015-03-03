package application;

public class TaskData {
	/****Attribute****/
	private String taskID;
	private String taskType;
	private String description;
	private String startDateTime;
	private String endDateTime;
	private String deadline;
	private String status;
	
	// dummy constructor delete in futre
	public TaskData() {
		
	}
	//(improvement to be made)
	//1. need to check validity of data
	//2. separate constructor for different task type
	public TaskData(String taskID, String taskType, String description, String startDateTime, String endDateTime, String deadline, String status) {
		this.taskID = taskID;
		this.taskType = taskType;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.deadline = deadline;
		this.status = status;
	}
	
	public void setStartDateTime(String newStartDateTime) {
		startDateTime = newStartDateTime;
	}
	
	public void setEndDateTime(String newEndDateTime) {
		endDateTime = newEndDateTime;
	}
	
	public void setDeadline(String newDeadline) {
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
	
	public String getStartDateTime() {	
		return startDateTime;
	}
	
	public String getEndDateTime(){
		return endDateTime;
	}
	
	public String getDeadLine(){
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

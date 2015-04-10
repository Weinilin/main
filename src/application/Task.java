package application;

import java.util.ArrayList;

/**
 * Task is a class that is used to store all the information of a task to be stored in TaskManager
 * 
 * @author A0113966Y
 *
 */

public class Task {
	
	private static final String[] dataField = {
		"Task Type",
		"Description",
		"Start Time",
		"End Time",
		"Status"
	};
	
	private String taskType;
	private String description;
	private String startDateTime;
	private String endDateTime;
	private String status;
		
	public Task(Task task) {
		this.taskType = task.getTaskType();
		this.description = task.getDescription();
		this.startDateTime = task.getStartDateTime();
		this.endDateTime = task.getEndDateTime();
		this.status = task.getStatus();
	}
	
	public Task(String taskType, String description, String startDateTime, String endDateTime, String status) {
		this.taskType = taskType;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.status = status;
	}
	
	public Task(ArrayList<String> taskInformation) {
		this.taskType = taskInformation.get(0);
		this.description = taskInformation.get(1);
		this.startDateTime = taskInformation.get(2);
		this.endDateTime = taskInformation.get(3);
		this.status = taskInformation.get(4);
	}
	public void setStartDateTime(String newStartDateTime) {
		startDateTime = newStartDateTime;
	}
	
	public void setEndDateTime(String newEndDateTime) {
		endDateTime = newEndDateTime;
	}

	public void setStatus(String newStatus) {
		status = newStatus;
	}
	
	public void setTaskType(String newTaskType) {
		taskType = newTaskType;
	}
	
	public void setDescription(String newDescription) {
		description = newDescription;
	}
	
	public String getTaskType() {
		return taskType;
	}
	
	public String getStartDateTime() {	
		return startDateTime;
	}
	
	public String getEndDateTime() {
		return endDateTime;
	}
	
	public String getStartDate() {
		return startDateTime.split(" ")[1];
	}
	
	public String getStartTime() {
		return startDateTime.split(" ")[2];
	}
	
	public String getEndDate(){
		return endDateTime.split(" ")[1];
	}
	
	public String getEndTime(){
		return endDateTime.split(" ")[2];
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDateTime() {
		
		return getStartDateTime() + " - " + getEndDateTime();
		
	}
	
	public boolean isDone() {
		if (status.equals("done")) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String str = "";
		
		String[] data = {
			getTaskType(),
			getDescription(),
			getStartDateTime(),
			getEndDateTime(),
			getStatus()
		};
		
		for (int i = 0; i < dataField.length; i++) {
			str += String.format("%-20s", dataField[i]) + ": " + String.format("%s", data[i]) + "\n";
		}

		return str;
	}
	
	public String[] toStringArray() {
		String[] stringArray = {getTaskType(), getDescription(), getStartDateTime(), getEndDateTime(), getStatus()};
		return stringArray;
	}

}

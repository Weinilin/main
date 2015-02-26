package application;

public class UndoData {
	private String operation;
	private TaskData taskData;
	
	public UndoData(String operation, TaskData taskData) {
		this.operation = operation;
		this.taskData = taskData;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public TaskData getTaskData() {
		return taskData;
	}
}

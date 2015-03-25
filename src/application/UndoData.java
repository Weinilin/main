package application;

public class UndoData {
	private String operation;
	private Task taskData;
	
	public UndoData(String operation, Task taskData) {
		this.operation = operation;
		this.taskData = taskData;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public Task getTaskData() {
		return taskData;
	}
}

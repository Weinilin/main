package ui;

import java.util.ArrayList;

import application.Task;
import application.TaskPrinter;

public class TaskListUI {
	ArrayList<Task> taskList;
	
	public TaskListUI(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}
	
	public void showTask() {
		TaskPrinter tp = new TaskPrinter();
		ArrayList<ArrayList<String>> taskListMatrix = new ArrayList<ArrayList<String>>();
		for (Task task : taskList) {
			ArrayList<String> taskInformation = new ArrayList<String>();
			taskInformation.add(task.getTaskType());
			taskInformation.add(task.getDescription());
			taskInformation.add(task.getStartDateTime());
			taskInformation.add(task.getEndDateTime());
			taskInformation.add(task.getDeadline());
			taskInformation.add(task.getStatus());
			taskListMatrix.add(taskInformation);
		}
		
		tp.print(taskListMatrix);
	}
}

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
		ArrayList<ArrayList<String>> taskListMatrix1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> taskListMatrix2 = new ArrayList<ArrayList<String>>();

		
		ArrayList<String> dataFields = new ArrayList<String>();
		dataFields.add("No.");
		dataFields.add("Description");
		dataFields.add("Time");
		dataFields.add("Status");
		taskListMatrix1.add(dataFields);
		taskListMatrix2.add(dataFields);

		int taskNumber = 1;
		
		for (Task task : taskList) {
			ArrayList<String> taskInformation = new ArrayList<String>();
			taskInformation.add((Integer.toString(taskNumber++)));
			taskInformation.add(task.getDescription());
			taskInformation.add(task.getDateTime());
			taskInformation.add(task.getStatus());
			
			if (task.getTaskType().equals("floating task")) {
				taskListMatrix2.add(taskInformation);
			} else {
				taskListMatrix1.add(taskInformation);
			}
		}
		
		tp.print(taskListMatrix1);
		if (taskListMatrix2.size() > 1) {
			System.out.println("Floating task");
		}
		tp.print(taskListMatrix2);

	}
}

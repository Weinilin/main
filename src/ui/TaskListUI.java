package ui;

import java.util.ArrayList;

import logic.LogicController;
import application.Task;
import application.TaskPrinter;

public class TaskListUI {
	
	
	public TaskListUI() {
		
	}
	
	public void showTask() {
        LogicController lc = LogicController.getInstance();
        ArrayList<Task> taskList = lc.getTaskList();
		TaskPrinter tp = new TaskPrinter();

		
		ArrayList<ArrayList<String>> taskListMatrix1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> taskListMatrix2 = new ArrayList<ArrayList<String>>();

		
		ArrayList<String> dataFields1 = new ArrayList<String>();
		ArrayList<String> dataFields2 = new ArrayList<String>();

		dataFields1.add("No.");
		dataFields1.add("Description");
		dataFields1.add("Time");
		dataFields1.add("Status");
		
		dataFields2.add("No.");
		dataFields2.add("Description");
		dataFields2.add("Status");
		
		taskListMatrix1.add(dataFields1);
		taskListMatrix2.add(dataFields2);

		int taskNumber = 1;

		for (Task task : taskList) {
			if (task.getTaskType().equals("floating task")) {
				ArrayList<String> taskInformation = new ArrayList<String>();
				taskInformation.add((Integer.toString(taskNumber++)));
				taskInformation.add(task.getDescription());
				taskInformation.add(task.getStatus());
				taskListMatrix2.add(taskInformation);
			} else {
				ArrayList<String> taskInformation = new ArrayList<String>();
				taskInformation.add((Integer.toString(taskNumber++)));
				taskInformation.add(task.getDescription());
				taskInformation.add(task.getDateTime());
				taskInformation.add(task.getStatus());
				taskListMatrix1.add(taskInformation);
			}
		}

		if (taskListMatrix1.size() > 1) {
			System.out.println("****************************");
			System.out.println("* Time Tasks and Deadlines *");
			System.out.println("****************************");
			tp.printTasksWithTime(taskListMatrix1);
			System.out.println();

		}
		
		if (taskListMatrix2.size() > 1) {
			System.out.println("*****************");
			System.out.println("* Floating Task *");
			System.out.println("*****************");
			tp.printFloatingTask(taskListMatrix2);
	        System.out.println();

		}
		
	}
	
//	public ArrayList<Task> getTaskList() {
//	    return taskList;
//	}
	
	
}

package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import application.Task;

public class MemoryTest {

	@Test
	public void test() {
Memory memory = Memory.getInstance();
		String taskType;
		String description;
		String startDateTime;
		String endDateTime;
		String deadline;
		String status;

		ArrayList<String> task1 = new ArrayList<String>();
		taskType = "time task";
		task1.add(taskType);
		description = "task description";
		task1.add(description);
		startDateTime = "01/02/2015 17:00";
		task1.add(startDateTime);
		endDateTime = "01/02/2015 18:00";
		task1.add(endDateTime);
		deadline = "-";
		task1.add(deadline);
		status = "not done";
		task1.add(status);
		Task newTask1 = new Task(task1);
		memory.addTask(newTask1);
		
		ArrayList<String> task2 = new ArrayList<String>();
		taskType = "deadline";
		task2.add(taskType);
		description = "task description";
		task2.add(description);
		startDateTime = "-";
		task2.add(startDateTime);
		endDateTime = "-";
		task2.add(endDateTime);
		deadline = "03/04/2015 14:00";
		task2.add(deadline);
		status = "not done";
		task2.add(status);
		Task newTask2 = new Task(task2);
		memory.addTask(newTask2);
		
		ArrayList<String> task3 = new ArrayList<String>();
		taskType = "floating task";
		task3.add(taskType);
		description = "-";
		task3.add(description);
		startDateTime = "-";
		task3.add(startDateTime);
		endDateTime = "-";
		task3.add(endDateTime);
		deadline = "-";
		task3.add(deadline);
		status = "not done";
		task3.add(status);
		Task newTask3 = new Task(task3);
		memory.addTask(newTask3);
		
		ArrayList<String> dummy = new ArrayList<String>();
		taskType = "-";
		task3.add(taskType);
		description = "-";
		task3.add(description);
		startDateTime = "-";
		task3.add(startDateTime);
		endDateTime = "-";
		task3.add(endDateTime);
		deadline = "-";
		task3.add(deadline);
		status = "-";
		task3.add(status);
		Task newTask4 = new Task(dummy);
		memory.addTask(newTask4);
		
		memory.display();
	}

}

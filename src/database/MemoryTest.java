package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import application.Task;

public class MemoryTest {

	@Test
	public void test() {
Memory memory = Memory.getInstance();

		ArrayList<String> taskData1 = new ArrayList<String>();
		String taskType = "time task";
		taskData1.add(taskType);
		String description = "task 1";
		taskData1.add(description);
		String startDateTime = "04/04/2015 17:00";
		taskData1.add(startDateTime);
		String endDateTime = "04/04/2015 18:00";
		taskData1.add(endDateTime);
		String deadline = "-";
		taskData1.add(deadline);
		String status = "not done";
		taskData1.add(status);
		Task newTaskData1 = new Task(taskData1);
		memory.addTask(newTaskData1);
		
		
		memory.display();
	}

}

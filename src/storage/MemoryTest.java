//@author A0113966Y

package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import application.Task;
import application.TaskComparator;

/**
 * Unit testing for Memory
 * 
 * @author A0113966Y
 *
 */

public class MemoryTest {
	final int FEEDBACK_CLASHING_TASK = 3;
	final int FEEDBACK_NON_CLASHING_TASK = 1;
	
	Memory memory = Memory.getInstance();

	@Test
	public void test() {
		
		/*
		 * Test for remove all
		 */
		memory.removeAll();
		assertEquals(memory.getTaskList().size(), 0);

		/*
		 * Test for adding task
		 */
		Task task1 = new Task("deadline", "task 1", "- -", "Sat 28/03/2015 21:00", "undone");
		memory.addTask(task1);
		assertEquals(memory.contains(task1), true);
		
		Task task2 = new Task("floating task", "task 2", "- -", "- -", "done");
		memory.addTask(task2);
		assertEquals(memory.contains(task2), true);

		Task task3 = new Task("time task", "task 3", "Sun 01/02/2015 01:00", "Mon 02/02/2015 02:00", "undone");
		memory.addTask(task3);
		assertEquals(memory.contains(task3), true);
		
		Task task4 = new Task("floating task", "dummy", "-", "-", "undone");
		memory.addTask(task4);
		assertEquals(memory.contains(task4), true);

		/*
		 * Test for remove
		 */
		Task removedTask = memory.removeTask(task2);
		assertEquals(removedTask, task2);

		/*
		 * Test for search description
		 */
		ArrayList<Task> searchList = memory.searchDescription("task");
		ArrayList<Task> correctSearchList = new ArrayList<Task>();
		correctSearchList.add(task3);
		correctSearchList.add(task1);
		Collections.sort(correctSearchList, new TaskComparator());
		assertEquals(correctSearchList, searchList);

		/*
		 * Test for mark
		 */
		memory.markDone(1);
		assertEquals(memory.getTaskList().get(0).getStatus(), "done");
		
		/*
		 * Test for search status
		 */
		searchList = memory.searchStatus("undone");
		correctSearchList = new ArrayList<Task>();
		correctSearchList.add(task1);
		correctSearchList.add(task4);
		Collections.sort(correctSearchList, new TaskComparator());
		assertEquals(correctSearchList, searchList);
				
		/*
		 * Test for addTask() method using Equivalence Partition
		 * The partition are overlapping tasks, non-overlapping tasks, tasks contained within the interval of other tasks,
		 * tasks with interval enclosing the interval of other tasks, tasks with exactly the same time interval as other
		 * tasks
		 * 
		 */
		int feedback;
		
		//Testing for overlapping tasks
		resetMemory();
		Task clashingTask1 = new Task("time task", "clashingTask1", "Sat 28/03/2015 19:59", "Sat 28/03/2015 20:01", "undone");
		feedback = memory.addTask(clashingTask1);
		assertEquals(feedback, FEEDBACK_CLASHING_TASK);
		
		resetMemory();
		Task clashingTask2 = new Task("time task", "clashingTask2", "Sat 28/03/2015 20:59", "Sat 28/03/2015 21:01", "undone");
		feedback = memory.addTask(clashingTask2);
		assertEquals(feedback, FEEDBACK_CLASHING_TASK);
		
		//Testing for non-overlapping tasks
		resetMemory();
		Task nonOverlappingTask1 = new Task("time task", "nonOverlappingTask1", "Sat 28/03/2015 21:00", "Sat 28/03/2015 22:00", "undone");
		feedback = memory.addTask(nonOverlappingTask1);
		assertEquals(feedback, FEEDBACK_NON_CLASHING_TASK);
		
		resetMemory();
		Task nonOverlappingTask2 = new Task("time task", "nonOverlappingTask2", "Sat 28/03/2015 19:00", "Sat 28/03/2015 20:00", "undone");
		feedback = memory.addTask(nonOverlappingTask2);
		assertEquals(feedback, FEEDBACK_NON_CLASHING_TASK);
		
		//Testing for adding tasks with interval contained within the interval of existing tasks
		resetMemory();
		clashingTask1 = new Task("time task", "clashingTask1", "Sat 28/03/2015 20:01", "Sat 28/03/2015 20:59", "undone");
		feedback = memory.addTask(clashingTask1);
		assertEquals(feedback, FEEDBACK_CLASHING_TASK);
				
		//Testing for adding tasks with exactly the same time interval as existing task
		resetMemory();
		clashingTask1 = new Task("time task", "clashingTask1", "Sat 28/03/2015 20:00", "Sat 28/03/2015 21:00", "undone");
		feedback = memory.addTask(clashingTask1);
		assertEquals(feedback, FEEDBACK_CLASHING_TASK);
		
		//Testing for adding tasks with interval enclosing the interval of existing tasks
		resetMemory();
		clashingTask1 = new Task("time task", "clashingTask1", "Sat 28/03/2015 19:00", "Sat 28/03/2015 22:00", "undone");
		feedback = memory.addTask(clashingTask1);
		assertEquals(feedback, FEEDBACK_CLASHING_TASK);
	}
	
	public void resetMemory() {
		memory.removeAll();
		Task task = new Task("time task", "task", "Sat 28/03/2015 20:00", "Sat 28/03/2015 21:00", "undone");
		memory.addTask(task);
	}
}

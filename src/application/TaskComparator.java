package application;

import java.util.Comparator;

/**
 * TaskComparator is used to sort tasks stored in Memory
 * Time tasks and deadlines are sorted into chronological order before floating task
 * Starting time of time task and ending time of deadline are used for comparison
 * 
 * @author A0113966Y
 *
 */

public class TaskComparator implements Comparator<Task> {
	
	private static final int PRECEDENCE_TIME_TASK = 2;
	private static final int PRECEDENCE_DEADLINE = 2;
	private static final int PRECEDENCE_FLOATING_TASK = 1;

	public int compare(Task task1, Task task2) {
		if (!isEqualPrecedence(task1, task2)) {
			return comparePrecedence(task1, task2);
		} else {
			if (isFloatingTask(task1)) {
				return compareFloatingTask(task1, task2);
			} else {
				return compareDateTime(task1, task2);			
			}
		}
	}

	public int compareDateTime(Task task1, Task task2) {
		String dateTime1;
		String dateTime2;
		
		if (isTimeTask(task1)) {
			dateTime1 = task1.getStartDateTime();
		} else {
			dateTime1 = task1.getEndDateTime();
		}
		
		if (isTimeTask(task2)) {
			dateTime2 = task2.getStartDateTime();
		} else {
			dateTime2 = task2.getEndDateTime();
		}
		
		TimeAnalyser ta = new TimeAnalyser();
		long dateTime1InMilliseconds = ta.getDateTimeInMilliseconds(dateTime1);
		
		long dateTime2InMilliseconds = ta.getDateTimeInMilliseconds(dateTime2);

		if (dateTime1InMilliseconds <= dateTime2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}

	
	private boolean isTimeTask(Task task) {
		if (task.getTaskType().equals("time task")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isFloatingTask(Task task) {
		if (task.getTaskType().equals("floating task")) {
			return true;
		} else {
			return false;
		}
	}

	
	private int comparePrecedence(Task task1, Task task2) {
		if (task1.getTaskType().equals("floating task")) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private int compareFloatingTask(Task task1, Task task2) {
		String description1 = task1.getDescription();
		String description2 = task2.getDescription();
		
		return description1.compareTo(description2);
	}
	
	private int getPrecedence(Task task) {
		if (task.getTaskType().equals("time task")) {
			return PRECEDENCE_TIME_TASK;
		} else if (task.getTaskType().equals("deadline")) {
			return PRECEDENCE_DEADLINE;
		} else {
			return PRECEDENCE_FLOATING_TASK;
		}
		
	}
	
	private boolean isEqualPrecedence(Task task1, Task task2) {
		if (getPrecedence(task1) == getPrecedence(task2)) {
			return true;
		} else {
			return false;
		}
	}
	
}

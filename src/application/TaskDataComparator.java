package application;

import java.util.Comparator;

import parser.DateParser;

public class TaskDataComparator implements Comparator<Task> {
	private static final int PRECEDENCE_TIME_TASK = 3;
	private static final int PRECEDENCE_DEADLINE = 2;
	private static final int PRECEDENCE_FLOATING_TASK = 1;
	
	public int compare(Task task1, Task task2) {
		if (!isEqualPrecedence(task1, task2)) {
			return comparePrecedence(task1, task2);
		} else if (isDeadline(task1)) {
			return compareDeadline(task1, task2);
		} else if (isTimeTask(task1)) {
			return compareTimeTask(task1, task2);
		} else {
			return compareFloatingTask(task1, task2);
		}
	}
	
	private boolean isEqualPrecedence(Task task1, Task task2) {
		int precedenceOfTask1 = getPrecedence(task1);
		int precedenceOfTask2 = getPrecedence(task2);
		
		if (precedenceOfTask1 == precedenceOfTask2) {
			return true;
		}

		return false;
	}
	
	private boolean isDeadline(Task task) {
		if (task.getTaskType().equals("deadline")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isTimeTask(Task task) {
		if (task.getTaskType().equals("time task")) {
			return true;
		} else {
			return false;
		}
	}

	private int compareDeadline(Task task1, Task task2) {
		DateParser dateParser1 = new DateParser(task1.getDeadline());
		long deadline1InMilliseconds = dateParser1.getDateTimeInMilliseconds();
		
		DateParser dateParser2 = new DateParser(task2.getDeadline());
		long deadline2InMilliseconds = dateParser2.getDateTimeInMilliseconds();
		
		if (deadline1InMilliseconds <= deadline2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
	
	private int compareTimeTask(Task task1, Task task2) {
		DateParser dateParser1 = new DateParser(task1.getStartDateTime());
		long startDateTime1InMilliseconds = dateParser1.getDateTimeInMilliseconds();
		
		DateParser dateParser2 = new DateParser(task2.getStartDateTime());
		long startDateTime2InMilliseconds = dateParser2.getDateTimeInMilliseconds();
		
		if (startDateTime1InMilliseconds <= startDateTime2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
	
	private int compareFloatingTask(Task task1, Task task2) {
		String description1 = task1.getDescription();
		String description2 = task2.getDescription();
		
		return description1.compareTo(description2);
	}
	
	private int comparePrecedence(Task task1, Task task2) {
		int precedenceOfTask1 = getPrecedence(task1);
		int precedenceOfTask2 = getPrecedence(task2);
		
		if (precedenceOfTask1 <= precedenceOfTask2) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private int getPrecedence(Task task) {
		int precedence = 0;
		
		if (task.getTaskType().equals("time task")) {
			precedence = PRECEDENCE_TIME_TASK;
		}
		
		if (task.getTaskType().equals("deadline")) {
			precedence = PRECEDENCE_DEADLINE;
		}
		
		if (task.getTaskType().equals("floating task")) {
			precedence = PRECEDENCE_FLOATING_TASK;
		}
		
		return precedence;
	}
}

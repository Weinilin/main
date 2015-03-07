package application;

import java.util.Comparator;

import parser.DateParser;

public class TaskDataComparator implements Comparator<TaskData> {
	private static final int PRECEDENCE_TIME_TASK = 3;
	private static final int PRECEDENCE_DEADLINE = 2;
	private static final int PRECEDENCE_FLOATING_TASK = 1;
	
	public int compare(TaskData taskData1, TaskData taskData2) {
		if (!isEqualPrecedence(taskData1, taskData2)) {
			return comparePrecedence(taskData1, taskData2);
		} else if (isDeadline(taskData1)) {
			return compareDeadline(taskData1, taskData2);
		} else if (isTimeTask(taskData1)) {
			return compareTimeTask(taskData1, taskData2);
		} else {
			return compareFloatingTask(taskData1, taskData2);
		}
	}
	
	private boolean isEqualPrecedence(TaskData taskData1, TaskData taskData2) {
		int precedenceOfTaskData1 = getPrecedence(taskData1);
		int precedenceOfTaskData2 = getPrecedence(taskData2);
		
		if (precedenceOfTaskData1 == precedenceOfTaskData2) {
			return true;
		}

		return false;
	}
	
	private boolean isDeadline(TaskData taskData) {
		if (taskData.getTaskType().equals("deadline")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isTimeTask(TaskData taskData) {
		if (taskData.getTaskType().equals("time task")) {
			return true;
		} else {
			return false;
		}
	}

	private int compareDeadline(TaskData taskData1, TaskData taskData2) {
		DateParser dateParser1 = new DateParser(taskData1.getDeadline());
		long deadline1InMilliseconds = dateParser1.getDateTimeInMilliseconds();
		
		DateParser dateParser2 = new DateParser(taskData2.getDeadline());
		long deadline2InMilliseconds = dateParser2.getDateTimeInMilliseconds();
		
		if (deadline1InMilliseconds <= deadline2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
	
	private int compareTimeTask(TaskData taskData1, TaskData taskData2) {
		DateParser dateParser1 = new DateParser(taskData1.getStartDateTime());
		long startDateTime1InMilliseconds = dateParser1.getDateTimeInMilliseconds();
		
		DateParser dateParser2 = new DateParser(taskData2.getStartDateTime());
		long startDateTime2InMilliseconds = dateParser2.getDateTimeInMilliseconds();
		
		if (startDateTime1InMilliseconds <= startDateTime2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
	
	private int compareFloatingTask(TaskData taskData1, TaskData taskData2) {
		String description1 = taskData1.getDescription();
		String description2 = taskData2.getDescription();
		
		return description1.compareTo(description2);
	}
	
	private int comparePrecedence(TaskData taskData1, TaskData taskData2) {
		int precedenceOfTaskData1 = getPrecedence(taskData1);
		int precedenceOfTaskData2 = getPrecedence(taskData2);
		
		if (precedenceOfTaskData1 <= precedenceOfTaskData2) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private int getPrecedence(TaskData taskData) {
		int precedence = 0;
		
		if (taskData.getTaskType().equals("time task")) {
			precedence = PRECEDENCE_TIME_TASK;
		}
		
		if (taskData.getTaskType().equals("deadline")) {
			precedence = PRECEDENCE_DEADLINE;
		}
		
		if (taskData.getTaskType().equals("floating task")) {
			precedence = PRECEDENCE_FLOATING_TASK;
		}
		
		return precedence;
	}
}

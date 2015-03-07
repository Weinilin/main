package application;

import java.util.Comparator;

import parser.DateParser;

public class TaskDataComparator implements Comparator<TaskData> {
	private static final int PRECEDENCE_TIME_TASK = 3;
	private static final int PRECEDENCE_DEADLINE = 2;
	private static final int PRECEDENCE_FLOATING_TASK = 1;
	
	public int compare(TaskData taskData1, TaskData taskData2) {
		if (isEqualPrecedence(taskData1, taskData2)) {
			return comparePrecedence(taskData1, taskData2);
		} else {
			return compareTime(taskData1, taskData2);
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
	
	private int compareTime(TaskData taskData1, TaskData taskData2) {
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
	
	private int comparePrecedence(TaskData taskData1, TaskData taskData2) {
		int precedenceOfTaskData1 = getPrecedence(taskData1);
		int precedenceOfTaskData2 = getPrecedence(taskData2);
		
		if (precedenceOfTaskData1 <= precedenceOfTaskData2) {
			return -1;
		} else {
			return 1;
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

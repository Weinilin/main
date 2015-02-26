package application;
import java.util.Comparator;

public class TaskDataComparator implements Comparator<TaskData> {
	final int PRECEDENCE_TIMETASK = 3;
	final int PRECEDENCE_DEADLINE = 2;
	final int PRECEDENCE_FLOATING_TASK = 1;
	
	public int compare(TaskData taskData1, TaskData taskData2) {
		int precedenceOfTaskData1 = getPrecedence(taskData1);
		int precedenceOfTaskData2 = getPrecedence(taskData2);
		
		if (precedenceOfTaskData1 < precedenceOfTaskData2) {
			return -1;
		}
		
		if (precedenceOfTaskData1 > precedenceOfTaskData2) {
			return 1;
		}
		
		if (precedenceOfTaskData1 == precedenceOfTaskData2) {
			if (taskData1.getTaskType().equals("Deadline")) {
				return compareDeadlines(taskData1, taskData2);
			} else {
				return compareStartDateTime(taskData1, taskData2);	
			}
		}
		
		return 0;
	}	
	
	private int getPrecedence(TaskData taskdata) {
		int precedence = 0;
		String typeOfTaskData = taskdata.getTaskType();
		
		if (typeOfTaskData.equals("Deadline")) {
			precedence = PRECEDENCE_DEADLINE;
		} else if (typeOfTaskData.equals("Timetask")) {
			precedence = PRECEDENCE_TIMETASK;
		} else if (typeOfTaskData.equals("FloatingTask")) {
			precedence = PRECEDENCE_FLOATING_TASK;
		}
		
		return precedence;
	}
	
	private int compareDeadlines(TaskData taskData1, TaskData taskData2) {
		long deadline1InMilliseconds = taskData1.getDeadLine().getDateTimeInMilliseconds();
		long deadline2InMilliseconds = taskData2.getDeadLine().getDateTimeInMilliseconds();
		
		if (deadline1InMilliseconds <= deadline2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
	
	private int compareStartDateTime(TaskData taskData1, TaskData taskData2) {
		long startDateTime1InMilliseconds = taskData1.getStartDateTime().getDateTimeInMilliseconds();
		long startDateTime2InMilliseconds = taskData2.getStartDateTime().getDateTimeInMilliseconds();
		
		if (startDateTime1InMilliseconds <= startDateTime2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
}

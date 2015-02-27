package application;

import java.util.Comparator;

public class DeadlineComparator implements Comparator<TaskData> {
	public int compare(TaskData taskData1, TaskData taskData2) {
		long deadline1InMilliseconds = taskData1.getDeadLine().getDateTimeInMilliseconds();
		long deadline2InMilliseconds = taskData2.getDeadLine().getDateTimeInMilliseconds();
		
		if (deadline1InMilliseconds <= deadline2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
}

package application;

import java.util.Comparator;

public class TimetaskComparator implements Comparator<TaskData>{
	public int compare(TaskData taskData1, TaskData taskData2) {
		long startDateTime1InMilliseconds = taskData1.getStartDateTime().getDateTimeInMilliseconds();
		long startDateTime2InMilliseconds = taskData2.getStartDateTime().getDateTimeInMilliseconds();
		
		if (startDateTime1InMilliseconds <= startDateTime2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
}

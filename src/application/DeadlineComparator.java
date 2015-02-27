package application;

import java.util.Comparator;

import parser.DateParser;

public class DeadlineComparator implements Comparator<TaskData> {
	public int compare(TaskData taskData1, TaskData taskData2) {
		DateParser dateParser1 = new DateParser(taskData1.getDeadLine());
		long deadline1InMilliseconds = dateParser1.getDateTimeInMilliseconds();
		
		DateParser dateParser2 = new DateParser(taskData2.getDeadLine());
		long deadline2InMilliseconds = dateParser2.getDateTimeInMilliseconds();
		
		if (deadline1InMilliseconds <= deadline2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
}

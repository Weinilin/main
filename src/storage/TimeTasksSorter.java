package storage;

import java.util.Comparator;

import application.Task;
import application.TimeAnalyser;

public class TimeTasksSorter implements Comparator<Task> {
	public int compare(Task task1, Task task2) {
		
		String dateTime1 = task1.getEndDateTime();
		String dateTime2 = task2.getEndDateTime();
		
		TimeAnalyser ta = new TimeAnalyser();
		
		long dateTime1InMilliseconds = ta.getDateTimeInMilliseconds(dateTime1);
		
		long dateTime2InMilliseconds = ta.getDateTimeInMilliseconds(dateTime2);
		
		if (dateTime1InMilliseconds <= dateTime2InMilliseconds) {
			return -1;
		} else {
			return 1;
		}
	}
}

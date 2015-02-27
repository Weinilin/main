package application;

import java.util.Comparator;

import parser.DateParser;

public class TimetaskComparator implements Comparator<TaskData>{
	public int compare(TaskData taskData1, TaskData taskData2) {
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
}

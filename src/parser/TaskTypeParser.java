package parser;

public class TaskTypeParser {

	/**
	 * Determine the type of task.
	 * @param storageOfTime
	 * @return the task type.
	 */
	public static String getTaskType(String userInput) {
		DateTimeParser.getDateTime(userInput);
		int numberOfTimeInput = DateTimeParser.getNumberOfTime();
		String taskType = null;
 
		if (numberOfTimeInput == 2) {
			taskType = "time task";
		} else if (numberOfTimeInput == 1) {
			taskType = "deadline";
		} else if(numberOfTimeInput == 0) {
			taskType = "floating task";
		}
		
		assert taskType != null;
		return taskType;
	}
}


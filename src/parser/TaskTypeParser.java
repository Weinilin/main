package parser;
import java.util.ArrayList;




public class TaskTypeParser {

	/**
	 * Determine the type of task.
	 * @param storageOfTime
	 * @return the task type.
	 */
	public static String getTaskType() {
		int numberOfTimeInput = DateTimeParser.getNumberOfTime();	
		String taskType;

		if(numberOfTimeInput == 2){
			taskType = "time task";
		}
		else if(numberOfTimeInput == 1){
			taskType = "deadline";
		}
		else{
			taskType = "floating task";
		}
		return taskType;
	}
}


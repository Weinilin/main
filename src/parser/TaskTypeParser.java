package parser;
import java.util.ArrayList;




public class TaskTypeParser {

	/**
	 * Determine the type of task.
	 * @param storageOfTime
	 * @return the task type.
	 */
	public String extractTaskType(ArrayList<String> storageOfTime) {
		int numberOfTimeInput = getNumberOfTimeInput(storageOfTime);
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

	/**
	 * Get the number of time stored in the arrayList(storageOfTime)
	 * @param storageOfTime
	 * @return the number of time stored.
	 */
	private int getNumberOfTimeInput(ArrayList<String> storageOfTime) {
		int numberOfTimeInput = storageOfTime.size();
		return numberOfTimeInput;
	}
}


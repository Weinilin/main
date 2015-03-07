package parser;
import java.util.ArrayList;




public class TaskTypeParser {

	public static String extractTaskType(ArrayList<String> storageOfTime) {
		int numberOfTimeInput = getNumberOfTimeInput(storageOfTime);
		String taskType;

		if(numberOfTimeInput == 2){
			taskType = "time task";
		}
		else if(numberOfTimeInput == 1){
			taskType = "deadlines";
		}
		else{
			taskType = "floating task";
		}
		return taskType;
	}

	private static int getNumberOfTimeInput(ArrayList<String> storageOfTime) {
		int numberOfTimeInput = storageOfTime.size();
		return numberOfTimeInput;
	}

}


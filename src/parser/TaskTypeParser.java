package parser;

public class TaskTypeParser {

	public String extractTaskType(ArrayList<String> storageOfTime) {
		int numberOfTimeInput = getNumberOfTimeInput(storageOfTime);
		String taskType;

		if(numberOfTimeInput == 2){
			taskType = "timedTask";
		}
		else if(numberOfTimeInput == 1){
			taskType = "deadlinesTask";
		}
		else{
			taskType = "floatingTask";
		}
		return taskType;
	}

	private int getNumberOfTimeInput(ArrayList<String> storageOfTime) {
		int numberOfTimeInput = storageOfTime.size();
		return numberOfTimeInput;
	}

}


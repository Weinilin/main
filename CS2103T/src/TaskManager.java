import java.util.ArrayList;
import java.util.Scanner;


public class TaskManager {
		private ArrayList<TaskData> uncompletedTasks;
		private ArrayList<TaskData> completedTasks;
		private static final String WELCOME_MESSAGE = "Welcome to TaskManager!";
		private static final String PROMPT_MESSAGE = "command: ";
		private static final String SPLIT_PARAMETER = "\\s+";
		private static final String NULL_STRING = null;
		private static final String NULL_ERROR_MESSAGE = "command type string cannot be null!";
		private static final String TIME_TASK_KEYWORD_NUMBER_1 = "at";
		private static final String TIME_TASK_KEYWORD_NUMBER_2 = "from";
		private static final String DEADLINE_TASK_KEYWORD_NUMBER_1 = "by";
		private static final String DEADLINE_TASK_KEYWORD_NUMBER_2 = "before";
		
		private enum COMMAND_TYPE {
			ADD_TASK, UNDO, DELETE_TASK
		};
		
		public TaskManager()	{
			
		}
		
		private void spillTheCommand(String userCommand){
			String taskType = identifyTaskType(userCommand);
			String 
			
		}
		
		
		private String identifyTaskType(String userCommand) {
			if(userCommand.contains(TIME_TASK_KEYWORD_NUMBER_1) || 
					userCommand.contains(TIME_TASK_KEYWORD_NUMBER_2)){
				return "timeTask"; 
			}
			else if(userCommand.contains(DEADLINE_TASK_KEYWORD_NUMBER_1) ||
					userCommand.contains(DEADLINE_TASK_KEYWORD_NUMBER_2)){
				return "deadlineTask";
			}
			else{
				return "floatingTask";
			}
		}

		public static void main (String[] args)	{
			Scanner sc = new Scanner(System.in);
			printWelcomeMessage(WELCOME_MESSAGE);
			
			executeProgram(sc);
		}

		private static void executeProgram(Scanner sc) {
			
			while(true){
				printCommandMessage();
				String command = sc.nextLine();
				String userCommand = command;
				executeCommand(userCommand);						
			}
		}

		private static void executeCommand(String userCommand) {
			String commandTypeString = getFirstWord(userCommand);
			COMMAND_TYPE commandType = determineCommandType(commandTypeString);
			
			switch(commandType){
				case ADD_TASK:
					spillTheCommand(userCommand);
				
				case UNDO:
					
				
			}
			
		}

		private static COMMAND_TYPE determineCommandType(
				String commandType) {
			
			if (commandType == NULL_STRING) {
				throw new Error(NULL_ERROR_MESSAGE);
			}
			
			if (commandType.equalsIgnoreCase("add")) {
				return COMMAND_TYPE.ADD_TASK;
			} else if (commandType.equalsIgnoreCase("delete")) {
				return COMMAND_TYPE.DELETE_TASK;
			} else if (commandType.equalsIgnoreCase("undo")) {
				return COMMAND_TYPE.UNDO;
			}
		}

		private static String getFirstWord(String userCommand) {
			String commandType = userCommand.trim().split(SPLIT_PARAMETER)[0];
			return commandType;
		}

		private static void printCommandMessage() {
			System.out.println(PROMPT_MESSAGE);
		}

		private static void printWelcomeMessage(String message) {
			System.out.println(message);
		}
			


}

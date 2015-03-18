package logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import application.Task;
import parser.IndexParser;

/**
 * CommandHandler for "edit" function
 * 
 * modify a task by specifying the field (description or time) and index
 * of the task in the ArrayList then the information that is intended to 
 * change
 * @author A0114463M
 *
 */
public class EditHandler extends CommandHandler {

	private static final Logger editLogger =
			Logger.getLogger(DeleteHandler.class.getName());
	
	@Override
	String getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String execute(String command, String parameter, ArrayList<Task> taskList) {
		editLogger.entering(getClass().getName(), "preparing for editing tasks");
		int index = -1;		
		
		IndexParser ip = new IndexParser();		
		String[] token = parameter.split(" ");
		Task removedTask = new Task();
		try {
			index = ip.getIndex(parameter);
			removedTask = taskList.remove(index);
		} catch (NumberFormatException nfe) {
			editLogger.log(Level.WARNING, "Not a number", nfe);
			return "Invalid number entered! Please check your input\n";
		} catch (IndexOutOfBoundsException iob) {
			editLogger.log(Level.WARNING, "Index out of range", iob);
			return "Number out of range! Please check your input\n";
		}
		
		switch (token[0].toLowerCase()) {
			case "description":
				
				break;
			case "time":
				break;
			default:
				try {
					index = Integer.parseInt(token[0]);									
				} catch (NumberFormatException nfe) {
					editLogger.log(Level.WARNING, "Invalid Input");
					return getHelp();
				}			
				
			}
		return null;
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

}
//**Depriciated
//	protected boolean editTask(String taskInformation) throws IndexOutOfBoundsException {
//		DeleteHandler dh = new DeleteHandler(memory);
//		AddHandler ah = new AddHandler(memory);
//		TaskData oldTask = new TaskData();
//		TaskData newTask = CommandHandler.createNewTask(taskInformation.substring(taskInformation.indexOf(" ")));	
//		try  {
//			oldTask = dh.deleteTask(taskInformation);
//			ah.addTask(newTask);
//		} catch (IndexOutOfBoundsException iob) {
//			return false;
//		}
//		
//		return true;
//	}
//}

/**
 *  handle adding a task to the task list
 *  and write the changes to database if adding is
 *  success
 */
package handler;

import database.Database;
import application.TaskData;

public class AddHandler {

	public static boolean addTask(String taskInformation, TaskList taskList, Database database) {
		// parsing the parameters of the taskData
		
		//
		TaskData newTask = new TaskData();
		if (taskList.addTask(newTask)) {
			/* it shall be i pass the new task to the database and database shall add
			 * accrodingly
			database.
			*/
			return true;
		} else {
			return false;
		}
	}
}

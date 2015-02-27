package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.TaskData;

public class Database {
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DATABASE_NAME = "TaskManagerDatabase";
	private static final String CONNECTION_URL = "jdbc:derby:" + DATABASE_NAME + ";create=true";
	private static final String COMMAND_CREATE_TABLE = "create table TaskList(Task_ID varchar(5), Task_Type varchar(20), "
			+ "Description varchar(60), Start_Date_Time varchar(20), End_Date_Time varchar(20), Deadline varchar(20), Status varchar(20))";
	private static final String COMMAND_CLEAR_DATABASE = "DROP TABLE TaskList";
	private static final String SEARCH_KEYWORD = "select $s from TaskList";
	private static final String[] fields = {
		"Task ID", 
		"Task type",
		"Description",
		"Start Date Time",
		"End Date Time",
		"Deadline",
		"Status"
	};
	private static final String COMMAND_ADD = "insert into TaskList values ('%s', '%s', '%s', '%s', '%s', '%s', '%s')";
	private static final String COMMAND_RETRIEVE_DATA = "SELECT * FROM TaskList";
	
	private static final int COLUMN_COUNT = 7;
	
	private static ArrayList<TaskData> deadlines;
	private static ArrayList<TaskData> timeTasks;
	private static ArrayList<TaskData> floatingTasks;
	
	
	public Database() {
		if (!isDatabaseCreated()) {
			createDatabase();
		}
		readDatabase();
		initMemory();
	}
	
	public void createDatabase() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(CONNECTION_URL);
			System.out.println("Connected to " + DATABASE_NAME);
			connection.createStatement().execute(COMMAND_CREATE_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Database is successfully created!");
	}
	
	public void readDatabase() {	
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(COMMAND_RETRIEVE_DATA);
			
			
			while (resultSet.next()) {
				System.out.println("");
				String[] parametersOfTaskData = new String[COLUMN_COUNT];
				
				for (int x = 1; x <= COLUMN_COUNT; x++) {
					parametersOfTaskData[x-1] = resultSet.getString(x);
					System.out.println(parametersOfTaskData[x-1]);
				}
				
				TaskData taskData = new TaskData(parametersOfTaskData[0], parametersOfTaskData[1], parametersOfTaskData[2], parametersOfTaskData[3], 
						parametersOfTaskData[4], parametersOfTaskData[5], parametersOfTaskData[6]);
				
				String taskType = parametersOfTaskData[1];
				
				addTaskToArrayList(taskType, taskData);
			}
			
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close(); 
			}
		} catch (SQLException e) {
			System.out.println("Database does not exit!");
			//e.printStackTrace();
		}
	}
	
	public void initMemory() {
		deadlines = new ArrayList<TaskData>();
		floatingTasks = new ArrayList<TaskData>();
		timeTasks = new ArrayList<TaskData>();
	}
	
	//try to think of a better name
	private void addTaskToArrayList(String taskType, TaskData taskData) {
		if (isDeadline(taskType)) {
			addDeadline(taskData);
		}
		
		if (isFloatingTask(taskType)) {
			addFloatingTask(taskData);
		}
		
		if (isTimeTask(taskType)) {
			addTimeTask(taskData);
		}
	}
	
	public void deleteDeadline() {
		
	}
	
	public void deleteTimeTask() {
		
	}
	
	public void deleteFloatingTask() {
		
	}
	
	private boolean isDeadline(String taskType) {
		return taskType.equals("deadline");
	}
	
	private boolean isFloatingTask(String taskType) {
		return taskType.equals("floating task");
	}
	
	private boolean isTimeTask(String taskType) {
		return taskType.equals("time task");
	}
	
	public void addDeadline(TaskData newDeadline) {
		deadlines.add(newDeadline);
		addTaskToDatabase(newDeadline);
	}
	
	public void addTimeTask(TaskData newTimeTask) {
		timeTasks.add(newTimeTask);
		addTaskToDatabase(newTimeTask);
	}
	
	public void addFloatingTask(TaskData newFloatingTask) {
		floatingTasks.add(newFloatingTask);
		addTaskToDatabase(newFloatingTask);

	}
	
	
	private String getAddCommand(TaskData taskData) {
		String taskID = taskData.getTaskID();
		String taskType = taskData.getTaskType();
		String description = taskData.getDescription();
		String startDateTime = taskData.getStartDateTime();
		String endDateTime = taskData.getEndDateTime();
		String deadline = taskData.getDeadLine();
		String status = taskData.getStatus();
		
		String addCommand = String.format(COMMAND_ADD, taskID, taskType, description, startDateTime, endDateTime, deadline, status);
		
		return addCommand;
	}
	
	
	
	public void addTaskToDatabase(TaskData taskData) {
		String addCommand = getAddCommand(taskData);
		
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			Statement statement = connection.createStatement();
			connection.createStatement().execute(addCommand);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Data added successfully!");
	}
	
	public void clearDatabase() {
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			connection.createStatement().execute("DROP TABLE TaskList");
			connection.close(); 
			System.out.println("Successfully cleared!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
	
	public void shutDownDatabase() {
		if (DRIVER.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
            boolean gotSQLExc = false;
            try {
               DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se)  {	
               if ( se.getSQLState().equals("XJ015") ) {		
                  gotSQLExc = true;
               }
            }
            if (!gotSQLExc) {
            	  System.out.println("Database did not shut down normally");
            }  else  {
               System.out.println("Database shut down normally");	
            }  
         }
	}
	
	public boolean isDatabaseCreated() {
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from TaskList");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public ArrayList<TaskData> getDeadlines() {
		return deadlines;
	}
	
	public ArrayList<TaskData> getTimeTasks() {
		return timeTasks;
	}
	
	public ArrayList<TaskData> getFloatingTasks() {
		return floatingTasks;
	}


}

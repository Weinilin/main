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
	
	private static ArrayList<TaskData> deadlines;
	private static ArrayList<TaskData> timeTasks;
	private static ArrayList<TaskData> floatingTasks;
	
	
	public static void createDatabase() {
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
	
	public static void readDatabase() {		
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(COMMAND_RETRIEVE_DATA);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			
			
			while (resultSet.next()) {
				System.out.println("");
				for (int x = 1; x <= columnCount; x++) {
					System.out.format("%-20s", fields[x-1]);
					System.out.print(": ");
					System.out.format("%-60s", resultSet.getString(x));
					System.out.println();
				}
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String getAddCommand(TaskData taskData) {
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
	
	public static void addData(TaskData taskData) {
		String addCommand = getAddCommand(taskData);
		
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			connection.createStatement().execute(addCommand);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Data added successfully!");
	}
	
	public static void clearDatabase() {
		
	}
	
	public static void shutDownDatabase() {
		
	}
	
	public static boolean isDatabaseCreated() {
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from TaskList");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static void main (String[] args) throws SQLException {
		if (!isDatabaseCreated()) {
			Database.createDatabase();

			DateTime deadline1 = new DateTime(1993,01,07,5,41);
			TaskData data1 = new TaskData("1", "deadline", "data1", null, null, deadline1, "uncompleted"); 

			DateTime deadline2 = new DateTime(1993,02,17,5,41);
			TaskData data2 = new TaskData("2", "deadline", "data2", null, null, deadline2, "uncompleted");

			DateTime deadline3 = new DateTime(1993,04,04,5,41);
			TaskData data3 = new TaskData("3", "deadline", "dummy", null, null, deadline3, "uncompleted");

			Database.addData(data3);
			Database.addData(data2);
			Database.addData(data1);
		}

		Database.readDatabase();
	}

}

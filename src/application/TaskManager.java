package application;

import database.Database;

class TaskManager	{
	
	public TaskManager()	{
		
	}
	
	public static void initProgram() {
		Database database = new Database();
		database.setEnvironment();
	}
	
	
	public static void main (String[] args)	{
		initProgram();
	}
	
	
}
package logic;

import java.util.ArrayList;

import parser.IndexParser;
import database.Memory;

public class MarkHandler {
	
	private Memory memory;
	
	public MarkHandler(Memory memory) {
		this.memory = memory;
	}
	
	protected boolean markTaskDone(String taskInformation) throws IndexOutOfBoundsException  {
		IndexParser ip = new IndexParser();
		int index = ip.extractIndex(taskInformation) - 1;
		try {
			memory.getTaskList().get(index).setStatus("done");
			
		} catch (IndexOutOfBoundsException iob) {
			return false;
		} 
	
		return true;
	}
}

package database;

import java.util.Stack;
import application.UndoData;

public class UndoStack {
	private static Stack<UndoData> undoStack;
	
	public UndoStack() {
		undoStack = new Stack<UndoData>();
	}
	
	public static void push(UndoData undoData) {
		undoStack.add(undoData);
	}
	
	public static UndoData pop() {
		return undoStack.pop();
	}
}

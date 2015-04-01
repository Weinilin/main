
package logic;

import java.util.ArrayList;
import application.Task;
/**
 * This class records all the actions performed during each single
 * line of command that user enters and successfully implemented to the system.
 * Multiple actions completed in a single line of command (such as deleting multiple
 * tasks) are recorded in one recorder.
 * 
 * @author Mr.Emosdnah
 *
 */
public class UndoRedoRecorder {

    private ArrayList<UndoRedoAction> actionList = new ArrayList<UndoRedoAction>();
    private ArrayList<Task> currentTaskList = new ArrayList<Task>();
    
    public UndoRedoRecorder(ArrayList<Task> taskList) {
        currentTaskList.clear();
        currentTaskList.addAll(taskList);
    }
    
    public void appendAction(UndoRedoAction newAction) {
        actionList.add(newAction);
    }
    
    public ArrayList<Task> getTaskList() {
        return currentTaskList;
    }
    
    public void performUndo() {
        for (UndoRedoAction ura: actionList) {
            ura.undo();
        }
    }
    
    public void performRedo() {
        for (UndoRedoAction ura: actionList) {
            ura.redo();
        }
    }
    
    public boolean isEmpty() {
        return actionList.isEmpty();
    }
}

//@author A0114463M
package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import application.Task;

public class LogicControllerTest {

    LogicController lc = LogicController.getInstance();
    ArrayList<Task> expected = new ArrayList<Task>();
    Task task1 = CommandHandler.createNewTask("super"),
         task2 = CommandHandler.createNewTask("superwoman"),
         task3 = CommandHandler.createNewTask("superman"),
         task4 = CommandHandler.createNewTask("a"),
         task5 = CommandHandler.createNewTask("b"),
         task6 = CommandHandler.createNewTask("c");
    
    public void  setUp1() {     
        expected.clear();
        expected.add(task1);
        expected.add(task2);
        expected.add(task3);
    }
    @Test
    public void testExecuteCommand() {
           lc.executeCommand("+ superman");
           lc.executeCommand("a super");
           lc.executeCommand("add superwoman");
           assertEquals(lc.getTaskList(), expected);
    }

}

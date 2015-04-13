package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.text.ParseException;
import application.Task;
import application.TaskCreator;
public class MemorySearchTest {
    Memory memory = Memory.getInstance();
    Task newTask1, newTask2, newTask3;
    String taskType;
    String description;
    String startDateTime;
    String endDateTime;
    String deadline;
    String status;
    
    public void taskSetup() throws Exception {
          TaskCreator tc = new TaskCreator("task 1 from 01/05/2015 5pm to 6pm");
          newTask1 = tc.createNewTask();
          tc.setNewString("task 2 01/05/2015 by 10pm");
          newTask2 = tc.createNewTask();
          tc.setNewString("task 3 02/05/2015 from 4am to 5am");
          newTask3 = tc.createNewTask();
    }

    @Test
    public void testSearchOnDay() {
        try {
            taskSetup();
        } catch (Exception e) {
            fail("Exception occured");
        }
        
        memory.removeAll();
        memory.addTask(newTask1);
        memory.addTask(newTask2);
        memory.addTask(newTask3);
        ArrayList<Task> correctSearchList = new ArrayList<Task>();
        correctSearchList.add(newTask1);
        correctSearchList.add(newTask2);
        //correctSearchList.clear();
        try {
             assertEquals(correctSearchList, memory.searchDate("01/05/2015"));
        } catch (ParseException pe) {
            System.out.println("Error while parsing date\n");
        }
    }

}

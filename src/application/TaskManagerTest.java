package application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.Test;

import logic.*;
import ui.CommandLineInterface;

public class TaskManagerTest {
    int err_count = 0;
    LinkedList err_case = new LinkedList<String>();
    
    Task task1 = CommandHandler.createNewTask("task a"),
         task2 = CommandHandler.createNewTask("task b from 10am to 12pm"),
         task3 = CommandHandler.createNewTask("task c by 8pm"),
         task4 = CommandHandler.createNewTask("task d from 6am to 7am"),
         task5 = CommandHandler.createNewTask("task e by 3 days later");
   
    ArrayList<Task> expected = new ArrayList<Task>();
    CommandLineInterface cli = new CommandLineInterface();
    LogicController lc = LogicController.getInstance();


    @Test
    public void testMainAdd1() {
        cli.processUserInput("clear");
        cli.processUserInput("+ task c by 8pm");
        expected.add(task3);
        assertEquals(lc.getTaskList().toString(), expected.toString());
    }
    
    @Test
    public void testMainAdd2() {
        cli.processUserInput("add task a");
        expected.clear();
        expected.add(task3);
        expected.add(task1);
        Collections.sort(expected, new TaskComparator());
        assertEquals(lc.getTaskList().toString(), expected.toString());
    }
    
    @Test
    public void testMainAdd3() {
        cli.processUserInput("a task d 0600-0700");
        expected.add(task4);
        Collections.sort(expected, new TaskComparator());
        assertEquals(lc.getTaskList().toString(), expected.toString());
    }
    
    @Test
    public void testMainAdd4() {
        cli.processUserInput("add task e by 05/04 23:59");
        expected.clear();
        expected.add(task1);
        expected.add(task3);
        expected.add(task5);
        Collections.sort(expected, new TaskComparator());
        assertEquals(lc.getTaskList().toString(), expected.toString());
    }

    @Test
    public void testMainDeleteSingle() {
        setupDeleteSingle();
        cli.processUserInput("d 1");
        assertEquals(lc.getTaskList().toString(), expected.toString());
    }

    private void setupDeleteSingle() {
        expected.clear();
        expected.add(task1);
        expected.add(task3);
        Collections.sort(expected, new TaskComparator());
        cli.processUserInput("clear");
        cli.processUserInput("+ task c by 8pm");
        cli.processUserInput("add task a");
        cli.processUserInput("a task b from 10am to 12pm");
    }
    
    @Test
    public void testMainDeleteMultiple() {
        setupDeleteMultiple();
        cli.processUserInput("d 1 3");
        assertEquals(lc.getTaskList().toString(), expected.toString());
    }
    private void setupDeleteMultiple() {
        expected.clear();
        expected.add(task3);
        Collections.sort(expected, new TaskComparator());
        cli.processUserInput("clear");
        cli.processUserInput("+ task c by 8pm");
        cli.processUserInput("add task a");
        cli.processUserInput("a task b from 10am to 12pm");
    }
}



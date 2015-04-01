package application;

import ui.CommandLineInterface;

public class TaskManagerTester {
    TaskManagerTester() {
        testForStorage();
        testForAdd();
        testForDelete();
    }

    public void testForStorage() {
        CommandLineInterface cli = new CommandLineInterface();
        String feedback = cli.processUserInput("setLocation TestSuite/SetLocationTest");
        if (feedback
                .equals("The file is now saved to TestSuite/SetLocationTest")) {
            System.out.println("set location sucess");
        } else {
            System.out.println("set location fail");
        }
    }
    
    public void testForAdd() {
        CommandLineInterface cli = new CommandLineInterface();
        String feedbackForDeadline = cli
                .processUserInput("add mds sale at 2pm.");
        if (feedbackForDeadline.equals("Task \"mds sale\" is added")) {
            System.out.println("add deadline task success");
        } else {
            System.out.println("add deadline task fail");
        }

        String feedbackForTimed = cli.processUserInput("add meeting 2 to 3pm");
        if (feedbackForTimed.equals("Task \"add meeting\" is added")) {
            System.out.println("add time task success");
        } else {
            System.out.println("add time task fail");
        }

        String feedbackForFloating = cli.processUserInput("add Swimming!");
        if (feedbackForFloating.equals("Task \"swimming!\" is added")) {
            System.out.println("add floating task success");
        } else {
            System.out.println("add floating task fail");
        }
    }
    
    public void testForDelete(){
        CommandLineInterface cli = new CommandLineInterface();
        String feedbackForError = cli
                .processUserInput("delete 1");
        if (feedbackForError.equals("Invalid input 1")) {
            System.out.println("detect error when task list is empty successful.");
        } else {
            System.out.println("detect error when task list is empty fail!");
        }

        cli.processUserInput("add meeting 2 to 3pm");
        String feedbackDeleteOneTask = cli.processUserInput("delete 1");
        if (feedbackDeleteOneTask.equals("Removed task 1 ")) {
            System.out.println("remove one task successful");
        } else {
            System.out.println("remove one task fail.");
        }

        cli.processUserInput("add meeting 2 to 3pm");
        cli.processUserInput("add Swimming!");
        String feedbackDeleteTwoTask = cli.processUserInput("delete 1 2");
        if (feedbackDeleteTwoTask.equals("Removed task 1 2 ")) {
            System.out.println("remove two in a row successful");
        } else {
            System.out.println("remove two in a row fail");
        }
    }
    }

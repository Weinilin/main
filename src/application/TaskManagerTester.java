package application;

import ui.CommandLineInterface;

public class TaskManagerTester {
    TaskManagerTester(CommandLineInterface cli) {
        String feedback = cli.processUserInput("setLocation TestSuite/SetLocationTest");
        if (feedback.equals("The file is now saved to TestSuite/SetLocationTest")) {
            System.out.println("set location sucess");
        } else {
            System.out.println("set location fail");
        }
        
        
    }
    
}
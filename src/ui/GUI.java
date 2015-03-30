package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;







import storage.Memory;
import application.Task;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.LogicController;

public class GUI extends JPanel implements ActionListener{

    private static final String COMMAND_MESSAGE = new String("Command: ");
    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
    private static final String GOODBYE_MESSAGE = new String("GoodBye!\n");
    
    private static String[] columnNames1 = {"No.",
                                            "Description",
                                            "Start Time",
                                            "End Time",
                                            "Deadline",
                                            "Status"};
    
    private static String[] columnNames2 = {"No.",
                                            "Description",
                                            "Status"};
    final static boolean shouldFill = true;
    
    private static JTextField textField;
    private static JTextArea textArea;
    private static JTable deadlinesAndTimeTasksTable;
    private static JTable floatingTasksTable;
    private static DefaultTableModel deadlinesAndTimeTasksModel;
    private static DefaultTableModel floatingTasksModel;

    
   
    private static Memory memory;



    public GUI() {
        
        super(new GridBagLayout());
        memory = Memory.getInstance();
        
      

        textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.addActionListener(this);

        textArea = new JTextArea(3, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);

        GridBagConstraints c = new GridBagConstraints();
       
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        
        add(textField, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        add(scrollPaneTextArea, c);



        JLabel tableLabel1 = new JLabel("Deadlines and Time Tasks", SwingConstants.CENTER);
        tableLabel1.setFont(new Font("Arial", Font.PLAIN, 12 ));
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        
        add(tableLabel1, c);

       



        deadlinesAndTimeTasksModel = new DefaultTableModel(columnNames1, 0)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };

        deadlinesAndTimeTasksTable = new JTable(deadlinesAndTimeTasksModel);
        deadlinesAndTimeTasksTable.setFont(new Font("Arial", Font.PLAIN, 12));
        deadlinesAndTimeTasksTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 13));


        deadlinesAndTimeTasksTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        deadlinesAndTimeTasksTable.getColumnModel().getColumn(1).setPreferredWidth(280);
        deadlinesAndTimeTasksTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        deadlinesAndTimeTasksTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        deadlinesAndTimeTasksTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        deadlinesAndTimeTasksTable.getColumnModel().getColumn(5).setPreferredWidth(50);



        deadlinesAndTimeTasksTable.setPreferredScrollableViewportSize(new Dimension(730, 200));
        deadlinesAndTimeTasksTable.setFillsViewportHeight(true);
        
     

        
        
        JScrollPane scrollPane1 = new JScrollPane(deadlinesAndTimeTasksTable);
        
        c.fill = GridBagConstraints.BOTH;
     
        c.gridx = 0;
        c.gridy = 3;
       

        add(scrollPane1, c);
        
        JLabel label2 = new JLabel("Floating Tasks", SwingConstants.CENTER);
        label2.setFont(new Font("Arial", Font.PLAIN, 12 ));
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 10;
        
        add(label2, c);

       
        floatingTasksModel = new DefaultTableModel(columnNames2, 0)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };


        floatingTasksTable = new JTable(floatingTasksModel);
        floatingTasksTable.setFont(new Font("Arial", Font.PLAIN, 12));
        floatingTasksTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 13));

        floatingTasksTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        floatingTasksTable.getColumnModel().getColumn(1).setPreferredWidth(640);
        floatingTasksTable.getColumnModel().getColumn(2).setPreferredWidth(50);
    



        floatingTasksTable.setPreferredScrollableViewportSize(new Dimension(730, 200));
        floatingTasksTable.setFillsViewportHeight(true);
        
        
        
        JScrollPane scrollPane2 = new JScrollPane(floatingTasksTable);
        
        c.fill = GridBagConstraints.BOTH;
     
        c.gridx = 0;
        c.gridy = 11;
       

        add(scrollPane2, c);
        updateTable();

    }
    
    
    private void updateTable() {

        deadlinesAndTimeTasksModel.setRowCount(0);

        ArrayList<Task> deadlinesAndTimeTasks = memory.getDeadlinesAndTimeTasks();


        Object[][] data = new Object[deadlinesAndTimeTasks.size()][6];

        int taskNumber = 1;

        for (int i = 0; i < deadlinesAndTimeTasks.size(); i++) {
            data[i][0] = taskNumber;
            taskNumber += 1;
            data[i][1] = deadlinesAndTimeTasks.get(i).getDescription();
            data[i][2] = deadlinesAndTimeTasks.get(i).getStartDateTime();
            data[i][3] = deadlinesAndTimeTasks.get(i).getEndDateTime();
            data[i][4] = deadlinesAndTimeTasks.get(i).getDeadline();
            data[i][5] = deadlinesAndTimeTasks.get(i).getStatus();
            deadlinesAndTimeTasksModel.addRow(data[i]);
        }

        floatingTasksModel.setRowCount(0);

        ArrayList<Task> floatingTasks = memory.getFloatingTasks();


        Object[][] data2 = new Object[floatingTasks.size()][3];



        for (int i = 0; i < floatingTasks.size(); i++) {
            data2[i][0] = taskNumber;
            taskNumber += 1;
            data2[i][1] = floatingTasks.get(i).getDescription();
            data2[i][2] = floatingTasks.get(i).getStatus();
            floatingTasksModel.addRow(data2[i]);
        }

    }
    
    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textField.selectAll();

      

        String feedback = processUserInput(text);
    

        System.out.println(feedback);
        textArea.setText(feedback);

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        
        textArea.setCaretPosition(textArea.getDocument().getLength());
        
        updateTable();
       
    }
    


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Flirter's Assistant");
        JLabel slogan = new JLabel("Having an affair has never been so easy.", SwingConstants.CENTER);
        slogan.setFont(new Font("Kokonor", Font.ITALIC, 12 ));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Create and set up the content pane.
        GUI gui = new GUI();
        frame.add(gui, BorderLayout.CENTER);
        frame.add(slogan, BorderLayout.SOUTH);


        //Display the window.
        frame.setLocation(200, 200);
        frame.pack();
        frame.setVisible(true);
    }

    public String processUserInput(String userInput){
        String message;
        LogicController commandHandler = LogicController.getInstance();

        
        printMessageToUser(String.format(WELCOME_MESSAGE));

        printMessageToUser(String.format(COMMAND_MESSAGE));

        message = commandHandler.executeCommand(userInput);
        if (message == null) {
            printMessageToUser(GOODBYE_MESSAGE);
            System.exit(0);
        }         

        return message;
    }
    
    public void run() {
        createAndShowGUI();
    }

    public void printMessageToUser(String message){
        System.out.println(message);
    }
//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
}
package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;






import storage.Memory;
import application.Task;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.LogicController;

public class GUI extends JPanel implements ActionListener, TableModelListener {

    private static final String COMMAND_MESSAGE = new String("Command: ");
    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
    private static final String GOODBYE_MESSAGE = new String("GoodBye!\n");
    
    private static String[] columnNames = {"No.",
                                           "Description",
                                           "Start Time",
                                           "End Time",
                                           "Deadline",
                                           "Status"};
    
    final static boolean shouldFill = true;
    
    private static JTextField textField;
    private static JTextArea textArea;
    private static JTable table;
    private static DefaultTableModel model;
    
    private static ArrayList<Task> taskList;
    private static Memory memory;



    public GUI() {
        
        super(new GridBagLayout());
        memory = Memory.getInstance();
        this.taskList = memory.getTaskList();

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(3, 20);
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



        JLabel tableLabel = new JLabel("List of Tasks", SwingConstants.CENTER);
        tableLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12 ));
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        
        add(tableLabel, c);

       

        Object[][] data = fillData(taskList);


        model = new DefaultTableModel(data, columnNames)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };

        table = new JTable(model);


        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(280);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);



        table.setPreferredScrollableViewportSize(new Dimension(730, 400));
        table.setFillsViewportHeight(true);
        
        table.getModel().addTableModelListener(new DatabaseListener());
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        c.fill = GridBagConstraints.BOTH;
     
        c.gridx = 0;
        c.gridy = 3;
       

        add(scrollPane, c);
        


    }
    
    public class DatabaseListener implements TableModelListener {
        public void tableChanged(TableModelEvent e) {
            
        }
    }
    public void tableChanged(TableModelEvent e) {
        System.out.println(e);
     }
    
    private Object[][] fillData(ArrayList<Task> taskList) {
        Object[][] data = new Object[taskList.size()][6];

        int taskNumber = 1;

        for (int i = 0; i < taskList.size(); i++) {
            data[i][0] = taskNumber;
            taskNumber += 1;
            data[i][1] = taskList.get(i).getDescription();
            data[i][2] = taskList.get(i).getStartDateTime();
            data[i][3] = taskList.get(i).getEndDateTime();
            data[i][4] = taskList.get(i).getDeadline();
            data[i][5] = taskList.get(i).getStatus();
        }
        
        return data;
    }

    public void updateTable() {
        taskList = memory.getTaskList();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }
        
        Object[][] data = fillData(taskList);
    
        for (int i = 0; i < data.length; i++) {
            model.addRow(data[i]);
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
        JLabel slogan = new JLabel("Life is Short. Have an Affair.", SwingConstants.CENTER);
        slogan.setFont(new Font("Comic Sans MS", Font.PLAIN, 12 ));


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
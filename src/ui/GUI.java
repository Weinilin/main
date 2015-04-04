package ui;


import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import application.Task;
import application.TimeAnalyser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Vector;

import logic.LogicController;

public class GUI extends JPanel implements ActionListener{
    

    private static final String WELCOME_MESSAGE = new String( "Welcome to TaskManager!\n");
    
    private static String[] columnNames1 = {"No.",
                                            "Description",
                                            "Start Time",
                                            "End Time",
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

    private static CommandLineInterface CLI;

    private static LogicController lc;

    private static JScrollPane scrollPane1;
    private static JScrollPane scrollPane2;
    

    
    public GUI() {
        super(new GridBagLayout());

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == e.VK_ESCAPE) {
                    System.exit(0);
                }
                return false;
            }
        });



        CLI = new CommandLineInterface();

        lc = LogicController.getInstance();



        textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.addActionListener(this);

        textArea = new JTextArea(4, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setText(WELCOME_MESSAGE);

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

        
        deadlinesAndTimeTasksTable.setRowSelectionAllowed(true);
        deadlinesAndTimeTasksTable.setCellSelectionEnabled(false);

        
        deadlinesAndTimeTasksTable.setFont(new Font("Arial", Font.PLAIN, 12));
        deadlinesAndTimeTasksTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 13));
        deadlinesAndTimeTasksTable.setForeground(Color.BLUE);





        deadlinesAndTimeTasksTable.setPreferredScrollableViewportSize(new Dimension(700, 160));
        deadlinesAndTimeTasksTable.setFillsViewportHeight(true);
        
        deadlinesAndTimeTasksTable.setRowSelectionAllowed(true);
        deadlinesAndTimeTasksTable.setColumnSelectionAllowed(true);
        deadlinesAndTimeTasksTable.setCellSelectionEnabled(true);


        
        JScrollPane scrollPane1 = new JScrollPane(deadlinesAndTimeTasksTable);
        
        final int rows = 11;
        


      
        
        deadlinesAndTimeTasksTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "scroll down");
        deadlinesAndTimeTasksTable.getActionMap().put("scroll down", new AbstractAction() {        
            public void actionPerformed(ActionEvent ae) {
                int height = deadlinesAndTimeTasksTable.getRowHeight()*(rows-1);
                JScrollBar bar = scrollPane1.getVerticalScrollBar();
                bar.setValue( bar.getValue()+height );
            }
                
               
        });
    
        deadlinesAndTimeTasksTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), "scroll up");
        deadlinesAndTimeTasksTable.getActionMap().put("scroll up", new AbstractAction() {     
            public void actionPerformed(ActionEvent ae) {
                int height = deadlinesAndTimeTasksTable.getRowHeight()*(rows-1);
                JScrollBar bar = scrollPane1.getVerticalScrollBar();
                bar.setValue( bar.getValue()-height );
            }
        });
        
        
        
      
        
        
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
        floatingTasksTable.setForeground(Color.BLUE);
        floatingTasksTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 13));





        floatingTasksTable.setPreferredScrollableViewportSize(new Dimension(700, 160));
        floatingTasksTable.setFillsViewportHeight(true);
        
        
        
        JScrollPane scrollPane2 = new JScrollPane(floatingTasksTable);
        
        
        floatingTasksTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "scroll down");
        floatingTasksTable.getActionMap().put("scroll down", new AbstractAction() {        
            public void actionPerformed(ActionEvent ae) {
                int height = floatingTasksTable.getRowHeight()*(rows-1);
                
                JScrollBar bar = scrollPane2.getVerticalScrollBar();
                bar.setValue( bar.getValue()+height );
            }
                
               
        });
    
        floatingTasksTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), "scroll up");
        floatingTasksTable.getActionMap().put("scroll up", new AbstractAction() {     
            public void actionPerformed(ActionEvent ae) {
                int height = floatingTasksTable.getRowHeight()*(rows-1);
                JScrollBar bar = scrollPane2.getVerticalScrollBar();
                bar.setValue( bar.getValue()-height );
            }
        });
        
        c.fill = GridBagConstraints.BOTH;
     
        c.gridx = 0;
        c.gridy = 11;
       

        add(scrollPane2, c);
        
        setUpColumnWidth2();
        setUpColumnWidth() ;
        updateTable();


    }
    
    
    
    
    public void updateTable() {

       
        
        deadlinesAndTimeTasksModel.setRowCount(0);

        ArrayList<Task> deadlinesAndTimeTasks = getDeadlinesAndTimeTasks(lc.getTaskList());


        Object[][] data = new Object[deadlinesAndTimeTasks.size()][5];

        int taskNumber = 1;

        for (int i = 0; i < deadlinesAndTimeTasks.size(); i++) {
            data[i][0] = taskNumber;
            taskNumber += 1;
            data[i][1] = deadlinesAndTimeTasks.get(i).getDescription();      
            data[i][2] = deadlinesAndTimeTasks.get(i).getStartDateTime();
            
            String deadline = deadlinesAndTimeTasks.get(i).getDeadline();
            String endDateTime = deadlinesAndTimeTasks.get(i).getEndDateTime();
            
           
            
            if (endDateTime.equals("- -")) {
                data[i][3] = deadline;
            } else {
                data[i][3] = endDateTime;
            }
            
           
            data[i][4] = deadlinesAndTimeTasks.get(i).getStatus();
            
      


          
            
            deadlinesAndTimeTasksModel.addRow(data[i]);
        }
       
      
        
   

 

        floatingTasksModel.setRowCount(0);

        ArrayList<Task> floatingTasks = getFloatingTasks(lc.getTaskList());


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

      
       
        String feedback = CLI.processUserInputFromGUI(text);
    

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
        JFrame frame = new JFrame("Task's Manager");
        frame.setMinimumSize(new Dimension(710,520));
      
        JLabel slogan = new JLabel("Managing Tasks Like A Boss", SwingConstants.CENTER);
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
    
   
    
    public void run() {
        createAndShowGUI();
    }

    public void printMessageToUser(String message){
        System.out.println(message);
    }
    
    public ArrayList<Task> getDeadlinesAndTimeTasks(ArrayList<Task> taskList) {
        ArrayList<Task> deadlinesAndTimeTasks = new ArrayList<Task> ();
        
        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            String taskType = currentTask.getTaskType();
            
            if (taskType.equals("deadline") || taskType.equals("time task") ) {
                deadlinesAndTimeTasks.add(currentTask);
            }
        }

        return deadlinesAndTimeTasks;
    }
    
    public ArrayList<Task> getFloatingTasks(ArrayList<Task> taskList) {
        ArrayList<Task> floatingTasks = new ArrayList<Task> ();
        
        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            String taskType = currentTask.getTaskType();
            
            if (taskType.equals("floating task")) {
                floatingTasks.add(currentTask);
            }
        }
        
        return floatingTasks;
    }
    
   

    private void setUpColumnWidth() {
        final int colNo = floatingTasksTable.getColumnCount();
        
        //_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        floatingTasksTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        Runnable runColWidthSetup = setAllColumns(colNo);
        SwingUtilities.invokeLater(runColWidthSetup);
    }

    /**
     * @param colNo
     * @return
     */
    private Runnable setAllColumns(final int colNo) {
        Runnable runColWidthSetup = new Runnable(){
            @Override
            public void run(){
                for (int i = 0; i < colNo; i++) {
                    TableColumn column = floatingTasksTable.getColumnModel().getColumn(i);
                    
                    boolean isID = i == 0;
                    boolean isDesc = i == 1;
                    boolean isStatus = i == 2;

                    if (isID) {
                        column.setCellRenderer(new TextAreaRenderer());
                        column.setPreferredWidth(40);
                    } else if (isDesc) {
                        column.setCellRenderer(new TextAreaRenderer());
                        column.setPreferredWidth(640);
                    } else if (isStatus) {
                        column.setCellRenderer(new TextAreaRenderer());
                        column.setPreferredWidth(50);
                    }
                }
                
                
            }
        };
        return runColWidthSetup;
    }

    private void setUpColumnWidth2() {
        final int colNo = deadlinesAndTimeTasksTable.getColumnCount();
        
        //_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        deadlinesAndTimeTasksTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        Runnable runColWidthSetup = setAllColumns2(colNo);
        SwingUtilities.invokeLater(runColWidthSetup);
    }

    /**
     * @param colNo
     * @return
     */
    private Runnable setAllColumns2(final int colNo) {
        Runnable runColWidthSetup = new Runnable(){
            @Override
            public void run(){
                for (int i = 0; i < colNo; i++) {
                    TableColumn column = deadlinesAndTimeTasksTable.getColumnModel().getColumn(i);
                    
                    boolean isID = i == 0;
                    boolean isDesc = i == 1;
                    boolean isStartTime = i == 2;
                    boolean isEndTime = i == 3;
                    boolean isStatus = i == 4;

                    if (isID) {
                        column.setCellRenderer(new TextAreaRenderer1());
                        column.setPreferredWidth(40);
                    } else if (isDesc) {
                        column.setCellRenderer(new TextAreaRenderer1());
                        column.setPreferredWidth(400);
                    } else if (isStartTime) {
                        column.setCellRenderer(new TextAreaRenderer1());
                        column.setPreferredWidth(120);
                    } else if (isEndTime) {
                        column.setCellRenderer(new TextAreaRenderer1());
                        column.setPreferredWidth(120);
                    } else if (isStatus) {
                        column.setCellRenderer(new TextAreaRenderer1());
                        column.setPreferredWidth(50);
                    }
                }
                
                
            }
        };
        return runColWidthSetup;
    }

}

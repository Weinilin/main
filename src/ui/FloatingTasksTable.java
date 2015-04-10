package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import logic.LogicController;
import application.Task;

/**
 * FloatingTasksTable is used to create a table that stores floating tasks.
 * 
 * @author A0113966Y
 *
 */
public class FloatingTasksTable extends JPanel {
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1640202558535486039L;
	
	private static final String NAME_LABEL = "Floating Tasks";
	private static final int ROWS = 11;
	private static final int COLUMN_SIZE = 3;
	
	private static final String[] NAMES_COLUMN = {"No.", "Description", "Status"};
	
	private static final Font FONT_ARIAL = new Font("Arial", Font.PLAIN, 12);

	private static JLabel label;
	private static JTable table;
	private static DefaultTableModel model = new DefaultTableModel(NAMES_COLUMN, 0)
	{
		/**
		 * generated
		 */
		private static final long serialVersionUID = 1729950971541446403L;

		//This causes all cells to be not editable
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	};
	private static JScrollPane scrollPane;
	
	private static FloatingTasksTable floatingTasksTable;
	private static LogicController logicController;
	
	public FloatingTasksTable() {
		super(new BorderLayout());
	
		logicController = LogicController.getInstance();

		initializeTableLabel();
		add(label, BorderLayout.NORTH);
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		initializeTable();
		setUpColumnWidth();
	}
	
	private void initializeTableLabel() {
		label = new JLabel(NAME_LABEL, SwingConstants.CENTER);
        label.setFont(FONT_ARIAL);
	}
	
	private void initializeTable() {
		table.setShowGrid(false);
		setFontAndColor();
		setSelectionOption();
		setSize();
		addKeyboardCommand();
		setUpColumnWidth();
	}
	
	private void setSize() {
		table.setPreferredScrollableViewportSize(new Dimension(680, 160));
		table.setFillsViewportHeight(true);

	}

	private void setFontAndColor() {
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 13));
		table.setForeground(Color.BLUE);
	}

	private void setSelectionOption() {
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);
	}

	private void addKeyboardCommand() {
		addScrollUpCommand();
		addScrollDownCommand();
	}
	
	private void addScrollUpCommand() {
		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "scroll down");
		table.getActionMap().put("scroll down", new AbstractAction() {        
			/**
			 * generated
			 */
			private static final long serialVersionUID = -5456434991999269675L;

			public void actionPerformed(ActionEvent ae) {
				int height = table.getRowHeight() * (ROWS - 1);
				JScrollBar bar = scrollPane.getVerticalScrollBar();
				bar.setValue(bar.getValue() + height);
			}
		});
	}
	
	private void addScrollDownCommand() {
		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), "scroll up");
		table.getActionMap().put("scroll up", new AbstractAction() {     
			/**
			 * generated
			 */
			private static final long serialVersionUID = -6151241733122082378L;

			public void actionPerformed(ActionEvent ae) {
				int height = table.getRowHeight() * (ROWS - 1);
				JScrollBar bar = scrollPane.getVerticalScrollBar();
				bar.setValue(bar.getValue() - height);
			}
		});
	}
	
	public void updateTable(int taskNumber) {
		model.setRowCount(0);

		ArrayList<Task> floatingTasks = getFloatingTasks(logicController.getTaskList());

		Object[][] data = new Object[floatingTasks.size()][COLUMN_SIZE];

		for (int i = 0; i < floatingTasks.size(); i++) {
		    data[i][0] = taskNumber;
		    taskNumber += 1;
		    data[i][1] = floatingTasks.get(i).getDescription();
		    data[i][2] = floatingTasks.get(i).getStatus();
		    model.addRow(data[i]);
		}
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
	
	private Runnable setAllColumns(final int colNo) {
        Runnable runColWidthSetup = new Runnable(){
            @Override
            public void run(){
                for (int i = 0; i < colNo; i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    
                    boolean isID = i == 0;
                    boolean isDesc = i == 1;
                    boolean isStatus = i == 2;

                    if (isID) {
                        column.setCellRenderer(new FloatingTasksTableRenderer());
                        column.setPreferredWidth(40);
                    } else if (isDesc) {
                        column.setCellRenderer(new FloatingTasksTableRenderer());
                        column.setPreferredWidth(640);
                    } else if (isStatus) {
                        column.setCellRenderer(new FloatingTasksTableRenderer());
                        column.setPreferredWidth(50);
                    }
                }


            }
        };
        return runColWidthSetup;
	}

	private void setUpColumnWidth() {
		final int colNo = table.getColumnCount();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		Runnable runColWidthSetup = setAllColumns(colNo);
		SwingUtilities.invokeLater(runColWidthSetup);
	}
	
	public static FloatingTasksTable getInstance() {
		if (floatingTasksTable == null) {
			floatingTasksTable = new FloatingTasksTable();
		}
		
		return floatingTasksTable;
	}
}



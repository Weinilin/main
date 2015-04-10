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

import application.Task;
import logic.LogicController;

/**
 * DeadlinesAndTimeTasksTable is used to create a table that stores deadlines and time tasks.
 * 
 * @author A0113966Y
 *
 */
public class DeadlinesAndTimeTasksTable extends JPanel {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 7618108371680630851L;
	
	private static final String NAME_LABEL = "Deadlines and Time Tasks";
	private static final String[] NAMES_COLUMN = {"No.", "Description", "Start Time", "End Time", "Status"};
	
	private static final Font FONT_ARIAL = new Font("Arial", Font.PLAIN, 12);
	
	private static final String KEY_EVENT_SCROLL_UP = "scroll up";
	private static final String KEY_EVENT_SCROLL_DOWN = "scroll up";

	private static final int ROWS = 11;
	private static final int COLUMN_SIZE = 5;

	
	private static JLabel label;
	private static JTable table; 
	private static DefaultTableModel model = new DefaultTableModel(NAMES_COLUMN, 0)
	{
		/**
		 * generated
		 */
		private static final long serialVersionUID = 55623221195634318L;

		//This causes all cells to be not editable
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	};
	private static JScrollPane scrollPane;
	
	private static DeadlinesAndTimeTasksTable deadlinesAndTimeTasksTable;
	private static LogicController logicController;

	private DeadlinesAndTimeTasksTable() {
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
		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), KEY_EVENT_SCROLL_DOWN);
		table.getActionMap().put(KEY_EVENT_SCROLL_DOWN, new AbstractAction() {        
			/**
			 * generated 
			 */
			private static final long serialVersionUID = 2464218770554059695L;

			public void actionPerformed(ActionEvent ae) {
				int height = table.getRowHeight() * (ROWS - 1);
				JScrollBar bar = scrollPane.getVerticalScrollBar();
				bar.setValue(bar.getValue() + height);
			}
		});
	}
	
	private void addScrollDownCommand() {
		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), KEY_EVENT_SCROLL_UP);
		table.getActionMap().put(KEY_EVENT_SCROLL_UP, new AbstractAction() {     
			/**
			 * generated
			 */
			private static final long serialVersionUID = -1827429458697486133L;

			public void actionPerformed(ActionEvent ae) {
				int height = table.getRowHeight() * (ROWS - 1);
				JScrollBar bar = scrollPane.getVerticalScrollBar();
				bar.setValue(bar.getValue() - height);
			}
		});
	}
	
	public int updateTable() {
		model.setRowCount(0);

		ArrayList<Task> deadlinesAndTimeTasks = getDeadlinesAndTimeTasks(logicController.getTaskList());

		Object[][] data = new Object[deadlinesAndTimeTasks.size()][COLUMN_SIZE];

		int taskNumber = 1;

		for (int i = 0; i < deadlinesAndTimeTasks.size(); i++) {
			data[i][0] = taskNumber;
			taskNumber += 1;
			data[i][1] = deadlinesAndTimeTasks.get(i).getDescription();      
			data[i][2] = deadlinesAndTimeTasks.get(i).getStartDateTime();
			data[i][3] = deadlinesAndTimeTasks.get(i).getEndDateTime();
			data[i][4] = deadlinesAndTimeTasks.get(i).getStatus();
			model.addRow(data[i]);
		}
		
		return taskNumber;
	}

	private ArrayList<Task> getDeadlinesAndTimeTasks(ArrayList<Task> taskList) {
		ArrayList<Task> deadlinesAndTimeTasks = new ArrayList<Task> ();

		for (int i = 0; i < taskList.size(); i++) {
			Task currentTask = taskList.get(i);

			if (isDeadline(currentTask) || isTimeTask(currentTask)) {
				deadlinesAndTimeTasks.add(currentTask);
			}
		}

		return deadlinesAndTimeTasks;
	}

	private void setUpColumnWidth() {
		final int colNo = table.getColumnCount();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		Runnable runColWidthSetup = setAllColumns(colNo);
		SwingUtilities.invokeLater(runColWidthSetup);
	}

	private Runnable setAllColumns(final int colNo) {
		Runnable runColWidthSetup = new Runnable(){
			@Override
			public void run(){
				for (int i = 0; i < colNo; i++) {
					TableColumn column = table.getColumnModel().getColumn(i);

					boolean isID = i == 0;
					boolean isDesc = i == 1;
					boolean isStartTime = i == 2;
					boolean isEndTime = i == 3;
					boolean isStatus = i == 4;

					if (isID) {
						column.setCellRenderer(new DeadlinesAndTimeTasksTableRenderer());
						column.setPreferredWidth(40);
					} else if (isDesc) {
						column.setCellRenderer(new DeadlinesAndTimeTasksTableRenderer());
						column.setPreferredWidth(360);
					} else if (isStartTime) {
						column.setCellRenderer(new DeadlinesAndTimeTasksTableRenderer());
						column.setPreferredWidth(140);
					} else if (isEndTime) {
						column.setCellRenderer(new DeadlinesAndTimeTasksTableRenderer());
						column.setPreferredWidth(140);
					} else if (isStatus) {
						column.setCellRenderer(new DeadlinesAndTimeTasksTableRenderer());
						column.setPreferredWidth(50);
					}
				}


			}
		};
		return runColWidthSetup;
	}
	
	public static DeadlinesAndTimeTasksTable getInstance() {
		if (deadlinesAndTimeTasksTable == null) {
			deadlinesAndTimeTasksTable = new DeadlinesAndTimeTasksTable();
		}
		return deadlinesAndTimeTasksTable;
	}
	
	private boolean isDeadline(Task task) {
		String taskType = task.getTaskType(); 
		return taskType.equals("deadline");

	}
	
	private boolean isTimeTask(Task task) {
		String taskType = task.getTaskType(); 
		return taskType.equals("time task");
	}
}

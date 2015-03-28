/*
 * author A0113966Y
 */

package application;

import static java.lang.String.format;

import java.util.ArrayList;

/**
 * Task Printer is used to print taskList in a table format
 * @author yxchng
 *
 */
public final class TaskPrinter {

    
    private static final char BORDER_TOP_LEFT = '╔';
    private static final char BORDER_TOP_RIGHT = '╗';
    private static final char BORDER_TOP_MID = '╦';
    
    private static final char BORDER_BOTTOM_LEFT = '╚';
    private static final char BORDER_BOTTOM_RIGHT = '╝';
    private static final char BORDER_BOTTOM_MID = '╩';


    
    private static final char HORIZONTAL_BORDER = '═';
    private static final char VERTICAL_BORDER = '║';

    private static final String DEFAULT_AS_NULL = "-";

    private final String asNull = DEFAULT_AS_NULL;


    
    public TaskPrinter() {
    	
    }
    
    /**
     * print time tasks and deadlines in a table format
     * @param table - a 2-dimensional matrix storing the list of tasks
     */

    public void printTasksWithTime(ArrayList<ArrayList<String>> table) {
        if ( table == null ) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if ( table.size() == 0 || table.size() == 1 ) {
            return;
        }
        int[] widths = new int[getMaxColumns(table)];
        adjustColumnWidths(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths), getTopHorizontalBorder(widths), getBotHorizontalBorder(widths));
    }
    
    /**
     * print list of floating tasks in a table format
     * @param table - a 2-dimensional matrix storing the list of tasks
     */
    
    public void printFloatingTask(ArrayList<ArrayList<String>> table) {
        if ( table == null ) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if ( table.size() == 0 || table.size() == 1 ) {
            return;
        }
        int[] widths = new int[getMaxColumns(table)];
        adjustColumnWidths(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths), getTopHorizontalBorder(widths), getBotHorizontalBorder(widths));
    }
    
    
    
    /**
     * helper method to print taskList in a table format
     * @param table - 2-dimensional matrix storing the list of tasks
     * @param widths - array storing the size of each column in a row
     * @param horizontalBorder 
     */

    private void printPreparedTable(ArrayList<ArrayList<String>> table, int widths[], String horizontalBorder, String topBorder, String botBorder) {
        int lineLength = horizontalBorder.length();
        System.out.println(topBorder);
        boolean isFirstLine = true;
        
        for (ArrayList<String> row : table) {
            if ( row != null ) {
             
                System.out.println(getRow(row, widths, lineLength));
                if (isFirstLine) {
                    System.out.println(horizontalBorder);
                    isFirstLine = false;
                }
            }
        }
        System.out.println(botBorder);

    }
    
    
    /**
     * get each row of the table
     * @param row - array storing all the data in a row
     * @param widths - array storing the size of each column in a row
     * @param lineLength
     * @return a nicely formatted string containing the data of each row in the table
     */

    private String getRow(ArrayList<String> row, int[] widths, int lineLength) {
        StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
        int maxWidths = widths.length;
        for (int i = 0; i < maxWidths; i++) {
            builder.append(padMiddle(getCellValue(row, i), widths[i])).append(VERTICAL_BORDER);
        }
        return builder.toString();
    }
    
    /**
     * get the horizontal border
     * @param widths - array storing the width of each column in a row
     * @return horizontal border
     */

    private String getHorizontalBorder(int[] widths) {
        StringBuilder builder = new StringBuilder(256);
        
        
        
        builder.append(VERTICAL_BORDER);
        for (int i = 0; i < widths.length; i++) {
        	int w = widths[i];
            for (int j = 0; j < w; j++) {
                builder.append(HORIZONTAL_BORDER);
            }
            builder.append(VERTICAL_BORDER);
        }
        return builder.toString();
    }
    
    private String getTopHorizontalBorder(int[] widths) {
        StringBuilder builder = new StringBuilder(256);
        
        
        builder.append(BORDER_TOP_LEFT);
        for (int i = 0; i < widths.length; i++) {
            int w = widths[i];
            for (int j = 0; j < w; j++) {
                builder.append(HORIZONTAL_BORDER);
            }
            
            if (i == widths.length - 1) {
                builder.append(BORDER_TOP_RIGHT);
            } else {
                builder.append(BORDER_TOP_MID);
            }
        }
        return builder.toString();
    }
    
    private String getBotHorizontalBorder(int[] widths) {
        StringBuilder builder = new StringBuilder(256);
        
        builder.append(BORDER_BOTTOM_LEFT);
        for (int i = 0; i < widths.length; i++) {
            int w = widths[i];
            for (int j = 0; j < w; j++) {
                builder.append(HORIZONTAL_BORDER);
            }
            if (i == widths.length - 1) {
                builder.append(BORDER_BOTTOM_RIGHT);
            } else {
                builder.append(BORDER_BOTTOM_MID);
            }
        }
        return builder.toString();
    }
    
    /**
     * get the number of columns in the table
     * @param rows
     * @return the number of columns in the table
     */

    private int getMaxColumns(ArrayList<ArrayList<String>> rows) {
        int max = 0;
        for (ArrayList<String> row : rows ) {
            if ( row != null && row.size() > max ) {
                max = row.size();
            }
        }
        return max;
    }

    /**
     * adjust the size of a column by taking the maximum size of each row in the column
     * @param rows
     * @param widths
     */
    private void adjustColumnWidths(ArrayList<ArrayList<String>> rows, int[] widths) {
        for (int i = 0; i < rows.size(); i++) {
        	ArrayList<String> row = rows.get(i);
            if ( row != null ) {
                for ( int c = 0; c < widths.length; c++ ) {
                    final String cv = getCellValue(row, c);
                    final int l = cv.length();
                    if ( widths[c] < l ) {
                        widths[c] = l;
                    }
                }
            }
        }
    }
    
    private static String padRight(String s, int n) {
        return format("%1$-" + n + "s", s);
    }

    private static String padMiddle(String s, int n) {
    	int bufferFront = n/2 - s.length()/2;
    	int bufferEnd = n - s.length() - bufferFront;
    	
    	String tempString = "";
    	
    	for (int i = 0; i < bufferFront; i++) {
    		tempString += " ";
    	}
    	
    	tempString += s;
    	
    	for (int i = 0; i < bufferEnd; i++) {
    		tempString += " ";
    	}
    	
    	return tempString;
    }

    /**
     * get the index-th cell value in a row 
     * @param array 
     * @param index
     * @return
     */
    
    private String getCellValue(ArrayList<String> array, int index) {
        return array.get(index) == null ? asNull : array.get(index);
    }

}
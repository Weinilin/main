package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class TimeAnalyser {
    
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    
    
    public TimeAnalyser() {}
    // need another constructor for parsing unformatted string
    private void processFormattedString(String dateTime) {
    
        String processedDateTime = dateTime.replaceAll("[:/]", " ");
        String[] token = processedDateTime.split("\\s+");
   
     

        day = Integer.parseInt(token[0]);
        month = Integer.parseInt(token[1]);
        year = Integer.parseInt(token[2]);
        hour = Integer.parseInt(token[3]);
        minute = Integer.parseInt(token[4]);
        
    }

    public long getDateTimeInMilliseconds(String dateTime) {
        processFormattedString(dateTime);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDateTime = formatDateTime();
        Date date = null;

        try {
            date = sdf.parse(formattedDateTime);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

        long dateTimeInMilliseconds = date.getTime();

        return dateTimeInMilliseconds;
    }

    public String formatDateTime() {
        String formattedDateTime = day + "/" + month + "/" + year + " " + hour
                + ":" + minute;
        return formattedDateTime;
    }
    
    public int compare(String dateStr1, String dateStr2) {
        long date1InMilliseconds = getDateTimeInMilliseconds(dateStr1);
        long date2InMilliseconds = getDateTimeInMilliseconds(dateStr2);
        
        if (date1InMilliseconds > date2InMilliseconds) {
            return -1;
        } else {
            return 1;
        }
    }
    
   
}

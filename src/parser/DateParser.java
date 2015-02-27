package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	public DateParser(String dateTime) {
		parseFormattedString(dateTime);
	}

	//need another constructor for parsing unformatted string

	private void parseFormattedString(String dateTime) {
		day = Integer.parseInt(dateTime.substring(0, 2));
		month = Integer.parseInt(dateTime.substring(3, 5));
		year = Integer.parseInt(dateTime.substring(6, 10));
		hour = Integer.parseInt(dateTime.substring(12, 14));
		minute = Integer.parseInt(dateTime.substring(14, 16));
	}

	public long getDateTimeInMilliseconds() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String formattedDateTime = formatDateTime();
		Date date = null;

		try {
			date = sdf.parse(formattedDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long dateTimeInMilliseconds = date.getTime();

		return dateTimeInMilliseconds;
	}

	public String formatDateTime() {
		String formattedDateTime = day + "/" + month + "/" + year + " " + hour + ":" + minute ;

		return formattedDateTime;
	}
}

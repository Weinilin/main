package parser;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
/**
 * add in missing time:
 * 1) user keyed: 2 dates (today, dates after today) : current time  23:59
 * 2) user keyed: 2 dates (dates after today, dates after today) : 00:00 23:59
 * 3) user keyed: 1 date (today) : current time
 * 4) user Keyed: 1 date (dates after today) : 23:59
 * 5) user keyed: 2 dates and 1 time : time detected 23:59
 * 
 * add in missing date:
 * 1) user keyed: two times (past current time) : today today
 * 2) user keyed: two times (before current time) : tomorrow tomorrow
 * 3) user keyed: two times (1 before current time and 1 after) : today tomorrow
 * 4) user keyed: two times (start time > end time) : current date, next current date
 * 5) user keyed: two times (start time < end time) : current date, current date
 * 6) user keyed: 2 times and 1 date (start time > end time) : date keyed, next day of date keyed
 * 7) user keyed: 2 times and 1 date (start time < end time) : date keyed, date keyed
 * 6) user keyed: 1 times (past current time) and 0 date : today
 * 7) user keyed: 1 times (before current time) and 0 date : tomorrow
 * 
 * Exceptions:
 * 1) overdue dates
 * 2) remainder when start date or time is before current date or time
 * 3) impossible combination of timed task with start time or date > end time or date
 * @author WeiLin
 *
 */
public class DateTimeParser {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private int numberOfTime;
    private String description;
    private String feedback;
    private static String startTime = "-", endTime = "-", startDate = "-",
            endDate = "-";

    public DateTimeParser(String userInput) throws Exception {
        ArrayList<String> storageOfTime = new ArrayList<String>();
        ArrayList<String> storageOfDate = new ArrayList<String>();

        Time1Parser times = new Time1Parser(userInput);
        storageOfTime = times.getTimeList();
        feedback = times.getFeedBack();
        int indexPrevTime = times.getPosition();
        System.out.println("afterTime1Parse : " + storageOfTime);

        Date1Parser dates = new Date1Parser();
        dates.extractDate(userInput, times.getInputLeft());
        feedback = dates.getFeedBack();
        storageOfDate = dates.getDateList();
        int indexPrevDate = dates.getIndex();
        System.out.println("afterDate1Parser : " + storageOfDate);
        
        DateTimeNattyParser dateTimeNatty = new DateTimeNattyParser();
        dateTimeNatty.extractDateTime(userInput, dates.getInputLeft(),
                storageOfDate, storageOfTime, indexPrevTime, indexPrevDate);

        storageOfTime = dateTimeNatty.getTimeList();
        storageOfDate = dateTimeNatty.getDateList();
        description = dateTimeNatty.getDescription();
        
        System.out.println("InDTP: storageOfTime: "+storageOfTime+" storageOfDate: "+storageOfDate
                + " dates.getInputLeft(): " + dates.getInputLeft()+" description: "+description +
                " times.getInputLeft(): "+times.getInputLeft());
        
        assert storageOfDate.size() <= 2 : "key in more than 2 dates!";
        assert storageOfTime.size() <= 2 : "key in more than 2 times!";

        testForExceptionCases(storageOfTime, storageOfDate);

        storageOfTime = addInMissingTime(storageOfTime, storageOfDate);
        storageOfDate = addInMissingDate(storageOfTime, storageOfDate);

        addWeekDayToDate(storageOfDate);
        setNumberOfTime(storageOfTime);
        setAllParametersToDash();

        setAllParameters(storageOfTime, storageOfDate);
    }

    /**
     * get the feedback for exception error
     * @return feedback for exception error to logic
     */
    public String getFeedBack(){
        return feedback;
    }

    /**
     * add weekday to the start of the date into <weekDay> dd/mm/yyyy
     * 
     * @param storageOfDate
     *            :contains date in dd/mm/yyyy
     */
    private void addWeekDayToDate(ArrayList<String> storageOfDate) {

        for (int i = 0; i < storageOfDate.size(); i++) {
            if (storageOfDate.get(i).substring(0, 1).matches("\\d")) {
                String weekDay = WeekDayParser.getWeekDay(storageOfDate.get(i));
                storageOfDate.set(i, weekDay + " " + storageOfDate.get(i));
            }
        }

    }

    /**
     * set all parameters : start time and date, end time and date, deadline
     * time and deadline time
     * 
     * @param storageOfTime
     *            : contains all time
     * @param storageOfDate
     *            contains all date
     */
    private void setAllParameters(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) {

        if (storageOfTime.size() == 1) {
            setStartTime("-");
            setEndTime(storageOfTime.get(0));
            setStartDate("-");
            setEndDate(storageOfDate.get(0));
        } else if (storageOfTime.size() == 2) {
            setStartTime(storageOfTime.get(0));
            setEndTime(storageOfTime.get(1));
            setStartDate(storageOfDate.get(0));
            setEndDate(storageOfDate.get(1));
        }
    }

    /**
     * 1) Key in same times and same dates 2) Key in time before current time on
     * current date 3) Key in date later than current date 4) key in start time
     * before current time but end time after current time --> remind the user
     * meeting is ongoing.
     * 
     * @param storageOfTime
     * @param storageOfDate
     */
    private void testForExceptionCases(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws Exception {

        checkOverdueDate(storageOfTime, storageOfDate);
        giveRemainderForOngoingTimeTask(storageOfDate, storageOfTime);
        checkImpossibleTimedTask(storageOfTime, storageOfDate);

    }

    /**
     * check impossible combination of time task For example, same day with two
     * same date keyed by user
     * 
     * @param storageOfTime
     *            : time contained all time detected
     * @param storageOfDate
     *            : date contined all date detected
     * @throws ParseException
     *             and IllegalArgumentException
     * 
     */
    private void checkImpossibleTimedTask(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws ParseException {
        Logger logger = Logger.getLogger("DateTimeParser");
        try {
            checkSameDayStartTimeLaterAndSameTime(storageOfTime, storageOfDate);
            checkStartDateLater(storageOfDate);
           
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "processing error", e);
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    /**
     * check that on same day is start time is later than end 
     * and same time and same date. if yes, throw
     * IllegalArgumentException
     * 
     * @param storageOfTime
     * @param storageOfDate
     * @throws ParseException
     *             and IllegalArgumentException
     */
    private void checkSameDayStartTimeLaterAndSameTime(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws ParseException {
        if (storageOfTime.size() == 2 && storageOfDate.size() == 2) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
            Date time1 = timeFormat.parse(storageOfTime.get(0));
            Date time2 = timeFormat.parse(storageOfTime.get(1));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dateFormat.parse(storageOfDate.get(0));
            Date date2 = dateFormat.parse(storageOfDate.get(1));

            if ((time1.after(time2)  && date1.equals(date2)) || (time1.equals(time2)
                    && date1.equals(date2))) {
                throw new IllegalArgumentException(
                        "Impossible combination for timed task! End time must be later than start time on the same day");
            }
        }
    }

    /**
     * check that start date later than end date, if yes, throws
     * IllegalArgumentException
     * 
     * @param storageOfDate
     * @throws ParseException
     *             and IllegalArgumentException
     */
    private void checkStartDateLater(ArrayList<String> storageOfDate)
            throws ParseException {
        if (storageOfDate.size() == 2) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dateFormat.parse(storageOfDate.get(0));
            Date date2 = dateFormat.parse(storageOfDate.get(1));

            if (date1.after(date2)) {
                throw new IllegalArgumentException(
                        "Impossible combination for timed task! End date must be later than start date");
            }
        }
    }

    /**
     * check 1) user key in overdue time that before current time 2) user key in
     * task that have start time before current time but end time that is after
     * current time
     * 
     * @param storageOfTime
     *            : contains time detected
     * @param storageOfDate
     *            : contains date detected
     * @param numberBeforeCurrentTime
     *            : number of time detected that is before the current time
     * @param isStartTimeBeforeCurrent
     *            : true if the start time is before current time, otherwise
     *            false
     * @throws IllegalArgumentException
     *             and ParseException
     */
    private void checkOverdueDate(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws Exception {
        Logger logger = Logger.getLogger("DateTimeParser");
        try {
            overdueTaskDueToTime(storageOfTime, storageOfDate);

            overdueTaskDueToDate(storageOfDate);

        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "processing error", e);
            feedback = e.getMessage();
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void overdueTaskDueToTime(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws Exception {
        if (storageOfTime.size() > 0) {
            int numberBeforeCurrentTime = countNumberBeforeCurrentTime(storageOfTime);
            if (storageOfTime.size() == 1
                    && numberBeforeCurrentTime == 1
                    && (storageOfDate.size() == 1 && storageOfDate.get(0)
                            .equals(getCurrentDate()))) {
                throw new IllegalArgumentException(
                        "Time keyed past the current time. The task have overdue!");
            } else if ((storageOfDate.size() == 2
                    && numberBeforeCurrentTime == 2
                    && storageOfDate.get(0).equals(getCurrentDate()) && storageOfDate
                    .get(1).equals(getCurrentDate()))) {
                throw new IllegalArgumentException(

                "Time keyed past the current time. The task have overdue!");
            }
        }
    }

    private void overdueTaskDueToDate(ArrayList<String> storageOfDate)
            throws ParseException {
        if (storageOfDate.size() > 0) {
            int numberBeforeCurrentDate = countNumberBeforeCurrentDate(storageOfDate);

            if (storageOfDate.size() == 1 && numberBeforeCurrentDate == 1) {
                throw new IllegalArgumentException(
                        "Date keyed past the current date. The task have overdue!");
            } else if (storageOfDate.size() == 2
                    && numberBeforeCurrentDate == 2) {
                throw new IllegalArgumentException(
                        "Date keyed past the current date. The task have overdue!");
            }
        }
    }

    /**
     * check if the start time is before the current time
     * 
     * @param storageOfTime
     *            : contains time detected in user input
     * @return true if the time is before current time otherwise false.
     * @throws ParseException
     */
    private boolean ifStartTimeBeforeCurrent(ArrayList<String> storageOfTime)
            throws ParseException {
        boolean isStartTimeBeforeCurrent = false;
        if (storageOfTime.size() >= 1) {
            String currentTime = getCurrentTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            Date time = dateFormat.parse(storageOfTime.get(0));
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time);
            Date timeNow = dateFormat.parse(currentTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(timeNow);

            if (calendar2.getTime().after(calendar1.getTime())) {
                isStartTimeBeforeCurrent = true;
            }

        }
        return isStartTimeBeforeCurrent;
    }

    /**
     * calculate the number of time keyed past the current time
     * 
     * @param storageOfDate
     *            : stored time keyed by user in hh:mm format
     * @param numberPastCurrentDate
     *            : store the number that past current time
     * @return number of time keyed past current time
     * @throws ParseException
     */
    private int countNumberBeforeCurrentTime(ArrayList<String> storageOfTime)
            throws ParseException {
        int numberBeforeCurrentTime = 0;
        for (int i = 0; i < storageOfTime.size(); i++) {
            String currentTime = getCurrentTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            Date time = dateFormat.parse(storageOfTime.get(i));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            Date timeForNow = dateFormat.parse(currentTime);
            Calendar calendarOfCurrentTime = Calendar.getInstance();
            calendarOfCurrentTime.setTime(timeForNow);

            System.out.println("time: "+time + " timeForNow: "+timeForNow +" currentTime: "+currentTime);
            if (calendarOfCurrentTime.getTime().after(calendar.getTime())) {
                numberBeforeCurrentTime++;
            }

        }
        System.out.println("111numberBeforeCurrentTime: "+numberBeforeCurrentTime);
        return numberBeforeCurrentTime;
    }

    /**
     * give remainder user key in task that have start date before current date
     * but end date that is after current date
     * 
     * @param storageOfDate
     *            :contains dates detected
     * @throws ParseException
     */
    private void giveRemainderForOngoingTimeTask(
            ArrayList<String> storageOfDate, ArrayList<String> storageOfTime)
            throws ParseException {

        if (storageOfDate.size() > 0) {
            int numberBeforeCurrentDate = countNumberBeforeCurrentDate(storageOfDate);
            if (storageOfDate.size() == 2 && numberBeforeCurrentDate == 1) {
                feedback = "The meeting have started and is still ongoing.";
                JOptionPane.showMessageDialog(null,
                        "The meeting have started and is still ongoing.");
            }
            
        }

        if (storageOfTime.size() > 0) {
            boolean isStartTimeBeforeCurrent = ifStartTimeBeforeCurrent(storageOfTime);
            int numberBeforeCurrentTime = countNumberBeforeCurrentTime(storageOfTime);

            if ((storageOfDate.size() > 0 && isStartTimeBeforeCurrent && storageOfDate
                    .get(0).equals(getCurrentDate()) && numberBeforeCurrentTime == 1)) {
                feedback = "The meeting have started and is still ongoing.";
                JOptionPane.showMessageDialog(null,
                        "The start date keyed have past the current date but end date have not.\n" + "Go For The Meeting!", "REMAINDER", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("count: ");
            }
        }
    }

    /**
     * calculate the number of date keyed past the current date
     * 
     * @param storageOfDate
     *            : stored date keyed by user in <weekday> dd/mm/yyyy format
     * @param numberPastCurrentDate
     *            : store the number that past current date
     * @return number of date keyed past current day
     * @throws ParseException
     */
    private int countNumberBeforeCurrentDate(ArrayList<String> storageOfDate)
            throws ParseException {
        int numberBeforeCurrentDate = 0;
        for (int i = 0; i < storageOfDate.size(); i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dateFormat.parse(storageOfDate.get(i));
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            Calendar calendar2 = Calendar.getInstance();
            String currentDate = getCurrentDate();
            Date dateOfToday = dateFormat.parse(currentDate);
            calendar2.setTime(dateOfToday);

            if (calendar2.getTime().after(calendar1.getTime())) {
                numberBeforeCurrentDate++;
            }

        }
        return numberBeforeCurrentDate;
    }

    /**
     * set all the parameters to "-"
     */
    private void setAllParametersToDash() {
        setStartTime("-");
        setEndTime("-");
        setStartDate("-");
        setEndDate("-");
    }

    /**
     * set start date
     * 
     * @param date
     */
    private void setStartDate(String date) {
        startDate = date;
    }

    /**
     * get start date
     * 
     * @return start date for timed task
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * set the end date
     * 
     * @param date
     */
    private void setEndDate(String date) {
        endDate = date;
    }

    /**
     * get end end
     * 
     * @return end date for timed
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * set end time
     * 
     * @param time
     */
    private void setEndTime(String time) {
        endTime = time;
    }

    /**
     * get end time
     * 
     * @return end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * set the start time for timed task
     * 
     * @param time
     */
    private void setStartTime(String time) {
        startTime = time;
    }

    /**
     * return start time for timed task
     * 
     * @return start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * add in the time that is not keyed in by the user and by the the right
     * interpreted
     * 
     * @param storageOfTime
     *            :contains all time detected
     * @param storageOfDate
     *            :conatains all date detected
     * @throws ParseException
     */
    private ArrayList<String> addInMissingTime(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws ParseException {
        if (storageOfTime.size() < storageOfDate.size()) {
            if (storageOfTime.size() == 0 && storageOfDate.size() == 1) {
                storageOfTime.add("23:59");
            } else if (storageOfTime.size() == 0 && storageOfDate.size() == 2) {
                addTimeWhenNoTimeAndTwoDate(storageOfTime, storageOfDate);

            } else if (storageOfTime.size() == 1 && storageOfDate.size() == 2) {
                storageOfTime.add("23:59");
            }
        }
        return storageOfTime;
    }

    /**
     * add missing two times when 2 dates and no time is detected
     * 
     * @param storageOfTime
     *            : contains time detected
     * @param storageOfDate
     *            : contains date detected
     * @throws ParseException
     */
    private void addTimeWhenNoTimeAndTwoDate(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws ParseException {
        int numberBeforeCurrentDate = countNumberBeforeCurrentDate(storageOfDate);
        String currentTime = getCurrentTime();
        // today - today, today - tomorrow
        if (storageOfDate.get(0).equals(getCurrentDate())) {
            storageOfTime.add(currentTime);
            storageOfTime.add("23:59");

            // yesterday - today
        } else if (numberBeforeCurrentDate == 1) {
            storageOfTime.add("23:59");
            storageOfTime.add("23:59");
        } else {
            storageOfTime.add("00:00");
            storageOfTime.add("23:59");
        }
    }

    /**
     * add in the date that is not keyed in by the user and by the the right
     * interpreted
     * 
     * @param storageOfTime
     *            :time detected by user
     * @param storageOfDate
     *            date detected by user
     * @throws ParseException
     */
    private ArrayList<String> addInMissingDate(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate) throws ParseException {
        int numberBeforeCurrentTime = countNumberBeforeCurrentTime(storageOfTime);
        boolean isStartTimeBeforeCurrent = ifStartTimeBeforeCurrent(storageOfTime);
        String currentDate = getCurrentDate();
        String nextDayDate = addDateToNumberOfDay(1, currentDate);
        String afterTwoDaysDate = addDateToNumberOfDay(2, currentDate);

    //    System.out.println("BEFORE: " + storageOfTime.size() + " time: "
      //          + storageOfTime + " date: " + storageOfDate.size());
        if (storageOfDate.size() < storageOfTime.size()) {
            if (storageOfDate.size() == 0 && storageOfTime.size() == 1) {
                // System.out.println("BEFORE: " + storageOfTime);
                addDateWhenZeroDateAndOneTime(storageOfDate,
                        numberBeforeCurrentTime, currentDate, nextDayDate);
            } else if (storageOfDate.size() == 0 && storageOfTime.size() == 2) {

                addDatesWhenZeroDateAndTwoTime(storageOfTime, storageOfDate,
                        numberBeforeCurrentTime, isStartTimeBeforeCurrent,
                        currentDate, nextDayDate, afterTwoDaysDate);

            } else if (storageOfDate.size() == 1 && storageOfTime.size() == 2) {
                addDateWhenOneDateAndTwoTimes(storageOfTime, storageOfDate,
                        numberBeforeCurrentTime, currentDate, nextDayDate);
            }
         
        }
        return storageOfDate;
    }

    /**
     * add dates when 0 date and 1 time was detected
     * 
     * @param storageOfDate
     *            : to be store with date
     * @param numberBeforeCurrentTime
     *            : number of time before current time
     * @param currentDate
     * @param nextDayDate
     *            : tomorrow date
     */
    private void addDateWhenZeroDateAndOneTime(ArrayList<String> storageOfDate,
            int numberBeforeCurrentTime, String currentDate, String nextDayDate) {
        if (numberBeforeCurrentTime == 1) {
            storageOfDate.add(nextDayDate);
        } else {
            storageOfDate.add(currentDate);
        }
    }

    /**
     * add when 1 dates and 2 times was detected
     * 
     * @param storageOfTime
     *            : contain the times detected
     * @param storageOfDate
     *            : to be store with date
     * @param numberBeforeCurrentTime
     *            : number of time before current time
     * @param currentDate
     * @param nextDayDate
     *            : tomorrow date
     * @throws ParseException
     */
    private void addDateWhenOneDateAndTwoTimes(ArrayList<String> storageOfTime,
            ArrayList<String> storageOfDate, int numberBeforeCurrentTime,
            String currentDate, String nextDayDate) throws ParseException {
        if (storageOfTime.get(0).equals(storageOfTime.get(1))
                || ifStartTimeLaterThanEnd(storageOfTime)) {

            storageOfDate.add(addDateToNumberOfDay(1, storageOfDate.get(0)));

        } else if (storageOfDate.get(0).equals(currentDate)
                && !ifStartTimeLaterThanEnd(storageOfTime)) {

            storageOfDate.add(currentDate);

        } else if (storageOfDate.get(0).equals(currentDate)
                && (ifStartTimeLaterThanEnd(storageOfTime) || numberBeforeCurrentTime == 2)) {

            storageOfDate.add(nextDayDate);

        } else if (!ifStartTimeLaterThanEnd(storageOfTime)) {

            storageOfDate.add(storageOfDate.get(0));
        }
    }

    /**
     * add dates when 0 dates and 2 time is detected
     * 
     * @param storageOfTime
     *            :contains of time detcted
     * @param storageOfDate
     *            :to be stored with the new date
     * @param numberBeforeCurrentTime
     *            : number of times detected before current time
     * @param isStartTimeBeforeCurrent
     *            : true if start time is before current time
     * @param currentDate
     * @param nextDayDate
     * @param afterTwoDaysDate
     * @throws ParseException
     */
    private void addDatesWhenZeroDateAndTwoTime(
            ArrayList<String> storageOfTime, ArrayList<String> storageOfDate,
            int numberBeforeCurrentTime, boolean isStartTimeBeforeCurrent,
            String currentDate, String nextDayDate, String afterTwoDaysDate)
            throws ParseException {
        if (storageOfTime.get(0).equals(storageOfTime.get(1))
                || (!isStartTimeBeforeCurrent && numberBeforeCurrentTime == 1)
                || (numberBeforeCurrentTime == 0 && ifStartTimeLaterThanEnd(storageOfTime))) {

            storageOfDate.add(currentDate);
            storageOfDate.add(nextDayDate);

        } else if ((numberBeforeCurrentTime == 1 && isStartTimeBeforeCurrent)
                || (numberBeforeCurrentTime == 2
                && !ifStartTimeLaterThanEnd(storageOfTime))) {
            storageOfDate.add(nextDayDate);
            storageOfDate.add(nextDayDate);

        } else if (numberBeforeCurrentTime == 2
                && ifStartTimeLaterThanEnd(storageOfTime)) {
            storageOfDate.add(nextDayDate);
            storageOfDate.add(afterTwoDaysDate);

        } else if (numberBeforeCurrentTime == 0
                && !ifStartTimeLaterThanEnd(storageOfTime)) {
            storageOfDate.add(currentDate);
            storageOfDate.add(currentDate);
        }
        System.out.println("numberBeforeCurrentTime: "+numberBeforeCurrentTime);
    }

    /**
     * check if start time is later than end time
     * 
     * @param storageOfTime
     *            : contain time detected
     * @return true if start time later than end time otherwise false
     * @throws ParseException
     */
    private boolean ifStartTimeLaterThanEnd(ArrayList<String> storageOfTime)
            throws ParseException {
        boolean isStartTimeLaterThanEnd = false;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date time1 = timeFormat.parse(storageOfTime.get(0));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time1);
        Date time2 = timeFormat.parse(storageOfTime.get(1));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(time2);

      //  System.out.println("ifStartTimeLaterThanEnd: "
        //        + isStartTimeLaterThanEnd + " time1 " + time1 + " time2: "
          //      + time2);
        if (calendar2.getTime().after(calendar3.getTime())) {
            isStartTimeLaterThanEnd = true;
        }

        return isStartTimeLaterThanEnd;
    }

   
    /**
     * get the current date in DD/MM/YYYY
     * 
     * @return date in DD/MM/YYYY
     */
    private static String getCurrentDate() {
        DateFormat date = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String dateOfTheTask = date.format(cal.getTime());
        return dateOfTheTask;
    }

    /**
     * get date after adding the number of days
     * 
     * @param numberOfDay
     * @return DD/MM/YYYY
     * @throws ParseException
     */
    private static String addDateToNumberOfDay(int numberOfDay, String date)
            throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, numberOfDay);
        String dateOfTheTask = dateFormat.format(cal.getTime());

        return dateOfTheTask;
    }

    /**
     * get the current time in HH:MM
     * 
     * @return time in HH:MM
     */
    private static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Calendar cal = Calendar.getInstance();
        String timeOfTheTask = dateFormat.format(cal.getTime());
        return timeOfTheTask;
    }

    /**
     * set the number of time stored in arrayList
     * 
     * @param storageOfParameters
     */
    public void setNumberOfTime(ArrayList<String> storageOfParameters) {
        numberOfTime = storageOfParameters.size();
    }

    /**
     * return the number of time detected and stored in arrayList
     * 
     * @return the number of time detected
     */
    public int getNumberOfTime() {
        return numberOfTime;
    }

    public String getUserInputLeft() { 
        return description;
    }
}

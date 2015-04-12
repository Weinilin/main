//@author 

package storage;

import application.Task;
import application.TaskComparator;
import application.TimeAnalyser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Memory acts as a facade between LogicController and Database.
 * LogicController makes changes to the taskList stored in Memory.
 * Memory writes these changes to the Database.
 * 
 * @author A0113966Y
 *
 */
public class Memory {

    private static final String DONE = "done";
    private static final String UNDONE = "undone";
    private static final int FEEDBACK_CLASHING_TASK = 3;
    private static final int FEEDBACK_NON_CLASHING_TASK = 1;

    private ArrayList<Task> taskList = new ArrayList<Task>();

    private static Memory memory;

    private static final Logger memoryLogger = Logger.getLogger(Memory.class.getName());

    private Memory() {
        Database database = Database.getInstance();
        initMemory(database);
    }

    public static Memory getInstance() {
        if (memory == null) {
            memory = new Memory();
        }
        return memory;
    }

    /**
     * insert a new task to the list, returns true if successfully
     * added
     * 
     * @param newTask - new task created
     * @return 
     */
    public int addTask(Task newTask) {
        memoryLogger.entering(getClass().getName(), "adding a new task to taskList");
        assert isValidTask(newTask);

        int feedback = FEEDBACK_NON_CLASHING_TASK;

        if (isTimeTask(newTask)) { 
            if (hasExactSameTimeIntervalAsOtherTask(newTask)) {
                memoryLogger.log(Level.FINE, "Clashing Task");
                feedback = FEEDBACK_CLASHING_TASK;
            }

            if (hasOtherTasksWithinIntervalOfAddedTask(newTask)) {
                memoryLogger.log(Level.FINE, "Clashing Task");
                feedback = FEEDBACK_CLASHING_TASK;
            }

            if (isOverlappingWithOtherTasks(newTask)) {
                memoryLogger.log(Level.FINE, "Clashing Task");
                feedback = FEEDBACK_CLASHING_TASK;
            }
        }

        if (taskList.add(newTask)) {
            memoryLogger.log(Level.FINE, "add success");
        } else {
            memoryLogger.log(Level.SEVERE, "Error adding new task!");
            throw new Error("Fatal error! Unable to add Task");
        }
        sortTaskList();
        writeToDatabase();
        memoryLogger.exiting(getClass().getName(), "adding a new task to taskList");

        return feedback;
    }

    private boolean isTimeTask(Task task) {
        String taskType = task.getTaskType();
        return taskType.equals("time task");
    }

    private boolean isOverlappingWithOtherTasks(Task task) {
        ArrayList<Task> timeTasks = getTimeTasks();
        TimeAnalyser ta = new TimeAnalyser();

        String startTime = task.getStartDateTime();
        long startTimeInMillis = ta.getDateTimeInMilliseconds(startTime);

        String endTime = task.getEndDateTime();
        long endTimeInMillis = ta.getDateTimeInMilliseconds(endTime);


        for (int i = 0; i < timeTasks.size(); i++) {
            Task currentTask = timeTasks.get(i);
            String startTime2 = currentTask.getStartDateTime();
            long startTime2InMillis = ta.getDateTimeInMilliseconds(startTime2);

            String endTime2 = currentTask.getEndDateTime();
            long endTime2InMillis = ta.getDateTimeInMilliseconds(endTime2);


            if (endTime2InMillis > endTimeInMillis && startTime2InMillis < endTimeInMillis ||
                    startTime2InMillis < startTimeInMillis && endTime2InMillis > startTimeInMillis) {
                return true;
            } 
        }

        return false;

    }

    private boolean isEntirelyWithinIntervalOfOtherTasks(Task task) {
        ArrayList<Task> timeTasks = getTimeTasks();
        TimeAnalyser ta = new TimeAnalyser();

        String startTime = task.getStartDateTime();
        long startTimeInMillis = ta.getDateTimeInMilliseconds(startTime);

        String endTime = task.getEndDateTime();
        long endTimeInMillis = ta.getDateTimeInMilliseconds(endTime);


        for (int i = 0; i < timeTasks.size(); i++) {
            Task currentTask = timeTasks.get(i);
            String startTime2 = currentTask.getStartDateTime();
            long startTime2InMillis = ta.getDateTimeInMilliseconds(startTime2);

            String endTime2 = currentTask.getEndDateTime();
            long endTime2InMillis = ta.getDateTimeInMilliseconds(endTime2);


            if (endTime2InMillis > endTimeInMillis && startTime2InMillis < endTimeInMillis &&
                    startTime2InMillis < startTimeInMillis && endTime2InMillis > startTimeInMillis) {
                return true;
            } 
        }

        return false;
    }

    private boolean hasOtherTasksWithinIntervalOfAddedTask(Task task) {
        ArrayList<Task> timeTasks = getTimeTasks();

        TimeAnalyser ta = new TimeAnalyser();

        String startTime = task.getStartDateTime();
        long startTimeInMillis = ta.getDateTimeInMilliseconds(startTime);

        String endTime = task.getEndDateTime();
        long endTimeInMillis = ta.getDateTimeInMilliseconds(endTime);


        for (int i = 0; i < timeTasks.size(); i++) {
            Task currentTask = timeTasks.get(i);
            String startTime2 = currentTask.getStartDateTime();
            long startTime2InMillis = ta.getDateTimeInMilliseconds(startTime2);

            String endTime2 = currentTask.getEndDateTime();
            long endTime2InMillis = ta.getDateTimeInMilliseconds(endTime2);

            if (endTime2InMillis < endTimeInMillis && endTime2InMillis > startTimeInMillis &&
                    startTime2InMillis > startTimeInMillis && startTime2InMillis < endTimeInMillis) {
                return true;
            } 
        }

        return false;
    }

    private boolean hasExactSameTimeIntervalAsOtherTask(Task task) {
        ArrayList<Task> timeTasks = getTimeTasks();

        String startTime = task.getStartDateTime();
        String endTime = task.getEndDateTime();

        for (int i = 0; i < timeTasks.size(); i++) {
            Task currentTask = timeTasks.get(i);
            String startTime2 = currentTask.getStartDateTime();
            String endTime2 = currentTask.getEndDateTime();

            if (startTime.equals(startTime2) && endTime.equals(endTime2)) {
                return true;
            } 
        }

        return false;
    }

    private void writeToDatabase() {
        memoryLogger.entering(getClass().getName(), "writing new task to database");
        Database database = Database.getInstance();
        if (database.writeToDatabase(taskList)) {
            memoryLogger.log(Level.FINE, "write success");
        } else {
            memoryLogger.log(Level.SEVERE, "error writing task to database");
        }
        memoryLogger.exiting(getClass().getName(), "writing new task to database");
    }


    public Task removeTask(Task removedTask) {
        memoryLogger.entering(getClass().getName(), "removing a task from database");
        assert isValidTask(removedTask);
        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            if (currentTask == removedTask) {
                if (taskList.remove(i) != null) {
                    memoryLogger.log(Level.FINE, "remove success");
                } else {
                    memoryLogger.log(Level.SEVERE, "task cannot be found in taskList");
                }
            }
        }
        sortTaskList();
        writeToDatabase();
        memoryLogger.exiting(getClass().getName(), "removing a task from database");
        return removedTask;
    }

    public ArrayList<Task> removeAll() {
        memoryLogger.entering(getClass().getName(), "removing all tasks from taskList");
        ArrayList<Task> deletedTaskList = taskList;
        taskList = new ArrayList<Task>();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "remove success");
        memoryLogger.exiting(getClass().getName(), "removing all tasks from taskList");

        return deletedTaskList;
    }

    /**
     * search the list by a keyword
     * 
     * @param keyword
     * @return result arraylist containing the tasks that contains the keyword
     */ 
    public ArrayList<Task> searchDescription(String keyword) {
        memoryLogger.entering(getClass().getName(), "searching task containing keyword");
        assert isValidKeyword(keyword);
        ArrayList<Task> searchList = new ArrayList<Task>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                searchList.add(task);
            }
        }
        memoryLogger.log(Level.FINE, "search success");
        memoryLogger.exiting(getClass().getName(), "searching task containing keyword");

        return searchList;
    }
    //@author A0114463M
    /**
     * search the list that the task that is occurring on this time, showing the uncompleted tasks only
     * @param time time that the task is occurring
     * @return result arraylist containing the tasks occurs on the time
     */
    public ArrayList<Task> searchTime(String time) throws ParseException {
        assert isValidKeyword(time);
        ArrayList<Task> searchList = new ArrayList<Task>();    

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime target = LocalDateTime.parse(time, formatter);
        for (Task task: taskList) {
            if (task.getStatus().equals("done")) {
                continue;
            }
            if (task.getTaskType().equals("deadline")) {
                String suspectDeadline = task.getEndDate() + " " + task.getEndTime();
                LocalDateTime suspectStart = LocalDateTime.parse(suspectDeadline, formatter), 
                        suspectEnd = LocalDateTime.parse(suspectDeadline, formatter);
                if (isWithinOneHour(target, suspectStart, suspectEnd)) {
                    searchList.add(task);
                }
            }
            else if (task.getTaskType().equals("time task")) {
                String suspectStart = task.getStartDate() + " " + task.getStartTime(),
                        suspectEnd = task.getEndDate() + " " + task.getEndTime();
                LocalDateTime start = LocalDateTime.parse(suspectStart, formatter),
                        end = LocalDateTime.parse(suspectEnd, formatter);
                if (isBetweenTime(start, end, target)) {
                    searchList.add(task);
                }
            }
        }
        return searchList;
    }
    //@author A0114463M
    /**
     * check if the target time is within one hour of the time given
     * @param target
     * @param suspectStart
     * @param suspectEnd
     * @return true if the target is within before or after one hour of the time given
     */
    private boolean isWithinOneHour(LocalDateTime target,
            LocalDateTime suspectStart, LocalDateTime suspectEnd) {
        return !suspectStart.minusHours(1).isAfter(target) && !suspectEnd.plusHours(1).isBefore(target);
    }
    //@author A0114463M
    /**
     * search the list that the task that is occurring between the time given this time
     * @param time time that the task is occurring
     * @return result arraylist containing the tasks occurs on the time
     */
    public ArrayList<Task> searchTime(String start, String end) throws ParseException {
        assert isValidKeyword(start);
        assert isValidKeyword(end);
        ArrayList<Task> searchList = new ArrayList<Task>();    

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime targetStart = LocalDateTime.parse(start, formatter),
                targetEnd = LocalDateTime.parse(end, formatter);
        for (Task task: taskList) {
            if (task.getStatus().equals("done")) {
                continue;
            }
            if (task.getTaskType().equals("deadline")) {
                String suspectDeadline = task.getEndDate() + " " + task.getEndTime();
                LocalDateTime suspect = LocalDateTime.parse(suspectDeadline, formatter);
                if (isBetweenTime(targetStart, targetEnd, suspect)) {
                    searchList.add(task);
                }
            }
            else if (task.getTaskType().equals("time task")) {
                String suspectStartString = task.getStartDate() + " " + task.getStartTime(),
                        suspectEndString = task.getEndDate() + " " + task.getEndTime();
                LocalDateTime suspectStart = LocalDateTime.parse(suspectStartString, formatter),
                        suspectEnd = LocalDateTime.parse(suspectEndString, formatter);
                if (isBetweenTime(targetStart, targetEnd, suspectStart, suspectEnd)) {
                    searchList.add(task);
                }
            }
        }
        return searchList;
    }


    /**
     * check if there is any overlapping of the two time periods
     * @param targetStart
     * @param targetEnd
     * @param suspectStart
     * @param suspectEnd
     * @return true if there is any overlapping
     */
    private boolean isBetweenTime(LocalDateTime targetStart,
            LocalDateTime targetEnd, LocalDateTime suspectStart,
            LocalDateTime suspectEnd) {
        return (!suspectStart.isAfter(targetEnd) && !suspectStart.isBefore(targetStart)) || 
                (!suspectEnd.isBefore(targetStart) && (!suspectStart.isAfter(targetEnd)));
    }
    //@author A0114463M
    /**
     * check if a time given is between the two time given
     * @param targetStart the target of start of the time
     * @param targetEnd the target of end of the time
     * @param suspect the time that is been checked
     * @return true if suspect falls within the range given (inclusively)
     */ 
    private boolean isBetweenTime(LocalDateTime targetStart,
            LocalDateTime targetEnd, LocalDateTime suspect) {
        return !suspect.isAfter(targetEnd) && !suspect.isBefore(targetStart);
    }
    //@author A0114463M
    /**
     * search the list that the task that is occurring on this particular time
     * for time task, this point of time shall be in the lapse of the task;
     * for deadline task, search for deadlines that occurs within one hour of the 
     * specified time
     * @param dateTime - time that the task is occurring
     * @return result arraylist containing the tasks occurs on the day
     */      
    public ArrayList<Task> searchDate(String date) throws ParseException{
        assert isValidKeyword(date);
        ArrayList<Task> searchList = new ArrayList<Task>();        

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar targetDate = Calendar.getInstance();
        targetDate.setTime(sdf.parse(date));
        for (Task task: taskList) {
            if (task.getStatus().equals("done")) {
                continue;
            }
            if (task.getTaskType().equals("deadline")) {
                Calendar suspectDate = Calendar.getInstance();
                String endDate = task.getEndDate();
                suspectDate.setTime(sdf.parse(endDate));               
                if (isSameDate(targetDate, suspectDate)) {
                    searchList.add(task);
                }
            }
            else if (task.getTaskType().equals("time task")) {
                Calendar suspectStartDate = Calendar.getInstance(),
                        suspectEndDate = Calendar.getInstance();
                suspectStartDate.setTime(sdf.parse(task.getStartDate()));
                suspectEndDate.setTime(sdf.parse(task.getEndDate()));
                if (isWithinDate(targetDate, suspectStartDate, suspectEndDate)) {
                    searchList.add(task);
                }
            }
        }        
        return searchList;
    }
    //@author A0114463M
    /**
     * search the list that the task that is occurring within the range specified
     * for time task, the task will be added if anyday of the task falls within the 
     * range
     * for deadline task, the task will be added to search listif deadline of the 
     * task falls within the range  
     * @param startDate - the start of the date
     * @param endDate - the start of the time
     * @return result arraylist containing the tasks occurs on the day
     */
    public ArrayList<Task> searchDate(String startDate, String endDate) throws ParseException{
        assert isValidKeyword(startDate);
        assert isValidKeyword(endDate);
        ArrayList<Task> searchList = new ArrayList<Task>();        

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar targetStartDate = Calendar.getInstance(),
                targetEndDate = Calendar.getInstance();
        targetStartDate.setTime(sdf.parse(startDate));
        targetEndDate.setTime(sdf.parse(endDate));

        for (Task task: taskList) {
            if (task.getStatus().equals("done")) {
                continue;
            }
            if (task.getTaskType().equals("deadline")) {
                Calendar suspectDate = Calendar.getInstance();
                suspectDate.setTime(sdf.parse(task.getEndDate()));               
                if (isWithinDate(suspectDate, targetStartDate, targetEndDate)) {
                    searchList.add(task);
                }
            }
            else if (task.getTaskType().equals("time task")) {
                Calendar suspectStartDate = Calendar.getInstance(),
                        suspectEndDate = Calendar.getInstance();
                suspectStartDate.setTime(sdf.parse(task.getStartDate()));
                suspectEndDate.setTime(sdf.parse(task.getEndDate()));
                if (isWithinDate(targetStartDate, targetEndDate, suspectStartDate, suspectEndDate)) {
                    searchList.add(task);
                }
            }
        }        
        return searchList;
    }
    //@author A0114463M
    /**
     * This method checks whether a timed task falls into one range of start date and end date,
     * or it covers the entire range to be checked
     * @param targetStartDate
     * @param targetEndDate
     * @param suspectStartDate
     * @param suspectEndDate
     * @return
     */ 
    private boolean isWithinDate(Calendar targetStartDate,
            Calendar targetEndDate, Calendar suspectStartDate,
            Calendar suspectEndDate) {
        return isWithinDate(suspectStartDate, targetStartDate, targetEndDate) ||
                isWithinDate(suspectEndDate, targetStartDate, targetEndDate) ||
                isWithinDate(targetStartDate, suspectStartDate, suspectEndDate);
    }

    //@author A0114463M
    /**
     * check whether the target date is within the range of the suspect date
     * @param targetDate
     * @param suspectStartDate
     * @param suspectEndDate
     * @return true if target date is within (inclusive) the suspect start and end
     */     
    private boolean isWithinDate(Calendar targetDate, Calendar suspectStartDate,
            Calendar suspectEndDate) {
        return targetDate.get(Calendar.YEAR) >= suspectStartDate.get(Calendar.YEAR) &&
                targetDate.get(Calendar.YEAR) <= suspectEndDate.get(Calendar.YEAR) &&
                targetDate.get(Calendar.DAY_OF_YEAR) >= suspectStartDate.get(Calendar.DAY_OF_YEAR) &&
                targetDate.get(Calendar.DAY_OF_YEAR) <= suspectEndDate.get(Calendar.DAY_OF_YEAR);
    }

    
    /**
     * check whether the target date is same as suspect date
     * @param targetDate
     * @param suspectate
     * @return true if target date is same as the suspect 
     */     
    private boolean isSameDate(Calendar targetDate, Calendar suspectDate) {
        return targetDate.get(Calendar.DAY_OF_YEAR) == suspectDate.get(Calendar.DAY_OF_YEAR) &&
                targetDate.get(Calendar.YEAR) == suspectDate.get(Calendar.YEAR);
    }

    //@author A0113966Y
    public ArrayList<Task> searchStatus(String status) {
        memoryLogger.entering(getClass().getName(), "searching task of the specified status");
        assert isValidStatus(status);
        ArrayList<Task> searchList = new ArrayList<Task>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getStatus().equals(status)) {
                searchList.add(task);
            }
        }
        memoryLogger.log(Level.FINE, "search success");
        memoryLogger.entering(getClass().getName(), "searching task of the specified status");
        return searchList;
    }

    public void editTime(int index, String newStartDateTime, String newEndDateTime) {
        memoryLogger.entering(getClass().getName(), "editing time");
        assert isValidIndex(index);
        Task task = taskList.get(index - 1);
        task.setStartDateTime(newStartDateTime);
        task.setEndDateTime(newEndDateTime);
        sortTaskList();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "edit success");
        memoryLogger.exiting(getClass().getName(), "editing time");

    }

    public void editDescription(int index, String newDescription) {
        memoryLogger.entering(getClass().getName(), "editing description");
        assert isValidIndex(index);
        Task task = taskList.get(index - 1);
        task.setDescription(newDescription);
        sortTaskList();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "edit success");
        memoryLogger.exiting(getClass().getName(), "editing description");

    }

    private void sortTaskList() {
        Collections.sort(taskList, new TaskComparator());

    }

    public void markDone(int index) {
        memoryLogger.entering(getClass().getName(), "marking task");
        assert isValidIndex(index);
        Task task = taskList.get(index - 1);
        task.setStatus(DONE);
        sortTaskList();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "mark success");
        memoryLogger.exiting(getClass().getName(), "marking task");
    }

    public void markDone(Task doneTask) {
        memoryLogger.entering(getClass().getName(), "marking task");
        Task task = taskList.get(taskList.indexOf(doneTask));
        task.setStatus(DONE);
        sortTaskList();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "mark success");
        memoryLogger.exiting(getClass().getName(), "marking task");
    }

    public void markUndone(Task undoneTask) {
        memoryLogger.entering(getClass().getName(), "marking task");
        Task task = taskList.get(taskList.indexOf(undoneTask));
        task.setStatus(UNDONE);
        sortTaskList();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "mark success");
        memoryLogger.exiting(getClass().getName(), "marking task");
    }

    public void editTaskType(int index, String newTaskType) {
        memoryLogger.entering(getClass().getName(), "editing taskType");
        assert isValidIndex(index);
        Task task = taskList.get(index - 1);
        task.setTaskType(newTaskType);
        sortTaskList();
        writeToDatabase();
        memoryLogger.log(Level.FINE, "edit success");
        memoryLogger.exiting(getClass().getName(), "marking task");

    }

    private void initMemory(Database database) {
        memoryLogger.entering(getClass().getName(), "initializing memory");
        taskList = database.readDatabase();
        memoryLogger.log(Level.FINE, "successfully initializing memory");
        memoryLogger.exiting(getClass().getName(), "initializing memory");

    }


    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void display() {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.print(taskList.get(i));
            System.out.println();
        }
    }

    public boolean contains(Task task) {
        return taskList.contains(task);
    }

    public boolean isValidTask(Task newData) {
        return newData != null;
    }

    public boolean isValidKeyword(String keyword) {
        return keyword != null;
    }

    public boolean isValidStatus(String status) {
        return status != null;
    }

    public boolean isValidIndex(int index) {
        return (index > 0 && index <= taskList.size());
    }

    public ArrayList<Task> getDeadlinesAndTimeTasks() {
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

    public ArrayList<Task> getFloatingTasks() {
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

    public ArrayList<Task> getTimeTasks() {
        ArrayList<Task> timeTasks = new ArrayList<Task> ();

        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            String taskType = currentTask.getTaskType();

            if (taskType.equals("time task")) {
                timeTasks.add(currentTask);
            }
        }

        return timeTasks;
    }

    public ArrayList<Task> getDone() {
        ArrayList<Task> doneTasks = new ArrayList<Task> ();

        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            String status = currentTask.getStatus();

            if (status.equals("done")) {
                doneTasks.add(currentTask);
            }
        }

        return doneTasks;
    }

    public ArrayList<Task> getUndone() {
        ArrayList<Task> undoneTasks = new ArrayList<Task> ();

        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            String status = currentTask.getStatus();

            if (status.equals("undone")) {
                undoneTasks.add(currentTask);
            }
        }

        return undoneTasks;
    }

    public ArrayList<Task> getBetween(String date1, String date2) {
        ArrayList<Task> searchList = new ArrayList<Task>();
        TimeAnalyser ta = new TimeAnalyser();

        ArrayList<Task> deadlinesAndTimeTasks = getDeadlinesAndTimeTasks();

        for (int i = 0; i < deadlinesAndTimeTasks.size(); i++) {
            Task currentTask = deadlinesAndTimeTasks.get(i);
            String date;

            if (isDeadline(currentTask)) {
                date = currentTask.getEndDateTime();
            } else {
                date = currentTask.getStartDate();
            }

            if (ta.compare(date1, date) == 1 && ta.compare(date, date2) == 1) {
                searchList.add(currentTask);
            }
        }

        return searchList;
    }

    private boolean isDeadline(Task task) {
        String status = task.getStatus();

        if (status.equals("deadline")) {
            return true;
        }

        return false;
    }

}

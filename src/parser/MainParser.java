package parser;

public class MainParser {
    private String endDate;
    private String endTime;
    private String startTime;
    private String startDate;
    private String feedback;
    private String description;
    private String taskType;
    private String userInputLeft;
    public int numberOfTime;
    public int index;

    public MainParser(String userInput) throws Exception {

        DateTimeParser dateTimeParser = new DateTimeParser(userInput);
        endDate = dateTimeParser.getEndDate();
        startDate = dateTimeParser.getStartDate();
        startTime = dateTimeParser.getStartTime();
        endTime = dateTimeParser.getEndTime();
        feedback = dateTimeParser.getFeedBack();
        numberOfTime = dateTimeParser.getNumberOfTime();
        userInputLeft = dateTimeParser.getUserInputLeft();

        DescriptionParser descriptionParser = new DescriptionParser(
                userInput, userInputLeft);
        description = descriptionParser.getDescription();

        TaskTypeParser taskTypeParser = new TaskTypeParser(numberOfTime);
        taskType = taskTypeParser.getTaskType();
    }
    
    public int getIndex(){
        return index;
    }

    public int getNumberOfTime(){
        return numberOfTime;
    }
    
    public String getUserLeft() {
        return userInputLeft;
    }
    
    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getDescription() {
        return description;
    }
    
    public String getFeedback() {
        return feedback;
    }

}

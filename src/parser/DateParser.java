package parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {

	private static final String  date_Keyword = "\\d+((\\s|\\S)\\w{3,9}(\\s|\\S)|[/.]\\d{2}[/.])\\d+";

	public DateParser(){

	}

	public String extractDate(String userInput){
		Pattern dateDetector = Pattern.compile(date_Keyword);
		Matcher containDate = dateDetector.matcher(
				userInput);

		while(matchedWithlistOfTaskDataWithoutTime.find()){
			String dateOfTheTask = matchedWithlistOfTaskDataWithoutTime.group();
		}

		return dateofTheTask;
	}

}

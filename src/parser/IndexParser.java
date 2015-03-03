import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class IndexParser {

	//private static final String NO_INDEX_IS_ENTER = "no index is enter!";

	public IndexParser(){

	}

	public int extractIndex(String userInput){
		String number = removeEverythingExceptIndex(userInput);
		//if(!number.equals(NO_INDEX_IS_ENTER)){
			int index = Integer.parseInt(number);
	//	}
			return index;
		}

		private String removeEverythingExceptIndex(String userInput) {
			String number = ""; 
			Pattern indexDetector = Pattern.compile("\\d+");
			Matcher indexMatch = indexDetector.matcher(userInput);
			if(indexMatch.find()){
				number = indexMatch.group();
			}
			/*
			else{
				number = NO_INDEX_IS_ENTER;
			}
			*/
			return number;
		}

	}

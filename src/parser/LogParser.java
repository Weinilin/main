package parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogParser {
	// Obtain a suitable logger.
	 private static Logger logger = Logger.getLogger("LogParser");

	 public void bar() {
	 // log a message at INFO level
	 logger.log(Level.INFO, "going to start processing");

	try{
		DateTimeParser.getDateTime("mds sale at 2pm");
	 } catch (Exception ex) {
	//log a message at WARNING level
	 logger.log(Level.WARNING, "processing error", ex);
	 }
	 logger.log(Level.INFO, "end of processing");
	 }

}

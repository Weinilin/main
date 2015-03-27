package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTEMPTEST {

    @Test
    public void test() {
        ArrayList<String> times = new ArrayList<String>();
     
       // ArrayList<String> times = new ArrayList<String>();
        times.clear();
        times.add("06:00");
        times.add("18:00");
        assertEquals(times,
                TimeParser.extractTime("CS2103T assignments 25/3/2016 24/3/2016 4pm"));
        
        
    
    
    }

}

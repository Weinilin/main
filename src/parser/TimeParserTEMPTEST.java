package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTEMPTEST {

    @Test
    public void test() {
        ArrayList<String> times1 = new ArrayList<String>();
     
        // test with am or pm
        times1.clear();
        times1.add("15:30");
        times1.add("17:30");
        assertEquals(times1,
                TimeParser.extractTime("mds sale start at 12pm-12:20pm"));
    }
        
        
    
    
    }



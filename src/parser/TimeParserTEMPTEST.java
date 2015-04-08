package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TimeParserTEMPTEST {

    @Test
    public void test() throws Exception {
        ArrayList<String> times1 = new ArrayList<String>();
        // test symbol of p.m.
        times1.clear();
        times1.add("23:30");
        times1.add("01:30");
        Time1Parser t11 = new Time1Parser("mds sale start at 11.30p for 2 hour");
        assertEquals(times1, t11.getTimeList());
    }  
}



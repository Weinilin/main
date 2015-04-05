package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TEMPDateParserTest {

    @Test
    public void test() throws Exception {
        ArrayList<String> times = new ArrayList<String>();
        // test without space
        times.clear();
        times.add("06:30");
        times.add("18:30");
        Time1Parser dateTime15 = new Time1Parser("mds sale from 6:30am-6:30");
        assertEquals(times, dateTime15.getTimeList()
                );
        
    }
}

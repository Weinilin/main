package parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author A0112823R
 *
 */
public class NumberParserTest {

    @Test
    public void test() {
        // test dd/mm/yyyy
        assertEquals(24, NumberParser.getNumber("24/05/2015"));

        // test dd/mm
        assertEquals(25, NumberParser.getNumber("25/07"));

        // test dd/mm/yyyy when dd is word
        assertEquals(23, NumberParser.getNumber("twenty-three aug 2015"));

        // test dd/mm when dd is word
        assertEquals(31, NumberParser.getNumber("thirty-onejan"));

        // test dd/mm
        assertEquals(1, NumberParser.getNumber("1 feb"));

        // test dd/mm
        assertEquals(20, NumberParser.getNumber("20april"));

        // test mm/dd
        assertEquals(12, NumberParser.getNumber("mar12"));

        // test mm/dd
        assertEquals(4, NumberParser.getNumber("may4 2015"));

        // test mm/dd
        assertEquals(24, NumberParser.getNumber("november twenty-four 2015"));

        // test number of days
        assertEquals(24, NumberParser.getNumber("twenty-four day later"));
    }

}

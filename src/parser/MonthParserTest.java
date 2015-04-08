package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonthParserTest {

    @Test
    public void testConvertWordToInteger() {
        // jan and all squeeze together
        assertEquals(1, MonthParser.convertMonthToNumber("24jan2016"));

        // january and all squeeze together
        assertEquals(1, MonthParser.convertMonthToNumber("24january2016"));

        // feb
        assertEquals(2, MonthParser.convertMonthToNumber("24 feb2016"));

        // feburary
        assertEquals(2, MonthParser.convertMonthToNumber("24 february2016"));

        // march
        assertEquals(3, MonthParser.convertMonthToNumber("24 march 2016"));

        // mar
        assertEquals(3, MonthParser.convertMonthToNumber("24 mar"));

        // April
        assertEquals(4, MonthParser.convertMonthToNumber("24 april 2016"));

        // apr
        assertEquals(4, MonthParser.convertMonthToNumber("24 apr"));

        // may
        assertEquals(5, MonthParser.convertMonthToNumber("24 may 2016"));

        // june
        assertEquals(6, MonthParser.convertMonthToNumber("24 june 2016"));

        // jun
        assertEquals(6, MonthParser.convertMonthToNumber("24 jun"));

        // july
        assertEquals(7, MonthParser.convertMonthToNumber("24 july 2016"));

        // jul
        assertEquals(7, MonthParser.convertMonthToNumber("24 jul"));

        // august
        assertEquals(8, MonthParser.convertMonthToNumber("24 august 2016"));

        // aug
        assertEquals(8, MonthParser.convertMonthToNumber("24 aug"));

        // september
        assertEquals(9, MonthParser.convertMonthToNumber("24 september 2016"));

        // sep
        assertEquals(9, MonthParser.convertMonthToNumber("24 sep"));

        // octobor
        assertEquals(10, MonthParser.convertMonthToNumber("24 octobor 2016"));

        // oct
        assertEquals(10, MonthParser.convertMonthToNumber("24 oct"));

        // november
        assertEquals(11, MonthParser.convertMonthToNumber("24 november 2016"));

        // nov
        assertEquals(11, MonthParser.convertMonthToNumber("24 nov"));

        // december
        assertEquals(12, MonthParser.convertMonthToNumber("24 december 2016"));

        // dec
        assertEquals(12, MonthParser.convertMonthToNumber("24 dec"));

        // not detected
        assertEquals(0, MonthParser.convertMonthToNumber("dgsgsg"));
    }

}

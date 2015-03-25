package parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DateParserTest {

	@Test
	public void testNull() {
		ArrayList<String> dates = new ArrayList<String>();

		// test if nothing detected
		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale at 3pm to 4pm"));
	}

	public void testDateKeyword1() {
		ArrayList<String> dates = new ArrayList<String>();
		// test date keyword 1 without year
		dates.clear();
		dates.add("01/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale due 1/3 2:30am"));

		// test date keyword 1 with year
		dates.clear();
		dates.add("01/03/2016");
		assertEquals(dates, DateParser.extractDate("mds sale due 1/3/2016"));
	}

	public void testDateKeyword2() {
		ArrayList<String> dates = new ArrayList<String>();
		// test date keyword 2 without year, the case sensitivity
		dates.clear();
		dates.add("12/09/2015");
		assertEquals(dates,
				DateParser.extractDate("etah krej lpp at 12 September 3pm"));

		// test date keyword 2 with year, the case sensitivity
		dates.clear();
		dates.add("12/10/2015");
		assertEquals(dates,
				DateParser.extractDate("etah krej lpp at 12 OcToBor 3pm"));
	}

	public void testDateKeyword3() {
		ArrayList<String> dates = new ArrayList<String>();
		// test date keyword 3 without year, the case sensitivity
		dates.clear();
		dates.add("12/01/2015");
		assertEquals(dates, DateParser.extractDate("mds sale 12 jan"));

		// test date keyword 3 with year
		dates.clear();
		dates.add("13/03/2014");
		assertEquals(dates, DateParser.extractDate("mds sale 13 mar 2014"));

		// test date keyword 3 with year and case sensitivity
		dates.clear();
		dates.add("13/07/2014");
		assertEquals(dates, DateParser.extractDate("mds sale 13 JuL 2014"));
	}

	public void testDateKeyword4() {
		ArrayList<String> dates = new ArrayList<String>();
		// test keyword 4: after _ days
		dates.clear();
		dates.add("28/03/2015");
		assertEquals(dates,
				DateParser.extractDate("mds sale start in after 3 days"));

		// test keyword 4: next _ days
		dates.clear();
		dates.add("28/03/2015");
		assertEquals(dates,
				DateParser.extractDate("mds sale start in next 3 days"));

		// test keyword 4: _ days from now
		dates.clear();
		dates.add("28/03/2015");
		assertEquals(dates,
				DateParser.extractDate("mds sale start 3 days from now"));

		dates.clear();
		assertEquals(dates,
				DateParser.extractDate("mds sale start in ~after 3 days~"));
	}

	public void testDateKeyword5() {
		ArrayList<String> dates = new ArrayList<String>();
		// test keyword 5: tomorrow
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale from tomorrow"));

		// test keyword 5: tmr
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale from tmr"));

		// test keyword 5: the following day
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates,
				DateParser.extractDate("mds sale the following day"));

		// test keyword 5: the next day
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale the next day"));

		// test keyword 5: after today
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale the next day"));

		// test keyword 5: after tomorrow
		dates.clear();
		dates.add("27/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale after tomorrow"));

		// test keyword 5: after today
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale after today"));
	}

	public void testDateKeyword6() {
		ArrayList<String> dates = new ArrayList<String>();
		// test keyword 6 : weeks later
		dates.clear();
		dates.add("01/04/2015");
		assertEquals(dates,
				DateParser.extractDate("mds sale start 1 weeks later"));

		// test keyword 6: month later
		dates.clear();
		dates.add("25/04/2015");
		assertEquals(dates,
				DateParser.extractDate("mds sale start 1 month later"));

		// test keyword 6: year later
		dates.clear();
		dates.add("25/04/2018");
		assertEquals(dates,
				DateParser.extractDate("mds sale start 3 year later"));
	}

	private void testDateKeyword7() {
		ArrayList<String> dates = new ArrayList<String>();
		// test date keyword 7
		dates.clear();
		dates.add("26/03/2015");
		assertEquals(dates, DateParser.extractDate("mds sale next monday"));

		dates.clear();
		dates.add("01/04/2015");
		assertEquals(dates, DateParser.extractDate("mds sale next sun"));
	}

	@Test
	public void testDateWithoutHashTag() {
		ArrayList<String> dates = new ArrayList<String>();

		// when 1 is being ~~
		dates.clear();
		assertEquals(dates, DateParser.extractDate("mds sale due ~1/3~ at 2pm"));

		// when two is being ~~
		dates.clear();
		assertEquals(
				dates,
				DateParser
						.extractDate("mds sale from ~12 Jan at 3pm to 13 March~"));
	}

}

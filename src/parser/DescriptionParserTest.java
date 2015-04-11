package parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * take note that this test case is current date and current time and weekday
 * sensitive eg: today and this weekday that pass the current weekday we are in
 * (today: fri, key in this mon will have error)
 * 
 * @author A0112823R
 *
 */
public class DescriptionParserTest {

    @Test
    public void withHashTagTest() throws Exception {

        MainParser m1 = new MainParser("~CS2103T assignment~ at 2pm 23/4");
        String userInputLeftOver = m1.getUserInputLeft();
        DescriptionParser d3 = new DescriptionParser(
                "~CS2103T assignment~ at 2pm 23/4", userInputLeftOver);
        assertEquals(d3.getDescription(), "CS2103T assignment");

        // To test for deadlines task and with escaped character
        // ~~ start of the statement
        MainParser m2 = new MainParser("~6am~ cafe at 2pm");
        userInputLeftOver = m2.getUserInputLeft();
        DescriptionParser d1 = new DescriptionParser("~6am~ cafe at 2pm",
                userInputLeftOver);
        assertEquals(d1.getDescription(), "6am cafe");

        // To test for timed task with escaped character
        // ~~middle of statement
        MainParser m3 = new MainParser("2pm to 7pm in ~6 in the morn~ 27/6");
        userInputLeftOver = m3.getUserInputLeft();
        DescriptionParser d2 = new DescriptionParser(
                "2pm to 7pm in ~6 in the morn~ 27/6", userInputLeftOver);
        assertEquals(d2.getDescription(), "in 6 in the morn");

        // ~~ end of the statement
        MainParser m4 = new MainParser("CS2103T assignment at ~2 jan~");
        String userInputLeftOver1 = m4.getUserInputLeft();
        DescriptionParser d5 = new DescriptionParser(
                "CS2103T assignment at ~2 jan~", userInputLeftOver1);
        assertEquals(d5.getDescription(), "CS2103T assignment at 2 jan");

        // ~~ more than 2
        MainParser m5 = new MainParser(
                "CS2103T ~tomorrow~ la la la on ~ooooo~ at 3pm 23/9 ");
        userInputLeftOver = m5.getUserInputLeft();
        DescriptionParser d7 = new DescriptionParser(
                "CS2103T ~tomorrow~ la la la on ~ooooo~ at 3pm 23/9 ",
                userInputLeftOver);
        assertEquals(d7.getDescription(), "CS2103T tomorrow la la la on ooooo");

        // no ~~
        MainParser m6 = new MainParser("CS2103T assignment on 2nd May at 2pm");
        userInputLeftOver = m6.getUserInputLeft();
        DescriptionParser d8 = new DescriptionParser(
                "CS2103T assignment on 2nd May at 2pm", userInputLeftOver);
        assertEquals(d8.getDescription(), "CS2103T assignment");

        // 1 ~ not 2
        MainParser m7 = new MainParser("CS2103T assignment on ~2nd december");
        userInputLeftOver = m7.getUserInputLeft();
        DescriptionParser d9 = new DescriptionParser(
                "CS2103T assignment on ~2nd december", userInputLeftOver);
        assertEquals(d9.getDescription(), "CS2103T assignment");

        // test ~with space hchhd~
        MainParser m8 = new MainParser(
                "CS2103T assignment on ~ 2nd december ~ at");
        userInputLeftOver = m8.getUserInputLeft();
        DescriptionParser d10 = new DescriptionParser(
                "CS2103T assignment on ~ 2nd december ~ at", userInputLeftOver);
        assertEquals(d10.getDescription(),
                "CS2103T assignment on 2nd december at");

        // test with having word join with ~ ~ as one word in the back
        MainParser m9 = new MainParser(
                "CS2103T assignment on ~2nd december~!!!!");
        userInputLeftOver = m9.getUserInputLeft();
        DescriptionParser d11 = new DescriptionParser(
                "CS2103T assignment on ~2nd december~!!!!", userInputLeftOver);
        assertEquals(d11.getDescription(),
                "CS2103T assignment on 2nd december !!!!");

        // test with having word join with ~ ~ as one word in the front
        MainParser m10 = new MainParser(
                "CS2103T assignment on !!!~2nd december~");
        userInputLeftOver = m10.getUserInputLeft();
        DescriptionParser d12 = new DescriptionParser(
                "CS2103T assignment on !!!~2nd december~", userInputLeftOver);
        assertEquals(d12.getDescription(),
                "CS2103T assignment on !!! 2nd december");

        // test with having word join with ~ ~ as one word in the front and back
        MainParser m11 = new MainParser(
                "e~2nd december~!!!  CS2103T assignment on");
        userInputLeftOver = m11.getUserInputLeft();
        DescriptionParser d13 = new DescriptionParser(
                "e~2nd december~!!! CS2103T assignment on", userInputLeftOver);
        assertEquals(d13.getDescription(),
                "e 2nd december !!! CS2103T assignment on");

        // test with having word join with ~ ~ as one word in the front and back
        MainParser m12 = new MainParser("!~task~!");
        userInputLeftOver = m12.getUserInputLeft();
        DescriptionParser d14 = new DescriptionParser("!~task~!",
                userInputLeftOver);
        assertEquals(d14.getDescription(), "! task !");

        // test with having word join with ~ ~ as one word in the front and back
        // with time
        MainParser m13 = new MainParser("dggdg~hhhh2pm~3pm24/03");
        userInputLeftOver = m13.getUserInputLeft();
        DescriptionParser d15 = new DescriptionParser(
                "dggdg~hhhh2pm~3pm on 24/3", userInputLeftOver);
        assertEquals(d15.getDescription(), "dggdg hhhh2pm");
        System.out.println("m13: " + m13.getEndDate());

    }

    @Test
    public void testWithoutHashTag() throws Exception {

        MainParser m2 = new MainParser("add complete developer guide on 13/04 ");
        String userInputLeftOver = m2.getUserInputLeft();
        DescriptionParser d11 = new DescriptionParser(
                "add complete developer guide on 13/04 ", userInputLeftOver);
        assertEquals(d11.getDescription(), "add complete developer guide");

        // test for timed task
        MainParser m1 = new MainParser("Superman from 2pm to 4pm");
        userInputLeftOver = m1.getUserInputLeft();
        DescriptionParser d10 = new DescriptionParser(
                "Superman from 2pm to 4pm", userInputLeftOver);
        assertEquals(d10.getDescription(), "Superman");

        // test for deadlines
        MainParser m3 = new MainParser(
                "CS2103T assignment at 20th April at 2pm");
        userInputLeftOver = m3.getUserInputLeft();
        DescriptionParser d8 = new DescriptionParser(
                "CS2103T assignment at 20th April at 2pm", userInputLeftOver);
        assertEquals(d8.getDescription(), "CS2103T assignment");

        MainParser m4 = new MainParser("On   sun for CS2103T assignment");
        userInputLeftOver = m4.getUserInputLeft();
        DescriptionParser d7 = new DescriptionParser(
                "On   sun for CS2103T assignment", userInputLeftOver);
        assertEquals(d7.getDescription(), "for CS2103T assignment");

        // test for floating
        MainParser m5 = new MainParser("CS2103T assignment!");
        userInputLeftOver = m5.getUserInputLeft();
        DescriptionParser d9 = new DescriptionParser("CS2103T assignment!",
                userInputLeftOver);
        assertEquals(d9.getDescription(), "CS2103T assignment!");

        // test for conjunction
        MainParser m6 = new MainParser(
                "gigi by assignment on next sun at 3pm at ivle from !");
        userInputLeftOver = m6.getUserInputLeft();
        DescriptionParser d14 = new DescriptionParser(
                "gigi by assignment on next sun at 3pm at ivle from !",
                userInputLeftOver);
        assertEquals(d14.getDescription(), "gigi by assignment at ivle from !");

    }

}

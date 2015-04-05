package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class EscapedTextParserTest {

    @Test
    public void test() {
        // test with two ~ escaped char
        EscapedTextParser et1 = new EscapedTextParser("~6am~ cafe");
        assertEquals(et1.getEscapedText(), "6am");

        // test 1 ~ escaped char
        EscapedTextParser et2 = new EscapedTextParser("~7pm jump");
        assertEquals(et2.getEscapedText(), "");
        
        //test no ~ escaped char
        EscapedTextParser et3 = new EscapedTextParser(" 7pm jump");
        assertEquals(et3.getEscapedText(), "");
        
        //test multiple ~~ escaped char
        EscapedTextParser et4 = new EscapedTextParser("mds hdshsa ~oooo~ dhhd ~bye bye~ dhsah ~cgcgcg~l");
        assertEquals(et4.getEscapedText(), "oooo bye bye cgcgcg");
    }

}

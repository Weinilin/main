package parser;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * 
 * @author A0112823R
 *
 */
public class EscapedTextParserTest {

    @Test
    public void test() {
        // test with two ~ escaped char
        EscapedTextParser et1 = new EscapedTextParser("~6am~ cafe");
        assertEquals(et1.getEscapedText(), " ~6am~");

        // test 1 ~ escaped char
        EscapedTextParser et2 = new EscapedTextParser("~7pm jump");
        assertEquals(et2.getEscapedText(), "");
        
        //test no ~ escaped char
        EscapedTextParser et3 = new EscapedTextParser(" 7pm jump");
        assertEquals(et3.getEscapedText(), "");
        
        //test multiple ~~ escaped char
        EscapedTextParser et4 = new EscapedTextParser("mds hdshsa ~oooo~ dhhd ~bye bye~ dhsah p~cgcgcg~l");
        assertEquals(et4.getEscapedText(), " ~oooo~ ~bye bye~ ~cgcgcg~");
        
        //test multiple ~~ escaped char with word at front and back
        EscapedTextParser et5 = new EscapedTextParser("mds hdshsa~oooo~dhhd ~bye bye~ dhsah p~cgcgcg~l");
        assertEquals(et5.getEscapedText(), " ~oooo~ ~bye bye~ ~cgcgcg~");
        
      //test multiple ~~ escaped char with word at front 
        EscapedTextParser et6 = new EscapedTextParser("mds hdshsa~oooo~ dhhd ~bye bye~ dhsah p~cgcgcg~l");
        assertEquals(et6.getEscapedText(), " ~oooo~ ~bye bye~ ~cgcgcg~");
        
        //test multiple ~~ escaped char with word at back 
        EscapedTextParser et7 = new EscapedTextParser("mds hdshsa ~oooo~dhhd ~bye bye~dhsah p~cgcgcg~l");
        assertEquals(et7.getEscapedText(), " ~oooo~ ~bye bye~ ~cgcgcg~");
    }

}

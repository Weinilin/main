package parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 1) timed task
 * 2) deadlines
 * 3) floating
 * 4) escaped text
 * @author WeiLin
 *
 */
public class MainParserTest {

    @Test
    public void testDeadline() throws Exception {
       //deadline with no ~~
        MainParser m1 = new MainParser("CS2103T project submit at 13/4");
        assertEquals("CS2103T project submit", m1.getDescription());
        assertEquals("Mon 13/04/2015", m1.getEndDate());
        assertEquals("-", m1.getStartDate());
        assertEquals("-", m1.getStartTime());
        assertEquals("23:59", m1.getEndTime());
        assertEquals("deadline", m1.getTaskType());
        
        //deadine with ~~
        MainParser m2 = new MainParser("Then go ~6am cafe~ to eat!!! at 14/4 2pm");
        assertEquals("Then go 6am cafe to eat!!!", m2.getDescription());
        assertEquals("Tues 14/04/2015", m2.getEndDate());
        assertEquals("-", m2.getStartDate());
        assertEquals("-", m2.getStartTime());
        assertEquals("14:00", m2.getEndTime());
        assertEquals("deadline", m2.getTaskType());      
    }

    @Test
    public void testFloating() throws Exception {
        
       //floating with no ~~
        MainParser m1 = new MainParser("la la sing and sing!:)");
        assertEquals("la la sing and sing!:)", m1.getDescription());
        assertEquals("-", m1.getEndDate());
        assertEquals("-", m1.getStartDate());
        assertEquals("-", m1.getStartTime());
        assertEquals("-", m1.getEndTime());
        assertEquals("floating task", m1.getTaskType());
       
        // floating with ~~
        MainParser m2 = new MainParser("!~12/4~ dhdh ~2 jan 2014~ hdhdh ");
        assertEquals("!12/4 dhdh 2 jan 2014 hdhdh", m2.getDescription());
        assertEquals("-", m2.getEndDate());
        assertEquals("-", m2.getStartDate());
        assertEquals("-", m2.getStartTime());
        assertEquals("-", m2.getEndTime());
        assertEquals("floating task", m2.getTaskType());      
    }
    
    @Test 
    //PS: it is current time sensitive. if 13:00 is before current time:
    //switch start and end to the next day
    public void testTime() throws Exception {
       
        // test timed
        MainParser m1 = new MainParser("go grandma house at 1300h to 6pm");
        assertEquals("go grandma house", m1.getDescription());
        assertEquals("Wed 08/04/2015", m1.getEndDate());
        assertEquals("Wed 08/04/2015", m1.getStartDate());
        assertEquals("13:00", m1.getStartTime());
        assertEquals("18:00", m1.getEndTime());
        assertEquals("time task", m1.getTaskType());
        
        // test timed with ~
        MainParser m2 = new MainParser("~1300 to 6pm~ go go go ~hshdh ~ sisisi from 07/04 13:00 to 18:00");
        assertEquals("1300 to 6pm go go go hshdh sisisi", m2.getDescription());
        assertEquals("Tues 07/04/2015", m2.getEndDate());
        assertEquals("Tues 07/04/2015", m2.getStartDate());
        assertEquals("13:00", m2.getStartTime());
        assertEquals("18:00", m2.getEndTime());
        assertEquals("time task", m2.getTaskType());
        
    }
}

package pig;

import java.io.IOException;
import java.net.URL;

import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
import org.junit.Before;
import org.junit.Test;

public class HBaseUsageTest {

    PigTest pigTest;
    @Before
    public void before() throws IOException{
        URL scriptUrl = this.getClass().getResource("/pig/HBaseUsage.pig");
        pigTest = new PigTest(scriptUrl.getPath());
    }
    
    @Test
    public void testHitOnKeyword() throws IOException, ParseException {
        String[] input = { 
                "id1" + '\t' + "user1" + '\t' + "00129293" + '\t' + "review has affordable keyword"
                };
        String[] expected = { "(id1,user1,00129293,review has affordable keyword)" };
        pigTest.assertOutput("reviews", input, "found", expected);
    }
    
    @Test
    public void testNoHit() throws IOException, ParseException {
        String[] input = { 
                "id1" + '\t' + "user1" + '\t' + "00129293" + '\t' + "review without keyword"
                };
        pigTest.assertOutput("reviews", input, "found", new String[]{});
    }

}

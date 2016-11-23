package pig;

import java.io.IOException;
import java.net.URL;

import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
import org.junit.Test;

public class MostSeenStartLetterTest {

    @Test
    public void testMostSeenLetterScript() throws IOException, ParseException {
        URL scriptUrl = this.getClass().getResource("/pig/MostSeenStartLetter.pig");
        PigTest pigTest = new PigTest(scriptUrl.getPath());

        String[] input = { 
                "input text for the script", 
                "second line for the input text" };
        String[] expected = { "(t,4)" };

        pigTest.assertOutput("lines", input, "result", expected);
    }
}

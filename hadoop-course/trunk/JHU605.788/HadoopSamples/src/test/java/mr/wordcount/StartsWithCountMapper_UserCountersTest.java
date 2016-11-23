package mr.wordcount;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import mr.wordcount.StartsWithCountMapper_UserCounters.Tokens;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

public class StartsWithCountMapper_UserCountersTest {

    @Test
    public void testCounters() throws IOException {
        LongWritable inputKey = new LongWritable();
        Text inputValue = new Text("Must process this line of text");
        MapDriver<LongWritable, Text, Text, IntWritable> mapDriver = 
                new MapDriver<LongWritable, Text, Text, IntWritable>()
            .withMapper(new StartsWithCountMapper_UserCounters())
            .withInput(inputKey, inputValue);
        mapDriver.run();
        Counters counters = mapDriver.getCounters();
        assertEquals(6, counters.findCounter(Tokens.Total).getValue());
        assertEquals(1, counters.findCounter(Tokens.FirstCharUpper).getValue());
        assertEquals(5, counters.findCounter(Tokens.FirstCharLower).getValue());
    }
}

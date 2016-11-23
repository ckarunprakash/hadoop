package mr.wordcount;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

public class StartsWithCountReducerTest {

    @Test
    public void testSum() throws IOException {
        Text inputKey = new Text("s");
        List<IntWritable> inputValue = Arrays.asList(
                new IntWritable(5), new IntWritable(5), new IntWritable(8));
        new ReduceDriver<Text, IntWritable, Text, IntWritable>()
            .withReducer(new StartsWithCountReducer())
            .withInput(inputKey, inputValue)
            .withOutput(inputKey, new IntWritable(18))
            .runTest();
    }

}

package mr.wordcount.writables;

import mr.wordcount.StartsWithCountMapper;
import mr.wordcount.StartsWithCountReducer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static org.junit.Assert.*;

// MRUnit does not supports MultipleInputs (yet? MRUNIT-69)
public class StartsWithCountJob_ObjectWritableTest {
    @Test
    public void withLongWritableMapper() throws IOException {
        doTest(new StartsWithCountJob_ObjectWritable.LongObjWritableMapper());
    }

    @Test
    public void withIntWritableMapper() throws IOException {
        doTest(new StartsWithCountJob_ObjectWritable.IntObjWritableMapper());
    }

    private void doTest(Mapper mapper) throws IOException {
        MapReduceDriver<LongWritable, Text, Text, ObjectWritable, Text, ObjectWritable> driver =
                new MapReduceDriver<LongWritable, Text, Text, ObjectWritable,Text, ObjectWritable>();

        driver.setMapper(mapper);
        driver.setReducer(new StartsWithCountJob_ObjectWritable.ObjectWritableReducer());

        LongWritable k = new LongWritable();
        driver.withInput(k, new Text("This is a line number one"));
        driver.withInput(k, new Text("This is another line"));

        List<Pair<Text,ObjectWritable>> res = driver.run();
        assertEquals(6, res.size());

        Collections.sort(res, new ComparatorByPairKey<ObjectWritable>());

        assertEquals("T", res.get(0).getFirst().toString());
        assertEquals(2, ((IntWritable)res.get(0).getSecond().get()).get());

        assertEquals("a", res.get(1).getFirst().toString());
        assertEquals(2, ((IntWritable)res.get(1).getSecond().get()).get());

        assertEquals("i", res.get(2).getFirst().toString());
        assertEquals(2, ((IntWritable)res.get(2).getSecond().get()).get());

        assertEquals("l", res.get(3).getFirst().toString());
        assertEquals(2, ((IntWritable)res.get(3).getSecond().get()).get());

        assertEquals("n", res.get(4).getFirst().toString());
        assertEquals(1, ((IntWritable)res.get(4).getSecond().get()).get());

        assertEquals("o", res.get(5).getFirst().toString());
        assertEquals(1, ((IntWritable)res.get(5).getSecond().get()).get());
    }
}

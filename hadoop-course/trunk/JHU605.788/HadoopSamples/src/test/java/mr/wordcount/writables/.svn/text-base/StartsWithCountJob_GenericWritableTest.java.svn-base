package mr.wordcount.writables;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

// MRUnit does not supports MultipleInputs (yet? MRUNIT-69)
public class StartsWithCountJob_GenericWritableTest {
    @Test
    public void withLongWritableMapper() throws IOException {
        doTest(new StartsWithCountJob_GenericWritable.LongObjWritableMapper());
    }

    @Test
    public void withIntWritableMapper() throws IOException {
        doTest(new StartsWithCountJob_GenericWritable.IntObjWritableMapper());
    }

    private void doTest(Mapper mapper) throws IOException {
        MapReduceDriver<LongWritable, Text, Text, StartsWithCountJob_GenericWritable.MyGenericObject, Text, StartsWithCountJob_GenericWritable.MyGenericObject> driver =
                new MapReduceDriver<LongWritable, Text, Text, StartsWithCountJob_GenericWritable.MyGenericObject,Text, StartsWithCountJob_GenericWritable.MyGenericObject>();

        driver.setMapper(mapper);
        driver.setReducer(new StartsWithCountJob_GenericWritable.GenericObjectReducer());

        LongWritable k = new LongWritable();
        driver.withInput(k, new Text("This is a line number one"));
        driver.withInput(k, new Text("This is another line"));

        List<Pair<Text,StartsWithCountJob_GenericWritable.MyGenericObject>> res = driver.run();
        assertEquals(6, res.size());

        Collections.sort(res, new ComparatorByPairKey<StartsWithCountJob_GenericWritable.MyGenericObject>());

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

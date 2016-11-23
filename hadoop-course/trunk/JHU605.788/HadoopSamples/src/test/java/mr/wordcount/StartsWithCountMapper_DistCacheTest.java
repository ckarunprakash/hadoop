package mr.wordcount;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StartsWithCountMapper_DistCacheTest {

    private File distCacheFile;
    @Before
    public void before() throws IOException{
        distCacheFile = new File(StartsWithCountMapper_DistCache.EXCLUDE_FILE);
        if(distCacheFile.exists()){
            removeDistCache();
        }
        FileUtils.write(distCacheFile, "p");
    }
    
    @After
    public void removeDistCache() throws IOException{
        if(distCacheFile.exists()){
            FileUtils.forceDelete(distCacheFile);
        }
    }
    
    @Test
    public void test() throws IOException {
        LongWritable inputKey = new LongWritable();
        Text inputValue = new Text("Must process this line of text");
        new MapDriver<LongWritable, Text, Text, IntWritable>()
            .withMapper(new StartsWithCountMapper_DistCache())
            .withInput(inputKey, inputValue)
            .withOutput(new Text("M"), new IntWritable(1))
            .withOutput(new Text("t"), new IntWritable(1))
            .withOutput(new Text("l"), new IntWritable(1))
            .withOutput(new Text("o"), new IntWritable(1))
            .withOutput(new Text("t"), new IntWritable(1))
            .runTest();
    }
}

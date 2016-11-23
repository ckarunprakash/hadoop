package mr.wordcount;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StartsWithCountMapper_DistCache_WithNativeSupportTest {

    private File distCacheFile;
    @Before
    public void before() throws IOException{
        distCacheFile = new File("./target/"+StartsWithCountMapper_DistCache.EXCLUDE_FILE);
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
        Text inputValue = new Text("Must process");
        new MapDriver<LongWritable, Text, Text, IntWritable>()
            .withMapper(new StartsWithCountMapper_DistCache1())
            .withInput(inputKey, inputValue)
            .withOutput(new Text("M"), new IntWritable(1))
            .withCacheFile(distCacheFile.toURI())
            .runTest();
    }
}

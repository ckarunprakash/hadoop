package mr.reviews.fsstruct;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.junit.Before;
import org.junit.Test;

public class EntireFileRecordReaderTests {

    private File inputFile = new File("./target/test/input.txt");
    private Configuration conf = new Configuration();

    @Before
    public void setUpTest() throws IOException {
        FileUtils.deleteQuietly(inputFile);
        conf.set("fs.defaultFS", "file:///");
    }

    @Test
    public void testNextKeyValue() throws IOException, InterruptedException {
        String inputStr = "first line\nsecond line\nthird line";
        FileUtils.writeStringToFile(inputFile, inputStr);
        
        FileSplit split = mock(FileSplit.class);
        when(split.getPath()).thenReturn(new Path(inputFile.getPath()));

        TaskAttemptContext context = mock(TaskAttemptContext.class);
        when(context.getConfiguration()).thenReturn(conf);
        
        EntireFileRecordReader underTest = new EntireFileRecordReader();
        underTest.initialize(split, context);

        assertTrue(underTest.nextKeyValue());
        assertEquals(NullWritable.get(), underTest.getCurrentKey());
        assertEquals(new BytesWritable(inputStr.getBytes()), underTest.getCurrentValue());
        
        assertFalse(underTest.nextKeyValue());
        assertFalse(underTest.nextKeyValue());
    }

}

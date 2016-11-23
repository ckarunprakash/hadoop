package mr.reviews.fsstruct;

import java.io.IOException;

import mr.reviews.fsstruct.support.FsHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class EntireFileRecordReader extends RecordReader<NullWritable, BytesWritable> {

    private FileSplit split;
    private Configuration conf;
    private BytesWritable result = new BytesWritable();
    private boolean readIn = false;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.split = (FileSplit) split;
        this.conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (readIn) {
            return false;
        }

        FsHelper fsHelper = new FsHelper(conf);
        byte[] buffer = fsHelper.readBytes(split.getPath());
        result.set(buffer, 0, buffer.length);
        
        readIn = true;
        return true;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return NullWritable.get();
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return result;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        if ( readIn){
            return 1;
        }
        return 0;
    }

    @Override
    public void close() throws IOException {
        // nothing to close in this case
    }
}

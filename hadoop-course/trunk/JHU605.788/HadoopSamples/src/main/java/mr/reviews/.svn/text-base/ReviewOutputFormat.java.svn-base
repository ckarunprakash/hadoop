package mr.reviews;

import java.io.IOException;

import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewReport;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ReviewOutputFormat extends FileOutputFormat<ReviewKeyWritable, ReviewReport>{
    @Override
    public RecordWriter<ReviewKeyWritable, ReviewReport> getRecordWriter(TaskAttemptContext job) throws IOException,
            InterruptedException {
        Path outPath = getOutputPath(job);
        return new ReviewRecordWriter(job.getConfiguration(), outPath);
    }
}

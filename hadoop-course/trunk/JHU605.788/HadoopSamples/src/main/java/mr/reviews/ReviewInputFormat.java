package mr.reviews;

import java.io.IOException;

import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class ReviewInputFormat extends FileInputFormat<LongWritable, ReviewWritable> {
    @Override
    public RecordReader<LongWritable, ReviewWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        ReviewWritablesRecordReader reader = new ReviewWritablesRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}

package mr.reviews;

import java.io.IOException;

import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class ReviewWritablesRecordReader extends RecordReader<LongWritable, ReviewWritable>{
    private final LineRecordReader lineReader = new LineRecordReader();
    private final ReviewWritable review = new ReviewWritable();
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        lineReader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        boolean hasNextKey = lineReader.nextKeyValue();
        if (hasNextKey){
            parse(lineReader.getCurrentValue());
        }
        return hasNextKey;
    }
    
    private void parse(Text value) {
        // expecting input "<username>,<comment>,<postedTimestamp>"
        String[] split = value.toString().split(",");
        review.setAuthor(split[0]);
        review.setContent(split[1]);
        review.setPostedTimestamp(Long.valueOf(split[2]));
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return lineReader.getCurrentKey();
    }

    @Override
    public ReviewWritable getCurrentValue() throws IOException, InterruptedException {
        return review;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        lineReader.close();
    }
}
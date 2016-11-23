package mr.reviews;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewWritable;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class ReviewMapper extends Mapper<LongWritable, ReviewWritable, ReviewKeyWritable, ReviewWritable> {

    private ReviewKeyWritable keyWritable = new ReviewKeyWritable();
    private List<String> valuesToLookFor;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String value = context.getConfiguration().get(PROP_FIND_VALUE);
        Validate.notEmpty(value, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        valuesToLookFor = Arrays.asList(value.split(","));
    }

    @Override
    protected void map(LongWritable reviewNum, ReviewWritable incomingReview, Context context) throws IOException, InterruptedException {
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, incomingReview.getContent())) {
                keyWritable.setKeyword(valueToLookFor);
                keyWritable.setAuthor(incomingReview.getAuthor());
                context.write(keyWritable, incomingReview);
            }
        }
    }

    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }

   
}

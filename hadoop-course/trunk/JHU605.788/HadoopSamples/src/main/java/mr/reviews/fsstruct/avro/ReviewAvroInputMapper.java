package mr.reviews.fsstruct.avro;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewWritable;

import org.apache.avro.mapred.AvroKey;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class ReviewAvroInputMapper extends Mapper<AvroKey<ReviewAvro>, NullWritable, ReviewKeyWritable, ReviewWritable> {

    private ReviewKeyWritable keyWritable = new ReviewKeyWritable();
    private ReviewWritable valueWritable = new ReviewWritable();
    private List<String> valuesToLookFor;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String value = context.getConfiguration().get(PROP_FIND_VALUE);
        Validate.notEmpty(value, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        valuesToLookFor = Arrays.asList(value.split(","));
    }

    @Override
    protected void map(AvroKey<ReviewAvro> key, NullWritable ignore, Context context) throws IOException, InterruptedException {
        ReviewAvro review = key.datum();
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, review.getText().toString())) {
                keyWritable.setKeyword(valueToLookFor);
                keyWritable.setAuthor(review.getUser().toString());
                populateValue(review);
                context.write(keyWritable, valueWritable);
            }
        }
    }

    private void populateValue(ReviewAvro review) {
        valueWritable.setAuthor(review.getUser().toString());
        valueWritable.setContent(review.getText().toString());
        valueWritable.setPostedTimestamp(review.getTimestamp());
    }

    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }

   
}

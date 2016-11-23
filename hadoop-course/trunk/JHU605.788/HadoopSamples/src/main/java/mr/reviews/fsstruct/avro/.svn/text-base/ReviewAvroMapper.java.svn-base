package mr.reviews.fsstruct.avro;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.commons.lang.Validate;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class ReviewAvroMapper extends Mapper<AvroKey<ReviewAvro>, NullWritable, AvroKey<ReviewKeyAvro>, AvroValue<ReviewReportAvro>> {

    private ReviewReportAvro report = new ReviewReportAvro();
    private ReviewKeyAvro reviewKey = new ReviewKeyAvro();
    
    private AvroKey<ReviewKeyAvro> key = new AvroKey<ReviewKeyAvro>();
    private AvroValue<ReviewReportAvro> value = new AvroValue<ReviewReportAvro>();
    private List<String> valuesToLookFor;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String value = context.getConfiguration().get(PROP_FIND_VALUE);
        Validate.notEmpty(value, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        valuesToLookFor = Arrays.asList(value.split(","));
    }

    @Override
    protected void map(AvroKey<ReviewAvro> keyIn, NullWritable ignore, Context context) throws IOException, InterruptedException {
        ReviewAvro review = keyIn.datum();
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, review.getText().toString())) {
                updateKey(review, valueToLookFor);
                updateValue(review, valueToLookFor);
                context.write(key, value);
            }
        }
    }

    private void updateValue(ReviewAvro review, String valueToLookFor) {
        report.setNumReviews(1);
        report.setKeyword(valueToLookFor);
        report.setReviews(Arrays.asList(review));
        report.setUser(review.getUser());
        value.datum(report);
    }

    private void updateKey(ReviewAvro review, String valueToLookFor) {
        reviewKey.setKeyword(valueToLookFor);
        reviewKey.setUser(review.getUser());
        key.datum(reviewKey);
    }

    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }

   
}

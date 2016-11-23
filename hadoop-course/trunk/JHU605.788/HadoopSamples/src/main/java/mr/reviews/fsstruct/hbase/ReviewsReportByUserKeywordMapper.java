package mr.reviews.fsstruct.hbase;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mr.reviews.model.ReviewKeyWritable;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

public class ReviewsReportByUserKeywordMapper extends TableMapper<ReviewKeyWritable, Result> {
    private List<String> valuesToLookFor;
    private ReviewKeyWritable keyWritable = new ReviewKeyWritable();
    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        String value = context.getConfiguration().get(PROP_FIND_VALUE);
        Validate.notEmpty(value, "You must provide value to find via [" + PROP_FIND_VALUE + "]");
        valuesToLookFor = Arrays.asList(value.split(","));
    }

    @Override
    public void map(ImmutableBytesWritable key, Result value, Context context) throws IOException,
            InterruptedException {
        byte[] txtAsBytes = value.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TEXT);

        String txt = Bytes.toString(txtAsBytes);
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, txt)) {
                byte[] usr = value.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_USER);
                keyWritable.setKeyword(valueToLookFor);
                keyWritable.setAuthor(Bytes.toString(usr));
                context.write(keyWritable, value);
            }
        }
    }

    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }
}

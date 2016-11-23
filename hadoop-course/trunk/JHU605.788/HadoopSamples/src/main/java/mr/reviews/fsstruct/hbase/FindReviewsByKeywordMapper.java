package mr.reviews.fsstruct.hbase;

import static mr.reviews.ReviewJob.PROP_FIND_VALUE;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.*;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_COLUMN_KEYWORD;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;

public class FindReviewsByKeywordMapper extends TableMapper<NullWritable, Writable> {
    private List<String> valuesToLookFor;
    
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
                byte[] timestamp = value.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TIMESTAMP);
                byte[] keywordsAsBytes = toBytes(valueToLookFor);
                byte [] keyUsr = Bytes.add(keywordsAsBytes, ReviewHBaseSchema.SPLIT, usr);
                byte [] uuid = toBytes(UUID.randomUUID().toString());
                Put put = new Put(Bytes.add(keyUsr, ReviewHBaseSchema.SPLIT, uuid));

                byte [] outFamily = REVIEW_REPORT_FAMILY_KEYWORDHITS;
                put.add(outFamily, REVIEW_REPORT_COLUMN_KEYWORD, keywordsAsBytes);
                put.add(outFamily, REVIEW_COLUMN_USER, usr);
                put.add(outFamily, REVIEW_COLUMN_TEXT, txtAsBytes);
                put.add(outFamily, REVIEW_COLUMN_TIMESTAMP, timestamp);
                context.write(NullWritable.get(), put);
            }
        }

    }

    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }
}

package crunch.reviews.hbase;

import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_COLUMN_KEYWORD;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_FAMILY_KEYWORDHITS;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.util.List;
import java.util.UUID;

import mr.reviews.fsstruct.hbase.ReviewHBaseSchema;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class FindReviewsHBaseDoFn extends DoFn<Result, Put> {
    private static final long serialVersionUID = 1L;

    private List<String> valuesToLookFor;
    public FindReviewsHBaseDoFn(List<String> valuesToLookFor){
        this.valuesToLookFor = valuesToLookFor;
    }
    
    @Override
    public void process(Result input, Emitter<Put> emitter) {
        byte[] txtAsBytes = input.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TEXT);

        String txt = Bytes.toString(txtAsBytes);
        for (String valueToLookFor : valuesToLookFor) {
            if (isValueIn(valueToLookFor, txt)) {
                emitResult(input, emitter, txtAsBytes, valueToLookFor);
            }
        }
    }
    
    private boolean isValueIn(String valueToLookFor, String content) {
        // this logic can be as intricate as it needs to be
        return content.contains(valueToLookFor);
    }

    private void emitResult(Result input, Emitter<Put> emitter, byte[] txtAsBytes, String valueToLookFor) {
        byte[] usr = input.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_USER);
        byte[] timestamp = input.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TIMESTAMP);
        byte[] keywordsAsBytes = toBytes(valueToLookFor);
        byte [] keyUsr = Bytes.add(keywordsAsBytes, ReviewHBaseSchema.SPLIT, usr);
        byte [] uuid = toBytes(UUID.randomUUID().toString());
        Put put = new Put(Bytes.add(keyUsr, ReviewHBaseSchema.SPLIT, uuid));

        byte [] outFamily = REVIEW_REPORT_FAMILY_KEYWORDHITS;
        put.add(outFamily, REVIEW_REPORT_COLUMN_KEYWORD, keywordsAsBytes);
        put.add(outFamily, REVIEW_COLUMN_USER, usr);
        put.add(outFamily, REVIEW_COLUMN_TEXT, txtAsBytes);
        put.add(outFamily, REVIEW_COLUMN_TIMESTAMP, timestamp);
        emitter.emit(put);
    }

    
}

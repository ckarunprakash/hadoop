package mr.reviews.fsstruct.hbase;

import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_COLUMN_KEYWORD;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_FAMILY_KEYWORDHITS;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mr.reviews.ReviewJob;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

public class FindReviewsByKeywordMapperTest {

    @Test
    public void test() throws IOException {
        ImmutableBytesWritable key = new ImmutableBytesWritable(Bytes.toBytes("key"));
        Result result = getResult(key);

        MapDriver<ImmutableBytesWritable, Result, NullWritable, Writable> mapD = 
                new MapDriver<ImmutableBytesWritable, Result, NullWritable, Writable>()
                    .withMapper(new FindReviewsByKeywordMapper())
                    .withInput(key, result);
        Configuration conf = mapD.getConfiguration();
        String keyword = "where";
        conf.set(ReviewJob.PROP_FIND_VALUE, keyword);

        List<Pair<NullWritable, Writable>> results = mapD.run();
        assertEquals(1, results.size());
        Put res = (Put)results.get(0).getSecond();
        assertTrue(Bytes.toString(res.getRow()).startsWith("where-user1"));
        assertColumnValue(REVIEW_REPORT_FAMILY_KEYWORDHITS, 
                REVIEW_REPORT_COLUMN_KEYWORD, keyword, res);
        assertColumnValue(REVIEW_REPORT_FAMILY_KEYWORDHITS, 
                REVIEW_COLUMN_USER, "user1", res);
        assertColumnValue(REVIEW_REPORT_FAMILY_KEYWORDHITS, 
                REVIEW_COLUMN_TEXT, "This is where the text is", res);
        assertColumnValue(REVIEW_REPORT_FAMILY_KEYWORDHITS, 
                REVIEW_COLUMN_TIMESTAMP, 479738973894l, res);
    }

    private Result getResult(ImmutableBytesWritable key) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new KeyValue(key.get(), 
                REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_USER, toBytes("user1")));
        keyValues.add(new KeyValue(key.get(), 
                REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TEXT, toBytes("This is where the text is")));
        keyValues.add(new KeyValue(key.get(), 
                REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TIMESTAMP, toBytes(479738973894l)));
        // Result objects get methods rely on KeyValues to be sored by family then qualifier
        Collections.sort(keyValues, new Comparator<KeyValue>() {
            @Override
            public int compare(KeyValue o1, KeyValue o2) {
                return Bytes.toString(o1.getQualifier())
                        .compareTo(Bytes.toString(o2.getQualifier()));
            }
        });
        Result result = new Result(keyValues);
        return result;
    }
    
    private void assertColumnValue(byte [] expectedFamily, byte [] expectedColumn, String expectedValue, Put res){
        KeyValue keyValue = getKeyVal(expectedFamily, expectedColumn, res);
        assertEquals( expectedValue, Bytes.toString(keyValue.getValue()));
    }
    private void assertColumnValue(byte [] expectedFamily, byte [] expectedColumn, long expectedValue, Put res){
        KeyValue keyValue = getKeyVal(expectedFamily, expectedColumn, res);
        assertEquals( expectedValue, Bytes.toLong(keyValue.getValue()));
    }
    private KeyValue getKeyVal(byte[] expectedFamily, byte[] expectedColumn, Put res) {
        List<KeyValue> keyVals = res.get(expectedFamily, expectedColumn);
        assertEquals("Expected only 1 key value for [" + 
                Bytes.toString(expectedFamily) + ":" + Bytes.toString(expectedColumn) + "]", 
                1, keyVals.size());
        KeyValue keyValue = keyVals.get(0);
        return keyValue;
    }

}

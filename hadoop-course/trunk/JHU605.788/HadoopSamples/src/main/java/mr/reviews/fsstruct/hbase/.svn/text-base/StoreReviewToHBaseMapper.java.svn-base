package mr.reviews.fsstruct.hbase;

import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import mr.reviews.fsstruct.support.Review;
import mr.reviews.fsstruct.support.XmlHelper;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

public class StoreReviewToHBaseMapper extends Mapper<NullWritable, BytesWritable, NullWritable, Writable> {
    private final XmlHelper helper = new XmlHelper();

    @Override
    public void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        Review review = helper.convert(value.copyBytes());
        Put put = new Put(toBytes(review.getTimestamp() + "_" + review.getUser()));
        put.add(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_USER, toBytes(review.getUser()));
        put.add(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TEXT, toBytes(review.getText()));
        put.add(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TIMESTAMP, toBytes(review.getTimestamp()));
        context.write(NullWritable.get(), put);
    }
}

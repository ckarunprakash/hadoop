package mr.reviews.fsstruct.hbase;

import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_COLUMN_NUMBER_OF_REVIEWS;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_FAMILY_HITSREPORT;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;

import mr.reviews.model.ReviewKeyWritable;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;

public class ReviewsReportByUserKeywordReducer extends TableReducer<ReviewKeyWritable, Result, NullWritable> {
    
    @Override
    public void reduce(ReviewKeyWritable key, Iterable<Result> reviews,
            Context context) throws IOException, InterruptedException {
        Put put = new Put(Bytes.add(toBytes(key.getKeyword()), 
                ReviewHBaseSchema.SPLIT, toBytes(key.getAuthor())));
        int reviewCount = 0;
        
        for (Result review : reviews){          
            put.add(REVIEW_REPORT_FAMILY_HITSREPORT, 
                    Bytes.add(REVIEW_COLUMN_TEXT, toBytes(reviewCount)), 
                    review.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TEXT));
            put.add(REVIEW_REPORT_FAMILY_HITSREPORT, 
                    Bytes.add(REVIEW_COLUMN_TIMESTAMP, toBytes(reviewCount)), 
                    review.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TIMESTAMP));
            reviewCount++;
        }
        put.add(REVIEW_REPORT_FAMILY_HITSREPORT, 
                REVIEW_COLUMN_USER, toBytes(key.getAuthor()));
        put.add(REVIEW_REPORT_FAMILY_HITSREPORT, 
                REVIEW_REPORT_COLUMN_NUMBER_OF_REVIEWS, toBytes(reviewCount));
        context.write(NullWritable.get(), put);
    }
    
}

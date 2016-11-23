package mr.reviews.fsstruct.hbase;

import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.ERROR_COLUMN_EXCEPTION;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.ERROR_FAMILY;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.ERROR_TABLE;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TEXT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_TIMESTAMP;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_COLUMN_USER;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_FAMILY_CONTENT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_COLUMN_NUMBER_OF_REVIEWS;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_FAMILY_HITSREPORT;
import static mr.reviews.fsstruct.hbase.ReviewHBaseSchema.REVIEW_REPORT_TABLE;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import mr.reviews.model.ReviewKeyWritable;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;

public class ReviewsReportViaHTablesReducer extends TableReducer<ReviewKeyWritable, Result, NullWritable> {
    private static String REPORT_COUNTER_GROUP = "REVIEWS";
    private HTable reportTable;
    private HTable errorTable;

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        reportTable = new HTable(context.getConfiguration(), REVIEW_REPORT_TABLE);
        errorTable = new HTable(context.getConfiguration(), ERROR_TABLE);
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        reportTable.close();
        errorTable.close();
    }

    @Override
    public void reduce(ReviewKeyWritable key, Iterable<Result> reviews, Context context) throws IOException,
            InterruptedException {
        try {
            saveReport(key, reviews);
            context.getCounter(REPORT_COUNTER_GROUP, "REPORTS").increment(1);
        } catch (Exception e) {
            context.getCounter(REPORT_COUNTER_GROUP, "ERRORS").increment(1);
            saveError(key, e);
        }
    }

    private void saveReport(ReviewKeyWritable key, Iterable<Result> reviews) throws IOException,
            InterruptedException {
        Put put = new Put(Bytes.add(toBytes(key.getKeyword()), ReviewHBaseSchema.SPLIT, toBytes(key.getAuthor())));
        int reviewCount = 0;
        for (Result review : reviews) {
            put.add(REVIEW_REPORT_FAMILY_HITSREPORT, Bytes.add(REVIEW_COLUMN_USER, toBytes(reviewCount)),
                    review.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_USER));
            put.add(REVIEW_REPORT_FAMILY_HITSREPORT, Bytes.add(REVIEW_COLUMN_TEXT, toBytes(reviewCount)),
                    review.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TEXT));
            put.add(REVIEW_REPORT_FAMILY_HITSREPORT, Bytes.add(REVIEW_COLUMN_TIMESTAMP, toBytes(reviewCount)),
                    review.getValue(REVIEW_FAMILY_CONTENT, REVIEW_COLUMN_TIMESTAMP));
            reviewCount++;
        }
        put.add(REVIEW_REPORT_FAMILY_HITSREPORT, REVIEW_REPORT_COLUMN_NUMBER_OF_REVIEWS, toBytes(reviewCount));
        reportTable.put(put);
    }

    private void saveError(ReviewKeyWritable key, Exception e) throws IOException {
        Put put = new Put(toBytes(UUID.randomUUID().toString()));
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        put.add(ERROR_FAMILY, ERROR_COLUMN_EXCEPTION, toBytes(sw.toString()));
        errorTable.put(put);
    }
}

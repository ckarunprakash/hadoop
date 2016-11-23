package mr.reviews;

import java.io.IOException;

import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewReport;
import mr.reviews.model.ReviewWritable;

import org.apache.hadoop.mapreduce.Reducer;

public class ReviewReducer extends Reducer<ReviewKeyWritable, ReviewWritable, ReviewKeyWritable, ReviewReport> {
    @Override
    protected void reduce(ReviewKeyWritable key, Iterable<ReviewWritable> reviews, Context context) throws IOException,
            InterruptedException {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Reviews that mention [" + key.getKeyword() + "] by [" + key.getAuthor() + "]\n");
        int numOfReviews = 0;
        for (ReviewWritable review : reviews){
            strBuilder.append("   " + review.getPostedTimestamp() + ":" + review.getContent() + "\n");
            numOfReviews++;
        }
        ReviewReport report = new ReviewReport(numOfReviews, key.getKeyword(), key.getAuthor(), strBuilder.toString());
        context.write(key, report);
    }
}

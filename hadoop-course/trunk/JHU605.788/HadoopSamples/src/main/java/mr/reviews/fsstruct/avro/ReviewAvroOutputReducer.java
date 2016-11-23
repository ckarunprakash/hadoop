package mr.reviews.fsstruct.avro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;
import mr.reviews.model.ReviewKeyWritable;
import mr.reviews.model.ReviewWritable;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class ReviewAvroOutputReducer extends Reducer<ReviewKeyWritable, ReviewWritable, AvroKey<ReviewReportAvro>, NullWritable> {
    
    private ReviewReportAvro report = new ReviewReportAvro();
    private AvroKey<ReviewReportAvro> outputKey = new AvroKey<ReviewReportAvro>();
    @Override
    protected void reduce(ReviewKeyWritable key, Iterable<ReviewWritable> incoming, Context context) throws IOException,
            InterruptedException {

        List<ReviewAvro> reviews = new ArrayList<ReviewAvro>();
        for (ReviewWritable review : incoming){
            reviews.add(convert(review));
        }
        report.setNumReviews(reviews.size());
        report.setKeyword(key.getKeyword());
        report.setUser(key.getAuthor());
        report.setReviews(reviews);
        
        outputKey.datum(report);
        context.write(outputKey, NullWritable.get());
    }
    private ReviewAvro convert(ReviewWritable review) {
        return ReviewAvro.newBuilder()
            .setUser(review.getAuthor())
            .setTimestamp(review.getPostedTimestamp())
            .setText(review.getContent())
            .build();
    }
}

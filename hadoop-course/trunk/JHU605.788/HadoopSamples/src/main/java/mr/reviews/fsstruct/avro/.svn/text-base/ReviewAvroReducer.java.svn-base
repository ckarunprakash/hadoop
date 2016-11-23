package mr.reviews.fsstruct.avro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mr.reviews.fsstruct.avro.model.ReviewAvro;
import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class ReviewAvroReducer extends
        Reducer<AvroKey<ReviewKeyAvro>, AvroValue<ReviewReportAvro>, AvroKey<ReviewReportAvro>, NullWritable> {
    private final ReviewReportAvro resultReport = new ReviewReportAvro();
    private AvroKey<ReviewReportAvro> outputKey = new AvroKey<ReviewReportAvro>();

    @Override
    protected void reduce(AvroKey<ReviewKeyAvro> key, Iterable<AvroValue<ReviewReportAvro>> incoming, Context context)
            throws IOException, InterruptedException {
        collect(key.datum(), resultReport, incoming);
        outputKey.datum(resultReport);
        context.write(outputKey, NullWritable.get());
    }

    public static void collect(ReviewKeyAvro key, ReviewReportAvro resultReport, Iterable<AvroValue<ReviewReportAvro>> incoming) {
        List<ReviewAvro> collected = new ArrayList<ReviewAvro>();
        for (AvroValue<ReviewReportAvro> avroVal : incoming) {
            collected.addAll(avroVal.datum().getReviews());
        }
        
        resultReport.setKeyword(key.getKeyword());
        resultReport.setUser(key.getUser());
        resultReport.setNumReviews(collected.size());
        resultReport.setReviews(collected);
    }
}

package mr.reviews.fsstruct.avro;

import java.io.IOException;

import mr.reviews.fsstruct.avro.model.ReviewKeyAvro;
import mr.reviews.fsstruct.avro.model.ReviewReportAvro;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.mapreduce.Reducer;

public class ReviewAvroCombiner extends Reducer<AvroKey<ReviewKeyAvro>, AvroValue<ReviewReportAvro>, AvroKey<ReviewKeyAvro>, AvroValue<ReviewReportAvro>> {
    private final ReviewReportAvro resultReport = new ReviewReportAvro();
    private AvroValue<ReviewReportAvro> outputVal = new AvroValue<ReviewReportAvro>();
    @Override
    protected void reduce(AvroKey<ReviewKeyAvro> key, Iterable<AvroValue<ReviewReportAvro>> incoming, Context context) throws IOException,
            InterruptedException {
        ReviewAvroReducer.collect(key.datum(), resultReport, incoming);
        outputVal.datum(resultReport);
        context.write(key, outputVal);
    }
}
